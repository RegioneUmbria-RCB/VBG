using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Microsis.Protocollazione
{
    public interface IProtocollazione
    {
        DatiProtocolloRes Protocolla(ProtocolloServiceWrapper wrapper);
    }
}
