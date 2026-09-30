using VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1;
using VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V2;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public class AggiungiAllegatiServiceBuilder
    {
        private readonly IParametriService _parametriService;
        private readonly ILog _logger;
        private readonly IProtocolloSerializer _serializer;
        private readonly IBindingFactory _bindingFactory;

        public AggiungiAllegatiServiceBuilder(IParametriService parametriService, ILog logger, IProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            this._parametriService = parametriService;
            this._logger = logger;
            this._serializer = serializer;
            this._bindingFactory = bindingFactory;
        }

        public IAggiungiAllegatiService Build()
        {
            switch (this._parametriService.Versione)
            {
                case VersioneEnum.VERSIONE_1:
                    {
                        return new AggiungiAllegatoV1Service(this._parametriService, this._logger, this._bindingFactory);
                    }
                case VersioneEnum.VERSIONE_2:
                    {
                        return new AggiungiAllegatoV2Service(this._parametriService, this._logger, this._serializer, this._bindingFactory);
                    }
                default:
                    {
                        throw new NotImplementedException();
                    }
            }
        }
    }
}
