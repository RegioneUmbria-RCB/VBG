using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.CoreServices.Configurazione
{
    public class ConfigurazioneSigeproSecurity : IConfigurazioneSigeproSecurity
    {
        public const string SectionName = "ConfigurazioneSigeproSecurity";

        public string LoginServiceUrl { get; set; }

        public string Password { get; set; }

        public string Username { get; set; }
    }
}
