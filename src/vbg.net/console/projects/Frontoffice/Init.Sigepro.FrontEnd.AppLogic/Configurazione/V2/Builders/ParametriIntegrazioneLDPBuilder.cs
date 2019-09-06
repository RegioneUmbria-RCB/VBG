using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriIntegrazioneLDPBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriIntegrazioneLDP>
    {
        internal ParametriIntegrazioneLDPBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository repo)
            : base(aliasSoftwareResolver, repo)
        {

        }

        public ParametriIntegrazioneLDP Build()
        {
            var config = GetConfig();

            return new ParametriIntegrazioneLDP(config.SitLDP.UrlPresentazioneDomandaLdp, config.SitLDP.UrlServiziDomandaLdp, config.SitLDP.Username, config.SitLDP.Password, config.SitLDP.UrlGenerazionePdfDomanda);
        }
    }
}
