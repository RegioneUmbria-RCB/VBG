using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    public class ParametriIntegrazioneLDPBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriIntegrazioneLDP>
    {
        public ParametriIntegrazioneLDPBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository repo)
            : base(aliasSoftwareResolver, repo)
        {

        }

        public ParametriIntegrazioneLDP Build()
        {
            var config = this.GetConfig();

            return new ParametriIntegrazioneLDP(config.SitLDP.UrlPresentazioneDomandaLdp, config.SitLDP.UrlServiziDomandaLdp, config.SitLDP.Username, config.SitLDP.Password, config.SitLDP.UrlGenerazionePdfDomanda, config.SitLDP.UrlRitornoPraticaGIS, config.SitLDP.UrlPresentazioneIntegrazione);
        }
    }
}
