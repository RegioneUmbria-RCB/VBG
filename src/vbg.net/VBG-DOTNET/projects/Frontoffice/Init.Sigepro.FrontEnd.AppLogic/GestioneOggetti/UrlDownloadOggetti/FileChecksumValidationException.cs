using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti
{

    [Serializable]
    public class FileChecksumValidationException : Exception
    {
        public FileChecksumValidationException() { }
        public FileChecksumValidationException(string message) : base(message) { }
        public FileChecksumValidationException(string message, Exception inner) : base(message, inner) { }
        protected FileChecksumValidationException(
          System.Runtime.Serialization.SerializationInfo info,
          System.Runtime.Serialization.StreamingContext context) : base(info, context) { }
    }
}
