using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Kibernetes.Verticalizzazioni
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string Url { get; private set; }
        public long IstatEnte { get; private set; }
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string UfficioProtocollante { get; private set; }

        public VerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloKibernetes vert)
        {
            _logs = logs;
            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloKibernetes paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.Username))
                    throw new Exception("IL PARAMETRO USERNAME NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Password))
                    throw new Exception("IL PARAMETRO PASSWORD NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Url))
                    throw new Exception("IL PARAMETRO URL RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.CodiceIstat))
                    throw new Exception("PARAMETRO CODICEISTAT NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.UfficioProtocollante))
                    _logs.WarnFormat("IL PARAMETRO UFFICIO_PROTOCOLLANTE NON E' STATO VALORIZZATO");

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloKibernetes vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione Kibernetes");

                VerificaIntegritaParametri(vert);
                
                long codiceIstat;
                var isParseble = long.TryParse(vert.CodiceIstat, out codiceIstat);

                if (!isParseble)
                    throw new Exception(String.Format("IL PARAMETRO CODICEISTAT NON E' UN VALORE NUMERICO, VALORE: {0}", vert.CodiceIstat));

                Url = vert.Url;
                Username = vert.Username;
                Password = vert.Password;
                IstatEnte = Convert.ToInt32(vert.CodiceIstat);
                UfficioProtocollante = vert.UfficioProtocollante;

                _logs.Debug("Fine recupero valori da verticalizzazioni Kibernetes");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_KIBERNETES", ex);
            }
        }
    }
}
