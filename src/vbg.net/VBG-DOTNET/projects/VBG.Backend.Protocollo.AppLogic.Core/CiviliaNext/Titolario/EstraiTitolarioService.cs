using Newtonsoft.Json;
using RestSharp;


namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Titolario
{
    public class EstraiTitolarioService
    {
        private ParametriRegoleInfo _parametri;

        public EstraiTitolarioService(ParametriRegoleInfo parametri)
        {
            this._parametri = parametri;
        }

        public EstraiTitolarioResponse EstraiTitolario(EstraiTitolarioRequest requestEstraiTitolario)
        {
            this._parametri.Logger.Debug("EstraiTitolario: Serializzazione della request");
            var jsonRequest = JsonConvert.SerializeObject(requestEstraiTitolario);
            this._parametri.Logger.Debug(jsonRequest);

            var client = new RestClient(this._parametri.EstraiTitolarioURL);

            var request = Common.GetRestRequest(jsonRequest, this._parametri.Token);

            this._parametri.Logger.Debug("EstraiTitolario: Chiamata al ws");
            RestResponse restResponse = client.Execute(request);
            this._parametri.Logger.Debug(jsonRequest);
            this._parametri.Logger.Debug("EstraiTitolario: Fine chiamata al ws");

            var response = JsonConvert.DeserializeObject<EstraiTitolarioResponse>(restResponse.Content);
            if (!response.IsOk)
            {
                throw new Exception("Errore generico del servizio che fornisce l'elenco delle classifiche");
            }
            return response;

        }
    }
}
