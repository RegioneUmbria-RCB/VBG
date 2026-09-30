using Microsoft.Extensions.Logging;
using Newtonsoft.Json.Linq;
using System.Net;

namespace VBG.Pagamenti.NodoPagamenti.ServiziRest
{
    public class DatiPosizioneDebitoriaRestClient
    {
        private readonly ILogger<DatiPosizioneDebitoriaRestClient> _log;// = LogManager.GetLogger(typeof(DatiPosizioneDebitoriaRestClient));
        private readonly NodoPagamentiSettings _settings;

        public DatiPosizioneDebitoriaRestClient(ILoggerFactory loggerFactory, NodoPagamentiSettings settings)
        {
            this._settings = settings;
            this._log = loggerFactory.CreateLogger<DatiPosizioneDebitoriaRestClient>();
        }

        public PosizioneDebitoriaRestResponse GetDatiPosizione(string cfEnteCreditore, string idPosizione)
        {
            var url = this._settings.UrlServizoRestDatiPosizioneDebitoria.GetUrl(cfEnteCreditore, idPosizione);

            this._log.LogDebug($"Chiamata all'url {url}");

            using (var wc = new WebClient())
            {
                var response = wc.DownloadString(url);

                var jObject = JObject.Parse(response);

                this._log.LogDebug($"Esito della lettura dello stato posizione (cfEnteCreditore: {cfEnteCreditore}, idPosizione: {idPosizione}): {response}");
                return jObject["posizione_debitoria"].ToObject<PosizioneDebitoriaRestResponse>();
            }
        }
    }
}
