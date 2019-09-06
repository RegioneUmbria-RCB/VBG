using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.Specifications
{
    public class NessunOnereDovutoSpecification : ISpecification<IOneriReadInterface>
    {
        public bool IsSatisfiedBy(IOneriReadInterface oneri)
        {
            return oneri.Oneri.Where(x => x.ModalitaPagamento != ModalitaPagamentoOnereEnum.NonDovuto).Count() == 0;
        }
    }
}