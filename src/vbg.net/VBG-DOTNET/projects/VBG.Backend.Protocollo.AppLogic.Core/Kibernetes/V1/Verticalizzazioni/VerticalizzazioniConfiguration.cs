using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1.Verticalizzazioni
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

                if (!paramVert.CodiceIstat.HasValue)
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

                Url = vert.Url;
                Username = vert.Username;
                Password = vert.Password;
                IstatEnte = vert.CodiceIstat.Value;
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
