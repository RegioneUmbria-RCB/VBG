using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloSidUmbriaService;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.SidUmbria
{
    public class ProtocolloService
    {
        VerticalizzazioniConfiguration _vert;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        authToken _token;

        public ProtocolloService(VerticalizzazioniConfiguration vert, string uo, string ruolo, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _vert = vert;
            _logs = logs;
            _serializer = serializer;

            _token = new authToken { token = uo, service = ruolo };
        }

        private WSProtocollazioneSEIClient CreaWebService()
        {
            try
            {
                if (String.IsNullOrEmpty(_vert.Url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_SIDUMBRIA NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var endPointAddress = new EndpointAddress(_vert.Url);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                var ws = new WSProtocollazioneSEIClient(binding, endPointAddress);
                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE, ERRORE: {0}", ex.Message));
            }
        }

        public identificatore LeggiEstremi(string idRichiesta)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("RICHIESTA DEGLI ESTREMI DEL DOCUMENTO {0}", idRichiesta);
                    var response = ws.getEstremiRichiesta(_token, idRichiesta);
                    _logs.Info("RISPOSTA OTTENUTA DA getEstremiRichiesta");

                    if (response == null)
                    {
                        _logs.InfoFormat("LA CHIAMATA A getEstremiRichiesta della richiesta {0} NON CONTIENE ALCUN VALORE (VALORE NULL)", idRichiesta);
                        return null;
                    }

                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response.esito == "KO")
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DI PROTOCOLLAZIONE DURANTE IL RECUPERO DEGLI ESTREMI DEL PROTOCOLLO, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.elCode, response.elMessage));

                    if (response.identificatore == null)
                        throw new Exception(String.Format("NON E' POSSIBILE RECUPERARE I DATI DALL'IDENTIFICATORE, CODICE MESSAGGIO RICEVUTO DAL WEB SERVICE: {0}, MESSAGGIO: {1}", response.elCode, response.elMessage));

                    _logs.InfoFormat("ESTREMI DEL DOCUMENTO {0} RECUPERATI CORRETTAMENTE, NUMERO {1}, ANNO {2}, DATA: {3}", idRichiesta, response.identificatore.numero, response.identificatore.anno, response.identificatore.data);

                    return response.identificatore;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE, ERRORE: {0}", ex.Message));
            }
        }

        public void Protocolla(infoProtocollo request)
        {
            try
            {
                using(var ws = CreaWebService())
                {
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _logs.Info("CHIAMATA A PROTOCOLLAZIONE");
                    var response = ws.protocollazioneDocumento(_token, request);

                    if (response.esito == "KO")
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DI PROTOCOLLAZIONE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.elCode, response.elMessage));

                    if (response.identificatore != null)
                        _logs.InfoFormat("DATI IDENTIFICATORE: NUMERO {0}, ANNO {1}, DATA: {2}", response.identificatore.numero, response.identificatore.anno, response.identificatore.data);

                    _logs.Info("PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE IN ATTESA DELLA RESTITUZIONE TRAMITE METODO GETESTREMIDOCUMENTO");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE, ERRORE: {0}", ex.Message));
            }
        }
    }
}
