using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Protocollo.ProtocolloInsiel3FilesTransferService;

namespace Init.SIGePro.Protocollo.Insiel3.Services
{
    public class AllegatiService : BaseService
    {
        public AllegatiService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(url, logs, serializer)
        {

        }

        private FileTransferPTClient CreaWebService()
        {
            try
            {
                Logs.Debug("Creazione del webservice di upload allegati INSIEL");

                var endPointAddress = new EndpointAddress(Url);
                var binding = new BasicHttpBinding("insielUploadHttpBinding");

                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL_WSUPLOAD DELLA VERTICALIZZAZIONE PROTOCOLLO_INSIEL NON È STATO VALORIZZATO.");

                var ws = new FileTransferPTClient(binding, endPointAddress);

                Logs.Debug("Fine creazione del web service di upload allegati INSIEL");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DI UPLOAD DEI FILES ALLEGATI, ERRORE: {0}", ex.Message), ex);
            }
        }

        internal UploadFileResponse Upload(UploadFileRequest request, string codiceAllegato)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    Serializer.Serialize(String.Format("{0}_{1}", codiceAllegato, ProtocolloLogsConstants.AllegatoRequestFileName), request);

                    Logs.InfoFormat("RICHIESTA DI UPLOAD DEL FILE TRAMITE WS, CODICE ALLEGATO: {0}", codiceAllegato);
                    var response = ws.uploadFile(request);

                    Serializer.Serialize(String.Format("{0}_{1}", codiceAllegato, ProtocolloLogsConstants.AllegatoResponseFileName), response);

                    if (!response.esito)
                    {
                        var err = (ErroreType)response.Item;
                        throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }

                    Logs.InfoFormat("UPLOAD FILE ID: {0} DA WS EFFETTUATO CORRETTAMENTE", codiceAllegato);

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL WEB SERVICE DI UPLOAD FILE HA RESTITUITO IL SEGUENTE ERRORE: {0}", ex.Message), ex);
                }
            }
        }

        internal DownloadFileType Download(string idFile)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    var request = new DownloadFileRequest { idFile = idFile };

                    Logs.InfoFormat("RICHIESTA DI DOWNLOAD FILE ID: {0}", request.idFile);
                    var response = ws.downloadFile(request);

                    if (!response.esito)
                    {
                        var err = (ErroreType)response.Item;
                        throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }

                    Logs.InfoFormat("DOWNLOAD FILE ID: {0} DA WS EFFETTUATO CORRETTAMENTE", request.idFile);

                    return (DownloadFileType)response.Item;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL DOWNLOAD DEL FILE DA WS HA RESTITUITO IL SEGUENTE ERRORE: {0}", ex.Message), ex);
                }
            }
        }
    }
}
