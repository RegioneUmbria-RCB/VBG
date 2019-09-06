using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Microsoft.Web.Services2.Attachments;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK.Protocollazione
{
    public class ProtocollazioneServiceWrapper : BaseServiceWrapper
    {
        public ProtocollazioneServiceWrapper(string url, string connectionString, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(url, connectionString, logs, serializer)
        {
            
        }

        public long InserisciDocumento(ProtocolloAllegati allegato)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    string path = Path.Combine(Logs.Folder, allegato.NOMEFILE);
                    File.WriteAllBytes(path, allegato.OGGETTO);

                    var attach = new Attachment(allegato.NOMEFILE, allegato.MimeType, Path.Combine(Logs.Folder, allegato.NOMEFILE));
                    Logs.InfoFormat("INSERIMENTO DEL FILE CODICE: {0}, NOME: {1}", allegato.CODICEOGGETTO, allegato.NOMEFILE);
                    ws.RequestSoapContext.Attachments.Add(attach);
                    var response = ws.insertDocumento(ConnectionString, allegato.NOMEFILE, allegato.Descrizione);

                    if (response.lngErrNumber != 0)
                    {
                        Logs.WarnFormat("SI E' VERIFICATO UN ERRORE DURANTE L'INSERIMENTO DEL DOCUMENTO {0}, CODICE ERRORE: {1}, DESCRIZIONE: {2}", allegato.NOMEFILE, response.lngErrNumber, response.strErrString);
                        return -1;
                    }
                    else
                        Logs.InfoFormat("INSERIMENTO DEL FILE CODICE: {0}, NOME: {1} AVVENUTO CON SUCCESSO, ID RESTITUITO: {2}", allegato.CODICEOGGETTO, allegato.NOMEFILE, response.lngDocID);

                    //throw new Exception(String.Format("CODICE:{0}, DESCRIZIONE: {1}, FILE: {2}, CODICE OGGETTO: {3}", response.lngErrNumber.ToString(), response.strErrString, allegato.NOMEFILE, allegato.CODICEOGGETTO));

                    return response.lngDocID;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DURANTE L'INSERIMENTO DI UN DOCUMENTO, ERRORE: {0}", ex.Message), ex);
                }
            }
        }

        public void AllegaSegnatura(ProtocolloStudioKStub ws, Segnatura segnatura)
        {
            try
            {
                Serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura, Validation.ProtocolloValidation.TipiValidazione.STUDIOK_SEGNATURA);
                string pathSegnatura = Path.Combine(Logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName);
                var attachment = new Attachment(ProtocolloLogsConstants.SegnaturaXmlFileName, ProtocolloConstants.MIMETYPE_XML, pathSegnatura);
                Logs.Info("INVIO DEL FILE SEGNATURA.XML");
                ws.RequestSoapContext.Attachments.Add(attachment);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("CREAZIONE ATTACHMENT DELLA SEGNATURA NON AVVENUTO CORRETTAMENTE, {0}", ex.Message), ex);
            }
        }

        public ProtocollazioneRet Protocolla(Segnatura segnatura)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    AllegaSegnatura(ws, segnatura);
                    Logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE");
                    var response = ws.registraProtocollo(ConnectionString);
                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response.lngErrNumber != 0 || !String.IsNullOrEmpty(response.strErrString))
                        throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE: {1}", response.lngErrNumber, response.strErrString));

                    Logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, NUMERO: {0}, ANNO: {1}", response.lngNumPG.ToString(), response.lngAnnoPG.ToString());

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }
    }
}
