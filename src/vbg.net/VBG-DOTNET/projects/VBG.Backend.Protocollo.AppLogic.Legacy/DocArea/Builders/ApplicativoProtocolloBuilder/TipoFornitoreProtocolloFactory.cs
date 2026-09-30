

using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Configurations;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders.ApplicativoProtocolloBuilder
{
    public class TipoFornitoreProtocolloFactory
    {
        public static ITipoFornitoreProtocolloDocArea Create(DocAreaSegnaturaParamConfiguration configuration)
        {
            var fornitore = configuration.VertParams.TipoFornitore;

            if (fornitore == FornitoreDocAreaEnum.ADS)
                return new Ads(configuration);
            else if (fornitore == FornitoreDocAreaEnum.DATAGRAPH)
                return new Datagraph(configuration);
            else if (fornitore == FornitoreDocAreaEnum.DATAMANAGEMENT)
                return new Datamanagement(configuration);
            else if (fornitore == FornitoreDocAreaEnum.MAGGIOLI)
                return new Maggioli(configuration);
            else
                return new Default(configuration);
        }
    }
}
