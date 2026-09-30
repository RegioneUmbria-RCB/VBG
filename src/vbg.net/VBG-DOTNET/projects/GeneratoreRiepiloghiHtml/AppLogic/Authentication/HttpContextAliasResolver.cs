namespace GeneratoreRiepiloghiHtml.AppLogic.Authorization
{
    public class HttpContextAliasResolver : IAliasResolver
    {
        public HttpContextAliasResolver(ClaimsBasedIdentityService identityService)
        {
            this._identityService = identityService;
        }

        private readonly ClaimsBasedIdentityService _identityService;

        public string Alias
        {
            get => this._identityService.GetUserClaims()?.Alias ?? throw new Exception("Alias non impostato");
        }
    }
}