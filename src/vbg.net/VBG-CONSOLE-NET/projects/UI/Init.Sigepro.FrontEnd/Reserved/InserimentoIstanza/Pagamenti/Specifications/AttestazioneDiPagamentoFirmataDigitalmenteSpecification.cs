using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.Specifications
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