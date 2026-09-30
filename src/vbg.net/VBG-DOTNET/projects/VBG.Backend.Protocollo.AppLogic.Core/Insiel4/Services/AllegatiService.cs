using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Exceptions;
using System.Text.Json;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services
{
    public class AllegatiService : BaseService
    {
        private static class Constants
        {
            public const string UPLOAD_FILE = "upload-file";
        }

        public AllegatiService(ParametriRegoleInfo par, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(par, logs, Protocollazione.Enum.ContentType.multipart)
        {

        }

        internal UploadFileResponse UploadFile(UploadFileRequest request, string codiceAllegato, string nomeFile, int idxTentativo)
        {
            var MAX_ATTEMPT = 4;

            try
            {
                var rr = InitRequest(Constants.UPLOAD_FILE, request);

                Logs.Info($"Chiamata al web service {Constants.UPLOAD_FILE}, codice allegato: {codiceAllegato}, {nomeFile}, tentativo n.: {idxTentativo}");
                var response = ExecuteRequest(rr);

                if (response.IsSuccessful)
                {
                    var uploadFileResponse = JsonSerializer.Deserialize<UploadFileResponse>(response.Content);

                    Logs.Info("CARICAMENTO FILE AVVENUTO CORRETTAMENTE");
                    return uploadFileResponse;
                }
                else
                {
                    Logs.Info($"IL TENTAVIVO DI UPLOAD N. {idxTentativo} HA GENERATO ERRORE, STATUS CODE: {response.StatusCode}, STATUS DESCRIPTION: {response.StatusDescription}; ERROR MESSAGE: {response.ErrorMessage}");
                    Logs.Info($"RESPONSE CONTENT: {response.Content}");

                    if (idxTentativo < MAX_ATTEMPT)
                    {
                        var delay = idxTentativo * 2000;
                        Thread.Sleep(delay);
                        return UploadFile(request, codiceAllegato, nomeFile, idxTentativo + 1);
                    }
                    else
                    {
                        throw new UploadFileException(
                            $"Errore upload file dopo {idxTentativo} tentativi",
                            (int?)response.StatusCode,
                            response.StatusDescription,
                            response.ErrorMessage,
                            response.Content
                        );
                    }
                }
            }
            catch (Exception ex)
            {
                throw new UploadFileException(
                    $"Errore nel servizio {Constants.UPLOAD_FILE}",
                    null,
                    null,
                    null,
                    null,
                    ex
                );
            }
        }

        // TODO da implementare: verificare se serve perchè la request di DownloadDocumento adesso è di tipo JSON e si trova sul file ProtocolloService.cs

        //internal DownloadFileType Download(string idFile)
        //{
        //    using (var ws = CreaWebService())
        //    {
        //        try
        //        {
        //            var request = new DownloadFileRequest { idFile = idFile };

        //            Logs.InfoFormat("RICHIESTA DI DOWNLOAD FILE ID: {0}", request.idFile);
        //            var response = ws.downloadFile(request);

        //            if (!response.esito)
        //            {
        //                var err = (ErroreType)response.Item;
        //                throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
        //            }

        //            Logs.InfoFormat("DOWNLOAD FILE ID: {0} DA WS EFFETTUATO CORRETTAMENTE", request.idFile);

        //            return (DownloadFileType)response.Item;
        //        }
        //        catch (Exception ex)
        //        {
        //            throw new Exception(String.Format("IL DOWNLOAD DEL FILE DA WS HA RESTITUITO IL SEGUENTE ERRORE: {0}", ex.Message), ex);
        //        }
        //    }
        //}
    }
}
