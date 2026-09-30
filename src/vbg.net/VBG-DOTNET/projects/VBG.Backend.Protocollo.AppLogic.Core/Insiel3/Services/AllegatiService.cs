using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloInsiel3FilesTransferService;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services
{
    public class AllegatiService : BaseService
    {
        private readonly ClientAllegatiServiceCreator _clientAllegatiServiceerviceCreator;

        public AllegatiService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory) : base(url, logs, serializer)
        {
            this._clientAllegatiServiceerviceCreator = new ClientAllegatiServiceCreator(logs, bindingFactory, url);
        }
        internal UploadFileResponse Upload(UploadFileRequest request, string codiceAllegato)
        {
            using (var ws = this._clientAllegatiServiceerviceCreator.CreateClient())
            {
                try
                {
                    Serializer.LogAndValidate(String.Format("{0}_{1}", codiceAllegato, ProtocolloLogsConstants.AllegatoRequestFileName), request);

                    Logs.InfoFormat("RICHIESTA DI UPLOAD DEL FILE TRAMITE WS, CODICE ALLEGATO: {0}", codiceAllegato);
                    var response = ws.Service.uploadFile(request);

                    Serializer.LogAndValidate(String.Format("{0}_{1}", codiceAllegato, ProtocolloLogsConstants.AllegatoResponseFileName), response);

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
            using (var ws = this._clientAllegatiServiceerviceCreator.CreateClient())
            {
                try
                {
                    var request = new DownloadFileRequest { idFile = idFile };

                    Logs.InfoFormat("RICHIESTA DI DOWNLOAD FILE ID: {0}", request.idFile);
                    var response = ws.Service.downloadFile(request);

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
