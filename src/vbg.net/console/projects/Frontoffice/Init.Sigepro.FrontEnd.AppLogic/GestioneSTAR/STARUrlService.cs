using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSTAR
{
    public class STARUrlService
    {
        CertificatoFirmaSTAR _certificato = new CertificatoFirmaSTAR();
        IConfigurazione<ParametriCart> _configurazione;

        public STARUrlService(IConfigurazione<ParametriCart> configurazione)
        {
            this._configurazione = configurazione;
        }

        private void VerificaUrlAccettatore()
        {
            if (String.IsNullOrEmpty(this._configurazione.Parametri.UrlAccettatore))
            {
                throw new ConfigurationErrorsException("L'url dell'accettatore non è stato configurato correttamente. Verificare i parametri della regola CART");
            }
        }

        public bool StarAttivo()
        {
            return !String.IsNullOrEmpty(this._configurazione.Parametri.UrlAccettatore);
        }

        public string GetUrlNuovaDomanda(string cf, string nome, string cognome, string sesso)
        {
            VerificaUrlAccettatore();

            var qs = GetQuerystringFirmata(cf,nome, cognome, sesso, DateTime.Now);

            return String.Format("{0}/nuovadomanda?{1}", this._configurazione.Parametri.UrlAccettatore, qs);
        }

        public string GetUrlIstanzeInSospeso(string cf, string nome, string cognome, string sesso)
        {
            VerificaUrlAccettatore();

            var qs = GetQuerystringFirmata(cf, nome, cognome, sesso, DateTime.Now);

            return String.Format("{0}/incompilazione?{1}", this._configurazione.Parametri.UrlAccettatore, qs);
        }

        private string GetQuerystringFirmata(string cf, string nome, string cognome, string sesso, DateTime time)
        {
            var qs = new Querystring(cf, nome, cognome, sesso, time);
            var cert = new QuerystringCertificate(_certificato.GetCertificato());

            return qs.SignWithCertificate(cert);
        }

        public string GetUrlLeMiePratiche(string cf, string nome, string cognome, string sesso)
        {
            VerificaUrlAccettatore();

            var qs = GetQuerystringFirmata(cf, nome, cognome, sesso, DateTime.Now);

            return String.Format("{0}/lemiepratiche?{1}", this._configurazione.Parametri.UrlAccettatore, qs);
        }
    }
}
