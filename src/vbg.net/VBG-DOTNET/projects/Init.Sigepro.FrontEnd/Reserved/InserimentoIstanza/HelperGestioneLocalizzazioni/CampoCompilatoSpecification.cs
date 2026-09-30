namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneLocalizzazioni
{
    using Init.Sigepro.FrontEnd.Infrastructure;

    public class CampoCompilatoSpecification : ISpecification<ICompilazioneVerificabile>
    {
        public bool IsSatisfiedBy(ICompilazioneVerificabile item)
        {
            return item.VerificaCompilazione();
        }
    }
}