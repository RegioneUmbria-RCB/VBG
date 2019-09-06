using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.EProt
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string UrlBase { get; private set; }
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string Registro { get; private set; }
        public bool EscludiClassifica { get; private set; }
        public IEnumerable<string> EstensioniAllegatiAccettate { get; private set; }

        public VerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloEProt paramVert)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_EPROT NON E' ATTIVA");

            _logs = logs;
            EstraiParametri(paramVert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloEProt paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.UrlBase))
                    throw new Exception("IL PARAMETRO URL_BASE NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' OBBLIGATORIO");
                
                if (String.IsNullOrEmpty(paramVert.Username))
                    throw new Exception("IL PARAMETRO USERNAME NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

                if (String.IsNullOrEmpty(paramVert.Password))
                    throw new Exception("IL PARAMETRO PASSWORD NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloEProt paramVert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");


                VerificaIntegritaParametri(paramVert);
                
                UrlBase = paramVert.UrlBase;
                Username = paramVert.Username;
                Password = paramVert.Password;
                Registro = paramVert.Registro;
                EscludiClassifica = paramVert.EscludiClassifica == "1";
                if (!String.IsNullOrEmpty(paramVert.EstensioniAllegati))
                    EstensioniAllegatiAccettate = paramVert.EstensioniAllegati.Split(';');    
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_EPROT", ex);
            }
        }
    }
}
