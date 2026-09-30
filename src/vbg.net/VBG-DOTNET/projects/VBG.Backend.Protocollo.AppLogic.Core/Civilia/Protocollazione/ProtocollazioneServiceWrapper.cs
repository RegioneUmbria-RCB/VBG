using Init.SIGePro.Manager.Utils;
using Newtonsoft.Json;
using System.Net;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Civilia.Protocollazione
{
    public class ProtocollazioneServiceWrapper
    {
        private class OAuthConstants
        {
            public const string ClientIdParameter = "client_id";
            public const string SecretParameter = "client_secret";
            public const string GrantTypeParameter = "grant_type";
            public const string GrantTypeValue = "client_credentials";
        }

        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;

        string _url;
        string _clientId;
        string _secret;
        string _urlOAuth;
        string _urlWrapperServiceOauth;
        string _urlResource;
        HttpVerb _tipoRichiesta;

        public ProtocollazioneServiceWrapper(ProtocolloInfo info, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            this._url = info.ParametriRegola.UrlWs;
            this._logs = logs;
            this._serializer = serializer;
            this._clientId = info.ParametriRegola.ClientId;
            this._secret = info.ParametriRegola.Secret;
            this._urlOAuth = info.ParametriRegola.UrlOAuth;
            this._urlWrapperServiceOauth = info.ParametriRegola.UrlWrapperServiceOAuth;
            this._urlResource = info.ParametriRegola.UrlWsResource;
            this._tipoRichiesta = info.ParametriRegola.TipoRichiesta;
        }

        public string GetTokenOAuth2()
        {
            try
            {
                var authRequest = new AutenticazioneRequestJSon
                {
                    AuthContextURL = this._urlOAuth,
                    ClientID = this._clientId,
                    ResourceUrlWs = this._urlResource,
                    Secret = this._secret
                };

                var jsonRequest = JsonConvert.SerializeObject(authRequest);
                this._logs.InfoFormat("REQUEST JSON DI AUTENTICAZIONE: {0}", jsonRequest);

                System.Net.ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
                var client = new RestClient
                {
                    EndPoint = this._urlWrapperServiceOauth,
                    Method = HttpVerb.POST,
                    PostData = jsonRequest,
                    ContentType = "application/json"
                };

                this._logs.Info("RICHIESTA DEL TOKEN");
                var jsonResponse = client.MakeRequest();
                var response = JsonConvert.DeserializeObject<OAuth2Response>(jsonResponse);

                this._logs.InfoFormat("TOKEN RESTITUITO: {0}", response.Token);

                return response.Token;
            }
            catch (Exception ex)
            {
                throw new Exception($"PROBLEMI DURANTE L'AUTENTICAZIONE OAUTH 2.0, {ex.Message}", ex);
            }
        }

        public ProtocolloResult Protocolla(PraticaWS jsonPostData, string token, string encoding)
        {
            try
            {
                _logs.Info("SERIALIZZAZIONE DELL'OGGETTO REQUEST");
                var jsonRequest = JsonConvert.SerializeObject(jsonPostData);
                //_logs.InfoFormat("SERIALIZZAZIONE DELL'OGGETTO REQUEST AVVENUTA CORRETTAMENTE, {0}", jsonRequest);

                var header = new WebHeaderCollection();
                header.Add("Authorization", $"Bearer {token}");

                _logs.Info("CREAZIONE DEL REST CLIENT");
                System.Net.ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
                var client = new RestClient
                {
                    EndPoint = _url,
                    Method = this._tipoRichiesta,
                    PostData = jsonRequest,
                    Headers = header,
                    ContentType = "application/json"
                };

                if (!String.IsNullOrEmpty(encoding))
                {
                    client.EncodingName = encoding;
                }

                _logs.Info("CREAZIONE DEL REST CLIENT AVVENUTA CORRETTAMENTE");

                _logs.Info("CHIAMATA A PROTOCOLLAZIONE");

                var jsonResponse = client.MakeRequest();
                _logs.Info("CHIAMATA A PROTOCOLLAZIONE TERMINATA");

                _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA");
                var response = JsonConvert.DeserializeObject<ProtocolloResult>(jsonResponse);
                _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA AVVENUTA CORRETTAMENTE");

                if (response.ResultType != 1)
                {
                    throw new Exception(response.ResultDescription);
                }

                _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE, PROTOCOLLO NUMERO: {0}, DATA: {1}, ID: {2}", response.Result.NumeroProtocollo, response.Result.DataRegistrazione.Value.ToString("dd/MM/yyyy"), response.Result.Id);

                return response;
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA CHIAMATA A PROTOCOLLAZIONE, {ex.Message}");
            }
        }
    }
}
