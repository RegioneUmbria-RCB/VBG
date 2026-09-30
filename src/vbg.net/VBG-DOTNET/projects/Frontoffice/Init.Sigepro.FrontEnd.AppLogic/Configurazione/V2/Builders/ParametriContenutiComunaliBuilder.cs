namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriContenutiComunaliBuilder : IConfigurazioneBuilder<ParametriContenutiComunali>
    {
        private static class Constants
        {
            public const string HomPageComuneConfigKey = "homePageComune";
        }

        private readonly IAppConfigurationReader _appConfigurationReader;

        public ParametriContenutiComunaliBuilder(IAppConfigurationReader appConfigurationReader)
        {
            this._appConfigurationReader = appConfigurationReader;
        }

        public ParametriContenutiComunali Build()
        {
            var homePageComune = this._appConfigurationReader.GetSetting(Constants.HomPageComuneConfigKey);

            return new ParametriContenutiComunali(homePageComune);
        }
    }
}
