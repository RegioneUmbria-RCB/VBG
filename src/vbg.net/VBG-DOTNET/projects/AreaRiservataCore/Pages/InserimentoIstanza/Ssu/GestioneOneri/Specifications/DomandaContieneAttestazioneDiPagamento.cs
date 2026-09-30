using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.Infrastructure;


namespace AreaRiservataCore.Pages.InserimentoIstanza.Ssu.GestioneOneri.Specifications
{
    public class DomandaContieneAttestazioneDiPagamento : ISpecification<IDomandaOnlineReadInterface>
    {
        public bool IsSatisfiedBy(IDomandaOnlineReadInterface item)
        {
            return item.Oneri.AttestazioneDiPagamento.Presente;
        }
    }
}
