using IBCSecurityV2;
using System.Security.Claims;
using VBG.SecurityLibrary;

namespace GeneratoreRiepiloghiHtml.AppLogic.Authorization
{



    public class ClaimsBasedIdentityService : IUserIdentityService
    {
        private static class Claims
        {
            public const string Token = "Token";
            public const string UserId = "UserId";
            public const string Alias = "Alias";
            public const string IdComune = "IdComune";
            public const string Contesto = "Contesto";
        }

        public class TokenClaims : ITokenClaims
        {
            public required string Token { get; init; }
            public required string UserId { get; init; }
            public required string Alias { get; init; }
            public required string IdComune { get; init; }
            public required ContestoType Contesto { get; init; }
        }

        private readonly IHttpContextAccessor _httpContextAccessor;

        public ClaimsBasedIdentityService(IHttpContextAccessor httpContextAccessor)
        {
            this._httpContextAccessor = httpContextAccessor;
        }

        public void SetUserIdentityClaims(string token, UserAuthenticationResult authResult)
        {
            var identity = new ClaimsIdentity(new List<Claim>
            {
                new(Claims.Token, token!, ClaimValueTypes.String),
                new(Claims.UserId, authResult.UserId, ClaimValueTypes.String),
                new(Claims.Alias, authResult.Alias, ClaimValueTypes.String),
                new(Claims.IdComune, authResult.IdComune, ClaimValueTypes.String),
                new(Claims.Contesto, authResult.Contesto.ToString(), ClaimValueTypes.String),
            });

            var context = this._httpContextAccessor.HttpContext;

            context.User = new ClaimsPrincipal(identity);
        }

        public ITokenClaims? GetUserClaims()
        {
            var context = this._httpContextAccessor.HttpContext;

            if (context?.User is null)
            {
                return null;
            }

            var principal = context.User;

            return new TokenClaims
            {
                Alias = principal.FindFirstValue(Claims.Alias),
                IdComune = principal.FindFirstValue(Claims.IdComune),
                Contesto = Enum.Parse<ContestoType>(principal.FindFirstValue(Claims.Contesto)),
                Token = principal.FindFirstValue(Claims.Token),
                UserId = principal.FindFirstValue(Claims.UserId)
            };
        }
    }
}