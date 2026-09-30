using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.Specifications
{
    public class DomandaContieneAttestazioneDiPagamentoSpecification : ISpecification<IOneriReadInterface>
    {
        #region ISpecification<DomandaOnline> Members

        public bool IsSatisfiedBy(IOneriReadInterface oneri)
        {
            return oneri.AttestazioneDiPagamento.Presente;
        }

        #endregion
    }
}