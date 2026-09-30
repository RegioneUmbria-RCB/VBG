using Init.Sigepro.FrontEnd.WebForms.AppLogic.Configurazione.WebConfig;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
    public class ConfigurazioneFrontEndWebConfig : ConfigurationSection, IConfigurazioneApplicazione
    {
        [ConfigurationProperty("sigeproSecurity")]
        public ConfigurazioneSigeproSecurity SigeproSecurity
        {
            get { return this["sigeproSecurity"] as ConfigurazioneSigeproSecurity; }
            set { this["sigeproSecurity"] = value; }
        }

        [ConfigurationProperty("parametriPerIdComune")]
        public ConfigurazioneLocalizzataCollection ParametriPerIdComune
        {
            get
            {
                return this["parametriPerIdComune"] as ConfigurazioneLocalizzataCollection;
            }
        }

        [ConfigurationProperty("parametriStc", IsRequired = false)]
        public ConfigurazioneStcCollection ParametriStc
        {
            get
            {
                return this["parametriStc"] as ConfigurazioneStcCollection;
            }
        }

        public IConfigurazioneStc GetParametriStc(string idComune, string idComuneDefault)
        {
            if (this.ParametriStc[idComune] == null)
            {
                idComune = idComuneDefault;
            }

            return this.ParametriStc[idComune];
        }
    }
}
