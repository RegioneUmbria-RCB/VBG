using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione
{
    [Serializable]
    public class ComuneNonAttivoException : Exception
    {
        public ComuneNonAttivoException()
        {
        }

        public ComuneNonAttivoException(string message) : base(message)
        {
        }

        public ComuneNonAttivoException(string message, Exception innerException) : base(message, innerException)
        {
        }
    }
}