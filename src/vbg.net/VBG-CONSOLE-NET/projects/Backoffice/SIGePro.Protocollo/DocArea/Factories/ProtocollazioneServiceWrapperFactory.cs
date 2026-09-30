using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.DocArea.Adapters;
using Init.SIGePro.Protocollo.DocArea.Configurations;
using Init.SIGePro.Protocollo.DocArea.DestinatariAggiuntivi.DataManagement;
using Init.SIGePro.Protocollo.DocArea.Interfaces;
using Init.SIGePro.Protocollo.DocArea.Services;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.Factories
{
    public class ProtocollazioneServiceWrapperFactory
    {
        public static DocAreaProtocollazioneService Create(DocAreaSegnaturaParamConfiguration conf, IEnumerable<IAnagraficaAmministrazione> mittDest, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            if (conf.VertParams.TipoFornitore == ProtocolloEnum.FornitoreDocAreaEnum.DATAMANAGEMENT && conf.VertParams.MultiMittenteDestinatario && conf.Flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
                return new AggiungiDestinatariServiceWrapper(conf, logs, serializer, mittDest);
            else
                return new DocAreaProtocollazioneService(conf.VertParams.Url, logs, serializer);
        }
    }
}
