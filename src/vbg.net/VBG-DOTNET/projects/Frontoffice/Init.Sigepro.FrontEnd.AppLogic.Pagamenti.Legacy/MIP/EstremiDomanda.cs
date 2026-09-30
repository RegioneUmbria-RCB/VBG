using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP
{
    public class EstremiDomanda
    {
        public readonly int IdDomanda;
        public readonly int StepId;
        public readonly string EmailUtente;
        public readonly string IdentificativoUtente;

        public EstremiDomanda( int idDomanda, int stepId, string emailUtente, string identificativoUtente)
        {
            this.IdDomanda = idDomanda;
            this.StepId = stepId;
            this.EmailUtente = emailUtente;
            this.IdentificativoUtente = identificativoUtente;
        }

        
    }
}
