using Init.SIGePro.Protocollo.ProtocolloItCityService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.Fascicolazione
{
    public class CreazioneFascicoloRequestInfo
    {
        public readonly CoordinateArchivio Coordinate;

        public CreazioneFascicoloRequestInfo(string classifica, string oggetto)
        {
            this.Coordinate = new CoordinateArchivio
            {
                IdIndice = Convert.ToInt32(classifica),
                FlagFascicolazione = FlagFascicolazione.F,
                OggettoFascicolazione = oggetto,
                IdFascicolo = 0,
                NumeroSottofascicolo = 0
            };
        }
    }
}
