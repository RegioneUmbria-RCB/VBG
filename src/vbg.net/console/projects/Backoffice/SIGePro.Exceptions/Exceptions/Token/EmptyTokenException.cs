using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Exceptions.Token
{
    public class EmptyTokenException : BaseException
    {
        public EmptyTokenException(System.Exception innerException) : base("Token non impostato", innerException) { }

        public EmptyTokenException() : base("Token non impostato", null) { }
    }
}
