using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro
{
    public class AccessoNegatoException : BaseException
    {
        public AccessoNegatoException(string message):base(message){}
    }
}
