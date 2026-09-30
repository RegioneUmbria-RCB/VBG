using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V2;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public class LeggiAllegatoServiceBuilder
    {
        private ParametriService _parametriService;
        private ProtocolloLogs _logger;
        private ProtocolloSerializer _serializer;
        private readonly IBindingFactory _bindingFactory;

        public LeggiAllegatoServiceBuilder(ParametriService parametriService, ProtocolloLogs logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            this._parametriService = parametriService;
            this._logger = logger;
            this._serializer = serializer;
            this._bindingFactory = bindingFactory;
        }

        public ILeggiAllegatoService Build()
        {
            switch (this._parametriService.Versione)
            {
                case VersioneEnum.VERSIONE_2:
                    {
                        return new LeggiAllegatoV2Service(this._parametriService, this._logger, this._serializer, this._bindingFactory);
                    }
                default:
                    {
                        throw new NotImplementedException();
                    }
            }
        }
    }
}
