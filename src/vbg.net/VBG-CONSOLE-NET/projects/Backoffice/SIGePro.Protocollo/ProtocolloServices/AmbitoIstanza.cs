using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class AmbitoIstanza : IAmbito
    {
        Istanze _istanza;
        public AmbitoIstanza(Istanze istanza)
        {
            _istanza = istanza;
        }

        public DateTime? DataRegistrazione
        {
            get { return _istanza.DATA; }
        }
    }
}
