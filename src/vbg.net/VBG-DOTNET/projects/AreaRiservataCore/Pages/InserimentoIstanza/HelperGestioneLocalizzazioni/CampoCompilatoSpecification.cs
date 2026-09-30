using Init.Sigepro.FrontEnd.Infrastructure;

namespace AreaRiservataCore.Pages.InserimentoIstanza.HelperGestioneLocalizzazioni
{
    public class CampoCompilatoSpecification : ISpecification<ICompilazioneVerificabile>
    {
        public bool IsSatisfiedBy(ICompilazioneVerificabile item)
        {
            return item.VerificaCompilazione();
        }
    }
}
