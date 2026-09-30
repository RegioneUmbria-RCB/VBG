using Newtonsoft.Json;
using RestSharp;


namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Assegnazione
{
    public class AssegnaPraticaService
    {
        private ParametriRegoleInfo _parametri;

        public AssegnaPraticaService(ParametriRegoleInfo parametri)
        {
            this._parametri = parametri;
        }

        public AssegnaPraticaResponse AssegnaPratica(AssegnaPraticaRequest requestAssegnazione)
        {
            
            this._parametri.Logger.Info("AssegnaPratica: Serializzazione della request");
            var jsonRequest = JsonConvert.SerializeObject(requestAssegnazione);
            this._parametri.Logger.Info(jsonRequest);

            var client = new RestClient(this._parametri.AssegnaPraticaURL);

            var request = Common.GetRestRequest(jsonRequest, this._parametri.Token);

            this._parametri.Logger.Debug("AssegnaPratica: Chiamata al ws");
            RestResponse restResponse = client.Execute(request);
            this._parametri.Logger.Debug(restResponse.Content);
            this._parametri.Logger.Debug("AssegnaPratica: Fine chiamata al ws");

            var response = JsonConvert.DeserializeObject<AssegnaPraticaResponse>(restResponse.Content);
            if (response.ResultType != 1)
            {
                this._parametri.Logger.Error($"ERRORE GENERATO DURANTE L'ASSEGNAZIONE DELLA PRATICA: {response.ResultDescription}");
                throw new Exception(response.ResultDescription);
            }
            return response;
        }
    }
}
