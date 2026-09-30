using System.Configuration;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Configurazione.WebConfig
{
    public class ConfigurazioneStcCollection : ConfigurazioneCollectionBase<ConfigurazioneStc>
    {
        protected override object GetElementKey(ConfigurationElement element)
        {
            return ((ConfigurazioneStc)element).IdComune;
        }
    }
}
