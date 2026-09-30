using Newtonsoft.Json;
using RestSharp;


namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.CercaPratiche
{
    public class CercaPraticheService
    {
        private ParametriRegoleInfo _parametri;

        public CercaPraticheService(ParametriRegoleInfo parametri)
        {
            this._parametri = parametri;
        }

        public CercaPraticheResponse CercaPratiche(CercaPraticheRequest requestCercaPratiche)
        {
            this._parametri.Logger.Debug("CercaPratiche: Serializzazione della request");
            var jsonRequest = JsonConvert.SerializeObject(requestCercaPratiche);
            this._parametri.Logger.Debug(jsonRequest);

            var client = new RestClient(this._parametri.CercaPraticheURL);

            var request = new RestRequest();
            request.Method = Method.Post;
            request.AddHeader("Authorization", $"Bearer {this._parametri.Token}");
            request.AddHeader("Accept", "application/json");
            // NON serve settare Content-Type manualmente
            request.AddStringBody(jsonRequest, DataFormat.Json);
                        
            this._parametri.Logger.Debug("CercaPratiche: Chiamata al ws");
            RestResponse restResponse = client.Execute(request);
            request.Method = Method.Post;
            this._parametri.Logger.Debug(restResponse.Content);
            this._parametri.Logger.Debug("CercaPratiche: Fine chiamata al ws");

            var response = JsonConvert.DeserializeObject<CercaPraticheResponse>(restResponse.Content);
            if (response.ResultType != 1)
            {
                throw new Exception(response.ResultDescription);
            }
            return response;
        }
    }
}
