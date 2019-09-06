using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriScrivaniaEntiTerziBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriScrivaniaEntiTerzi>
    {
        protected ParametriScrivaniaEntiTerziBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository) : 
            base(aliasResolver, configurazioneAreaRiservataRepository)
        {
        }

        public ParametriScrivaniaEntiTerzi Build()
        {
            var cfg = GetConfig();
            var config = new ParametriScrivaniaEntiTerzi(false, "");


            if (cfg.ScrivaniaEntiTerzi != null)
            {
                config = new ParametriScrivaniaEntiTerzi(true, cfg.ScrivaniaEntiTerzi.SoftwareAttivazione);
            }

            return config;
        }
    }
}
