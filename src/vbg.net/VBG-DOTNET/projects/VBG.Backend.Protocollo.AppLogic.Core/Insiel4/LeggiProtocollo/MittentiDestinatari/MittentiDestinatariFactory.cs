using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(DettaglioProtocolloResponse response)
        {
            var flusso = response.InfoGenerali.ProtoApProt;

            ILeggiProtoMittentiDestinatari rVal;

            if (flusso == Verso.arrivo)
                rVal = new MittentiDestinatariArrivo(response);
            //else if (flusso == ProtocolloConstants.COD_INTERNO)
            //    rVal = new MittentiDestinatariInterno(response.Destinatari);
            else if (flusso == Verso.partenza)
                rVal = new MittentiDestinatariPartenza(response.Destinatari);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", flusso));

            return rVal;
        }
    }
}
