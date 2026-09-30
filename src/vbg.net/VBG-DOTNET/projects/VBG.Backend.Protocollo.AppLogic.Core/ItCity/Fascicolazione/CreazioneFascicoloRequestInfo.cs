using ItCityService;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Fascicolazione
{
    public class CreazioneFascicoloRequestInfo
    {
        public readonly CoordinateArchivio Coordinate;

        public CreazioneFascicoloRequestInfo(int classifica, string oggetto)
        {
            this.Coordinate = new CoordinateArchivio
            {
                IdIndice = classifica,
                FlagFascicolazione = FlagFascicolazione.F,
                OggettoFascicolazione = oggetto,
                IdFascicolo = 0,
                NumeroSottofascicolo = 0
            };
        }
    }
}
