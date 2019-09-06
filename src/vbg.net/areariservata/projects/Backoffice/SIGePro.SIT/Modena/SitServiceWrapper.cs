using Init.SIGePro.Sit.Utils;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.SIGePro.Sit.Modena
{
    public class SitServiceWrapper
    {

        private static class Constants
        {
            public const string Servizio = "servizio";
            public const string Richiesta = "richiesta";
            public const string ValidazioneMappaliUrbanoService = "ValidazioneMappaliUrbanoService";
        }

        string _url;

        public SitServiceWrapper(string url)
        {
            this._url = url;
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
                using (var client = GetHttpClient())
                {
                    var nvc = new NameValueCollection();
                    nvc.Add(Constants.Servizio, nomeServizio);
                    nvc.Add(Constants.Richiesta, request);
                    var data = client.UploadValues(_url, "POST", nvc);

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
