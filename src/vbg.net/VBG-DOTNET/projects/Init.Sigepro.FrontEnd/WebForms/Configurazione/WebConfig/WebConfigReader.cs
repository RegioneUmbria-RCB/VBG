using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Configurazione.WebConfig
{
    public class WebConfigReader : IAppConfigurationReader
    {
        private static class Constants
        {
            public const string NomeComuneDefault = "default";
            public const string SectionName = "sigepro/frontEnd";
        }

        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;

        public WebConfigReader(IAliasSoftwareResolver aliasSoftwareResolver)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        private ConfigurazioneFrontEndWebConfig ReadConfig()
        {
            ConfigurazioneFrontEndWebConfig config = (ConfigurazioneFrontEndWebConfig)System.Configuration.ConfigurationManager.GetSection(Constants.SectionName);

            return config;
        }


        public IConfigurazioneStc GetParametriStc()
        {
            var config = this.ReadConfig();
            var parametriStc = config.GetParametriStc(this._aliasSoftwareResolver.AliasComune, Constants.NomeComuneDefault);
            return parametriStc.GetSpecializzazionePerSoftware(this._aliasSoftwareResolver);
        }

        public IConfigurazionePerComune GetParametriPerComune()
        {
            var config = this.ReadConfig();
            var idComune = this._aliasSoftwareResolver.AliasComune;

            if (config.ParametriPerIdComune[idComune] == null)
            {
                idComune = Constants.NomeComuneDefault;
            }

            var parametri = config.ParametriPerIdComune[idComune];

            return new ConfigurazionePerComune
            {
                PaginaIniziale = parametri.PaginaIniziale,
                ProcessFile = parametri.ProcessFile.Default
            };

        }

        public IConfigurazioneSigeproSecurity GetParametriSigeproSecurity()
        {
            var config = this.ReadConfig();

            return config.SigeproSecurity;
        }

        public string GetSetting(string name)
        {
            return ConfigurationManager.AppSettings[name];
        }
    }
}
