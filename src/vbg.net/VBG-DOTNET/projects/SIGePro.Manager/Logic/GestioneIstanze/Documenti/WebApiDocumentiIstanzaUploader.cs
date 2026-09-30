using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Authentication;
using log4net;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneIstanze.Documenti
{

    public class WebApiDocumentiIstanzaUploader : IDocumentiIstanzaUploader
    {
        private readonly DocumentiIstanzaRestUrl _webApiUrl;
        private readonly IResolveToken _tokenResolver;
        private readonly ILog _log = LogManager.GetLogger(typeof(WebApiDocumentiIstanzaUploader));

        public class UploadFileResponse
        {
            public static UploadFileResponse FromResponseString(string postResponse)
            {
                return JsonConvert.DeserializeObject<UploadFileResponse>(postResponse);
            }

            public int CodiceOggetto { get; set; }
            public string ResponseString { get; private set; }

            internal static UploadFileResponse FromResponse(WebResponse webResponse)
            {
                using (Stream stream = webResponse.GetResponseStream())
                {
                    var reader = new StreamReader(stream);

                    var responseString = reader.ReadToEnd();

                    var obj = JsonConvert.DeserializeObject<UploadFileResponse>(responseString);
                    obj.ResponseString = responseString;

                    return obj;
                }
            }
        }

        public WebApiDocumentiIstanzaUploader(DocumentiIstanzaRestUrl webApiUrl, IResolveToken tokenResolver)
        {
            _webApiUrl = webApiUrl;
            _tokenResolver = tokenResolver;
        }


        public int Upload(int codiceIstanza, DescrittoreFile descrittore, byte[] fileContent)
        {
            var jsonFile = new UploadingFileData
            {
                Name = "parametri.json",
                Data = Encoding.UTF8.GetBytes(descrittore.ToJsonString()),
                Mime = "application/json"
            };


            var blobFile = new UploadingFileData
            {
                Name = "documento.blob",
                Data = fileContent,
                Mime = descrittore.Mime
            };

            return this.UploadFile(codiceIstanza, jsonFile, blobFile);

        }


        private int UploadFile(int codiceIstanza, UploadingFileData jsonFile, UploadingFileData blobFile)
        {
            var url = this._webApiUrl.GetUploadUrl(codiceIstanza);

            this._log.Debug($"Invio dei files {jsonFile.Name} e {blobFile.Name} tramite richiesta REST all'indirizzo {url}");

            HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
            httpWebRequest.Method = "POST";
            httpWebRequest.KeepAlive = true;
            httpWebRequest.Timeout = 60000;
            httpWebRequest.ReadWriteTimeout = 60000;
            //httpWebRequest.AllowWriteStreamBuffering = true;
            httpWebRequest.Credentials = System.Net.CredentialCache.DefaultCredentials;
            httpWebRequest.Headers.Add("Authorization", this._tokenResolver.Token);

            try
            {
                using (var webWriter = new WebRequestAttachmentWriter(httpWebRequest))
                {
                    webWriter.WriteFiles(new[] { jsonFile, blobFile });

                    var result = UploadFileResponse.FromResponse(httpWebRequest.GetResponse());

                    this._log.Debug($"Invio riuscito, codiceoggetto={result.CodiceOggetto}");

                    return result.CodiceOggetto;
                }
            }
            catch (Exception ex)
            {
                this._log.Error($"Si è verificato un errore durante l'invio dei files: {ex.ToString()}");
                throw;
            }

        }
    }
}
