using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using log4net;


namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriMenuBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriMenuV2>
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ParametriMenuBuilder));

        public ParametriMenuBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
            : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
        }

        ParametriMenuV2 IConfigurazioneBuilder<ParametriMenuV2>.Build()
        {
            var cfg = this.GetConfig();

            return new ParametriMenuV2(cfg.CodiceOggettoMenuXml);
        }
    }
}
