using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    public class ParametriDomandaPNRRBuiler : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriDomandaPNRR>
    {
        public ParametriDomandaPNRRBuiler(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository) : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
        }

        public ParametriDomandaPNRR Build()
        {
            var cfg = this.GetConfig();

            if (cfg.DomandaOnLine.UrlLayoutConfigService.IndexOf("{alias}") != -1)
            {
                cfg.DomandaOnLine.UrlLayoutConfigService = cfg.DomandaOnLine.UrlLayoutConfigService.Replace("{alias}", this._aliasResolver.AliasComune);
            }

            return new ParametriDomandaPNRR(cfg.DomandaOnLine.UrlLayoutConfigService, cfg.DomandaOnLine.UrlHomepageComune, cfg.DomandaOnLine.UrlTerminiECondizioni, cfg.DomandaOnLine.ModalitaInvioNotifiche);
        }
    }
}
