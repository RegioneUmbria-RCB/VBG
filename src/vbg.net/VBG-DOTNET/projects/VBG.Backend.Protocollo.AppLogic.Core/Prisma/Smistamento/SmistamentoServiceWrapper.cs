using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Smistamento
{
    public class SmistamentoServiceWrapper
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly string _username;
        private readonly string _token;

        public SmistamentoServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, CredentialsInfo credentials)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, credentials, url);
            this._username = credentials.Username;
            this._token = credentials.Token;
        }

        public void Smista(SmistamentoInXML request)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    using (var scope = new OperationContextScope(ws.Service.InnerChannel))
                    {
                        //base.AggiungiCredenzialiAContextScope();

                        this._serializer.LogAndValidate(ProtocolloLogsConstants.SegnaturaSmistamentoFileName, request);
                        var obj = this._serializer.SerializeToStream<SmistamentoInXML>(request).ToArray();
                        this._logs.Info($"CHIAMATA A SMISTAMENTO ACTION, DEL PROTOCOLLO NUMERO {request.Intestazione.Identificatore.NumeroProtocollo}, ANNO {request.Intestazione.Identificatore.AnnoProtocollo}");
                        var response = ws.Service.smistamentoAction(this._username, this._token, obj);

                        if (response.lngErrNumber != 0)
                        {
                            throw new Exception($"NUMERO ERRORE: {response.lngErrNumber}, DESCRIZIONE ERRORE: {response.strErrString}");
                        }

                        this._logs.Info($"CHIAMATA A SMISTAMENTO ACTION, DEL PROTOCOLLO NUMERO {request.Intestazione.Identificatore.NumeroProtocollo}, ANNO {request.Intestazione.Identificatore.AnnoProtocollo} AVVENUTA CON SUCCESSO");
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"SI E' VERIFICATO UN PROBLEMA DURANTE LO SMISTAMENTO, {ex.Message}", ex);
            }
        }
    }
}
