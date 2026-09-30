using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    internal class ParametriServiziAreaRiservataBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriServiziAreaRiservata>
    {
        protected ParametriServiziAreaRiservataBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository) : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
        }

        public ParametriServiziAreaRiservata Build()
        {
            var cfg = this.GetConfig();

            return new ParametriServiziAreaRiservata(
                cfg.ParametriServiziConsoleAreaRiservata.UrlVisuraIstanzaConsole,
                cfg.ParametriServiziConsoleAreaRiservata.UrlRicercaPraticaConsole
                );
        }
    }
}
