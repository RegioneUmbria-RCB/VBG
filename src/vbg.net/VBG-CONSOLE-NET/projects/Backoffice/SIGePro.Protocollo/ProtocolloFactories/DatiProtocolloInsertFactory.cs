using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloAdapters;

namespace Init.SIGePro.Protocollo.ProtocolloFactories
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
