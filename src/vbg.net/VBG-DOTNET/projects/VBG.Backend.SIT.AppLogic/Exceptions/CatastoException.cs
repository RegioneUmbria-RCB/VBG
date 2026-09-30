
namespace VBG.Backend.SIT.AppLogic.Exceptions
{
    public class CatastoException : System.Exception
    {
        public bool ReturnValue
        {
            get; set;
        }

        public CatastoException() : base("Tipo catasto non impostato")
        {
            this.ReturnValue = false;
        }

        public CatastoException(bool returnValue) : base("Tipo catasto non impostato")
        {
            this.ReturnValue = returnValue;
        }

        public CatastoException(string message) : base(message)
        {
            this.ReturnValue = false;
        }

        public CatastoException(string message, bool returnValue) : base(message)
        {
            this.ReturnValue = returnValue;
        }

        public CatastoException(string message, System.Exception innerException) : base(message, innerException)
        {
            this.ReturnValue = false;
        }

        public CatastoException(string message, System.Exception innerException, bool returnValue) : base(message, innerException)
        {
            this.ReturnValue = returnValue;
        }
    }
}
