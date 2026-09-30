using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    public class ParametriQuestionarioBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriQuestionarioSoddisfazione>
    {
        public ParametriQuestionarioBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
            : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
        }

        public ParametriQuestionarioSoddisfazione Build()
        {
            var cfg = this.GetConfig();

            return new ParametriQuestionarioSoddisfazione(cfg.QuestionarioSoddisfazione.Attivo);
        }
    }
}
