using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione
{
    public class ProtocollazioneDocIn : IProtocollazioneJIrideDocIn
    {
        private ParametriRegoleInfo _vert;
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private readonly IBindingFactory _bindingFactory;

        public ProtocollazioneDocIn(ParametriRegoleInfo vert, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._vert = vert;
            this._bindingFactory = bindingFactory;
        }
        public ProtocolloOutXml Inserisci(ProtocolloInXml request)
        {
            var service = new ProtocollazioneServiceWrapper(this._vert.Url, this._logs, this._serializer, this._bindingFactory, this._vert.CodiceAmministrazione, this._vert.Aoo);
            return service.InserisciDocumento(request);
        }
    }
}
