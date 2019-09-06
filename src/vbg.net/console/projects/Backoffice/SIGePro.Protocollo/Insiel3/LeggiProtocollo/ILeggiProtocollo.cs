using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;

namespace Init.SIGePro.Protocollo.Insiel3.LeggiProtocollo
{
    public interface ILeggiProtocollo
    {
        DettagliProtocollo Leggi();
    }
}
