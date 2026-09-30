namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriAreaRiservataCore : IParametriConfigurazione
    {
        public readonly bool UsaAreaRiservataCore = false;
        public readonly string? BaseUrlCore = null;
        public readonly string? BaseUrlFramework = null;
        public readonly bool UsaPresentazioneDomandaCore;

        public ParametriAreaRiservataCore(bool usaAreaRiservataCore, string? baseUrlCore, string? baseUrlFramework, bool usaPresentazioneDomandaCore)
        {
            this.UsaAreaRiservataCore = usaAreaRiservataCore;
            this.BaseUrlCore = baseUrlCore;
            this.BaseUrlFramework = baseUrlFramework;
            this.UsaPresentazioneDomandaCore = usaPresentazioneDomandaCore;
        }
    }
}
