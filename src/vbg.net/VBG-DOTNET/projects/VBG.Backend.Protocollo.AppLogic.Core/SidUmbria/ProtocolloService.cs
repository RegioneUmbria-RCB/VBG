using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria
{
    public class ProtocolloService
    {
        VerticalizzazioniConfiguration _vert;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        authToken _token;
        private readonly ProtocollazioneClientServiceCreator _protocollazioneClientServiceCreator;

        public ProtocolloService(VerticalizzazioniConfiguration vert, string uo, string ruolo, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            _vert = vert;
            _logs = logs;
            _serializer = serializer;
            _protocollazioneClientServiceCreator = new ProtocollazioneClientServiceCreator(_logs, bindingFactory, _vert.Url);

            _token = new authToken { token = uo, service = ruolo };
        }

        public identificatore LeggiEstremi(string idRichiesta)
        {
            try
            {
                using (var ws = _protocollazioneClientServiceCreator.CreateClient())
                {
                    _logs.Info($"RICHIESTA DEGLI ESTREMI DEL DOCUMENTO {idRichiesta}");
                    var response = ws.Service.getEstremiRichiesta(_token, idRichiesta);
                    _logs.Info("RISPOSTA OTTENUTA DA getEstremiRichiesta");

                    if (response == null)
                    {
                        _logs.Info($"LA CHIAMATA A getEstremiRichiesta della richiesta {idRichiesta} NON CONTIENE ALCUN VALORE (VALORE NULL)");
                        return null;
                    }

                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response.elCode == ProtocolloSidUmbriaConstants.MessaggioErroreOK04Coda)
                        return null;

                    if (response.esito == ProtocolloSidUmbriaConstants.EsitoKo)
                        throw new Exception($"ERRORE RESTITUITO DAL WEB SERVICE DI PROTOCOLLAZIONE DURANTE IL RECUPERO DEGLI ESTREMI DEL PROTOCOLLO, CODICE ERRORE: {response.elCode}, DESCRIZIONE ERRORE: {response.elMessage}");

                    if (response.identificatore == null)
                        throw new Exception($"NON E' POSSIBILE RECUPERARE I DATI DALL'IDENTIFICATORE, CODICE MESSAGGIO RICEVUTO DAL WEB SERVICE: {response.elCode}, MESSAGGIO: {response.elMessage}");

                    _logs.Info($"ESTREMI DEL DOCUMENTO {idRichiesta} RECUPERATI CORRETTAMENTE, NUMERO {response.identificatore.numero}, ANNO {response.identificatore.anno}, DATA: {response.identificatore.data}");

                    return response.identificatore;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE, ERRORE: {ex.Message}", ex);
            }
        }

        public esitoElaborazione Protocolla(infoProtocollo request)
        {
            try
            {
                using (var ws = this._protocollazioneClientServiceCreator.CreateClient())
                {
                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE, TOKEN: {0}, SERVICE: {1}", _token.token, _token.service);
                    var response = ws.Service.protocollazioneDocumento(_token, request);

                    if (response.esito == ProtocolloSidUmbriaConstants.EsitoKo)
                        throw new Exception($"ERRORE RESTITUITO DAL WEB SERVICE DI PROTOCOLLAZIONE, CODICE ERRORE: {response.elCode}, DESCRIZIONE ERRORE: {response.elMessage}");

                    if (response.identificatore != null)
                        _logs.Info($"DATI IDENTIFICATORE: NUMERO {response.identificatore.numero}, ANNO {response.identificatore.anno}, DATA: {response.identificatore.data}, CODICE ERRORE: {response.elCode}");

                    _logs.Info("PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE IN ATTESA DELLA RESTITUZIONE TRAMITE METODO GETESTREMIDOCUMENTO");

                    return response;
                }
            }
            catch (Exception ex)
            {

                throw new Exception($"ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE, ERRORE: {ex.Message}", ex);
            }
        }
    }
}
