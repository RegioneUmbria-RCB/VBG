using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Sigedo.Proxies.Allegati;

namespace Init.SIGePro.Protocollo.Sigedo.Services
{
    public class SigedoAllegatiService
    {

        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _endPointAddress;
        string _usernameWs;
        string _passwordWs;

        public SigedoAllegatiService(ProtocolloLogs logs, ProtocolloSerializer serializer, string endPointAddress, string usernameWs, string passwordWs)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPointAddress;
            _usernameWs = usernameWs;
            _passwordWs = passwordWs;
        }

        private AllegatiServiceService CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice Allegati Service Sigedo");
                
                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new Exception("IL PARAMETRO URL_WS_ALLEGATI DELLA VERTICALIZZAZIONE PROTOCOLLO_SIGEDO NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new AllegatiServiceService
                {
                    Url = _endPointAddress,
                    Credentials = new NetworkCredential(_usernameWs, _passwordWs),
                    PreAuthenticate = true
                };

                _logs.Debug("Fine creazione del webservice Allegati Service SIGEDO");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE, ERRORE: {0}", ex.Message), ex);
            }
        }

        internal Allegato GetAllegato(int codiceAllegato, string utenteApplicativo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("Chiamata a web method ricerca allegato, codice allegato {0}", codiceAllegato.ToString());
                    var response = ws.getAllegato(codiceAllegato, utenteApplicativo);
                    _logs.Info("Chiamata a web method ricerca allegato effettuata con successo");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DELL'ALLEGATO, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
