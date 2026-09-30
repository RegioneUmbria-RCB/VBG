using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti
{

    [Serializable]
    public class AttivazionePagamentoException : Exception
    {
        public AttivazionePagamentoException() { }
        public AttivazionePagamentoException(string message) : base(message) { }
        public AttivazionePagamentoException(string message, Exception inner) : base(message, inner) { }
        protected AttivazionePagamentoException(
          System.Runtime.Serialization.SerializationInfo info,
          System.Runtime.Serialization.StreamingContext context) : base(info, context) { }
    }
}
