using VBG.SecurityLibrary.Services;
using WsDatiDomandaService;
using static WsDatiDomandaService.WsDatiDomandaServiceClient;

namespace VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi.DomandeInBozza
{
    public class DomandeInBozzaService : IDomandeInBozzaService
    {
        private readonly ILogger<DomandeInBozzaService> _logger;
        private readonly ISecurityService _securityService;

        public DomandeInBozzaService(ILogger<DomandeInBozzaService> logger, ISecurityService securityService)
        {
            this._logger = logger;
            this._securityService = securityService;
        }

        private async Task<string> GetApplicationTokenAsync(string alias)
        {
            string token = await this._securityService.GetApplicationTokenAsync(alias) ?? "";

            if (string.IsNullOrEmpty(token))
            {
                this._logger.LogError("Impossibile recuperare il token applicativo per l'alias {0}", alias);
                throw new InvalidOperationException();
            }

            return token;
        }

        private WsDatiDomandaServiceClient CreateClient() 
        {
            var binding = EndpointConfiguration.BasicHttpBinding_IWsDatiDomandaService;

            var wsHostUrl = string.IsNullOrEmpty(this._securityService.GetParameter("WSHOSTURL_ASPNET_CORE")) ? this._securityService.GetParameter("WSHOSTURL_ASPNET") : this._securityService.GetParameter("WSHOSTURL_ASPNET_CORE");
            var endpoint = $"{wsHostUrl}/WebServices/WsAreaRiservata/WcfServices/DatiDomanda/WsDatiDomandaService.svc";

            this._logger.LogDebug("Creazione del client per il web service WsDatiDomandaService sull'endpoint {0} utilizzando il binding {1}", endpoint, binding);

            var ws = new WsDatiDomandaServiceClient(binding, endpoint);

            return ws;
        }

        public async Task EliminaDomandaAsync(string alias, int IdDomanda) 
        {
            try
            {          
                using (var ws = this.CreateClient())
                {
                    string token = await this.GetApplicationTokenAsync(alias);

                    this._logger.LogDebug("Invocazione del ws EliminaDomandaAsync all'indirizzo: {0}", ws.Endpoint.Address);

                    await ws.EliminaDomandaAsync(token, IdDomanda);
                }
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore nella chiamata a EliminaDomandaAsync: {0}", ex.ToString());
                throw;
            }
        }
    }
}
