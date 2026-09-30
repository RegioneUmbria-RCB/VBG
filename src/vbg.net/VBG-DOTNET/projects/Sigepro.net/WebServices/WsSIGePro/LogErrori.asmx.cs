using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;
using log4net;
using Ninject;
using Ninject.Web;
using System.Web.Services;

namespace SIGePro.Net.WebServices.WsSIGePro
{
    /// <summary>
    /// Descrizione di riepilogo per LogErrori.
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    public class LogErrori : WebServiceBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(LogErrori));

        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }


        // ESEMPIO DI SERVIZIO WEB
        // Il servizio di esempio HelloWorld() restituisce la stringa Hello World.
        // Per generare, rimuovere i commenti dalle righe seguenti, quindi salvare e generare il progetto.
        // Per verificare il servizio Web, premere F5.

        [WebMethod(Description = "Logga un errore nel log di sigepro")]
        public void Log(string token, string codiceErrore, string modulo, string descrizioneEstesa)
        {
            AuthenticationInfo authInfo = this._authenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);

            this._log.Error($"{modulo}: {descrizioneEstesa} - {codiceErrore}");
        }
    }
}
