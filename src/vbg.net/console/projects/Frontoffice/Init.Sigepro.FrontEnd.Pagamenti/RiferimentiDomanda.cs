using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti
{
    public class RiferimentiDomanda
    {
        public readonly string IdComune;
        public readonly string Software;
        public readonly int IdDomanda;
        public readonly int StepId;
        public readonly string Token;

        public RiferimentiDomanda(string idComune, string software, int idDomanda, int stepId)
        {
            this.IdComune = idComune;
            this.Software = software;
            this.IdDomanda = idDomanda;
            this.StepId = stepId;
        }
    }
}
