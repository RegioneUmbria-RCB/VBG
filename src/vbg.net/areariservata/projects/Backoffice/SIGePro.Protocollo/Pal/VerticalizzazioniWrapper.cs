using Init.SIGePro.Manager.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal
{
    public class VerticalizzazioniWrapper
    {
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string CodiceIstat { get; private set; }
        public string CodiceAoo { get; private set; }
        public string UrlBaseWs { get; private set; }
        public bool InvioPec { get; private set; }
        public bool GestisciAssegnazioni { get; private set; }

        VerticalizzazioneProtocolloPal _vert;

        public VerticalizzazioniWrapper(VerticalizzazioneProtocolloPal vert)
        {
            if (!vert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_PAL NON E' ATTIVA");

            _vert = vert;

            EstraiParametri();
        }

        private void VerificaIntegritaParametri()
        {
            if (String.IsNullOrEmpty(_vert.Username))
                throw new Exception("IL PARAMETRO USERNAME DELLA REGOLA PROTOCOLLO_PAL NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.Password))
                throw new Exception("IL PARAMETRO PASSWORD DELLA REGOLA PROTOCOLLO_PAL NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.UrlBase))
                throw new Exception("IL PARAMETRO URL_BASE DELLA REGOLA PROTOCOLLO_PAL NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.CodiceIstat))
                throw new Exception("IL PARAMETRO CODICE_ISTAT DELLA REGOLA PROTOCOLLO_PAL NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_vert.CodiceAoo))
                throw new Exception("IL PARAMETRO CODICE_AOO DELLA REGOLA PROTOCOLLO_PAL NON E' STATO VALORIZZATO");
        }

        private void EstraiParametri()
        {
            //this.CodiceUtente = "gruppopa";
            //this.Password = "protocollows";
            //this.CodiceIstat = "048221";
            //this.CodiceAoo = "adg_tosc";
            //this.BaseUrlWs = "http://cw2.gruppoapra.com/cw2/services/";

            this.Username = _vert.Username;
            this.Password = _vert.Password;
            this.CodiceIstat = _vert.CodiceIstat;
            this.CodiceAoo = _vert.CodiceAoo;
            this.UrlBaseWs = _vert.UrlBase;
            this.InvioPec = _vert.InvioPec == "1";
            this.GestisciAssegnazioni = _vert.GestisciAssegnatari == "1";
        }
    }
}
