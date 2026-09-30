using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class AmbitoDefault : IAmbito
    {
        public DateTime? DataRegistrazione
        {
            get { return (DateTime?) null; }
        }
    }
}
