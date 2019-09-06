using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;

namespace Init.SIGePro.Protocollo.Insiel3.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(DettagliProtocollo response)
        {

            var flusso = response.infoGenerali.protoApProt;

            ILeggiProtoMittentiDestinatari rVal;
            
            if (flusso == ProtocolloConstants.COD_ARRIVO)
                rVal = new MittentiDestinatariArrivo(response);
            else if (flusso == ProtocolloConstants.COD_INTERNO)
                rVal = new MittentiDestinatariInterno(response.destinatari);
            else if (flusso == ProtocolloConstants.COD_PARTENZA)
                rVal = new MittentiDestinatariPartenza(response.destinatari);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", flusso));

            return rVal;
            
        }
    }
}
