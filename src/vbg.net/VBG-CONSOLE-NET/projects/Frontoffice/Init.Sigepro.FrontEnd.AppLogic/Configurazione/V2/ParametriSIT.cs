using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriSIT : IParametriConfigurazione
    {
        private static class Constants
        {
            public const string WS_SIT = "/WebServices/WsSIGePro/wssit.asmx";
        }

        private Dictionary<string, ConfigurazioneSitDto> ConfigurazioniSIT = new Dictionary<string, ConfigurazioneSitDto>();


        public ParametriSIT(List<ConfigurazioneSitDto> configurazioniSit)
        {
            foreach (var config in configurazioniSit)
            {
                ConfigurazioniSIT.Add(config.CodiceComune, config);
            }
        }

        public bool SitAttivo(string codiceComune) => ConfigurazioniSIT.ContainsKey(codiceComune);

        public ConfigurazioneSitDto GetConfigurazioneSIT(string codiceComune) => SitAttivo(codiceComune) ? ConfigurazioniSIT.FirstOrDefault(x => x.Key == codiceComune).Value : null;

        public bool RedirectToSitPage(string codiceComune)
        {
            var cfg = GetConfigurazioneSIT(codiceComune);
            if (cfg == null)
                return false;
            return cfg.ForzaStepLocalizzazioniSit;
        }
    }
}
