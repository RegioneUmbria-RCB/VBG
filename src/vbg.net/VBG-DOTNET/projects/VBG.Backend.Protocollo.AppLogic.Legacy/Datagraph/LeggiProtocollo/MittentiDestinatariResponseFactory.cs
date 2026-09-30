using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Datagraph.LeggiProtocollo
{
    public class MittentiDestinatariResponseFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(string flusso, Mittente mittente, Destinatario destinatario)
        {
            if (flusso == ProtocolloConstants.COD_ARRIVO_DOCAREA)
            {
                return new MittentiDestinatariResponseArrivo(flusso, mittente, destinatario);
            }
            else if (flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
            {
                return new MittentiDestinatariResponsePartenza(flusso, mittente, destinatario);
            }
            else
            {
                throw new Exception($"FLUSSO {flusso} NON GESTITO");
            }
        }
    }
}
