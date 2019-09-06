using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Archiflow
{
    public class VerticalizzazioniWrapper
    {
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string CodiceEnte { get; private set; }
        public string Url { get; private set; }

        ProtocolloLogs _logs;
        VerticalizzazioneProtocolloArchiflow _paramVert;

        public VerticalizzazioniWrapper(VerticalizzazioneProtocolloArchiflow paramVert, ProtocolloLogs logs)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_ARCHIFLOW NON E' ATTIVA");

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

            if (String.IsNullOrEmpty(_paramVert.CodiceEnte))
                throw new Exception("IL PARAMETRO CODICE_ENTE NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

            if (String.IsNullOrEmpty(_paramVert.Url))
                throw new Exception("IL PARAMETRO URL RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");
        }

        private void EstraiParametri()
        {
            Username = _paramVert.Username;
            Password = _paramVert.Password;
            CodiceEnte = _paramVert.CodiceEnte;
            Url = _paramVert.Url;
        }
    }
}
