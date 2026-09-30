using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestionePagamenti.Specifications
{
    public class NessunOnereDovutoSpecification : ISpecification<IOneriReadInterface>
    {
        public bool IsSatisfiedBy(IOneriReadInterface oneri)
        {
            return oneri.Oneri.Where(x => x.ModalitaPagamento != ModalitaPagamentoOnereEnum.NonDovuto).Count() == 0;
        }
    }
}
