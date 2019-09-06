using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using System.IO;
using Init.SIGePro.Data;
using Microsoft.Web.Services2.Attachments;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Allegati;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Sicraweb.Services
{
    public class ProtocolloService
    {
        string _url;
        string _connectionString;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;

        public ProtocolloService(string url, string connectionString, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _url = url;
            _logs = logs;
            _serializer = serializer;
            _connectionString = connectionString;
        }

        private ProtocolloServiceProxy CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione Sicraweb");
                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_SICRAWEB NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new ProtocolloServiceProxy(_url);

                _logs.Debug("Fine creazione del webservice SICRAWEB");

                //ws.Pipeline.OutputFilters.Insert(0, new SicrawebFilters());

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }

        internal long InserisciDocumento(ProtocolloAllegati allegato)
        {
            using (var ws = CreaWebService())
            {
                try
                {

                    string path = Path.Combine(_logs.Folder, allegato.NOMEFILE);
                    File.WriteAllBytes(path, allegato.OGGETTO);

                    var attach = new Attachment(allegato.NOMEFILE, allegato.MimeType, Path.Combine(_logs.Folder, allegato.NOMEFILE));

                    ws.RequestSoapContext.Attachments.Add(attach);
                    var response = ws.insertDocumento(_connectionString, allegato.NOMEFILE, allegato.Descrizione);

                    if (response.lngErrNumber != 0)
                        throw new Exception(String.Format("CODICE:{0}, DESCRIZIONE: {1}, FILE: {2}, CODICE OGGETTO: {3}", response.lngErrNumber.ToString(), response.strErrString, allegato.NOMEFILE, allegato.CODICEOGGETTO));

                    return response.lngDocID;
                }
                catch (Exception ex)
                {
                    throw new Exception("ERRORE RESTITUITO DAL WEB SERVICE DURANTE L'INSERIMENTO DI UN DOCUMENTO", ex);
                }
            }

        }

        public LeggiProtocollo.Segnatura.Segnatura LeggiProtocollo(long numeroProtocollo, long annoProtocollo, string aoo)
        {
            string response = "";

            using (var ws = CreaWebService())
            {
                try
                {
                    _logs.InfoFormat("CHIAMATA A LEGGIPROTOCOLLO (infoProtocollo), CONNECTIONSTRING: {0}, NUMEROPROTOCOLLO: {1}, ANNOPROTOCOLLO: {2}, AOO: {3}", _connectionString, numeroProtocollo, annoProtocollo, aoo);
                    response = ws.infoProtocollo(_connectionString, numeroProtocollo, annoProtocollo, aoo);
                    _logs.DebugFormat("STRINGA XML RESTITUITA DA LEGGIPROTOCOLLO (infoProtocollo): {0}", response);
                }
                catch (Exception ex)
                {
                    throw new Exception("ERRORE RESTITUITO DAL WEB SERVICE", ex);
                }
            }

            try
            {
                var segnaturaResponse = (LeggiProtocollo.Segnatura.Segnatura)_serializer.Deserialize(response, typeof(LeggiProtocollo.Segnatura.Segnatura));
                return segnaturaResponse;
            }
            catch (Exception ex)
            {
                throw new Exception("DESERIALIZZAZIONE DELLA STRINGA XML OTTENUTA DA LEGGI PROTOCOLLO NON AVVENUTA CORRETTAMENTE", ex);
            }
        }

        public void AllegaSegnatura(ProtocolloServiceProxy ws, Segnatura segnatura)
        {
            try
            {
                _logs.Info("SERIALIZZAZIONE DELLA SEGNATURA");
                _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura);
                string pathSegnatura = Path.Combine(_logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName);

                var attachment = new Attachment(ProtocolloLogsConstants.SegnaturaXmlFileName, ProtocolloConstants.MIMETYPE_XML, pathSegnatura);

                _logs.Info("Creato Attachment del file segnatura.xml");
                ws.RequestSoapContext.Attachments.Add(attachment);
            }
            catch (Exception ex)
            {
                throw new Exception("CREAZIONE ATTACHMENT DELLA SEGNATURA NON AVVENUTO CORRETTAMENTE", ex);
            }
        }

        public ProtocollazioneRet Protocolla(Segnatura segnatura, string connectionString)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    AllegaSegnatura(ws, segnatura);
                    _logs.InfoFormat("Chiamata a registraProtocollo, connectionString {0}", connectionString);
                    var response = ws.registraProtocollo(connectionString);
                    
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response.lngErrNumber != 0 || !String.IsNullOrEmpty(response.strErrString))
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));

                    _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, numero protocollo: {0}, anno protocollo: {1}", response.lngNumPG.ToString(), response.lngAnnoPG.ToString());
                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception("ERRORE RESTITUITO DAL WEB SERVICE", ex);
                }
            }
        }
    }
}
