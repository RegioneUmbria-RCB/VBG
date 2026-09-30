using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Exceptions
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
