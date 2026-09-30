using System;

namespace IntegrazioneCUnicoWS
{

    [Serializable]
    public class CUnicoWSException : Exception
    {
        public CUnicoWSException() { }
        public CUnicoWSException(string message) : base(message) { }
        public CUnicoWSException(string message, Exception inner) : base(message, inner) { }
    }
}
