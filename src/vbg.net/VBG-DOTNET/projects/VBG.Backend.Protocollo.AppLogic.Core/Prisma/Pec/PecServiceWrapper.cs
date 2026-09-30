using VBG.Shared.Infrastructure.ServiceModel;
using InvioPecAdsService;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Pec
{
    public class PecServiceWrapper
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        private readonly PecSOAPClientServiceCreator _pecSOAPClientServiceCreator;

        public PecServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, CredentialsInfo credentials, IBindingFactory bindingFactory)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._pecSOAPClientServiceCreator = new PecSOAPClientServiceCreator(logs, bindingFactory, credentials, url);
        }

        public void InviaPec(ParametriIngressoPG request)
        {
            try
            {
                using (var ws = _pecSOAPClientServiceCreator.CreateClient())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.Service.InnerChannel))
                    {
                        //AggiungiCredenzialiAContextScope();
                        var requestXml = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaPecRequestFileName, request);

                        _logs.InfoFormat("CHIAMATA A INVIO PEC PRISMA, PROTOCOLLO NUMERO: {0}, ANNO: {1}, request: {2}", request.numero, request.anno, requestXml);
                        var response = ws.Service.invioPecPG(request);

                        if (response.codice != 0 && !String.IsNullOrEmpty(response.msgId))
                        {
                            throw new Exception($"CODICE ERRORE: {response.codice}, DESCRIZIONE ERRORE: {response.descrizione}");
                        }

                        _logs.Info($"CHIAMATA A INVIO PEC PRISMA, PROTOCOLLO NUMERO: {request.numero}, ANNO: {request.anno}, AVVENUTA CON SUCCESSO, RISPOSTA DAL WS, CODICE: {response.codice}, DESCRIZIONE: {response.descrizione}, MSGID: {response.msgId}");
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
