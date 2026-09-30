using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.InsielMercato.Verticalizzazioni
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public bool EscludiClassifica { get; private set; }
        public string ModalitaSequenza { get; private set; }
        public string Password { get; private set; }
        public string Url { get; private set; }
        public bool UsaWsClassifiche { get; private set; }
        public string Username { get; private set; }
        public string Registro { get; private set; }
        public string CodiceUfficioOperante { get; private set; }
        
        public VerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloInsielmercato vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_INSIELMERCATO non è attiva");

            _logs = logs;
            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloInsielmercato paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.Username))
                    throw new Exception("IL PARAMETRO USERNAME NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Password))
                    throw new Exception("IL PARAMETRO PASSWORD NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Url))
                    throw new Exception("IL PARAMETRO URL RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Registro))
                    throw new Exception("IL PARAMETRO REGISTRO NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.CodiceUfficioOperante))
                    throw new Exception("IL PARAMETRO CODICE_UFFICIO_OPERANTE NON E' STATO VALORIZZATO");

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private VerticalizzazioneProtocolloInsielmercato EstraiParametri(VerticalizzazioneProtocolloInsielmercato paramVert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(paramVert);

                EscludiClassifica = paramVert.EscludiClassifica == "1";
                ModalitaSequenza = paramVert.ModalitaSequenza;
                Password = paramVert.Password;
                Url = paramVert.Url;
                UsaWsClassifiche = paramVert.UsaWsClassifiche == "1";
                Username = paramVert.Username;
                Registro = paramVert.Registro;
                CodiceUfficioOperante = paramVert.CodiceUfficioOperante;

                return paramVert;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_INSIELMERCATO", ex);
            }
        }
    }
}
