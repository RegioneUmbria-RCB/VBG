using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetElencoFascicoli
{
    public class GetElencoFascicoliService : ItalSoftClient
    {
        private readonly FascicolazioneClientServiceCreator _clientCreator;

        public GetElencoFascicoliService(ILog logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string urlFascicolazione) : base(logger, serializer, "", urlFascicolazione)
        {
            _clientCreator = new FascicolazioneClientServiceCreator(logger, bindingFactory, urlFascicolazione);
        }

        public GetElencoFascicoliResponse GetElencoFascicoli(GetElencoFascicoliRequest request)
        {
            using (var client = _clientCreator.CreateClient())
            {
                base.Serialize("GetElencoFascicoliRequest.xml", request);

                var wsResponse = client.Service.GetElencoFascicoli(request.Token, new ArrayParamRicercaBuilder(request).Build(), out var messageResult);

                base.Serialize("GetElencoFascicoliResponse.xml", wsResponse);

                var response = GetElencoFascicoliResponse.FromdettaglioFascicolo(wsResponse, messageResult);

                return response;

            }
        }
    }
}
