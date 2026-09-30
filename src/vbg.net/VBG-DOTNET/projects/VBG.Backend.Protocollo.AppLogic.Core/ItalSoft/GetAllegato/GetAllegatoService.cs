using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetAllegato
{
    public class GetAllegatoService : ItalSoftClient
    {
        private readonly ProtocolloClientServiceCreator _clientCreator;

        public GetAllegatoService(ILog logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string urlProtocollazione) : base(logger, serializer, urlProtocollazione, "")
        {
            _clientCreator = new ProtocolloClientServiceCreator(logger, bindingFactory, urlProtocollazione);
        }

        public AllegatoProtocollo GetAllegato(GetAllegatoRequest request)
        {
            using (var client = _clientCreator.CreateClient())
            {
                var response = client.Service.GetAllegato(request.Token, request.Id, null, null, out var messageResult);

                return AllegatoProtocollo.Fromallegato(response);
            }
        }
    }
}
