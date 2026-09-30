using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.ProtocolloInterfaces
{
    public interface IAllegatiAdapter
    {
        IEnumerable<AllOut> Adatta();
    }
}
