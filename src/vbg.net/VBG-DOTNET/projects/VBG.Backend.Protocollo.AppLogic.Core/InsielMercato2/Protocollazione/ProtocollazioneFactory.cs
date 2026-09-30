using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Services;
using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazioneInsielMercato2 Create(IDatiProtocollo datiProto, ProtocollazioneService wrapper, VerticalizzazioniConfiguration vert)
        {
            if (datiProto.ProtoIn.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneArrivo(datiProto);
            else if (datiProto.ProtoIn.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazionePartenza(datiProto, wrapper, vert);
            else
                throw new Exception(String.Format("FLUSSO {0} NON DEFINITO", datiProto.Flusso));
        }
    }
}
