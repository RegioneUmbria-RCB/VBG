using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti
{
    public class OrdinamentoEndo
    {
        public readonly int OrdineFamiglia;
        public readonly int OrdineTipo;
        public readonly int OrdineEndo;

        public OrdinamentoEndo(int ordineFamiglia, int ordineTipo, int ordineEndo)
        {
            this.OrdineFamiglia = ordineFamiglia;
            this.OrdineTipo = ordineTipo;
            this.OrdineEndo = ordineEndo;
        }
    }
}
