using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.StudioK.LeggiProtocollo
{
    public class LeggiProtocolloFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(Segnatura segnatura)
        {
            var flusso = segnatura.Intestazione.Identificatore.Flusso;

            if (flusso == ProtocolloConstants.COD_ARRIVO_DOCAREA)
                return new LeggiProtocolloArrivo((Persona)segnatura.Intestazione.Mittente.Item, (Amministrazione)segnatura.Intestazione.Destinatario[0].Item);
            else if (flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
                return new LeggiProtocolloPartenza((Amministrazione)segnatura.Intestazione.Mittente.Item, segnatura.Intestazione.Destinatario);
            else
                throw new Exception(String.Format("FLUSSO {0} NON RICONOSCIUTO", flusso));

        }
    }
}
