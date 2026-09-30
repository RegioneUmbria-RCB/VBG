using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

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
            var cfg = this.GetConfig();

            return new ParametriLocalizzazioni(cfg.CiviciNumerici, cfg.EsponentiNumerici);
        }
    }
}
