using System.Configuration;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Configurazione.WebConfig
{
    public class ConfigurazioneSpecializzazioneProcessFileCollection : ConfigurazioneCollectionBase<ConfigurazioneSpecializzazioneProcessFile>
    {
        protected override object GetElementKey(ConfigurationElement element)
        {
            return ((ConfigurazioneSpecializzazioneProcessFile)element).Software;
        }
    }
}
