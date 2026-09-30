using Init.SIGePro.Protocollo.DocArea.Configurations;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.Builders.ApplicativoProtocolloBuilder
{
    public class TipoFornitoreProtocolloFactory
    {
        public static ITipoFornitoreProtocolloDocArea Create(DocAreaSegnaturaParamConfiguration configuration)
        {
            var fornitore = configuration.VertParams.TipoFornitore;

            if (fornitore == ProtocolloEnum.FornitoreDocAreaEnum.ADS)
                return new Ads(configuration);
            else if (fornitore == ProtocolloEnum.FornitoreDocAreaEnum.DATAGRAPH)
                return new Datagraph(configuration);
            else if (fornitore == ProtocolloEnum.FornitoreDocAreaEnum.DATAMANAGEMENT)
                return new Datamanagement(configuration);
            else if (fornitore == ProtocolloEnum.FornitoreDocAreaEnum.MAGGIOLI)
                return new Maggioli(configuration);
            else
                return new Default(configuration);
        }
    }
}
