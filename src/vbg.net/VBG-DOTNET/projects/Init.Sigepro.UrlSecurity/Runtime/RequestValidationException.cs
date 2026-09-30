using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Runtime
{

    [Serializable]
    public class RequestValidationException : Exception
    {
        public RequestValidationException() { }
        public RequestValidationException(string message) : base(message) { }
        public RequestValidationException(string message, Exception inner) : base(message, inner) { }
        protected RequestValidationException(
          System.Runtime.Serialization.SerializationInfo info,
          System.Runtime.Serialization.StreamingContext context) : base(info, context) { }
    }
}
