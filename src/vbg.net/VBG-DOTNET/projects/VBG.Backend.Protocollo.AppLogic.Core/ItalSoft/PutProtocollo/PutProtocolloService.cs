using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.PutProtocollo
{
    public class PutProtocolloService : ItalSoftClient
    {
        private readonly ProtocolloClientServiceCreator _clientCreator;

        public PutProtocolloService(ILog logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string urlProtocollazione) : base(logger, serializer, urlProtocollazione, "")
        {
            _clientCreator = new ProtocolloClientServiceCreator(logger, bindingFactory, urlProtocollazione);
        }

        public PutProtocolloResponse PutProtocollo(PutProtocolloRequest request)
        {
            using (var client = _clientCreator.CreateClient())
            {
                var datiProtocollo = request.TodatiProtocollo();
                base.Serialize("PutProtocolloRequest.xml", datiProtocollo);

                var wsResponse = client.Service.PutProtocollo(request.Token, datiProtocollo, out var messageResult);

                base.Serialize("PutProtocolloResponse.xml", wsResponse);

                var response = PutProtocolloResponse.FromretProtocollo(wsResponse, messageResult);

                base.Serialize("ProtocolloResponse.xml", response);

                return response;
            }
        }
    }
}
