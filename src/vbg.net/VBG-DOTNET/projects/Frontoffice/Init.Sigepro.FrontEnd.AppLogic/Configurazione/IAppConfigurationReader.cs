namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione
{
    public interface IAppConfigurationReader
    {
        IConfigurazioneStc GetParametriStc();
        IConfigurazionePerComune GetParametriPerComune();
        IConfigurazioneSigeproSecurity GetParametriSigeproSecurity();
        string GetSetting(string name);
    }
}
