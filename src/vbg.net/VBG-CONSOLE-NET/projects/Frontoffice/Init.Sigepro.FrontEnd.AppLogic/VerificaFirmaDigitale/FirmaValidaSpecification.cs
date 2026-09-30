using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{
    public class FirmaValidaSpecification : ISpecification<EsitoVerificaFirmaDigitale>
    {
        public bool IsSatisfiedBy(EsitoVerificaFirmaDigitale item)
        {
            return item.Stato == StatoVerificaFirma.FirmaValida;
        }
    }
}
