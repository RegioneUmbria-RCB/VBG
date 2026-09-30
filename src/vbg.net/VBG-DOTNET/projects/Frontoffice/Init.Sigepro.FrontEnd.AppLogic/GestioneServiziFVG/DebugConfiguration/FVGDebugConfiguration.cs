using Init.Sigepro.FrontEnd.AppLogic.Configurazione;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.DebugConfiguration
{
    public class FVGDebugConfiguration : IFVGDebugConfiguration
    {
        private readonly IAppConfigurationReader _appConfigurationReader;

        public FVGDebugConfiguration(IAppConfigurationReader appConfigurationReader)
        {
            this._appConfigurationReader = appConfigurationReader;
        }

        public bool IsDebugEnabled => !string.IsNullOrEmpty(this._appConfigurationReader.GetSetting("FvgDatabasePersistenceMediumFactory.debugMode"));

        public string ManagedDataMappingsDevFile => "~/moduli-fvg/compilazione/managed-data-mappings.nocopy.xml";
    }
}
