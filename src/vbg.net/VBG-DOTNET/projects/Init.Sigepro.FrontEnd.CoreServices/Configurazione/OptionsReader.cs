using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Microsoft.Extensions.Configuration;

namespace Init.Sigepro.FrontEnd.CoreServices.Configurazione
{
    public class OptionsReader : IAppConfigurationReader
    {
        private readonly IConfiguration _configuration;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;

        public OptionsReader(IConfiguration configuration, IAliasSoftwareResolver aliasSoftwareResolver)
        {
            this._configuration = configuration;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }


        public IConfigurazionePerComune GetParametriPerComune()
        {
            return this._configuration.GetSection(ConfigurazionePerComune.SectionName)?.Get<ConfigurazionePerComune>();
        }

        public IConfigurazioneSigeproSecurity GetParametriSigeproSecurity()
        {
            return this._configuration.GetSection(ConfigurazioneSigeproSecurity.SectionName)?.Get<ConfigurazioneSigeproSecurity>();
        }

        public IConfigurazioneStc GetParametriStc()
        {
            var cfg = this._configuration.GetSection(ConfigurazioneStc.SectionName).Get<ConfigurazioneStc>();

            return cfg.GetSpecializzazionePerSoftware(this._aliasSoftwareResolver);
        }

        public string GetSetting(string name)
        {
            return this._configuration[$"Settings:{name}"];
        }
    }
}
