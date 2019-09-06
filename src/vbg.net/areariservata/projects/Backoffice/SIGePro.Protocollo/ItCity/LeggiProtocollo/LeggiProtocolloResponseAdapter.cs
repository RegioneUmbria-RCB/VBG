using Init.SIGePro.Protocollo.ProtocolloItCityService;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        public LeggiProtocolloResponseAdapter()
        {

        }

        public DatiProtocolloLetto Adatta(ProtocolloItCityService.Protocollo response)
        {
            bool isFascicolato = response.Fascicolo != null && response.Fascicolo.Numero != 0;

            var numeroFascicolo = isFascicolato ? response.Fascicolo.Numero.ToString() : "";

            if (isFascicolato && response.Fascicolo.NumeroSottofascicolo != 0)
            {
                numeroFascicolo += $".{response.Fascicolo.NumeroSottofascicolo}";
            }

            var classifica = response.Fascicolo != null ? response.Fascicolo.Titolo : "";

            if (response.Fascicolo != null && !String.IsNullOrEmpty(response.Fascicolo.Classe))
            {
                classifica += $".{response.Fascicolo.Classe}";
            }

            if (response.Fascicolo != null && !String.IsNullOrEmpty(response.Fascicolo.Sottoclasse))
            {
                classifica += $".{response.Fascicolo.Sottoclasse}";
            }

            return new DatiProtocolloLetto
            {
                AnnoProtocollo = response.AnnoProtocollo,
                DataProtocollo = response.DataProtocollo,
                NumeroProtocollo = response.NumeroProtocollo,
                Oggetto = response.Oggetto,
                AnnoNumeroPratica = response.Fascicolo.Segnatura,
                NumeroPratica = numeroFascicolo,
                Classifica = classifica,
                Classifica_Descrizione = classifica
            };
        }
    }
}
