using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Creazione;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca;
using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione
{
    public class FascicolazioneService : BaseServiceWrapper
    {
        private readonly ProtocolloLogs _logger;

        public bool Ok { get; internal set; }
        public string Codice { get; internal set; }
        public string Messaggio { get; internal set; }
        public DatiFascType[] Fascicoli { get; internal set; }

        public FascicolazioneService(ProtocolloLogs logger, ProtocolloSerializer serializer, string username, string password, string url) : base(logger, serializer, username, password, url)
        {
            this._logger = logger;
        }

        public FascicolazioneResponse Fascicola(FascicolaRequest request)
        {
            try
            {
                // 1. Raccolta dei parametri necessari
                var parametri = request.ToParametriNuovaFascicolazione();

                using (var client = GetHttpClient())
                {
                    client.QueryString = parametri;
                    this._logger.InfoFormat("Fascicolazione, Request: {0}", Utility.NameValueCollectionToString(parametri));
                    var res = client.DownloadString(_url);
                    this._logger.InfoFormat("Fascicolazione, Response {0}", res);

                    var retVal = (WSFascicolazioneResponse)_serializer.Deserialize(res, typeof(WSFascicolazioneResponse));

                    return FascicolazioneResponse.FromWSFascicolazioneResponse(retVal);
                }

            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA FASCICOLAZIONE, ERRORE {ex.Message}", ex);
            }

        }

        public RicercaFascicoliResponse CercaFascicoli(RicercaFascicoliRequest request)
        {
            try
            {
                var parametri = request.ToParametriRicercaFascicolo();

                using (var client = GetHttpClient())
                {
                    client.QueryString = parametri;
                    this._logger.InfoFormat("Ricerca fascicoli, Request: {0}", Utility.NameValueCollectionToString(parametri));
                    var res = client.DownloadString(_url);
                    this._logger.InfoFormat("Ricerca fascicoli, Response {0}", res);

                    var retVal = (WsRicercaFascicoloResponse)_serializer.Deserialize(res, typeof(WsRicercaFascicoloResponse));

                    return RicercaFascicoliResponse.FromWsRicercaFascicoloResponse(retVal);
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Errore generato durante la ricerca dei fascicoli: {ex.Message}", ex);
            }
        }
    }
}
