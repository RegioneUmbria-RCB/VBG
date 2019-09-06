using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using System.Net;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.Sigedo.Services
{
    internal class SigedoUoService
    {
        public static class Constants
        {
            public const string FILE_RECUPERO_SMISTAMENTI = "RecuperoSmistamento.xml";
        }

        private string _url;
        private string _matricola;
        private ProtocolloSerializer _serializer;
        private ProtocolloLogs _logs;
        //public readonly PersonaUo PersonaSmistamento;


        public SigedoUoService(ProtocolloLogs logs, ProtocolloSerializer serializer, string url, string matricola)
        {
            _url = url;
            _matricola = matricola;
            _logs = logs;
            _serializer = serializer;
            //PersonaSmistamento = GetPersonaSmistamento();
        }

        public PersonaUo GetPersonaSmistamento()
        {
            try
            {
                var wc = new WebClient();
                string urlCompleto = String.Concat(_url, _matricola);
                _logs.InfoFormat("Chiamata al servizio di restituzione uo, url cgi: {0}, matricola: {1}", urlCompleto, _matricola);
                string response = wc.DownloadString(urlCompleto);
                _logs.InfoFormat("Risposta al servizio di restituzione uo, url cgi: {0}, matricola: {1}, xml: {2}", urlCompleto, _matricola, response);

                var segnatura = (SigedoSegnaturaUo)_serializer.Deserialize(response, typeof(SigedoSegnaturaUo));

                PersonaUo retVal = null;

                if (segnatura.Persone != null && segnatura.Persone.Count == 1)
                {
                    retVal = segnatura.Persone[0];
                    _logs.InfoFormat("VALORE SMISTAMENTO TORNATO: {0}, PER L'OPERATORE: {1}", segnatura.Persone[0].CodiceUnitaOrganizzativa, _matricola);
                    
                    if (_logs.IsDebugEnabled)
                        _serializer.Serialize(Constants.FILE_RECUPERO_SMISTAMENTI, segnatura.Persone[0]);
                }
                /*else
                    _logs.WarnFormat("NON E' STATO POSSIBILE RECUPERARE IL VALORE DELLO SMISTAMENTO DAL SERVIZIO, VALORE TORNATO NULL PER L'OPERATORE: {0}", _matricola);*/

                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CHIAMATA ALL'APPLICAZIONE CGI CHE RECUPERA L'UO RELATIVA ALL'UTENTE LOGGATO", ex);
            }
        }
    }
}
