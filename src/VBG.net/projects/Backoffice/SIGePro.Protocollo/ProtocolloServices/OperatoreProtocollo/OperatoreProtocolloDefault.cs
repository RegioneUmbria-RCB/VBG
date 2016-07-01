using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.ProtocolloServices.OperatoreProtocollo
{
    public class OperatoreProtocolloDefault : IOperatoreProtocollo
    {
        string _segnalibro = "";
        public OperatoreProtocolloDefault(string segnalibro)
        {
            _segnalibro = segnalibro;
        }

        #region IOperatoreProtocollo Members

        public string CodiceOperatore
        {
            get { return _segnalibro; }
        }

        public bool IsOperatoreDefault
        {
            get { return true; }
        }

        #endregion
    }
}
