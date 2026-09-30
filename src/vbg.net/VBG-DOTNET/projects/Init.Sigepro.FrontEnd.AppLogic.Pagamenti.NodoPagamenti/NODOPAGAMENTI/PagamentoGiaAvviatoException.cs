using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.NODOPAGAMENTI
{
    [System.Serializable]
    public class PagamentoGiaAvviatoException : Exception
    {
        public PagamentoGiaAvviatoException() { }
        public PagamentoGiaAvviatoException(string message) : base(message) { }
        public PagamentoGiaAvviatoException(string message, Exception inner) : base(message, inner) { }
    }

}
