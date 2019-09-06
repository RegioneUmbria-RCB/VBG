using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.InvioPecAdsService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.PEC.Ads
{
    public class PecService : IPecService
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        IEnumerable<IAnagraficaAmministrazione> _destinatari;
        string _utente;
        string _username;
        string _password;

        public PecService(ProtocolloLogs logs, ProtocolloSerializer serializer, IEnumerable<IAnagraficaAmministrazione> destinatari, string utente, string username, string password)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._destinatari = destinatari;
            this._utente = utente;
            this._username = username;
            this._password = password;
        }

        private PecSOAPImplClient CreaWebService(string url)
        {
            _logs.Debug("Creazione del webservice di invio pec Ads");
            try
            {
                var endPointAddress = new EndpointAddress(url);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                if (String.IsNullOrEmpty(url))
                    throw new Exception("IL PARAMETRO URL_PEC DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCAREA NON È STATO VALORIZZATO.");

                _logs.Debug("Fine creazione del webservice di invio pec Ads");

                return new PecSOAPImplClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DI INVIO PEC, {0}", ex.Message), ex);
            }
        }

        private void AggiungiCredenzialiAContextScope(string username, string password)
        {
            if (!String.IsNullOrEmpty(username))
            {
                var credentials = GetCredentials(username, password);
                var request = new HttpRequestMessageProperty();

                request.Headers[System.Net.HttpRequestHeader.Authorization] = "Basic " + credentials;

                OperationContext.Current.OutgoingMessageProperties.Add(HttpRequestMessageProperty.Name, request);
            }
        }

        private string GetCredentials(string username, string password)
        {
            var credentials = username + ":" + password;

            return Convert.ToBase64String(Encoding.UTF8.GetBytes(credentials));
        }

        public string InviaPec(string url, ProtocollazioneRet responseProtocollo)
        {
            var request = new PecRequestAdapter(_logs);
            var parametri = request.Adatta(responseProtocollo, _destinatari, _utente);

            try
            {
                using (var ws = CreaWebService(url))
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        _logs.InfoFormat("AUTENTICAZIONE BASIC AL WS, USERNAME: {0}, PASSWORD: {1}", _username, _password);
                        AggiungiCredenzialiAContextScope(_username, _password);
                        _logs.InfoFormat("AUTENTICAZIONE AVVENUTA CON SUCCESSO");
                        var requestXml = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaPecRequestFileName, parametri);

                        _logs.InfoFormat("CHIAMATA A INVIO PEC PROTOCOLLO GENERALE ADS, PROTOCOLLO NUMERO: {0}, ANNO: {1}, request: {2}", parametri.numero, parametri.anno, requestXml);
                        var response = ws.invioPecPG(parametri);

                        if (response.codice != 0 && !String.IsNullOrEmpty(response.msgId))
                        {
                            throw new Exception($"CODICE ERRORE: {response.codice}, DESCRIZIONE ERRORE: {response.descrizione}");
                        }

                        _logs.InfoFormat("CHIAMATA A INVIO PEC PROTOCOLLO GENERALE ADS, PROTOCOLLO NUMERO: {0}, ANNO: {1}, AVVENUTA CON SUCCESSO, RISPOSTA DAL WS, CODICE: {2}, DESCRIZIONE: {3}, MSGID: {4}", parametri.numero, parametri.anno, response.codice, response.descrizione, response.msgId);

                        return String.Concat(response.codice, response.descrizione);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"{ex.Message}", ex);
            }
        }
    }
}
