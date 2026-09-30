using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
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
