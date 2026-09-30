using VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione.Flusso
{
    public class ProtocollazioneFlussoFactory
    {
        public static IProtocollazioneFlusso Create(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert)
        {
            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneFlussoArrivo(datiProto, vert);
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazioneFlussoPartenza(datiProto);
            else if (datiProto.Flusso == ProtocolloConstants.COD_INTERNO)
                return new ProtocollazioneFlussoInterno(datiProto);
            else
                throw new Exception(String.Format("FLUSSO {0} NON VALIDO", datiProto.Flusso));
        }
    }
}
