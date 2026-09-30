using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Exceptions.SIT
{
    public class CatastoException : BaseException
    {
        //public CatastoException(System.Exception innerException) : base("Tipo catasto non impostato", innerException) { }
        private bool returnValue;

        public bool ReturnValue
	    {
            get { return returnValue; }
            set { returnValue = value; }
	    }
	
        public CatastoException() : base("Tipo catasto non impostato") 
        {
            ReturnValue = false;
        }

        public CatastoException(bool returnValue) : base("Tipo catasto non impostato") 
        {
            ReturnValue = returnValue;
        }

        public CatastoException(string message) : base(message) 
        {
            ReturnValue = false;
        }

        public CatastoException(string message, bool returnValue) : base(message)
        {
            ReturnValue = returnValue;
        }

        public CatastoException(string message, System.Exception innerException) : base(message, innerException) 
        {
            ReturnValue = false;
        }

        public CatastoException(string message, System.Exception innerException, bool returnValue) : base(message, innerException)
        {
            ReturnValue = returnValue;
        }
    }
}
