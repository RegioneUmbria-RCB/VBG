using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.Delta.Adapters
{
    internal class DeltaVerticalizzazioneParametriAdapter
    {
        ProtocolloLogs _logs;

        public string Url { get; private set; }
        public string Password { get; private set; }
        public string Registro { get; private set; }
        public string CodiceCC { get; private set; }
        public string Username { get; private set; }

        public DeltaVerticalizzazioneParametriAdapter(ProtocolloLogs logs, VerticalizzazioneProtocolloDelta paramVert)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_DELTA NON È ATTIVA");

            _logs = logs;
            EstraiParametri(paramVert);    
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloDelta paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.Url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_DELTA, RIGUARDANTE L'ENDPOINT DEL WEB SERVICE, NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Username))
                    throw new Exception("IL PARAMETRO USERNAME DELLA VERTICALIZZAZIONE PROTOCOLLO_DELTA NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Password))
                    throw new Exception("IL PARAMETRO PASSWORD DELLA VERTICALIZZAZIONE PROTOCOLLO_DELTA NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Registro))
                    throw new Exception("IL PARAMETRO REGISTRO DELLA VERTICALIZZAZIONE PROTOCOLLO_DELTA NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.CodiceCc))
                    throw new Exception("IL PARAMETRO CODICE_CC DELLA VERTICALIZZAZIONE PROTOCOLLO_DELTA NON E' STATO VALORIZZATO");
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloDelta paramVert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(paramVert);

                Url = paramVert.Url;
                Password = paramVert.Password;
                Registro = paramVert.Registro;
                CodiceCC = paramVert.CodiceCc;
                Username = paramVert.Username;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_DELTA", ex);
            }
        }
    }
}
