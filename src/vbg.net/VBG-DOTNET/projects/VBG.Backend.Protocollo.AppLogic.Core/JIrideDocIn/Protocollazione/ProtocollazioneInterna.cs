using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione
{
    public class ProtocollazioneInterna : IProtocollazioneJIrideDocIn
    {
        private ProtocollazioneConfiguration _conf;
        private readonly IBindingFactory _bindingFactory;

        public ProtocollazioneInterna(ProtocollazioneConfiguration conf, IBindingFactory bindingFactory)
        {
            this._conf = conf;
            this._bindingFactory = bindingFactory;
        }

        public ProtocolloOutXml Inserisci(ProtocolloInXml request)
        {
            var service = new ProtocollazioneServiceWrapper(this._conf.Vert.Url, this._conf.Logs, this._conf.Serializer, this._bindingFactory, this._conf.Vert.CodiceAmministrazione, this._conf.Vert.Aoo);
            return service.InserisciDocumento(request);
        }
    }
}
