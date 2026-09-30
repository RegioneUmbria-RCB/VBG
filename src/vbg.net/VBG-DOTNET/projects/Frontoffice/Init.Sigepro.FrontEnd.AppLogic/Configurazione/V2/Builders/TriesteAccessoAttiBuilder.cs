using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class TriesteAccessoAttiBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriTriesteAccessoAtti>
    {
        public TriesteAccessoAttiBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repository)
            : base(aliasResolver, repository)
        {

        }

        public ParametriTriesteAccessoAtti Build()
        {
            var cfg = this.GetConfig();

            return new ParametriTriesteAccessoAtti(cfg.TriesteAccessoAtti?.UrlTrasferimentoControllo, cfg.TriesteAccessoAtti?.UrlWebService);

        }
    }
}
