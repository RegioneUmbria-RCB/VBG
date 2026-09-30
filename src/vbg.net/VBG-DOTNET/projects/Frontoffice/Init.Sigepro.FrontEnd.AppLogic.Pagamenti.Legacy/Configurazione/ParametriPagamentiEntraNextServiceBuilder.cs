using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.Configurazione
{
    internal class ParametriPagamentiEntraNextServiceBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriConfigurazionePagamentiEntraNext>
    {
        public ParametriPagamentiEntraNextServiceBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository) : base(aliasResolver, configurazioneAreaRiservataRepository)
        {

        }

        public ParametriConfigurazionePagamentiEntraNext Build()
        {
            var cfg = this.GetConfig();
            var entraNextConf = cfg.PagamentiEntraNext;
            string urlRitorno = "~/Reserved/InserimentoIstanza/Pagamenti/PagamentoEntraNext.aspx?";
            string urlNotififica = "";//"http://devel3.init.gruppoinit.it:9090/AreaRiservata/Public/pagamenti/EntraNext/NotificaPagamentoEntraNext.ashx?";
            return new ParametriConfigurazionePagamentiEntraNext(entraNextConf.UrlWs, entraNextConf.IdentificativoConnettore, entraNextConf.CodiceFiscaleEnte, entraNextConf.Versione, entraNextConf.Identificativo, entraNextConf.Username, entraNextConf.PasswordMd5, urlRitorno, urlNotififica, entraNextConf.CodiceTipoPagamento);
        }
    }
}
