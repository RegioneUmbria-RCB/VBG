using Init.SIGePro.Manager.Utils;
using Newtonsoft.Json;
using RestSharp;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class AuthenticationRestClient
    {
        private readonly string _endPointUrl;
        private readonly string _consumerKey;
        private readonly string _consumerSecret;
        private readonly ProtocolloLogs _logs;

        private string _accessToken;
        private DateTime? _tokenExpiration;

        public AuthenticationRestClient(string endPointUrl, string consumerKey, string consumerSecret, ProtocolloLogs logs)
        {
            this._endPointUrl = endPointUrl;
            this._consumerKey = consumerKey;
            this._consumerSecret = consumerSecret;
            this._logs = logs;
        }

        public String GetAuthToken()
        {
            if (!string.IsNullOrEmpty(_accessToken) && DateTime.Now < this._tokenExpiration)
                return this._accessToken;

            var response = GetToken();

            this._tokenExpiration = DateTime.Now.AddSeconds(response.ExpiresIn ?? 0);
            this._accessToken = response.AccessToken;
            return response.AccessToken;
        }

        public void RenewToken()
        {
            var response = GetToken();

            this._tokenExpiration = DateTime.Now.AddSeconds(response.ExpiresIn ?? 0);
            this._accessToken = response.AccessToken;
        }

        private AuthenticationRestClientResponse GetToken()
        {
            var authHeader = Base64Utils.Base64Encode($"{this._consumerKey}:{this._consumerSecret}");

            var request = new RestRequest();
            request.Method = Method.Post;

            request.AddHeader("Authorization", $"Basic {authHeader}");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            request.AddParameter("grant_type", "client_credentials");

            var client = new RestSharp.RestClient($"{this._endPointUrl}");

            var response = client.Execute(request);

            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                _logs.Info("AUTENTICAZIONE AVVENUTA CON SUCCESSO");
                // Deserializza il contenuto JSON nella classe AuthenticationRestClientResponse
                return JsonConvert.DeserializeObject<AuthenticationRestClientResponse>(response.Content);
            }
            else
            {
                throw new Exception($"ERRORE DURANTE IL RECUPERO DEL TOKEN, ERRORE: {response.ErrorMessage}, STATUS: {response.StatusCode}");
            }
        }
    }

    public class AuthenticationRestClientResponse
    {
        [JsonProperty(PropertyName = "access_token")]
        public string AccessToken { get; set; }

        [JsonProperty(PropertyName = "scope")]
        public string Scope { get; set; }

        [JsonProperty(PropertyName = "token_type")]
        public string TokenType { get; set; }

        [JsonProperty(PropertyName = "expires_in")]
        public int? ExpiresIn { get; set; }
    }
}
