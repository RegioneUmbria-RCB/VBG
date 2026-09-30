using Init.Sigepro.FrontEnd.Infrastructure;

namespace AreaRiservataCore.Pages.InserimentoIstanza.HelperGestioneLocalizzazioni
{
    public class RegExMatchedSpecification : ISpecification<IRegexVerificabile>
    {
        public bool IsSatisfiedBy(IRegexVerificabile item)
        {
            return item.RegexVerificata();
        }
    }
}
