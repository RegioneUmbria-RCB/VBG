using Init.Sigepro.FrontEnd.Infrastructure;

namespace AreaRiservataCore.Pages.InserimentoIstanza.HelperGestioneLocalizzazioni
{
    public class ValoreInRangeSpecification : ISpecification<IValoreinRangeVerificabile>
    {
        public bool IsSatisfiedBy(IValoreinRangeVerificabile item)
        {
            return item.VerificaValoreInRange();
        }
    }
}
