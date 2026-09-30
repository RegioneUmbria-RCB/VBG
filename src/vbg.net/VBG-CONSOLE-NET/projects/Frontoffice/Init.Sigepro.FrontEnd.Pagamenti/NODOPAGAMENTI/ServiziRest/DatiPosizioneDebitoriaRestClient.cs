using log4net;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.ServiziRest
{
    public class DatiPosizioneDebitoriaRestClient
    {
        ILog _log = LogManager.GetLogger(typeof(DatiPosizioneDebitoriaRestClient));
        private readonly NodoPagamentiSettings _settings;

        public DatiPosizioneDebitoriaRestClient(NodoPagamentiSettings settings)
        {
            _settings = settings;
        }

        public PosizioneDebitoriaRestResponse GetDatiPosizione(string cfEnteCreditore, string idPosizione)
        {
            var url = this._settings.UrlServizoRestDatiPosizioneDebitoria.GetUrl(cfEnteCreditore, idPosizione);

            this._log.Debug($"Chiamata all'url {url}");

            using (var wc = new WebClient())
            {
                var response = wc.DownloadString(url);

                var jObject= JObject.Parse(response);

                this._log.Debug($"Esito della lettura dello stato posizione (cfEnteCreditore: {cfEnteCreditore}, idPosizione: {idPosizione}): {response}");
                return jObject["posizione_debitoria"].ToObject<PosizioneDebitoriaRestResponse>();
            }
        }
    }
}
