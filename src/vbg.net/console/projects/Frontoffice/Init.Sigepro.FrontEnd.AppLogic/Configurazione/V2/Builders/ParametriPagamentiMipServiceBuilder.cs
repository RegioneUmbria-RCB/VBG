using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.Pagamenti.MIP;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriPagamentiMipServiceBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriConfigurazionePagamentiMIP>
    {
        public ParametriPagamentiMipServiceBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
			: base(aliasResolver, configurazioneAreaRiservataRepository)
		{
		}

        public ParametriConfigurazionePagamentiMIP Build()
        {
            var cfg = GetConfig();
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
            
            if (!String.IsNullOrEmpty(mip.PortaProxy)) {
                proxyAddress += ":" + mip.PortaProxy;
            }
            
            var urlBase = "~/Reserved/InserimentoIstanza/Pagamenti/PagamentoMIP.aspx?reason={0}";
            var urlNotifica = String.IsNullOrEmpty(mip.UrlNotifica) ? "~/public/pagamenti/callbackpagamenti.aspx" : mip.UrlNotifica;
            var urlHome = String.Format(urlBase, "HOME");
            var urlBack = String.Format(urlBase, "BACK");
            var urlErrore = String.Format(urlBase, "ERRORE");
            var urlRitorno = String.Format(urlBase, "OK");
            
            var xslRicevuta = "~/Reserved/InserimentoIstanza/Pagamenti/RicevutaPagamentiMIP.xsl";
            var intestazioneRicevuta = mip.IntestazioneRicevuta;
            var parametriESED = new ParametriConfigurazionePagamentiMIP.ParametriConfigurazionePagamentiESED(mip.ChiaveIV, mip.CodiceUtente, mip.CodiceEnte, mip.TipoUfficio, mip.CodiceUfficio, mip.TipologiaServizio);
            
            return new ParametriConfigurazionePagamentiMIP(idPortale, idServizio, commitNotifica, proxyAddress, componentName, 
                                                            chiaveSegreta, urlServizi, urlNotifica, urlHome, urlBack, 
                                                            urlErrore, urlRitorno, emailPortale, timeWindow, xslRicevuta,
                                                            tipoPagamentoDefault, intestazioneRicevuta, parametriESED);
        }
    }
}
