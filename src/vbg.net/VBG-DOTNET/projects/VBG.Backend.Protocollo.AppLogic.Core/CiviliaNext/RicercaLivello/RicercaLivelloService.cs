using Newtonsoft.Json;
using RestSharp;


namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.RicercaLivello
{
   public class RicercaLivelloService
    {
        private ParametriRegoleInfo _parametri;

        public RicercaLivelloService(ParametriRegoleInfo parametri)
        {
            this._parametri = parametri;
        }

        public RicercaLivelloResponse RicercaLivello(RicercaLivelloRequest requestCercaPratiche)
        {
            this._parametri.Logger.Debug("RicercaLivello: Serializzazione della request");
            var jsonRequest = JsonConvert.SerializeObject(requestCercaPratiche);
            this._parametri.Logger.Debug(jsonRequest);

            var client = new RestClient(this._parametri.RicercaLivelloURL);

            var request = Common.GetRestRequest(jsonRequest, this._parametri.Token);

            this._parametri.Logger.Debug("RicercaLivello: Chiamata al ws");
            RestResponse restResponse = client.Execute(request);
            this._parametri.Logger.Debug(restResponse.Content);
            this._parametri.Logger.Debug("RicercaLivello: Fine chiamata al ws");

            var response = JsonConvert.DeserializeObject<RicercaLivelloResponse>(restResponse.Content);
            if (!response.IsOk)
            {
                throw new Exception($"Errore generico restituito durante la chiamata al servizio {this._parametri.CercaPraticheURL}");
            }
            return response;
        }
    }
}
