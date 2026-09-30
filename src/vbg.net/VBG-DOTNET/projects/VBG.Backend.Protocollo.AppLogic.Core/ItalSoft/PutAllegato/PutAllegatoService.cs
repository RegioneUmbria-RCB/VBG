using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.PutAllegato
{
    public class PutAllegatoService : ItalSoftClient
    {
        private readonly ProtocolloClientServiceCreator _clientCreator;

        public PutAllegatoService(ILog logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string urlProtocollazione) : base(logger, serializer, urlProtocollazione, "")
        {
            _clientCreator = new ProtocolloClientServiceCreator(logger, bindingFactory, urlProtocollazione);
        }

        public AllegatoProtocollo PutAllegato(PutAllegatoRequest request)
        {
            using (var client = _clientCreator.CreateClient())
            {
                var response = client.Service.PutAllegato(request.Token, request.AnnoProtocollo, request.NumeroProtocollo, request.TipoProtocollo, request.Tipo,
                    request.Nome, request.Estensione, Convert.ToBase64String(request.Content), null, null, null,
                    out var messageResult);

                return AllegatoProtocollo.Fromallegato(response.FirstOrDefault());
            }
        }
    }
}
