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
        public readonly string CodiceUnivocoDomanda;

        public readonly int StepId;

        public RiferimentiDomanda(IRiferimentiDomandaPerPagamenti domanda, int stepId)
        {
            this.IdComune = domanda.IdComune;
            this.Software = domanda.Software;
            this.IdDomanda = domanda.IdPresentazione;
            this.StepId = stepId;
            this.CodiceUnivocoDomanda = domanda.CodiceUnivocoDomanda;
        }


    }
}
