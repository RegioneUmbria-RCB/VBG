using System.Configuration;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Configurazione.WebConfig
{
    public class ConfigurazioneLocalizzataCollection : ConfigurazioneCollectionBase<ConfigurazioneLocalizzata>
    {
        protected override object GetElementKey(ConfigurationElement element)
        {
            return ((ConfigurazioneLocalizzata)element).IdComune;
        }
    }
}
