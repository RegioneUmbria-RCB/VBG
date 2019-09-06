using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Exceptions.Protocollo
{
    public class ProtocolloException : BaseException
    {
        public ProtocolloException(string message) : base(message) { }
        public ProtocolloException(string message, System.Exception innerException) : base(message, innerException) { }
    }
}
