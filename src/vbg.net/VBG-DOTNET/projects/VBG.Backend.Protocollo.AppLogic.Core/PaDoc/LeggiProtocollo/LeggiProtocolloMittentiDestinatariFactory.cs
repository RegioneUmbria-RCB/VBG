using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.LeggiProtocollo
{
    public class LeggiProtocolloMittentiDestinatariFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(rispostaRisultatoMittente mittente, rispostaRisultatoDestinatario[] destinatari, string flusso)
        {
            if (flusso == ProtocolloConstants.COD_ARRIVO_DOCAREA)
                return new LeggiProtocolloArrivo(mittente, destinatari);
            else if (flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
                return new LeggiProtocolloPartenza(mittente, destinatari);
            else if (flusso == ProtocolloConstants.COD_INTERNO_DOCAREA)
                return new LeggiProtocolloInterno(mittente, destinatari);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", flusso));
        }
    }
}
