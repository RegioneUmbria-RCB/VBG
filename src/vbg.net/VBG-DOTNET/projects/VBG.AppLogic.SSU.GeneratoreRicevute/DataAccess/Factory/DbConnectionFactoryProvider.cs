using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.DataAccess;
using Init.Sigepro.FrontEnd.CoreServices.Autenticazione;
using Microsoft.Extensions.Options;
using VBG.AppLogic.SSU.GeneratoreRicevute.Configurazione;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.DataAccess.Factory
{
    public class DbConnectionFactoryProvider : IDbConnectionFactoryProvider
    {
        private readonly IServiceProvider _serviceProvider;
        private readonly IOptions<GeneratoreRicevuteOptions> _options;
        private readonly SigeproSecurityProxy _sigeproSecurityProxy;
        private readonly ILogger<DbConnectionFactoryProvider> _logger;

        public DbConnectionFactoryProvider(
            IServiceProvider serviceProvider,
            IOptions<GeneratoreRicevuteOptions> options,
            SigeproSecurityProxy sigeproSecurityProxy,
            ILogger<DbConnectionFactoryProvider> logger)
        {
            this._serviceProvider = serviceProvider;
            this._options = options;
            this._sigeproSecurityProxy = sigeproSecurityProxy;
            this._logger = logger;
        }

        private string AliasIniziale => this._options.Value.AliasIniziale;

        private bool CheckApplicationToken(string? applicationToken)
        {
            if (string.IsNullOrEmpty(applicationToken))
            {
                return false;
            }

            var response = this._sigeproSecurityProxy.CheckToken(applicationToken);

            if (response.tokenInfo is null ||
                response.tokenInfo.alias.ToUpper() != AliasIniziale.ToUpper())
            {
                _logger.LogInformation(
                    "Accesso negato. l'id comune iniziale {0} non corrisponde con quello del token ({1})",
                    AliasIniziale,
                    response.tokenInfo?.alias);

                return false;
            }

            return true;
        }

        public DbConnectionFactory Create(string applicationToken)
        {
            if (!CheckApplicationToken(applicationToken))
            {
                throw new UnauthorizedAccessException("Accesso negato: token non valido");
            }

            using var scope = this._serviceProvider.CreateScope();
            var tokenService = scope.ServiceProvider.GetRequiredService<TokenProvider>();
            tokenService.Token = applicationToken;

            return new DbConnectionFactory(
                new AliasSoftwareProvider
                {
                    AliasComune = AliasIniziale
                }, 
                this._sigeproSecurityProxy, tokenService
            );
        }

        public DbConnectionFactory Create()
        {
            using var scope = this._serviceProvider.CreateScope();
            var tokenService = scope.ServiceProvider.GetRequiredService<TokenProvider>();
            var tokenIniziale = this._sigeproSecurityProxy.GetApplicationToken(AliasIniziale);
            tokenService.Token = tokenIniziale;
            return new DbConnectionFactory(new AliasSoftwareProvider
            {
                AliasComune = AliasIniziale
            }, this._sigeproSecurityProxy, tokenService);
        }
    }
}
