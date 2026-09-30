using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.SiprWebTest.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariFactory
    {
        public static IMittentiDestinatari Create(IDatiProtocollo datiProto, ProtocolloLogs logs, string codiceCC)
        {
            IMittentiDestinatari rVal = null;

            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                rVal = new MittentiDestinatariArrivo(datiProto, logs);
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                rVal = new MittentiDestinatariPartenza(datiProto, logs, codiceCC);
            else if (datiProto.Flusso == ProtocolloConstants.COD_INTERNO)
                rVal = new MittentiDestinatariInterno(datiProto);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", datiProto.Flusso));

            return rVal;
        }
    }
}
