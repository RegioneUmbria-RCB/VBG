using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.JProtocollo2.Proxy;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.JProtocollo2.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(leggiProtocolloResponseRispostaLeggiProtocollo response)
        {
            if (response.protocollo.tipo == ProtocolloConstants.COD_ARRIVO)
                return new MittentiDestinatariArrivo(response);
            else if (response.protocollo.tipo == ProtocolloConstants.COD_PARTENZA)
                return new MittentiDestinatariPartenza(response);
            else if (response.protocollo.tipo == ProtocolloConstants.COD_INTERNO)
                return new MittentiDestinatariInterno(response);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", response.protocollo.tipo));
        }
    }
}
