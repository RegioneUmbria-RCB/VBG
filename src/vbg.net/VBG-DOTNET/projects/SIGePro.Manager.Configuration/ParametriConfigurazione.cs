using Init.SIGePro.Manager.Authentication;

namespace Init.SIGePro.Manager.Configuration
{
    public class ParametriConfigurazione
    {
        protected ParametriConfigurazione() { }

        public static ConfigurazioneGenerale Get => new ConfigurazioneGenerale(new SigeproSecurityProxy());

    }
}
