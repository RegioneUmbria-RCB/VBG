using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{

    [Serializable]
    public class InvalidUserTokenException : Exception
    {
        public InvalidUserTokenException() { }
        public InvalidUserTokenException(string message) : base(message) { }
        public InvalidUserTokenException(string message, Exception inner) : base(message, inner) { }
        protected InvalidUserTokenException(
          System.Runtime.Serialization.SerializationInfo info,
          System.Runtime.Serialization.StreamingContext context) : base(info, context) { }
    }
}
