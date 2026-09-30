using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetProtocollo
{
    public class GetProtocolloService : ItalSoftClient
    {
        private readonly ProtocolloClientServiceCreator _clientCreator;

        public GetProtocolloService(ILog logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string urlProtocollazione) : base(logger, serializer, urlProtocollazione, "")
        {
            _clientCreator = new ProtocolloClientServiceCreator(logger, bindingFactory, urlProtocollazione);
        }

        public GetProtocolloResponse GetProtocollo(GetProtocolloRequest request)
        {

            using (var client = _clientCreator.CreateClient())
            {

                base.Serialize("GetProtocolloRequest.xml", request);

                var tipiProtocollo = string.IsNullOrEmpty(request.IdComposto)
                                        ? new string[] { "A", "P", "C" }
                                        : new string[] { request.IdComposto.Split('-')[1] };


                foreach (var tipo in tipiProtocollo)
                {
                    var response = client.Service.GetProtocollo(request.Token, request.Anno, request.Numero, tipo, null, out var message);
                    if (response != null)
                    {
                        base.Serialize("GetProtocolloResponse.xml", response);

                        return GetProtocolloResponse.FromitemProtocollo(response);
                    }
                }

                throw new ArgumentException($"Non è stato trovato il protocollo con numero {request.Numero} e anno {request.Anno}");

            }
        }
    }
}
