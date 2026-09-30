using GeneratoreRiepiloghiHtml.AppLogic.Authorization;

namespace GeneratoreRiepiloghiHtml.AppLogic.Authentication
{
    public interface ITokenResolver
    {
        string Token { get; }
    }

    public class HttpContextTokenResolver : ITokenResolver
    {
        public HttpContextTokenResolver(ClaimsBasedIdentityService identityService)
        {
            this._identityService = identityService;
        }

        private readonly ClaimsBasedIdentityService _identityService;

        public string Token
        {
            get => this._identityService.GetUserClaims()?.Token ?? throw new Exception("Token non impostato");
        }
    }
}
