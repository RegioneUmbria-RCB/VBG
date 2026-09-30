using ItCityService;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Protocollazione
{
    public class ProtocollazioneServiceWrapper
    {
        private readonly LoginWsInfo _loginInfo;
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly ProtocolloSerializer _serializer;

        public ProtocollazioneServiceWrapper(string url, LoginWsInfo login, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            this._loginInfo = login;
            this._logs = logs;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
            this._serializer = serializer;
        }

        public ProtocolloOutput Protocolla(ProtocollazioneRequestInfo request, IEnumerable<byte[]> buffers)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    var requestXml = this._serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    this._logs.Info($"CHIAMATA A PROTOCOLLAZIONE, USERNAME WS: {this._loginInfo.Username}, PASSWORD WS: {this._loginInfo.Password}, ID UNITA OPERATIVA: {this._loginInfo.Identificativo.IdUnitaOperativa}, ID UTENTE: {this._loginInfo.Identificativo.IdUtente}, REQUEST: {requestXml}");

                    var response = ws.Service.ProtocollaAllegati(this._loginInfo.Username, this._loginInfo.Password, this._loginInfo.Identificativo, request.CoordinateArchivioInfo, request.MittenteInternoInfo, request.MittentiEsterniInfo, request.DestinatariInterniInfo, request.DestinatariEsterniInfo, null, request.Allegati, buffers.ToArray());

                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);
                    this._logs.Info($"RESPONSE DELLA CHIAMATA A PROTOCOLLAZIONE: {responseXml}");

                    if (response.Exitcode != 0)
                    {
                        throw new Exception(response.ExitMessage);
                    }

                    this._logs.Info($"CHIAMATA A PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE, NUMERO PROTOCOLLO: {response.Protocollo.NumeroProtocollo}, DATA PROTOCOLLO: {response.Protocollo.DataProtocollo}, ID PROTOCOLLO: {response.Protocollo.IdDocumento}");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"CHIAMATA A METODO PROTOCOLLAALLEGATI FALLITA, {ex.Message}", ex);
            }
        }
    }
}
