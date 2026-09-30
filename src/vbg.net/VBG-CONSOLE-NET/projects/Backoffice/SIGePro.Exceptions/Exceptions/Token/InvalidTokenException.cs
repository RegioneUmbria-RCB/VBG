using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Exceptions.Token
{
    public class InvalidTokenException : BaseException
    {
        public InvalidTokenException(string token, System.Exception innerException) : base("Token " + token + " non valido!!!", innerException) {}

        public InvalidTokenException( string token  ) : base( "Token " + token + " non valido!!!" , null ) {}
    }
}
