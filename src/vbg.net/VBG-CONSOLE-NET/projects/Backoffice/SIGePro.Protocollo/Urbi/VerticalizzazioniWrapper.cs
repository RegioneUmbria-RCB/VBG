using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi
{
    public class VerticalizzazioniWrapper
    {
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string Url { get; private set; }
        public string Aoo { get; private set; }
        public string ReplaceTitolario { get; private set; }
        public bool InvioPec { get; private set; }
        public string DestinatariUtentiCOAutomatici { get; private set; }

        ProtocolloLogs _logs;
        VerticalizzazioneProtocolloUrbi _paramVert;

        public VerticalizzazioniWrapper(VerticalizzazioneProtocolloUrbi paramVert, ProtocolloLogs logs)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_URBI NON E' ATTIVA");

            _logs = logs;
            _paramVert = paramVert;

            EstraiParametri();
        }

        private void VerificaIntegritaParametri()
        {
            if (String.IsNullOrEmpty(_paramVert.Username))
                throw new Exception("IL PARAMETRO USERNAME NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

            if (String.IsNullOrEmpty(_paramVert.Password))
                throw new Exception("IL PARAMETRO PASSWORD NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

            if (String.IsNullOrEmpty(_paramVert.Url))
                throw new Exception("IL PARAMETRO URL RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(_paramVert.Aoo))
                throw new Exception("IL PARAMETRO AOO NON E' STATO VALORIZZATO");
        }

        private void EstraiParametri()
        {
            Username = _paramVert.Username;
            Password = _paramVert.Password;
            Url = _paramVert.Url;
            Aoo = _paramVert.Aoo;
            ReplaceTitolario = _paramVert.ReplaceTitolario;
            InvioPec = _paramVert.InvioPec == "1";
            DestinatariUtentiCOAutomatici = String.IsNullOrEmpty(_paramVert.DestinatarioCoAutomatici) ? "S" : _paramVert.DestinatarioCoAutomatici;
        }
    }
}
