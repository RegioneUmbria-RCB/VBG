using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriLocalizzazioniBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriLocalizzazioni>
    {
        public ParametriLocalizzazioniBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
			: base(aliasResolver, configurazioneAreaRiservataRepository)
		{
		}

        public ParametriLocalizzazioni Build()
        {
            var cfg = GetConfig();

            return new ParametriLocalizzazioni(cfg.CiviciNumerici, cfg.EsponentiNumerici);
        }
    }
}
