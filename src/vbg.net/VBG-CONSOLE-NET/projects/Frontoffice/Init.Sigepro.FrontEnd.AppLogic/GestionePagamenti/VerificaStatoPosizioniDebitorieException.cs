using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti
{

    [Serializable]
    public class VerificaStatoPosizioniDebitorieException : Exception
    {
        public VerificaStatoPosizioniDebitorieException() { }
        public VerificaStatoPosizioniDebitorieException(string message) : base(message) { }
        public VerificaStatoPosizioniDebitorieException(string message, Exception inner) : base(message, inner) { }
        protected VerificaStatoPosizioniDebitorieException(
          System.Runtime.Serialization.SerializationInfo info,
          System.Runtime.Serialization.StreamingContext context) : base(info, context) { }
    }
}
