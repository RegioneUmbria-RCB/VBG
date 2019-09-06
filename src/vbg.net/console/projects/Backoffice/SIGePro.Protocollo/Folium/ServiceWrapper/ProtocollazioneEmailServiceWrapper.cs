using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloFoliumEmailService;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Folium.ServiceWrapper
{
    public class ProtocollazioneEmailServiceWrapper
    {
        private static class ConstantsEsito
        {
            public const string SENZA_ERRORI = "000";
            public const string ERRORE_LOGIN = "107";
            public const string ERRORE_INTERNO = "108";
            public const string NESSUN_ALLEGATO = "001";
        }

        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        WSAuthentication _auth;
        string _urlWs;
        string _bindingName;

        public ProtocollazioneEmailServiceWrapper(string url, string bindingName, ProtocolloLogs logs, ProtocolloSerializer serializer, WSAuthentication auth)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._urlWs = url;
            this._bindingName = bindingName;
            this._auth = auth;
        }

        private ProtocolloEmailWebServiceClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione EMAIL Folium");
                if (String.IsNullOrEmpty(_urlWs))
                    throw new Exception("IL PARAMETRO URL_EMAILWS DELLA VERTICALIZZAZIONE PROTOCOLLO_FOLIUM NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                if (String.IsNullOrEmpty(_bindingName))
                    _bindingName = "defaultHttpBinding";

                var endPointAddress = new EndpointAddress(_urlWs);
                var binding = new BasicHttpBinding(_bindingName);

                var ws = new ProtocolloEmailWebServiceClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del webservice EMAIL FOLIUM");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }


        public DocumentoProtocollato Protocolla(DocumentoProtocollato request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLA DEL WS PROTOCOLLA EMAIL, FLUSSO: {0}", request.tipoProtocollo);

                    var response = ws.protocolla(_auth, request, false);

                    if (response == null)
                        throw new Exception("LA RISPOSTA DEL WEB SERVICE MAIL PROTOCOLLO E' NULL");

                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response.esito == null)
                        throw new Exception("LA RISPOSTA DEL WEB SERVICE MAIL NON HA VALORIZZATO L'ESITO");

                    if (response.esito.codiceEsito != ConstantsEsito.SENZA_ERRORI)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.esito.codiceEsito, response.esito.descrizioneEsito));

                    _logs.InfoFormat("PROTOCOLLO CREATO CON SUCCESSO, NUMERO PROTOCOLLO: {0}, DATA PROTOCOLLO: {1}, FLUSSO: {2}", response.numeroProtocollo, response.dataProtocollo.Value.ToString("dd/MM/yyyy"), response.tipoProtocollo);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL METODO PROTOCOLLA DEL WEB SERVICE, {0}", ex.Message), ex);
            }
        }

        public void InviaEmail(long id)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A INVIA MAIL WS PROTOCOLLA EMAIL, ID: {0}", id);

                    var response = ws.inviaEmail(_auth, id);

                    if (response == null)
                        throw new Exception("LA RISPOSTA NON E' STATA VALORIZZATA");

                    _serializer.Serialize(ProtocolloLogsConstants.SegnaturaPecResponseFileName, response);

                    if (response.codiceEsito != ConstantsEsito.SENZA_ERRORI)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.codiceEsito, response.descrizioneEsito));

                    _logs.InfoFormat("PEC INVIATA CORRETTAMENTE");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'INVIO PEC, {0}", ex.Message), ex);
            }
        }

    }
}
