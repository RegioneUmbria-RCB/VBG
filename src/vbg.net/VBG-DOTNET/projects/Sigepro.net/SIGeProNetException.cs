using Init.SIGePro.Manager.Authentication;
using log4net;
using PersonalLib2.Data;
using System;

namespace SIGePro.Net
{
    /// <summary>
    /// Eccezione di tipo SIGeProNetException.
    /// </summary>
    public class SIGeProNetException : Exception
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(SIGeProNetException));

        public SIGeProNetException(AuthenticationInfo authInfo, string modulo, string message, Exception innerException) : base(message, innerException)
        {
            this._log.Error($"{authInfo?.IdComune},{modulo},{message}");
        }

        public SIGeProNetException(AuthenticationInfo authInfo, string modulo, string message) : this(authInfo, modulo, message, null)
        {
        }

        public SIGeProNetException(DataBase db, string idComune, string modulo, string message, Exception innerException) : base(message, innerException)
        {
            this._log.Error($"{idComune},{modulo},{message}");
        }

        public SIGeProNetException(DataBase db, string idComune, string modulo, string message) : this(db, idComune, modulo, message, null)
        {
        }

        public SIGeProNetException(string idComune, string message)
        {
            // TODO: come loggare in questo caso?
        }
        public SIGeProNetException() : base()
        {
        }
    }
}