using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.FascicolaProtocollo
{
    public class FascicolaProtocolloService : ItalSoftClient
    {
        private readonly FascicolazioneClientServiceCreator _clientCreator;

        public FascicolaProtocolloService(ILog logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string urlFascicolazione) : base(logger, serializer, "", urlFascicolazione)
        {
            _clientCreator = new FascicolazioneClientServiceCreator(logger, bindingFactory, urlFascicolazione);
        }

        public FascicolaProtocolloResponse FascicolaProtocollo(FascicolaProtocolloRequest request)
        {
            using (var client = _clientCreator.CreateClient())
            {
                base.Serialize("FascicolaProtocolloRequest.xml", request);

                var wsResponse = client.Service.FascicolaProtocollo(request.Token, request.AnnoProtocollo, request.NumeroProtocollo, request.TipoProtocollo, request.Fascicolo, request.SottoFascicolo, out var messageResult);

                base.Serialize("FascicolaProtocolloResponse.xml", wsResponse);

                var response = FascicolaProtocolloResponse.FromretFascicolo(wsResponse, messageResult);

                return response;

            }
        }
    }
}
