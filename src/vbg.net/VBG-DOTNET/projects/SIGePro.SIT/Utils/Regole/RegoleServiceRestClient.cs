using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.Configuration;
using RestSharp;
using System;

namespace Init.SIGePro.Sit.Utils.Regole
{
    public class RegoleServiceRestClient
    {
        private readonly IAuthenticationManager _authenticationManager;

        public RegoleServiceRestClient(IAuthenticationManager authenticationManager)
        {
            this._authenticationManager = authenticationManager;
        }

        public void SetRegola(string alias, SetRegolaRequest request)
        {
            var authInfo = this._authenticationManager.GetTokenApplicativo(alias);

            if (authInfo == null)
                throw new Exception($"AUTHENTICATION INFO NON VALORIZZATO, IdComuneAlias: {alias}");

            var urlBackend = ParametriConfigurazione.Get.WsHostUrlApiBackend;

            if (!urlBackend.EndsWith("/"))
                urlBackend += "/";

            string wsUrl = $"{urlBackend}services/api-rest/configurazioni/regole/{request.Software}/{request.Modulo}/{request.Parametro}";

            var rr = new RestRequest(Method.PUT);
            rr.AddHeader("Authorization", authInfo.Token);
            rr.AlwaysMultipartFormData = true; //imposta multipart form
            rr.AddParameter("comune", "");
            rr.AddParameter("nuovoValore", request.NuovoValore);

            var client = new RestClient(wsUrl);
            var response = client.Execute(rr);

            if (response.StatusCode != System.Net.HttpStatusCode.OK)
            {
                throw new Exception(response.ErrorMessage);
            }
        }
    }

    public class SetRegolaRequest
    {
        public string Software { get; set; }
        public string Modulo { get; set; }
        public string Parametro { get; set; }
        public string Comune { get; set; }
        public string NuovoValore { get; set; }
    }
}
