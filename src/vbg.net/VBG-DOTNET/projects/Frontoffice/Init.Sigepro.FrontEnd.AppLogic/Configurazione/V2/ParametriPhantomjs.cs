namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriPhantomjs : IParametriConfigurazione
    {
        public readonly string PhantomjsPath;

        public ParametriPhantomjs(string phantomjsPath)
        {
            this.PhantomjsPath = phantomjsPath;
        }
    }
}
