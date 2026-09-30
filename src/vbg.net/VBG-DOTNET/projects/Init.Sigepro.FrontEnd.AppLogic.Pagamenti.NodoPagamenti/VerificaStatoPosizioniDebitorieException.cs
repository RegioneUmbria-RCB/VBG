using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti
{

    [Serializable]
    public class VerificaStatoPosizioniDebitorieException : Exception
    {
        public VerificaStatoPosizioniDebitorieException() { }
        public VerificaStatoPosizioniDebitorieException(string message) : base(message) { }
        public VerificaStatoPosizioniDebitorieException(string message, Exception inner) : base(message, inner) { }
    }
}
