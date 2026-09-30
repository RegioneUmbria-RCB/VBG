using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloFoliumEmailService;
using System.Runtime.CompilerServices;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Core.Folium.ServiceWrapper
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

        private readonly ClientProtocollazioneEmailServiceCreator _clientProtocollazioneEmailServiceCreator;

        public ProtocollazioneEmailServiceWrapper(string url, string bindingName, ProtocolloLogs logs, ProtocolloSerializer serializer, WSAuthentication auth, IBindingFactory bindingFactory)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._auth = auth;
            this._clientProtocollazioneEmailServiceCreator = new ClientProtocollazioneEmailServiceCreator(logs, bindingFactory, url, bindingName);
        }

        public DocumentoProtocollato Protocolla(DocumentoProtocollato request)
        {
            try
            {
                using (var ws = this._clientProtocollazioneEmailServiceCreator.CreateClient())
                {
                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLA DEL WS PROTOCOLLA EMAIL, FLUSSO: {0}", request.tipoProtocollo);

                    var response = ws.Service.protocolla(_auth, request, false);

                    if (response == null)
                        throw new Exception("LA RISPOSTA DEL WEB SERVICE MAIL PROTOCOLLO E' NULL");

                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

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
                using (var ws = this._clientProtocollazioneEmailServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("CHIAMATA A INVIA MAIL WS PROTOCOLLA EMAIL, ID: {0}", id);

                    var response = ws.Service.inviaEmail(_auth, id);

                    if (response == null)
                        throw new Exception("LA RISPOSTA NON E' STATA VALORIZZATA");

                    _serializer.LogAndValidate(ProtocolloLogsConstants.SegnaturaPecResponseFileName, response);

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
