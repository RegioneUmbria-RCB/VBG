using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Proxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.LeggiProtocollo.MittentiDestinatari
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
