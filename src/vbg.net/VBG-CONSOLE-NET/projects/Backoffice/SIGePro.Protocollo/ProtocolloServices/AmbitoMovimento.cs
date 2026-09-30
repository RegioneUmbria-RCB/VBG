using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class AmbitoMovimento : IAmbito
    {
        Movimenti _movimento;

        public AmbitoMovimento(Movimenti movimento)
        {
            _movimento = movimento;
        }

        public DateTime? DataRegistrazione
        {
            get { return _movimento.DATA; }
        }
    }
}
