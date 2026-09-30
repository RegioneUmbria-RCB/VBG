using System;

namespace VBG.Backend.SIT.AppLogic.Exceptions
{
    [System.Serializable]
    public class SitValidationException : Exception
    {
        public SitValidationException() { }
        public SitValidationException(string message) : base(message) { }
        public SitValidationException(string message, Exception inner) : base(message, inner) { }
        protected SitValidationException(
          System.Runtime.Serialization.SerializationInfo info,
          System.Runtime.Serialization.StreamingContext context) : base(info, context) { }
    }
}
