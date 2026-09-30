using System;
using System.Collections.Specialized;
using System.Net;
using System.Text;
using VBG.Backend.SIT.AppLogic.Utils;

namespace VBG.Backend.SIT.AppLogic.Modena
{
    public class SitServiceWrapper
    {

        private static class Constants
        {
            public const string Servizio = "servizio";
            public const string Richiesta = "richiesta";
            public const string ValidazioneMappaliUrbanoService = "ValidazioneMappaliUrbanoService";
        }

        private readonly string _url;

        public SitServiceWrapper(string url)
        {
            this._url = url;

            if (String.IsNullOrEmpty(this._url))
            {
                throw new Exception("Parametro di configurazione URL_WS_CATASTO non impostato, Verificare la configurazione della verticalizzazione");
            }
        }

        private WebClient GetHttpClient()
        {
            var client = new WebClient();
            client.Headers.Add(HttpRequestHeader.ContentType, "application/x-www-form-urlencoded");
            return client;
        }

        public T GetDati<T>(string request, string nomeServizio)
        {
            try
            {
                using (var client = this.GetHttpClient())
                {
                    var nvc = new NameValueCollection();
                    nvc.Add(Constants.Servizio, nomeServizio);
                    nvc.Add(Constants.Richiesta, request);
                    var data = client.UploadValues(this._url, "POST", nvc);

                    var retVal = SerializationExtensions.XmlDeserializeFromString<T>(Encoding.UTF8.GetString(data));

                    return retVal;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Errore nella chiamata al servizio {ex.Message}");
            }
        }
    }
}
