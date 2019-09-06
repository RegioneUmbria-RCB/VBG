using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class AmbitoNessuno : IAmbito
    {
        public DateTime? DataRegistrazione
        {
            get { return DateTime.Now; }
        }
    }
}
