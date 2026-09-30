using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
//using Init.SIGePro.Protocollo.ProtocolloEGrammataNuovaUD;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel.Description;
using System.ServiceModel;
using Init.SIGePro.Protocollo.EGrammata.Proxy.NuovaUD;
using Microsoft.Web.Services2.Attachments;
using Init.SIGePro.Data;
using System.IO;

namespace Init.SIGePro.Protocollo.EGrammata.Services
{
    internal class EGrammataProtocollazioneService : BaseEGrammataService
    {
        public EGrammataProtocollazioneService(string endPointAddress, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(endPointAddress, logs, serializer)
        {
            _endPointAddress = endPointAddress;
            _logs = logs;
            _serializer = serializer;
        }

        private WSNuovaUDService CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione E-Grammata");

                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_EGRAMMATA NON È STATO VALORIZZATO.");

                var ws = new WSNuovaUDService(_endPointAddress);

                _logs.Debug("Fine creazione del web service di protocollazione E-GRAMMATA");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }

        internal void InserisciAllegati(List<ProtocolloAllegati> listAllegati)
        {
            try
            {
                using (var client = CreaWebService())
                {
                    listAllegati.ForEach(x => 
                    {
                        string path = Path.Combine(_logs.Folder, x.NOMEFILE);
                        File.WriteAllBytes(path, x.OGGETTO);
                        _logs.InfoFormat("SALVATO IL FILE {0}, CODICE ALLEGATO {1}", x.NOMEFILE, x.CODICEOGGETTO);
                        var attachment = new Attachment(x.NOMEFILE, x.MimeType, path);
                        client.RequestSoapContext.Attachments.Add(attachment);
                        _logs.InfoFormat("File {0}, codice {1} inviato come attachment alla richiesta soap", x.NOMEFILE, x.CODICEOGGETTO);
                    });
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE L'INSERIMENTO DI UN ALLEGATO", ex);
            }
        }

        internal string Protocollazione(string userId, string password, string idUnita, string livelloUnita, string segnaturaXml)
        {
            string response = "";
            try
            {
                using (var client = CreaWebService())
                {
                    _logs.InfoFormat("SEGNATURA: {0}", segnaturaXml);
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE (METODO SERVICE), USERID: {0}, PASSWORD: {1}, IDUNITA: {2}, LIVELLOUNITA: {3}, SEGNATURA: {4}", userId, password, idUnita, livelloUnita, ProtocolloLogsConstants.SegnaturaXmlFileName);
                    response = client.service(userId, password, idUnita, livelloUnita, segnaturaXml);
                    _logs.InfoFormat("RISPOSTA DAL WEB SERVICE IN BASE64: {0}", response);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }
    }
}
