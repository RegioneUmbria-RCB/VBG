using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloKibernetesService;
using Init.SIGePro.Protocollo.Kibernetes.Verticalizzazioni;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;

namespace Init.SIGePro.Protocollo.Kibernetes.Protocollazione
{
    public class ProtocollazioneService
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _url;
        string _username;
        string _password;

        public ProtocollazioneService(string url, string username, string password, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _url = url;
            _username = username;
            _password = password;
            _logs = logs;
            _serializer = serializer;
        }

        private WS_AnagraficaClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione KIBERNETES");

                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("kibernetesHttpBinding");

                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_KIBERNETES NON È STATO VALORIZZATO.");

                var ws = new WS_AnagraficaClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del web service di protocollazione KIBERNETES");

                return ws;
            }
            catch(Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE, {0}", ex.Message), ex);
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

        public StatusProtocollo Protocolla(IProtocollazione proto)
        {
            using (var ws = CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    AggiungiCredenzialiAContextScope(_username, _password);
                    var response = proto.Protocolla(ws, _logs, _serializer);
                    return response;
                }
            }
        }

        public void AggiungiAllegato(ProtocolloAllegati allegato, long numeroProtocollo, short annoProtocollo, VerticalizzazioniConfiguration vert, bool isPrincipale)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        AggiungiCredenzialiAContextScope(_username, _password);
                        var response = ws.setAllegato4Protocollo(vert.IstatEnte, annoProtocollo, numeroProtocollo, Convert.ToBase64String(allegato.OGGETTO), allegato.NOMEFILE, allegato.Descrizione, isPrincipale);

                        if (response.CodStato.Equals(1))
                            throw new Exception(response.Descizione);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'INSERIMENTO DELL'ALLEGATO CODICE {0}, NOME FILE: {1}, AL PROTOCOLLO NUMERO: {2}, ANNO: {3}, DETTAGLIO ERRORE: {4}", allegato.CODICEOGGETTO, allegato.NOMEFILE, numeroProtocollo, annoProtocollo, ex.Message), ex);
            }
        }
    }
}
