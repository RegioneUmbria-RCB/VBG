using System;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Exceptions
{
    public class RicercaAnagraficaException : Exception
    {
        public RicercaAnagraficaException()
        {
        }

        public RicercaAnagraficaException(string message) : base(message)
        {
        }

        public RicercaAnagraficaException(string message, Exception innerException) : base(message, innerException)
        {
        }
    }
}
