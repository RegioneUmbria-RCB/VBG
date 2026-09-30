using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

namespace Init.Sigepro.FrontEnd.CoreServices.Autenticazione
{
    public class InitialApplicationState
    {
        public string Token { get; set; }
        public string Alias { get; set; }
        public string Software { get; set; }
        public UserAuthenticationResult UserAuthenticationResult { get; set; }
    }
}
