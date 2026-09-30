using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Configurations;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.DestinatariAggiuntivi.DataManagement;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Factories
{
    public class ProtocollazioneServiceWrapperFactory
    {
        public static DocAreaProtocollazioneService Create(DocAreaSegnaturaParamConfiguration conf, IEnumerable<IAnagraficaAmministrazione> mittDest, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            if (conf.VertParams.TipoFornitore == FornitoreDocAreaEnum.DATAMANAGEMENT && conf.VertParams.MultiMittenteDestinatario && conf.Flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
                return new AggiungiDestinatariServiceWrapper(conf, logs, serializer, mittDest);
            else
                return new DocAreaProtocollazioneService(conf.VertParams.Url, logs, serializer);
        }
    }
}
