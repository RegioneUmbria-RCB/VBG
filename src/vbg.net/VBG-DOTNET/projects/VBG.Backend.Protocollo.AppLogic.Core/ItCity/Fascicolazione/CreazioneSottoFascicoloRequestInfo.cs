using ItCityService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Fascicolazione
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