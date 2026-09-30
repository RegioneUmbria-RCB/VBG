using ProtocolloInsielMercatoService2;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.LeggiProtocollo
{
    public class MittentiDestinatariFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(protocolDetail response)
        {
            if (response.recordIdentifier.direction == direction.A)
                return new MittentiDestinatariArrivo(response);
            else if (response.recordIdentifier.direction == direction.P)
                return new MittentiDestinatariPartenza(response);
            else
                throw new Exception(String.Format("FLUSSO {0} NON GESTITO", response.recordIdentifier.direction.ToString()));
        }
    }
}
