using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriAccessoAttiBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriAccessoAtti>
    {
        public ParametriAccessoAttiBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository) : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
        }

        public ParametriAccessoAtti Build()
        {
            var cfg = this.GetConfig();

            return new ParametriAccessoAtti(cfg.AccessoAgliAtti?.MostraDatiMovimenti ?? false);
        }
    }
}
