using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Classificazione
{
    public class ClassificheServiceWrapper
    {
        private readonly ProtocolloLogs _log;
        private IProtocolloSerializer _serializer;
        private readonly ExtendedClientServiceCreator _protocolloClientServiceCreator;
        private readonly string _username;
        private readonly string _token;

        public ClassificheServiceWrapper(string url, ProtocolloLogs logs, IProtocolloSerializer serializer, CredentialsInfo credential, IBindingFactory bindingFactory)
        {
            _log = logs;
            _serializer = serializer;
            _protocolloClientServiceCreator = new ExtendedClientServiceCreator(logs, bindingFactory, credential, url);
            _username = credential.Username;
            _token = credential.Token;
        }

        public ClassificheOutXML LeggiClassifiche(ClassificheInXML request)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.Service.InnerChannel))
                    {
                        //base.AggiungiCredenzialiAContextScope();
                        var requestXml = this._serializer.Serialize(ProtocolloLogsConstants.ListaClassificheRequest, request);
                        _log.Info($"CHIAMATA A GET CLASSIFICHE {requestXml}");
                        var responseXml = ws.Service.getClassifiche(_username, _token, requestXml);

                        _log.Info("DESERIALIZZAZIONE DEI DATI DI RISPOSTA DA GET CLASSIFICHE");
                        var response = this._serializer.Deserialize<ClassificheOutXML>(responseXml);
                        _log.Info("CHIAMATA A GET CLASSIFICHE AVVENUTA CORRETTAMENTE");

                        return response;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE IL RECUPERO DEL TITOLARIO, {ex.Message}", ex);
            }
        }
    }
}
