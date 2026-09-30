using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWeb.Protocollazione.MittentiDestinatari
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
