using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti
{

    [Serializable]
    public class AttivazionePagamentoException : Exception
    {
        public AttivazionePagamentoException() { }
        public AttivazionePagamentoException(string message) : base(message) { }
        public AttivazionePagamentoException(string message, Exception inner) : base(message, inner) { }
    }
}
