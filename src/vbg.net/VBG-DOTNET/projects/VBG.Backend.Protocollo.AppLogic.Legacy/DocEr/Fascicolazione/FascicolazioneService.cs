using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Protocollo.ProtocolloDocErFascicolazioneService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Fascicolazione
{
    public class FascicolazioneService
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _endPointAddress;
        string _token;
        private readonly ClientFascicolazioneServiceCreator _clientFascicolazioneServiceCreator;

        public FascicolazioneService(string endPoinAddress, string token, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPoinAddress;
            _token = token;
            _clientFascicolazioneServiceCreator = new ClientFascicolazioneServiceCreator(_logs, bindingFactory, _endPointAddress);
        }

        public CreaFascicolo.esito CreaFascicolo(KeyValuePair[] metadati)
        {
            try
            {
                using (var ws = this._clientFascicolazioneServiceCreator.CreateClient())
                {
                    _logs.Info("CHIAMATA A CREA FASCICOLO");
                    var responseXml = ws.Service.creaFascicolo(_token, metadati);
                    _logs.InfoFormat("CREAZIONE FASCICOLO AVVENUTO CON SUCCESSO, CREATO FASCICOLO {0}", responseXml);

                    //ws.updateACLFascicolo(_token);

                    var response = (CreaFascicolo.esito)_serializer.Deserialize(responseXml, typeof(CreaFascicolo.esito));

                    if (response == null)
                        throw new System.Exception("RISPOSTA AL CREAFASCICOLO NULL");

                    if (response.codice != "0")
                        throw new System.Exception(String.Format("ERRORE DURANTE IL CREA FASCICOLO, CODICE ERRORE: {0}, DESCRIZIONE: {1}", response.codice, response.descrizione));

                    if(response.esito_fascicolo.Length == 0)
                        throw new System.Exception("ERRORE DURANTE IL CREA FASCICOLO, NON SONO STATI RESTITUITI DATI");

                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DI CREAZIONE FASCICOLO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void UpdateAclFascicolo(Dictionary<string, string> ruoli, KeyValuePair[] metadati, string username)
        {
            try
            {
                using (var ws = this._clientFascicolazioneServiceCreator.CreateClient())
                {
                    if (ruoli == null)
                    {
                        _logs.WarnFormat("ATTENZIONE, L'UTENTE {0} NON HA SETTATO ALCUN RUOLO IN QUANTO IL DATO PROVIENE DALLA VERTICALIZZAZIONE", username);
                        return;
                    }

                    var ruoliDocEr = ruoli.Keys.Select(x => new KeyValuePair { key = x, value = ruoli[x] }).ToArray();

                    _logs.Info("CHIAMATA A UPDATE ACL FASCICOLO");
                    var response = ws.Service.updateACLFascicolo(_token, metadati, ruoliDocEr);

                    if (!response)
                    {
                        _logs.Warn("CHIAMATA A UPDATE ACL FASCICOLO NON ANDATA A BUON FINE");
                        return;
                    }

                    _logs.Info("CHIAMATA A UPDATE ACL FASCICOLO AVVENUTA CON SUCCESSO");
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DOCUMENTALE DURANTE UPDATE ACL FASCICOLO, ERRORE: {0}", ex.Message), ex);
            }
        }

        public esito Fascicola(long unitaDocumentale, string datiFascicolo)
        {
            try
            {
                using (var ws = this._clientFascicolazioneServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("CHIAMATA A FASCICOLABYID, unita documentale: {0}, dati fascicolo: {1}", unitaDocumentale.ToString(), datiFascicolo);
                    var responseXml = ws.Service.fascicolaById(_token, unitaDocumentale, datiFascicolo);

                    var response = (esito)_serializer.Deserialize(responseXml, typeof(esito));
                    _logs.InfoFormat("FASCICOLAZIONE AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DI FASCICOLAZIONE, ERRORE: {0}", ex.Message), ex);
            }            
        }
    }
}
