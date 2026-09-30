using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Microsis
{
    public class VerticalizzazioniWrapper
    {
        public string Url { get; private set; }
        public string Username { get; private set; }
        public string Password { get; private set; }

        ProtocolloLogs _logs;
        VerticalizzazioneProtocolloMicrosis _paramVert;

        public VerticalizzazioniWrapper(VerticalizzazioneProtocolloMicrosis paramVert, ProtocolloLogs logs)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_MICROSIS NON E' ATTIVA");

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
        }

        private void EstraiParametri()
        {
            VerificaIntegritaParametri();

            Username = _paramVert.Username;
            Password = _paramVert.Password;
            Url = _paramVert.Url;
        }
    }
}
