using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.JProtocollo2.Verticalizzazioni
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string Codiceente { get; private set; }
        public string Mittente { get; private set; }
        public string Mittenteinterno { get; private set; }
        public string Mittente_libero { get; private set; }
        public string Tramitedefault { get; private set; }
        public string Uo { get; private set; }
        public string Url { get; private set; }
        public string Username { get; private set; }

        public VerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloJprotocollo vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_JPROTOCOLLO non è attiva");

            _logs = logs;

            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloJprotocollo paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.Url))
                    throw new Exception("IL PARAMETRO URL NON E' STATO VALORIZZATO");

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloJprotocollo vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(vert);

                Codiceente = vert.Codiceente;
                Mittente = vert.Mittente;
                Mittenteinterno = vert.Mittenteinterno;
                Mittente_libero = vert.MittenteLibero;
                Tramitedefault = vert.Tramitedefault;
                Uo = vert.Uo;
                Url = vert.Url;
                Username = vert.Username;

                _logs.Debug("Fine recupero valori da verticalizzazioni");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_JPROTOCOLLO", ex);
            }
        }
    }
}
