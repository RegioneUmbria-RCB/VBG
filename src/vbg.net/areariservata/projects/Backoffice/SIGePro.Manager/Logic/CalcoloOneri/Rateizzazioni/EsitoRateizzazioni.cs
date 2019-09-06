using Init.SIGePro.Manager.WsRateizzazioniService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.CalcoloOneri.Rateizzazioni
{
    public class EsitoRateizzazioni
    {
        public List<DatiRateizzazione> Rate { get; private set; }

        public EsitoRateizzazioni(ImportoRateizzatoXML[] result, decimal importo, decimal speseRateizzazione)
        {
            this.Rate = new List<DatiRateizzazione>();

            decimal capitaleResiduo = Math.Round(importo, 2);

            for (int i = 0; i < result.Length; i++)
            {
                var quotaCapitale = Math.Round(result[i].importoRateizzato, 2) - Math.Round(result[i].importoInteresse, 2);

                var esito = new DatiRateizzazione
                {
                    DataScadenza = result[i].scadenza,
                    Prezzo = i == 0 ? Math.Round(result[i].importoRateizzato, 2) + Math.Round(speseRateizzazione, 2) : Math.Round(result[i].importoRateizzato, 2),
                    NumeroRata = i + 1,
                    Interesse = Math.Round(result[i].importoInteresse, 2),
                    QuotaCapitale = Math.Round(quotaCapitale, 2),
                    CapitaleResiduo = Math.Round(capitaleResiduo, 2)
                };

                capitaleResiduo = Math.Round(capitaleResiduo, 2) - Math.Round(quotaCapitale, 2);

                this.Rate.Add(esito);
            }
        }
    }
}
