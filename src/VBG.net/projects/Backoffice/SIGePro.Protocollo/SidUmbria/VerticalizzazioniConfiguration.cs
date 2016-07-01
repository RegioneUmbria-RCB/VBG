using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.SidUmbria
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string Token { get; private set; }
        public string Service { get; private set; }
        public string Url { get; private set; }
        public string DestinatarioCC { get; private set; }

        public VerticalizzazioniConfiguration(VerticalizzazioneProtocolloSidumbria vert, ProtocolloLogs logs)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_SIDUMBRIA non è attiva");

            _logs = logs;

            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloSidumbria vert)
        {
            try
            {
                if (String.IsNullOrEmpty(vert.Url))
                    throw new Exception("IL PARAMETRO URL NON E' STATO VALORIZZATO");
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloSidumbria vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(vert);


                Url = vert.Url;
                Token = vert.Token;
                Service = vert.Service;
                DestinatarioCC = vert.DestinatarioCc;

                _logs.Debug("Fine recupero valori da verticalizzazioni");
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_SIDUMBRIA, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
