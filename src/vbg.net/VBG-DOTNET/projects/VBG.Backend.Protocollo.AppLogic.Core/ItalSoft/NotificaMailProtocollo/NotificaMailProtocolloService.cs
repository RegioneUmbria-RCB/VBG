using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.NotificaMailProtocollo
{
    public class NotificaMailProtocolloService : ItalSoftClient
    {
        private readonly ProtocolloClientServiceCreator _clientCreator;

        public NotificaMailProtocolloService(ILog logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string urlProtocollazione) : base(logger, serializer, urlProtocollazione, "")
        {
            _clientCreator = new ProtocolloClientServiceCreator(logger, bindingFactory, urlProtocollazione);
        }

        public string NotificaMailProtocollo(NotificaMailProtocolloRequest request)
        {
            using (var client = _clientCreator.CreateClient())
            {
                base.Serialize("NotificaMailProtocolloRequest.xml", request);

                var response = client.Service.NotificaMailProtocollo(request.Token, request.Anno, request.Numero, request.Tipo, request.Oggetto, request.Corpo, "", out var messageResult);

                base.Serialize("NotificaMailProtocolloResponse.xml", response);

                return response.stato;
            }
        }
    }
}
