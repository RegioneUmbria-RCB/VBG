using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Configurazione.WebConfig
{
    public class ConfigurazioneSigeproSecurity : ConfigurationElement, IConfigurazioneSigeproSecurity
    {
        [ConfigurationProperty("username", IsRequired = true)]
        public string Username
        {
            get { return this["username"].ToString(); }
            set { this["username"] = value; }
        }

        [ConfigurationProperty("password", IsRequired = true)]
        public string Password
        {
            get { return this["password"].ToString(); }
            set { this["password"] = value; }
        }

        [ConfigurationProperty("loginServiceUrl", IsRequired = true)]
        public string LoginServiceUrl
        {
            get { return this["loginServiceUrl"].ToString(); }
            set { this["loginServiceUrl"] = value; }
        }
    }
}
