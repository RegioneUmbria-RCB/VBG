using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriLivornoConfigurazioneDrupalBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriLivornoConfigurazioneDrupal>
    {
        public ParametriLivornoConfigurazioneDrupalBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
            : base(aliasResolver, configurazioneAreaRiservataRepository)
        {

        }

        public ParametriLivornoConfigurazioneDrupal Build()
        {
            var cfg = this.GetConfig();



            return new ParametriLivornoConfigurazioneDrupal(cfg.ServiziCittadino.UrlWsModulisticaDrupal);
        }
    }
}
