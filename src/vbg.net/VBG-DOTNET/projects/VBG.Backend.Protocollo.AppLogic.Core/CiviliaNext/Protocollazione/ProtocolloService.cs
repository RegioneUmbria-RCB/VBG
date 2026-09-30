using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.AnnullaProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.InvioProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.Protocolla;
using Newtonsoft.Json;
using RestSharp;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione
{
    public class ProtocolloService
    {
        private class OAuthConstants
        {
            public const string ClientIdParameter = "client_id";
            public const string SecretParameter = "client_secret";
            public const string GrantTypeParameter = "grant_type";
            public const string GrantTypeValue = "client_credentials";
        }

        ParametriRegoleInfo _parametri;

        public ProtocolloService(ParametriRegoleInfo parametri)
        {
            this._parametri = parametri;
        }

        public ProtocollazioneResponse Protocolla(ProtocollazioneRequest protocollazioneRequest)
        {
            try
            {
                this._parametri.Logger.Info("Protocolla: Serializzazione della request");
                var jsonRequest = JsonConvert.SerializeObject(protocollazioneRequest);
                this._parametri.Logger.Info(jsonRequest);

                var client = new RestClient(this._parametri.ProtocollazioneURL);

                var request = Common.GetRestRequest(jsonRequest, this._parametri.Token);

                this._parametri.Logger.Info("Protocolla: Chiamata al ws");
                RestResponse restResponse = client.Execute(request);
                this._parametri.Logger.Info(restResponse.Content);
                this._parametri.Logger.Info("Protocolla: Fine chiamata al ws");

                var response = JsonConvert.DeserializeObject<ProtocollazioneResponse>(restResponse.Content);
                if (response.ResultType != 1)
                {
                    throw new Exception(response.ResultDescription);
                }
                return response;

            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA CHIAMATA A PROTOCOLLAZIONE, {ex.Message}");
            }
        }

        public AnnullaProtocolloResponse AnnullaProtocollo(AnnullaProtocolloRequest annullaProtocolloRequest)
        {
            try
            {
                this._parametri.Logger.Info("AnnullaProtocollo: Serializzazione della request");
                var jsonRequest = JsonConvert.SerializeObject(annullaProtocolloRequest);

                var client = new RestClient(this._parametri.AnnullaProtocolloURL);
                var request = Common.GetRestRequest(jsonRequest, this._parametri.Token);
                this._parametri.Logger.Info("AnnullaProtocollo: Chiamata al ws");
                RestResponse restResponse = client.Execute(request);
                this._parametri.Logger.Info("AnnullaProtocollo: Fine chiamata al ws");
                var response = JsonConvert.DeserializeObject<AnnullaProtocolloResponse>(restResponse.Content);
                if (response.ResultType != 1)
                {
                    throw new Exception(response.ResultDescription);
                }
                return response;

            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA CHIAMATA A PROTOCOLLAZIONE, {ex.Message}");
            }
        }

        public InvioProtocolloResponse InvioProtocollo(InvioProtocolloRequest invioProtocolloRequest) 
        {
            try
            {
                this._parametri.Logger.Info("InvioProtocollo: Serializzazione della request");
                var jsonRequest = JsonConvert.SerializeObject(invioProtocolloRequest);

                var client = new RestClient(this._parametri.InviaProtocolloURL);
                var request = Common.GetRestRequest(jsonRequest, this._parametri.Token);
                this._parametri.Logger.Info("InvioProtocollo: Chiamata al ws");
                RestResponse restResponse = client.Execute(request);
                this._parametri.Logger.Info("InvioProtocollo: Fine chiamata al ws");
                var response = JsonConvert.DeserializeObject<InvioProtocolloResponse>(restResponse.Content);
                if (response.ResultType != 1)
                {
                    throw new Exception(response.ResultDescription);
                }
                return response;
            }
            catch (Exception)
            {

                throw;
            }
        }
    }
}
