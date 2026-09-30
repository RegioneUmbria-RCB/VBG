namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
    using System;

    internal class ParametriFirmaDigitaleBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriFirmaDigitale>
    {
        private readonly IAppConfigurationReader _appConfigurationReader;

        private static class Constants
        {
            public const string ConfigKeyName = "FirmaDigitale.UrlJspFirmaDigitale";
        }

        public ParametriFirmaDigitaleBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository repo, IAppConfigurationReader appConfigurationReader)
            : base(aliasSoftwareResolver, repo)
        {
            this._appConfigurationReader = appConfigurationReader;
        }

        public ParametriFirmaDigitale Build()
        {
            var cfgKey = this._appConfigurationReader.GetSetting(Constants.ConfigKeyName);
            var config = this.GetConfig();

            if (string.IsNullOrEmpty(cfgKey))
            {
                throw new Exception("Nel web.config non è stato configurato in parametro " + Constants.ConfigKeyName);
            }

            return new ParametriFirmaDigitale(cfgKey, config.IdSchedaEstremiDocumento);
        }
    }
}
