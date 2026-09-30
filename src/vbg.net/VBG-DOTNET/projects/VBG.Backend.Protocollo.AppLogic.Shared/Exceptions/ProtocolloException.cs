using Init.SIGePro.Exceptions;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Exceptions
{
    public class ProtocolloException : BaseException
    {
        public ProtocolloException() : base() { }
        public ProtocolloException(string message) : base(message) { }
        public ProtocolloException(string message, System.Exception innerException) : base(message, innerException) { }
    }
}
