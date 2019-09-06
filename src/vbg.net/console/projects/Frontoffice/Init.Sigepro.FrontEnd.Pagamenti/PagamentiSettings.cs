using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti
{
    public class PagamentiSettings
    {
        public readonly string IdPortale;
        public readonly string CommitNotifica;
        public readonly string IDServizio;
        public readonly string ProxyAddress;
        public readonly string ComponentName;
        public readonly string ChiaveSegreta;
        public readonly string UrlServizi;
        public readonly string UrlNotifica;
        public readonly string UrlHome;
        public readonly string UrlBack;
        public readonly string UrlErrore;
        public readonly string UrlRitorno;
        public readonly string EmailPortale;
        public readonly string WindowMinutes;
        public readonly string TipoPagamento;
        public readonly string IntestazioneRicevuta;

        public readonly string IV;
        public readonly string CodiceUtente;
        public readonly string CodiceEnte;
        public readonly string TipoUfficio;
        public readonly string CodiceUfficio;
        public readonly string TipologiaServizio;


        public PagamentiSettings(string idPortale, string idServizio, string commitNotifica, string proxyAddress,
                                        string componentName, string chiaveSegreta, string urlServizi, string urlNotifica,
                                        string urlHome, string urlBack, string urlErrore, string urlRitorno, string emailPortale,
                                        string windowMinutes, string tipoPagamento, string intestazioneRicevuta, string iv, string codiceUtente,
                                        string codiceEnte, string tipoUfficio, string codiceUfficio, string tipologiaServizio)
        {
            this.IdPortale = idPortale;
            this.IDServizio = idServizio;
            this.CommitNotifica = commitNotifica;
            this.ProxyAddress = proxyAddress;
            this.ComponentName = componentName;
            this.ChiaveSegreta = chiaveSegreta;
            this.UrlServizi = urlServizi;
            this.UrlNotifica = urlNotifica;
            this.UrlHome = urlHome;
            this.UrlBack = urlBack;
            this.UrlErrore = urlErrore;
            this.UrlRitorno = urlRitorno;
            this.EmailPortale = emailPortale;
            this.WindowMinutes = windowMinutes;
            this.TipoPagamento = tipoPagamento;
            this.IntestazioneRicevuta = intestazioneRicevuta;

            this.IV = iv;
            this.CodiceUtente = codiceUtente;
            this.CodiceEnte = codiceEnte;
            this.TipoUfficio = tipoUfficio;
            this.CodiceUfficio = codiceUfficio;
            this.TipologiaServizio = tipologiaServizio;
        }
    }
}
