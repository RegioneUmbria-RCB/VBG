using System.Configuration;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Configurazione.WebConfig
{
    public class ConfigurazioneSpecializzazioneStcCollection : ConfigurazioneCollectionBase<ConfigurazioneSpecializzazioneStc>
    {

        protected override object GetElementKey(ConfigurationElement element)
        {
            return ((ConfigurazioneSpecializzazioneStc)element).Software;
        }
    }
}
