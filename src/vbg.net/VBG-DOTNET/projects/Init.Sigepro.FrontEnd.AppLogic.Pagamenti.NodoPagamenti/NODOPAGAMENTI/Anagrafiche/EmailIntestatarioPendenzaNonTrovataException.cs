using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Anagrafiche
{

    [Serializable]
    public class EmailIntestatarioPendenzaNonTrovataException : Exception
    {
        public EmailIntestatarioPendenzaNonTrovataException() { }
        public EmailIntestatarioPendenzaNonTrovataException(string message) : base(message) { }
        public EmailIntestatarioPendenzaNonTrovataException(string message, Exception inner) : base(message, inner) { }
    }
}
