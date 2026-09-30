using GeneratoreRiepiloghiHtml.AppLogic.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc.Filters;
using VBG.SecurityLibrary.Services;

namespace GeneratoreRiepiloghiHtml.AppLogic.Authorization
{
    public class TokenAuthorizationFilter : IAsyncAuthorizationFilter
    {
        private static class Constants
        {
            public const string HeaderName = "Authorization";
            public const string Scheme = "bearer";
            public const string ResponseErrorString = "Utente non autorizzato";
        }

        private readonly ISecurityService _securityService;
        private readonly ClaimsBasedIdentityService _claimsService;

        public TokenAuthorizationFilter(ISecurityService securityService, ClaimsBasedIdentityService claimsService)
        {
            this._securityService = securityService;
            this._claimsService = claimsService;
        }

        public async Task OnAuthorizationAsync(AuthorizationFilterContext context)
        {
            var allowAnonymous = context.ActionDescriptor.EndpointMetadata.OfType<AllowAnonymousAttribute>().Any();

            if (allowAnonymous)
            {
                return;
            }

            // Ottieni il valore del token dagli header
            var tokenWithPrefix = context.HttpContext.Request.Headers[Constants.HeaderName]!.FirstOrDefault(); // Verifica se il token è valido

            if (tokenWithPrefix is null)
            {
                context.Result = Errore.ToUnauthorizedObjectResult();
                return;
            }

            var token = tokenWithPrefix!.Replace($"{Constants.Scheme}", "", StringComparison.OrdinalIgnoreCase).Trim();

            var tokenInfo = await this._securityService!.CheckTokenAsync(token!);

            if (tokenInfo == null)
            {
                context.Result = Errore.ToUnauthorizedObjectResult();
                return;
            }

            this._claimsService.SetUserIdentityClaims(token, tokenInfo);
        }

    }
}
