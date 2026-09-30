using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Parma
{
    public class AnagrafeServiceWrapper
    {
        private class Constants
        {
            public const string MetodoGetAnagrafica = "anagrafe";
            public const string MetodoVariazioni = "variazioni";
            public const string From = "from";
            public const string To = "to";
        }

        private readonly string _username;
        private readonly string _password;
        private readonly string _urlBase;

        public AnagrafeServiceWrapper(string username, string password, string urlBase)
        {
            this._username = username;
            this._password = password;
            this._urlBase = urlBase;
        }

        private WebClient GetHttpClient()
        {
            var client = new WebClient();

            var credentials = this._username + ":" + this._password;
            string authInfo = Convert.ToBase64String(Encoding.UTF8.GetBytes(credentials));
            client.Headers.Add(HttpRequestHeader.Authorization, "Basic " + authInfo);
            client.Headers.Remove(HttpRequestHeader.Expect);
            client.Credentials = new NetworkCredential(this._username, this._password);

            return client;
        }

        public AnagrafeResponse GetAnagrafica(string codiceFiscale)
        {
            var endPoint = $"{this._urlBase}/{Constants.MetodoGetAnagrafica}/{codiceFiscale}";

            using (var client = this.GetHttpClient())
            {
                var response = client.DownloadString(endPoint);
                var result = JsonConvert.DeserializeObject<AnagrafeResponse>(response);
                return result;
            }
        }

        public IEnumerable<VariazioniResponse> GetVariazioni(DateTime from, DateTime to)
        {
            var endPoint = $"{this._urlBase}/{Constants.MetodoVariazioni}";

            using (var client = this.GetHttpClient())
            {
                client.QueryString.Add(Constants.From, from.ToString("yyyyMMdd"));
                client.QueryString.Add(Constants.To, to.ToString("yyyyMMdd"));

                var response = client.DownloadString(endPoint);
                var result = JsonConvert.DeserializeObject<IEnumerable<VariazioniResponse>>(response);
                return result;
            }
        }
    }
}
