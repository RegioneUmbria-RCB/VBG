using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.WsRateizzazioniService;
using System.ServiceModel;
using Init.SIGePro.Manager.Configuration;
using log4net;

namespace Init.SIGePro.Manager.Logic.CalcoloOneri.Rateizzazioni
{



    public class RateizzazioniService
    {
        private static class Constants
        {
            public const string BindingName = "rateizzazioniHttpBinding";
        }

        string _token;
        ILog _log = LogManager.GetLogger(typeof(RateizzazioniService));

        public RateizzazioniService(string token)
        {
            this._token = token;
        }

        private RateizzazioniClient CreateClient()
        {
            _log.DebugFormat("Inizializzazione del servizio di rateizzazione all'indirizzo {0} utilizzando il binding {1}", ParametriConfigurazione.Get.WsRateizzazioniServiceUrl, Constants.BindingName);

            var endpoint = new EndpointAddress(ParametriConfigurazione.Get.WsRateizzazioniServiceUrl);
            var binding = new BasicHttpBinding(Constants.BindingName);

            return new RateizzazioniClient(binding, endpoint);
        }

        public IEnumerable<DatiRateizzazione> Rateizza(int tipoRateizzazione, DateTime data, DateTime? dataInizio, decimal importo, decimal speseRateizzazione)
        {
            try
            {
                using (var client = CreateClient())
                {

                    var rateizzazioniRequest = new RateizzazioniRequest
                    {
                        codiceOneriTipiRateizzazione = tipoRateizzazione,
                        data = data,
                        dataInizio = dataInizio,
                        importo = importo,
                        token = this._token
                    };

                    var result = client.Rateizzazioni(rateizzazioniRequest);

                    if (result == null || result.Length == 0)
                        return new List<DatiRateizzazione>();

                    var esitoList = new List<DatiRateizzazione>();
                    var esito = new EsitoRateizzazioni(result, importo, speseRateizzazione);

                    return esito.Rate;
                }
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("Errore durante l'invocazione del metodo Rateizzazioni del servizio di rateizzazione: {0}", ex.ToString());

                throw;
            }
        }
    }
}
