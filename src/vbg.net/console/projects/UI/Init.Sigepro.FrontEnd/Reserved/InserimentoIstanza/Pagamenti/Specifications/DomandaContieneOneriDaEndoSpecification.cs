using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.Specifications
{
    public class DomandaContieneOneriDaEndoSpecification : ISpecification<IDomandaOnlineReadInterface>
    {
        #region ISpecification<double> Members

        public bool IsSatisfiedBy(IDomandaOnlineReadInterface item)
        {
            return item.Oneri.Oneri.Where(x => x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Endoprocedimento).Count() > 0;
        }

        #endregion
    }
}