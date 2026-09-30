using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Adapters;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Factories
{
    public class DatiProtocolloInsertFactory
    {
        public static IDatiProtocollo Create(DatiProtocolloIn protoIn)
        { 
            IDatiProtocollo retVal = null;

            if (protoIn.Flusso == ProtocolloConstants.COD_ARRIVO)
                retVal = new DatiProtocolloInsertArrivoAdapter(protoIn);
            else if (protoIn.Flusso == ProtocolloConstants.COD_PARTENZA)
                retVal = new DatiProtocolloInsertPartenzaAdapter(protoIn);

            if (protoIn.Flusso == ProtocolloConstants.COD_INTERNO)
                retVal = new DatiProtocolloInsertInternoAdapter(protoIn);

            return retVal;
        }
    }
}
