using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Exceptions.Protocollo
{
    [Serializable]
    public class ProtocolloRemotoException : Exception
    {
        public ProtocolloRemotoException() { }
        public ProtocolloRemotoException(string message) : base(message) { }
        public ProtocolloRemotoException(string message, Exception inner) : base(message, inner) { }
        protected ProtocolloRemotoException(
          System.Runtime.Serialization.SerializationInfo info,
          System.Runtime.Serialization.StreamingContext context)
            : base(info, context) { }
    }
}
