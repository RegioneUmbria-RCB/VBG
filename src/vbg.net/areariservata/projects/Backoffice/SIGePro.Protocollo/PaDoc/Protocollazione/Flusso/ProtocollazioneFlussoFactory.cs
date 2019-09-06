using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.PaDoc.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.Flusso
{
    public class ProtocollazioneFlussoFactory
    {
        public static IProtocollazioneFlusso Create(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert)
        {
            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneFlussoArrivo(datiProto, vert);
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazioneFlussoPartenza(datiProto);
            else if (datiProto.Flusso == ProtocolloConstants.COD_INTERNO)
                return new ProtocollazioneFlussoInterno(datiProto);
            else
                throw new Exception(String.Format("FLUSSO {0} NON VALIDO", datiProto.Flusso));
        }
    }
}
