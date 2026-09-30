using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace Init.SIGePro.Manager.Logic.GestioneIstanze.Movimenti
{
    public class JsonClient
    {
        private readonly Authorization _auth;
        private readonly string _url;

        public JsonClient(Authorization auth, string url)
        {
            if (string.IsNullOrEmpty(url))
            {
                throw new ArgumentException($"'{nameof(url)}' non può essere Null o vuoto", nameof(url));
            }

            this._auth = auth ?? throw new ArgumentNullException(nameof(auth));
            this._url = url;
        }

        public MovimentoRestBeanResponse InserisciMovimento(MovimentoRestBean request)
        {
            try
            {
                using (var client = GetHttpClient())
                {
                    var data = client.UploadString(_url, "POST", JsonConvert.SerializeObject(request));

                    return JsonConvert.DeserializeObject<MovimentoRestBeanResponse>(data);
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Errore nella chiamata al servizio {ex.Message}");
            }
        }

        private WebClient GetHttpClient()
        {
            var client = new WebClient();
            client.Headers.Add(HttpRequestHeader.Authorization, Convert.ToBase64String(Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(this._auth))));
            client.Headers.Add(HttpRequestHeader.ContentType, "application/json");
            return client;
        }
    }
}
