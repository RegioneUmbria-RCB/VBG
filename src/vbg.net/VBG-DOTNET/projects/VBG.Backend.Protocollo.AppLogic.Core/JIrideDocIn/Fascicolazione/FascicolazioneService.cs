using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Fascicolazione.Lettura;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Fascicolazione
{
    public class FascicolazioneService
    {
        private ProtocolloLogs _protocolloLogs;
        private ProtocolloSerializer _protocolloSerializer;
        private readonly IBindingFactory _bindingFactory;

        public FascicolazioneService(ProtocolloLogs protocolloLogs, ProtocolloSerializer protocolloSerializer, IBindingFactory bindingFactory)
        {
            this._protocolloLogs = protocolloLogs;
            this._protocolloSerializer = protocolloSerializer;
            this._bindingFactory = bindingFactory;
        }

        public FascicoloOutXml FascicoloNuovo(FascicoloNuovoRequest request)
        {
            return new FascicolazioneClient(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory, request.Url).CreaFascicolo(request.Request, request.CodiceAmministrazione, request.Aoo);
        }

        public FascicoloOutXml LeggiFascicolo( LeggiFascicoloRequest request ) 
        {
            return new FascicolazioneClient(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory, request.Url).LeggiFascicolo(request.Request, request.CodiceAmministrazione, request.Aoo);
        }
    }
}
