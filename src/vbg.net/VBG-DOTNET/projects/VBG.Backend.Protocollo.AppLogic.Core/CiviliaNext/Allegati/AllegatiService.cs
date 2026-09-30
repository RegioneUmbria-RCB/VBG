using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati.AggiungiAllegati;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati.GetAllegati;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati.GetAllegato;
using Newtonsoft.Json;
using RestSharp;
using System;
using System.Diagnostics;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati
{
    public class AllegatiService
    {
        private readonly ParametriRegoleInfo _parametri;

        public AllegatiService(ParametriRegoleInfo parametri)
        {
            this._parametri = parametri;
        }

        public AggiungiAllegatoResponse AggiungiAllegato(AggiungiAllegatiRequest requestAllegati)
        {
            this._parametri.Logger.Info("AggiungiAllegato: Serializzazione della request");
            var jsonRequest = JsonConvert.SerializeObject(requestAllegati);
            this._parametri.Logger.Info(jsonRequest);

            var client = new RestClient(this._parametri.AggiungiAllegatoURL);

            var request = Common.GetRestRequest(jsonRequest, this._parametri.Token);

            this._parametri.Logger.Info("AggiungiAllegato: Chiamata al ws");
            RestResponse restResponse = client.Execute(request);
            this._parametri.Logger.Info(restResponse.Content);
            this._parametri.Logger.Info("AggiungiAllegato: Fine chiamata al ws");

            var response = JsonConvert.DeserializeObject<AggiungiAllegatoResponse>(restResponse.Content);
            if (response.ResultType != 1)
            {
                this._parametri.Logger.Error($"ERRORE GENERATO DURANTE L'INSERIMENTO DEGLI ALLEGATI: {response.ResultDescription}");
                throw new Exception(response.ResultDescription);
            }
            return response;
        }

        public GetAllegatiResponse GetAllegati(GetAllegatiRequest requestGetAllegati)
        {
            this._parametri.Logger.Debug("GetAllegati: Serializzazione della request");
            var jsonRequest = JsonConvert.SerializeObject(requestGetAllegati);
            this._parametri.Logger.Debug(jsonRequest);

            var client = new RestClient(this._parametri.GetAllegatiURL);

            var request = Common.GetRestRequest(jsonRequest, this._parametri.Token);

            this._parametri.Logger.Debug("GetAllegati: Chiamata al ws");
            RestResponse restResponse = client.Execute(request);
            this._parametri.Logger.Debug(restResponse.Content);
            this._parametri.Logger.Debug("GetAllegati: Fine chiamata al ws");

            var response = JsonConvert.DeserializeObject<GetAllegatiResponse>(restResponse.Content);
            if (response.ResultType != 1)
            {
                throw new Exception(response.ResultDescription);
            }
            return response;
        }

        public GetAllegatoResponse DownloadAllegato(GetAllegatoRequest requestGetAllegato)
        {
            this._parametri.Logger.Debug("DownloadAllegato: Serializzazione della request");
            var jsonRequest = JsonConvert.SerializeObject(requestGetAllegato);
            this._parametri.Logger.Debug(jsonRequest);

            var client = new RestClient(this._parametri.GetAllegatoURL);

            var request = Common.GetRestRequest(jsonRequest, this._parametri.Token);

            this._parametri.Logger.Debug("DownloadAllegato: Chiamata al ws");
            RestResponse restResponse = client.Execute(request);
            this._parametri.Logger.Debug(restResponse.Content);
            this._parametri.Logger.Debug("DownloadAllegato: Fine chiamata al ws");

            var response = JsonConvert.DeserializeObject<GetAllegatoResponse>(restResponse.Content);
            if (response.ResultType != 1)
            {
                throw new Exception(response.ResultDescription);
            }
            return response;
        }
    }
}
