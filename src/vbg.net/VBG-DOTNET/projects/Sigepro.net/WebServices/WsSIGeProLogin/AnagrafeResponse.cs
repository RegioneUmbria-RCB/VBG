using System;
using System.Collections.Generic;
using System.Web;

namespace Sigepro.net.WebServices.WsSIGeProLogin
{
    /// <summary>
    /// Risultato della registrazione di un utente e del salvataggio di una password
    /// </summary>
    public class AnagrafeResponse
    {
        public int ErrorCode;
        public string ErrorMessage;
        public string CodiceAnagrafe;
    }
}
