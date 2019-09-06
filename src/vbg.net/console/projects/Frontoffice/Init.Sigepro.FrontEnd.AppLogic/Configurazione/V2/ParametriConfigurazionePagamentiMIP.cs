using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.Pagamenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriConfigurazionePagamentiMIP : PagamentiSettings, IParametriConfigurazione
    {
        public class ParametriConfigurazionePagamentiESED
        {
            public readonly string ChiaveIV;
            public readonly string CodiceUtente;
            public readonly string CodiceEnte;
            public readonly string TipoUfficio;
            public readonly string CodiceUfficio;
            public readonly string TipologiaServizio;

            public ParametriConfigurazionePagamentiESED(string chiaveIV, string codiceUtente,string codiceEnte, string tipoUfficio, string codiceUfficio, string tipologiaServizio)
            {
                this.ChiaveIV = chiaveIV;
                this.CodiceUtente = codiceUtente;
                this.CodiceEnte = codiceEnte;
                this.TipoUfficio = tipoUfficio;
                this.CodiceUfficio = codiceUfficio;
                this.TipologiaServizio = tipologiaServizio;
            }
        }


        public readonly string XslRicevutaPagamento;

        public ParametriConfigurazionePagamentiMIP(string idPortale, string idServizio, string commitNotifica, string proxyAddress,
                                        string componentName, string chiaveSegreta, string urlServizi, string urlNotifica,
                                        string urlHome, string urlBack, string urlErrore, string urlRitorno, string emailPortale, string timeWindow,
                                        string xslRicevutaPagamento, string tipoPagamento, string intestazioneRicevuta, ParametriConfigurazionePagamentiESED parametriEsed) :
            base(idPortale, idServizio, commitNotifica, proxyAddress, 
                                        componentName, chiaveSegreta, urlServizi, urlNotifica,
                                        urlHome, urlBack, urlErrore, urlRitorno, emailPortale, timeWindow, tipoPagamento, intestazioneRicevuta, parametriEsed.ChiaveIV, parametriEsed.CodiceUtente, parametriEsed.CodiceEnte, parametriEsed.TipoUfficio, parametriEsed.CodiceUfficio, parametriEsed.TipologiaServizio)
        {
            this.XslRicevutaPagamento = xslRicevutaPagamento;
        }
    }
}
