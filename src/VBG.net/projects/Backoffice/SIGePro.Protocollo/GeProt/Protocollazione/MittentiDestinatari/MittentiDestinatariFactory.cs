using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.GeProt.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.GeProt.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariFactory
    {
        public static IMittentiDestinatari Create(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert)
        {
            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new MittentiDestinatariArrivo(datiProto, vert);
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new MittentiDestinatariPartenza(datiProto, vert);
            else if (datiProto.Flusso == ProtocolloConstants.COD_INTERNO)
                return new MittentiDestinatariInterno(datiProto, vert);
            else
                throw new Exception(String.Format("FLUSSO {0} NON GESTITO", datiProto.Flusso));
        }
    }
}
