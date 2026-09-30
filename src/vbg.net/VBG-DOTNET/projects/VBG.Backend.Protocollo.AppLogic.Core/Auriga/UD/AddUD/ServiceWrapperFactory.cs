using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.AddUD.V1;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.AddUD.V2;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.AddUD
{
    public class ServiceWrapperFactory
    {

        private readonly IServiceWrapper _wrapper;

        public ServiceWrapperFactory(ParametriRegoleInfo parametri, ProtocolloSerializer serializer, ProtocolloLogs log, ProxyRequestInfo request, IBindingFactory bindingFactory)
        {
            switch (parametri.Versione)
            {
                case Versione.V2:
                    {
                        this._wrapper = new AddUDServiceWrapperV2(parametri, serializer, log, request, bindingFactory);
                        break;
                    }
                default:
                    {
                        this._wrapper = new AddUDServiceWrapperV1(parametri, serializer, log, request, bindingFactory);
                        break;
                    }
            }

        }

        public ResponseInfo Protocolla(DatiProtocolloIn protoIn)
        {
            return this._wrapper.Protocolla(protoIn);
        }
    }
}
