using Init.SIGePro.Protocollo.ProtocolloItCityService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.Fascicolazione
{
    public class CreazioneSottoFascicoloRequestInfo
    {
        public readonly CoordinateArchivio Coordinate;

        public CreazioneSottoFascicoloRequestInfo(string classifica, string oggetto, int idFascicolo)
        {
            this.Coordinate = new CoordinateArchivio
            {
                // IdIndice = Convert.ToInt32(classifica),
                FlagFascicolazione = FlagFascicolazione.S,
                OggettoFascicolazione = oggetto,
                IdFascicolo = idFascicolo,
                NumeroSottofascicolo = 0
            };
        }
    }
}