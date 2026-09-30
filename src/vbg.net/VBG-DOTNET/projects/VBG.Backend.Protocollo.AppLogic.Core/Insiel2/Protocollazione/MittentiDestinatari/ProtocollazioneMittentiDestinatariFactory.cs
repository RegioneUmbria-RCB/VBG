using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Protocollazione.MittentiDestinatari
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
