using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda
{
    public class HashCheckFailedException : ArgumentException
    {
        public HashCheckFailedException()
            : base("Il file inviato non corrisponde al file che è stato scaricato: è possibile che il file scaricato sia stato modificato. <br />Scaricare, firmare e caricare di nuovo il file per risolvere il problema")
        {

        }

        public HashCheckFailedException(string message) : base(message)
        {
        }

        public HashCheckFailedException(string message, Exception innerException) : base(message, innerException)
        {
        }

        public HashCheckFailedException(string message, string paramName) : base(message, paramName)
        {
        }

        public HashCheckFailedException(string message, string paramName, Exception innerException) : base(message, paramName, innerException)
        {
        }
    }
}
