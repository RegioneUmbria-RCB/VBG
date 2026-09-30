using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Services;
using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazioneInsielMercato Create(IDatiProtocollo datiProto)
        {
            if (datiProto.ProtoIn.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneArrivo(datiProto);
            else if (datiProto.ProtoIn.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazionePartenza(datiProto);
            else
                throw new Exception(String.Format("FLUSSO {0} NON DEFINITO", datiProto.Flusso));
        }
    }
}
