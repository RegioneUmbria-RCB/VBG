using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Anagrafiche
{

    [Serializable]
    public class EmailIntestatarioPendenzaNonTrovataException : Exception
    {
        public EmailIntestatarioPendenzaNonTrovataException() { }
        public EmailIntestatarioPendenzaNonTrovataException(string message) : base(message) { }
        public EmailIntestatarioPendenzaNonTrovataException(string message, Exception inner) : base(message, inner) { }
        protected EmailIntestatarioPendenzaNonTrovataException(
          System.Runtime.Serialization.SerializationInfo info,
          System.Runtime.Serialization.StreamingContext context) : base(info, context) { }
    }
}
