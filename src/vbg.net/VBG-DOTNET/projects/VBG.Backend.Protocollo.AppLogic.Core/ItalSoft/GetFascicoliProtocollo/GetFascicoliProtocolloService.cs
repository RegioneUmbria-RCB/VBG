using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetFascicoliProtocollo
{
    public class GetFascicoliProtocolloService : ItalSoftClient
    {
        private readonly FascicolazioneClientServiceCreator _clientCreator;

        public GetFascicoliProtocolloService(ILog logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string urlFascicolazione) : base(logger, serializer, "", urlFascicolazione)
        {
            _clientCreator = new FascicolazioneClientServiceCreator(logger, bindingFactory, urlFascicolazione);
        }

        public GetFascicoliProtocolloResponse GetFascicoliProtocollo(GetFascicoliProtocolloRequest request)
        {
            using (var client = _clientCreator.CreateClient())
            {
                base.Serialize("GetFascicoliProtocolloRequest.xml", request);

                var wsResponse = client.Service.GetFascicoliProtocollo(request.Token, request.AnnoProtocollo, request.NumeroProtocollo, request.TipoProtocollo, out var messageResult);

                if (wsResponse != null)
                {
                    base.Serialize("GetFascicoliProtocolloResponse.xml", wsResponse);
                }

                var response = GetFascicoliProtocolloResponse.FromdettaglioFascicolazione(wsResponse, messageResult);

                return response;

            }
        }
    }
}
