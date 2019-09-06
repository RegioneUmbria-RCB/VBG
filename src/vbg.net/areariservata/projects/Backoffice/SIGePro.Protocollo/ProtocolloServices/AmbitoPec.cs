using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class AmbitoPec : IAmbito
    {
        PecInbox _pec;

        public AmbitoPec(PecInbox pec)
        {
            _pec = pec;
        }

        public DateTime? DataRegistrazione
        {
            get { return _pec.PecDate; }
        }
    }
}
