using Init.SIGePro.Manager.Utils;
using Newtonsoft.Json;
using System;
using System.Net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public class AuthenticationRestClient
    {
        private readonly string _endPointUrl;
        private readonly string _consumerKey;
        private readonly string _consumerSecret;
        private readonly IProtocolloSerializer _serializer;

        public AuthenticationRestClient(IProtocolloSerializer serializer, string endPointUrl, string consumerKey, string consumerSecret)
        {
            this._serializer = serializer;
            this._endPointUrl = endPointUrl;
            this._consumerKey = consumerKey;
            this._consumerSecret = consumerSecret;
        }

        public String GetAuthToken()
        {
            var authHeader = Base64Utils.Base64Encode($"{this._consumerKey}:{this._consumerSecret}");

            var header = new WebHeaderCollection
            {
                { "Authorization", $"Basic {authHeader}" }
            };
            System.Net.ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
            var client = new RestClient
            {
                EndPoint = this._endPointUrl,
                Method = HttpVerb.POST,
                ContentType = "application/json",
                Headers = header
            };

            var jsonResponse = client.MakeRequest();

            this._serializer.LogAndValidate("GetAuthTokenResponse.txt", jsonResponse, "Fine chiamata a GetAuthToken");

            var response = JsonConvert.DeserializeObject<AuthenticationRestServiceResponse>(jsonResponse);

            return response.AccessToken;
        }
    }
}
