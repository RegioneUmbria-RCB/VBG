using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Insiel2.Protocollazione.MittentiDestinatari
{
    public class ProtocollazioneMittentiDestinatariFactory
    {
        public static IProtocollazioneMittentiDestinatari Create(IDatiProtocollo datiProto, ProtocolloLogs logs)
        {
            IProtocollazioneMittentiDestinatari rVal = null;

            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                rVal = new ProtocollazioneInputMittentiDestinatariArrivo(datiProto, logs);
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                rVal = new ProtocollazioneInputMittentiDestinatariPartenza(datiProto, logs);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", datiProto.Flusso));

            return rVal;
        }
    }
}
