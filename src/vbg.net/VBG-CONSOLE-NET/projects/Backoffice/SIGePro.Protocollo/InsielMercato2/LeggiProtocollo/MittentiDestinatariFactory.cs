using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;

namespace Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo
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
