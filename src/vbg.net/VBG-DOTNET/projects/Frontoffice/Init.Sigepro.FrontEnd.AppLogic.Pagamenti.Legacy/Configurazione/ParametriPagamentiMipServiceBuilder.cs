using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.Configurazione
{
    internal class ParametriPagamentiMipServiceBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriConfigurazionePagamentiMIP>
    {
        private readonly IAppConfigurationReader _appConfigurationReader;

        public ParametriPagamentiMipServiceBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository, IAppConfigurationReader appConfigurationReader)
            : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
            this._appConfigurationReader = appConfigurationReader;
        }

        public ParametriConfigurazionePagamentiMIP Build()
        {
            var cfg = this.GetConfig();
            var mip = cfg.ConfigurazionePagamentiMIP;

            var idPortale = mip.PortaleID;
            var idServizio = mip.IdServizio;
            var commitNotifica = "N"; //"S";
            var proxyAddress = mip.IndirizzoProxy;
            var componentName = mip.IdentificativoComponente;
            var chiaveSegreta = mip.PasswordChiamate;
            var urlServizi = mip.UrlServerPagamento;
            var emailPortale = mip.EmailPortale;
            var timeWindow = mip.WindowMinutes;
            var tipoPagamentoDefault = mip.CodiceTipoPagamento;

            if (!String.IsNullOrEmpty(mip.PortaProxy))
            {
                proxyAddress += ":" + mip.PortaProxy;
            }

            var urlBase = "~/Reserved/InserimentoIstanza/Pagamenti/PagamentoMIP.aspx?reason={0}";
            var urlNotifica = String.IsNullOrEmpty(mip.UrlNotifica) ? "~/public/pagamenti/callbackpagamenti.aspx" : mip.UrlNotifica;
            var urlHome = String.Format(urlBase, "HOME");
            var urlBack = String.Format(urlBase, "BACK");
            var urlErrore = String.Format(urlBase, "ERRORE");
            var urlRitorno = String.Format(urlBase, "OK");
            var verticalizzazioneAttiva = mip.VerticalizzazioneAttiva;


            var xslRicevuta = "~/Reserved/InserimentoIstanza/Pagamenti/RicevutaPagamentiMIP.xsl";
            var intestazioneRicevuta = mip.IntestazioneRicevuta;
            var tipoClientMip = this._appConfigurationReader.GetSetting("PayServerClientWrapper");
            var parametriESED = new ParametriConfigurazionePagamentiMIP.ParametriConfigurazionePagamentiESED(mip.ChiaveIV, mip.CodiceUtente, mip.CodiceEnte, mip.TipoUfficio, mip.CodiceUfficio, mip.TipologiaServizio);

            return new ParametriConfigurazionePagamentiMIP(verticalizzazioneAttiva, idPortale, idServizio, commitNotifica, proxyAddress, componentName,
                                                            chiaveSegreta, urlServizi, urlNotifica, urlHome, urlBack,
                                                            urlErrore, urlRitorno, emailPortale, timeWindow, xslRicevuta,
                                                            tipoPagamentoDefault, intestazioneRicevuta, parametriESED, tipoClientMip);
        }
    }
}
