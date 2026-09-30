using log4net;
using RestSharp;
using System;
using System.Text;
using System.Text.Json;
using VBG.Backend.SIT.Verticalizzazioni;

namespace VBG.Backend.SIT.AppLogic.Jesi
{
    public class AuthenticationRestClient
    {
        private readonly string _endPointUrl;
        private readonly string _consumerKey;
        private readonly string _consumerSecret;
        private readonly string _alias;
        private readonly string _software;
        private readonly string _nomeVerticalizzazione;
        private readonly string _passwordParameterName;

        private string _accessToken;
        private DateTime? _tokenExpiration;

        private readonly ILog _log = LogManager.GetLogger(typeof(AuthenticationRestClient));

        public AuthenticationRestClient(VerticalizzazioneSitJesi verticalizzazioneSitJesi)
        {
            this._endPointUrl = verticalizzazioneSitJesi.UrlWsBase;
            this._consumerKey = verticalizzazioneSitJesi.Username;
            this._consumerSecret = verticalizzazioneSitJesi.Password;
            this._alias = verticalizzazioneSitJesi.Alias;
            this._software = verticalizzazioneSitJesi.Software;
            this._nomeVerticalizzazione = verticalizzazioneSitJesi.NomeVerticalizzazione;
            this._passwordParameterName = verticalizzazioneSitJesi.PasswordParameterName;
        }

        public String GetAuthToken()
        {
            if (!string.IsNullOrEmpty(this._accessToken) && DateTime.Now < this._tokenExpiration)
                return this._accessToken;

            var response = this.GetToken();

            this._tokenExpiration = DateTime.Now.AddSeconds(Convert.ToInt32(response.HxSucc[1].ToString()));
            this._accessToken = response.HxSucc[0].ToString();
            return this._accessToken;
        }

        public void SetTokenExpired()
        {
            this._tokenExpiration = null;
            this._accessToken = null;
        }

        private JesiAuthenticationRestClientResponse GetToken()
        {
            var authHeader = Convert.ToBase64String(Encoding.UTF8.GetBytes($"{this._consumerKey}:{this._consumerSecret}"));

            var request = new RestRequest(this._endPointUrl, Method.Post);

            request.AddHeader("Authorization", $"Basic {authHeader}");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            request.AddParameter("grant_type", "client_credentials");

            var options = new RestClientOptions(this._endPointUrl)
            {
                RemoteCertificateValidationCallback = (sender, certificate, chain, sslPolicyErrors) => true,
            };

            var client = new RestClient(options);
            System.Net.ServicePointManager.SecurityProtocol = System.Net.SecurityProtocolType.Tls12;

            var response = client.Execute(request);

            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                return JsonSerializer.Deserialize<JesiAuthenticationRestClientResponse>(response.Content);
            }
            else
            {
                var respErr = JsonSerializer.Deserialize<JesiAuthenticationRestClientResponse>(response.Content);

                if (respErr.ErrorResponse.Code == "hx_00011") // "Account sospeso"
                {
                    return this.RegeneratePassword();
                }
                else
                {
                    throw new Exception(respErr.ErrorResponse.Message);
                }
            }
        }

        //genera una nuova password randomica, chiama il servizio per il rinnovo password, salva la nuova password nel db
        private JesiAuthenticationRestClientResponse RegeneratePassword()
        {
            throw new NotImplementedException("La vecchia password è scaduta ma la rigenerazione della password non è supportata");

            /*
            string pwd = PasswordGenerator.GeneratePassword();
            string pwdSha1 = PasswordHasher.HashPasswordSHA1(pwd);

            this._log.InfoFormat("hash della nuova password generata: {0}", pwdSha1);

            var authHeader = Init.SIGePro.Manager.Utils.Base64Utils.Base64Encode($"{this._consumerKey}:{this._consumerSecret}");
            var request = new RestRequest(Method.POST);

            request.AddHeader("Authorization", $"Basic {authHeader}");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            request.AddParameter("grant_type", "client_credentials");
            request.AddHeader("Nuova", pwdSha1);

            var client = new RestClient(this._endPointUrl);
            System.Net.ServicePointManager.SecurityProtocol = System.Net.SecurityProtocolType.Tls12;
            client.RemoteCertificateValidationCallback = (sender, certificate, chain, sslPolicyErrors) => true;

            var response = client.Execute(request);

            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                this._consumerSecret = pwdSha1;

                var timedCache = StaticKernelContainer.GetService<ITimedCache>();
                var regoleRestClient = new RegoleServiceRestClient(new AuthenticationManager(timedCache));
                regoleRestClient.SetRegola(this._alias, new SetRegolaRequest
                {
                    Software = this._software,
                    Modulo = this._nomeVerticalizzazione,
                    Parametro = this._passwordParameterName,
                    Comune = null,
                    NuovoValore = pwdSha1
                });

                return this.GetToken();
            }
            else
            {
                var respErr = JsonSerializer.Deserialize<JesiAuthenticationRestClientResponse>(response.Content);
                throw new Exception($"(errorCode:{respErr.ErrorResponse.Code}) - {respErr.ErrorResponse.Message}");
            }
            */
        }
    }
}
