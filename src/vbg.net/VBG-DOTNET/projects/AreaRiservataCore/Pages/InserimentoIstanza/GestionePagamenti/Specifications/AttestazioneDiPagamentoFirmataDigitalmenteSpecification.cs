using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestionePagamenti.Specifications
{
    public class AttestazioneDiPagamentoFirmataDigitalmenteSpecification : ISpecification<IOneriReadInterface>
    {
        #region ISpecification<IDomandaOnlineReadInterface> Members

        public bool IsSatisfiedBy(IOneriReadInterface oneri)
        {
            return oneri.AttestazioneDiPagamento.FirmatoDigitalmente;
        }

        #endregion
    }
}
