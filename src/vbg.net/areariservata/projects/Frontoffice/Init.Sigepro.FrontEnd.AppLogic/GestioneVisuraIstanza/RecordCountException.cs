using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{

    [Serializable]
    public class RecordCountException : Exception
    {
        public RecordCountException() { }
        public RecordCountException(string message) : base(message) { }
        public RecordCountException(string message, Exception inner) : base(message, inner) { }
        protected RecordCountException(
          System.Runtime.Serialization.SerializationInfo info,
          System.Runtime.Serialization.StreamingContext context) : base(info, context) { }
    }
}
