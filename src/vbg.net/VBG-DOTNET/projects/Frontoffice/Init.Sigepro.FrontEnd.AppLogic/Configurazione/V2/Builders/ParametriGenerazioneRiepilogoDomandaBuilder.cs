using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriGenerazioneRiepilogoDomandaBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriGenerazioneRiepilogoDomanda>
    {
        public ParametriGenerazioneRiepilogoDomandaBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository repo)
            : base(aliasSoftwareResolver, repo)
        {

        }

        public ParametriGenerazioneRiepilogoDomanda Build()
        {
            var config = this.GetConfig();

            return new ParametriGenerazioneRiepilogoDomanda(config.ConfigurazioneRiepilogoDomanda.FlagIncludiSchede);
        }
    }
}
