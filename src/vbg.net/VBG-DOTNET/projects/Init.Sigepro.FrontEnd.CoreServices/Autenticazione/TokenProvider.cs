using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.CoreServices.Autenticazione
{
    public class TokenProvider : ITokenResolver
    {
        public string Token { get; set; }
    }
}
