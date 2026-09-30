using log4net;
using RestSharp;
using System;
using System.Text.Json;

namespace VBG.Backend.SIT.AppLogic.Genova.Authentication
{
    public interface IWso2Authenticator
    {
        GenovaGetTokenResponse GetToken();
    }

    public class GenovaWSO2Authenticator : IWso2Authenticator
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(GenovaWSO2Authenticator));
        private readonly Verticalizzazioni.VerticalizzazioneSitGenova _verticalizzazione;

        public GenovaWSO2Authenticator(Verticalizzazioni.VerticalizzazioneSitGenova verticalizzazione)
        {
            this._verticalizzazione = verticalizzazione;
        }
        public GenovaGetTokenResponse GetToken()
        {
            var wso2Url = this._verticalizzazione.Wso2BaseUrl;
            var wso2Key = this._verticalizzazione.Wso2Key;
            var wso2Secret = this._verticalizzazione.Wso2Secret;

            var options = new RestClientOptions(wso2Url);
            var client = new RestClient(options);

            var requestBody = new GenovaGetTokenRequest
            {
                Key = wso2Key,
                Secret = wso2Secret
            };

            var request = new RestRequest("get-token", Method.Post);
            request.AddHeader("Content-Type", "application/json");
            request.AddBody(JsonSerializer.Serialize(requestBody));

            var result = client.Execute(request);

            if (!(result?.IsSuccessful ?? false))
            {
                this._log.DebugFormat("Errore durante la lettura del token di autenticazione: {0}, {1}", result?.StatusCode, result?.Content);

                throw new Exception("Impossibile ottenere il token da WSO2");
            }

            var tokenInfo = JsonSerializer.Deserialize<GenovaGetTokenResponse>(result.Content);

            if (tokenInfo == null)
            {
                this._log.DebugFormat("Errore durante la deserializzazione del token di autenticazione: {0}", result.Content);

                throw new Exception("Impossibile ottenere il token da WSO2");
            }

            return tokenInfo;

        }
    }
}
