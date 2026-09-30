using System;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche
{
    [Serializable]
    internal class RicercheAnagraficheException : Exception
    {
        public RicercheAnagraficheException()
        {
        }

        public RicercheAnagraficheException(string? message) : base(message)
        {
        }

        public RicercheAnagraficheException(string? message, Exception? innerException) : base(message, innerException)
        {
        }
    }
}