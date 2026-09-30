using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.LeggiProtocollo
{
    public class LeggiProtocolloFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(protocolli response)
        {
            if (response.protocollo[0].tipologia == "A")
                return new LeggiProtocolloArrivo(response);
            else if (response.protocollo[0].tipologia == "P")
                return new LeggiProtocolloPartenza(response);
            else if (response.protocollo[0].tipologia == "I")
                return new LeggiProtocolloInterno(response);
            else
                throw new Exception(String.Format("FLUSSO (TIPOLOGIA) {0} NON RICOSCIUTO", response.protocollo[0].tipologia));
        }
    }
}
