using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Protocollo.ProtocolloFilesInsielService2;

namespace Init.SIGePro.Protocollo.Insiel2.Services
{
    public class AllegatiService : BaseService
    {
        Utente _utente;

        public AllegatiService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, string codiceUtente, string password) : base(url, logs, serializer)
        {
            _utente = new Utente { codice = codiceUtente, password = password };
        }

        private ProtocolloFilesPortTypeClient CreaWebService()
        {
            try
            {
                Logs.Debug("Creazione del webservice di upload allegati INSIEL");

                var endPointAddress = new EndpointAddress(Url);
                var binding = new BasicHttpBinding("insielUploadHttpBinding");

                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL_WSUPLOAD DELLA VERTICALIZZAZIONE PROTOCOLLO_INSIEL NON È STATO VALORIZZATO.");

                var ws = new ProtocolloFilesPortTypeClient(binding, endPointAddress);

                Logs.Debug("Fine creazione del web service di upload allegati INSIEL");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DI UPLOAD DEI FILES ALLEGATI", ex);
            }
        }

        internal AttachmentData DownloadDocumento(DownloadDocumentoRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    request.Utente = _utente;
                    Serializer.Serialize(ProtocolloLogsConstants.LeggiAllegatoRequest, request);
                    Logs.InfoFormat("RICHIESTA DOWNLOAD DEL DOCUMENTO ID {0}", request.idDoc);
                    var response = ws.downloadDocumento(request);
                    
                    if (!response.esito.Value)
                        throw new Exception(String.Format("CODICE {0}, DESCRIZIONE: {1}", response.Errore.codice, response.Errore.descrizione));

                    Logs.InfoFormat("DOWNLOAD DEL DOCUMENTO ID {0} AVVENUTA CORRETTAMENTE", request.idDoc);

                    return response.documento;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE IL DOWNLOAD DEL DOCUMENTO CON ID {0}, ERRORE: {1}", request.idDoc, ex.Message), ex);
                }
            }
        }

        internal UploadResponse Upload(UploadRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    request.codiceUtente = _utente.codice;
                    request.passwordUtente = _utente.password;

                    Logs.Info("Chiamata al web method upload del web service di Upload File");
                    var response = ws.upload(request);

                    if (!response.esito.Value)
                    {
                        var err = response.Errore;
                        throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", response.Errore.codice, response.Errore.descrizione));
                    }

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception("IL WEB SERVICE DI UPLOAD FILE HA RESTITUITO UN ERRORE", ex);
                }
            }
        }
    }
}
