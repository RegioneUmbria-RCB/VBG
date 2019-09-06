using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK.LeggiProtocollo
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
