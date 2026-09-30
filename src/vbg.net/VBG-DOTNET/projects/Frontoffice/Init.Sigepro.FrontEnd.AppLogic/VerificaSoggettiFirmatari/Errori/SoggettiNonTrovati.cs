using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari.Errori
{
    public class SoggettiNonTrovati
    {
        public readonly string NomeDocumento;
        public readonly IEnumerable<string> TipiSoggettoRichiesti;

        internal SoggettiNonTrovati(string nomeDocumento, IEnumerable<string> tipiSoggettoRichiesti)
        {
            // TODO: Complete member initialization
            this.NomeDocumento = nomeDocumento;
            this.TipiSoggettoRichiesti = tipiSoggettoRichiesti;
        }
    }
}
