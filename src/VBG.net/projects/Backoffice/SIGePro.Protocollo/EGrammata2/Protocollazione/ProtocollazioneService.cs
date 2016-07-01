using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Protocollo.EGrammata2.Verticalizzazioni;
using Microsoft.Web.Services2.Attachments;
using Init.SIGePro.Data;
using System.IO;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Response;
using Init.SIGePro.Protocollo.ProtocollaAllegatiEGrammata2;
using Init.SIGePro.Manager.Utils;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
{
    public class ProtocollazioneService : BaseService
    {
        public ProtocollazioneService(ProtocolloLogs logs, ProtocolloSerializer serializer, VerticalizzazioniConfiguration vert) : base(logs, serializer, vert)
        {

        }

        private ProtAllegatiClient CreaWebService()
        {
            try
            {
                if (String.IsNullOrEmpty(Parametri.UrlProtoAllegati))
                    throw new Exception("IL PARAMETRO URL_PROTO DELLA VERTICALIZZAZIONE PROTOCOLLO_EGRAMMATA2 NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");


                var endPointAddress = new EndpointAddress(Parametri.UrlProtoAllegati);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                var ws = new ProtAllegatiClient(binding, endPointAddress);

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("CREAZIONE DEL WEB SERVICE NON AVVENUTA CORRETTAMENTE, {0}", ex.Message));
            }
        }

        public Risposta Protocollazione(ProtocollaAllegatiRequestType request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    Logs.Info("CHIAMATA A PROTOCOLLAZIONE");
                    var responseWs = ws.ProtocollaAllegati(request);

                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, responseWs);

                    if (responseWs.code != 0)
                        throw new Exception(String.Format("ERRORE GENERATO DAL WEB SERVICE PONTE PROTOCOLLA ALLEGATI, ERRORE: {0}", responseWs.message));

                    string responseString = Base64Utils.Base64Decode(responseWs.message);
                    Logs.InfoFormat("RISPOSTA DECODIFICATA DAL WEB SERVICE DI PROTOCOLLAZIONE: {0}", responseString);

                    var response = (Risposta) Serializer.Deserialize(responseString, typeof(Risposta));

                    if (response.Stato != null && !String.IsNullOrEmpty(response.Stato.Codice) && response.Stato.Codice != "0")
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE: {0}, DESCRIZIONE: {1}", response.Stato.Codice, response.Stato.Messaggio));

                    Logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, numero protocollo: {0}, anno protocollo: {1}", response.NumeroProtocollo.numero, response.NumeroProtocollo.anno);

                    if (!String.IsNullOrEmpty(response.Stato.Messaggio))
                        Logs.Warn(response.Stato.Messaggio);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE HA GENERATO UN ERRORE, {0}", ex.Message));
            }
        }
    }
}
