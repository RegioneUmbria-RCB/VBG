using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace AreaRiservataCore.Pages.InserimentoIstanza.Ssu.GestionePagamenti.Specifications
{
    public class DomandaContieneAttestazioneDiPagamentoSpecification : ISpecification<IOneriReadInterface>
    {
        public bool IsSatisfiedBy(IOneriReadInterface oneri)
        {
            return oneri.AttestazioneDiPagamento.Presente;
        }
    }
}
