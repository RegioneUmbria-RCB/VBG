using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;

namespace Init.SIGePro.Protocollo.Insiel2.LeggiProtocollo
{
    public interface ILeggiProtoInputAdapter
    {
        DettagliProtocolloRequest Adatta();
    }
}
