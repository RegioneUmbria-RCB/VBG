
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        public LeggiProtocolloResponseAdapter()
        {

        }

        public DatiProtocolloLettoResponseType Adatta(ItCityService.Protocollo response)
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

            return new DatiProtocolloLettoResponseType
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
