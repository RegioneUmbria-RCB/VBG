

using Microsoft.Extensions.Options;
using ServiceReference1;
using static ServiceReference1.NlaClient;


namespace StcServerTest
{
    public class NlaService
    {
        private readonly NlaConfigurations _config;
        private readonly ILogger<NlaService> _logger;

        public NlaService(ILogger<NlaService> logger, IOptions<NlaConfigurations> config)
        {
            this._logger = logger;
            this._config = config.Value;
        }

        private NlaClient CreateClient()
        {
            string url = this._config.Endpoint;
            return new NlaClient(EndpointConfiguration.BasicHttpBinding_Nla, url);
        }

        public async Task<InserimentoAttivitaNLAResponse1> InserimentoAttivitaNLAAsync(InserimentoAttivitaNLARequest request) 
        {
            using (var ws = this.CreateClient())
            {
                try
                {
                    return await ws.InserimentoAttivitaNLAAsync(request);
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore durante la chiamata a InserimentoAttivitaNLAAsync: {0}", ex);
                    ws.Abort();
                    throw;
                }
            }
        }

        public ServiceReference1.SportelloType GetSportelloMittente() 
        {
            var sportelloMittente = this._config.SportelloMittente;

            return new ServiceReference1.SportelloType
            {
                idSportello = sportelloMittente.IdSportello,
                idEnte = sportelloMittente.IdEnte,
                idNodo = sportelloMittente.IdNodo
            };
        }

        public ServiceReference1.SportelloType GetSportelloDestinatario()
        {
            var sportelloDestinatario = this._config.SportelloDestinatario;

            return new ServiceReference1.SportelloType
            {
                idSportello = sportelloDestinatario.IdSportello,
                idEnte = sportelloDestinatario.IdEnte,
                idNodo = sportelloDestinatario.IdNodo
            };
        }
    }
}
