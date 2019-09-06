using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.Specifications
{
    public class ImportoDaPagareUgualeAZeroSpecification : ISpecification<IDomandaOnlineReadInterface>
    {
        #region ISpecification<double> Members

        public bool IsSatisfiedBy(IDomandaOnlineReadInterface item)
        {
            return item.Oneri.TotalePagato <= 0.01d;
        }

        #endregion
    }
}