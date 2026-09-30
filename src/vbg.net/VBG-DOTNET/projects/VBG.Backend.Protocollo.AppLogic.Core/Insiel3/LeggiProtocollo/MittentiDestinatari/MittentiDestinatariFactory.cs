using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.LeggiProtocollo.MittentiDestinatari
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
