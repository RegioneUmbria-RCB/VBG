using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public class ProtocollazioneFlussoFactory
    {
        public static IProtocollazioneFlusso Create(IDatiProtocollo datiProto, string destinatarioCC)
        {
            if (datiProto.Flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneFlussoArrivo(datiProto);
            else if (datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazioneFlussoPartenza(datiProto, destinatarioCC);
            else
                throw new Exception(String.Format("FLUSSO {0} NON PREVISTO DAL SISTEMA DI PROTOCOLLO", datiProto.Flusso));
        }
    }
}
