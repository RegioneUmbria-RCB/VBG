using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService
{
    public static class MovimentiExtensions
    {
        public static Movimenti[] GetMovimentiPubblicati(this Movimenti[] movimenti)
        {
            return movimenti?.Where(x => x.PUBBLICA == "1" && x.DATA.HasValue).ToArray() ?? Array.Empty<Movimenti>();
        }
        public static MovimentiAllegati[] GetAllegatiPubbliciConCodiceOggetto(this Movimenti movimento)
        {
            if (movimento.MovimentiAllegati is null)
            {
                return Array.Empty<MovimentiAllegati>();
            }

            return movimento.MovimentiAllegati.Where(y => y.FlagPubblica.GetValueOrDefault(0) == 1 && y.ContieneOggetto).ToArray();
        }
    }
}
