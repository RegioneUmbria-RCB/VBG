using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Microsoft.Extensions.Options;
using VBG.AppLogic.SSU.GeneratoreRicevute.Configurazione;

namespace VBG.AppLogic.SSU.GeneratoreRicevute
{
    public class TokenValidationFilter : IEndpointFilter
    {
        private readonly SigeproSecurityProxy _securityProxy;
        private readonly GeneratoreRicevuteOptions _options;

        public TokenValidationFilter(
            SigeproSecurityProxy securityProxy,
            IOptions<GeneratoreRicevuteOptions> options)
        {
            _securityProxy = securityProxy;
            _options = options.Value;
        }

        public async ValueTask<object?> InvokeAsync(
            EndpointFilterInvocationContext context,
            EndpointFilterDelegate next)
        {
            var token = context.GetArgument<string>(0); // primo parametro dell'endpoint

            var response = _securityProxy.CheckToken(token);
            if (!response.valid)
            {
                return Results.Unauthorized();
            }

            var applicationToken =
                _securityProxy.GetApplicationToken(_options.AliasIniziale);

            // lo rendi disponibile all'endpoint
            context.HttpContext.Items["ApplicationToken"] = applicationToken;

            return await next(context);
        }
    }

}
