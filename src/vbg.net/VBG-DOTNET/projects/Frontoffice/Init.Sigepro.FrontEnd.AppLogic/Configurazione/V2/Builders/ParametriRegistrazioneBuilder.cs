using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriRegistrazioneBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriRegistrazione>
    {

        public ParametriRegistrazioneBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repo) : base(aliasResolver, repo)
        {

        }

        #region IBuilder<ParametriRegistrazione> Members

        public ParametriRegistrazione Build()
        {
            var cfg = this.GetConfig();

            return new ParametriRegistrazione(cfg.MessaggioRegistrazioneCompletata);
        }

        #endregion
    }
}
