using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Data
{
    public class ListaMittDest
    {
        public List<ProtocolloAnagrafe> Anagrafe { get; set; }
        public List<Amministrazioni> Amministrazione { get; set; }
    }
}
