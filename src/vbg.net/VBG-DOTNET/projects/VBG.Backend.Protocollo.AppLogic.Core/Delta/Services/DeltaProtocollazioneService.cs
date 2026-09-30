using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Delta.Services
{
    public class DeltaProtocollazioneService 
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly string _username;
        private readonly string _password;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public DeltaProtocollazioneService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string username, string password, string proxy)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._username = username;
            this._password = password;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(this._logs, bindingFactory, url, proxy);
        }

        internal ProtocolloDeltaService.Protocollo Protocolla(ProtocolloDeltaService.Protocollo request)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE, username: {0}, password: {1}, request: {2}", _username, _password, ProtocolloLogsConstants.ProtocollazioneRequestFileName);

                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    var response = ws.Service.insertProtocollo(request, _username, _password);
                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE L'INSERIMENTO DEL PROTOCOLLO", ex);
            }
        }

        internal ProtocolloDeltaService.Protocollo LeggiProtocollo(string codiceRegistro, string anno, string progressivo)
        {
            try
            {
                int annoParsed;
                if(!int.TryParse(anno, out annoParsed))
                    throw new Exception("IL PARAMETRO ANNO DEVE AVERE UN FORMATO NUMERICO");

                int progressivoParsed;
                if(!int.TryParse(progressivo, out progressivoParsed))
                    throw new Exception("IL PARAMETRO PROGRESSIVO (NUMERO PROTOCOLLO) DEVE AVERE UN FORMATO NUMERICO");

                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO, REGISTRO: {0}, ANNO: {1}, PROGRESSIVO: {2}", codiceRegistro, anno, progressivo);
                    var response = ws.Service.getProtocollo(codiceRegistro, annoParsed, progressivoParsed, _username, _password);
                    _serializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);
                    _logs.InfoFormat("LETTURA DEL PROTOCOLLO AVVENUTA CORRETTAMENTE");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO NUMERO (PROGRESSIVO) {0}, ANNO {1} DAL WEB SERVICE", progressivo, anno), ex);
            }
        }
    }
}
