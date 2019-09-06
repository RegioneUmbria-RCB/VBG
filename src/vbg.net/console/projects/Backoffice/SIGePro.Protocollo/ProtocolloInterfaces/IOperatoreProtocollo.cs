using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtocolloInterfaces
{
    public interface IOperatoreProtocollo
    {
        string CodiceOperatore { get; }
        bool IsOperatoreDefault { get; }
    }
}
