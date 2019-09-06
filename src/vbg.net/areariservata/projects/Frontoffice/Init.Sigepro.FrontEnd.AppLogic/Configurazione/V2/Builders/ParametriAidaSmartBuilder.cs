using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriAidaSmartBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriAidaSmart>
    {
        public ParametriAidaSmartBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository repo) : base(aliasSoftwareResolver, repo)
        {
        }

        public ParametriAidaSmart Build()
        {
            var cfg = GetConfig();

            return new ParametriAidaSmart(cfg.AidaSmart.CrossLoginUrl, cfg.AidaSmart.UrlNuovaDomanda, cfg.AidaSmart.UrlIstanzeInSospeso);
        }
    }
}
