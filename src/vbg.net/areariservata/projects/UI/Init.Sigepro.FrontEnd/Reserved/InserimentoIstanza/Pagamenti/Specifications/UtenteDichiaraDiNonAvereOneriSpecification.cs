using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.Specifications
{
    public class UtenteDichiaraDiNonAvereOneriSpecification : ISpecification<IOneriReadInterface>
    {
        #region ISpecification<DomandaOnline> Members

        public bool IsSatisfiedBy(IOneriReadInterface oneri)
        {
            return oneri.DichiaraDiNonAvereOneriDaPagare;
        }

        #endregion
    }
}