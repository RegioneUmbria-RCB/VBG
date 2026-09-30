using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    public class ParametriScrivaniaEntiTerziBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriScrivaniaEntiTerzi>
    {
        public ParametriScrivaniaEntiTerziBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository) :
            base(aliasResolver, configurazioneAreaRiservataRepository)
        {
        }

        public ParametriScrivaniaEntiTerzi Build()
        {
            var cfg = this.GetConfig();
            var config = new ParametriScrivaniaEntiTerzi(false, "");


            if (cfg.ScrivaniaEntiTerzi != null)
            {
                config = new ParametriScrivaniaEntiTerzi(true, cfg.ScrivaniaEntiTerzi.SoftwareAttivazione);
            }

            return config;
        }
    }
}
