using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneEventiDellaVita
{
    public class SsuEventiDellaVitaService
    {
        private readonly SsuCatalogoServiziClient _apiClient;
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;
        private readonly ILogger<SsuEventiDellaVitaService> _logger;

        public SsuEventiDellaVitaService(SsuCatalogoServiziClient apiClient, ISalvataggioDomandaStrategy persistenzaStrategy, ILogger<SsuEventiDellaVitaService> logger)
        {
            this._apiClient = apiClient;
            this._persistenzaStrategy = persistenzaStrategy;
            this._logger = logger;
        }

        public async Task<IList<EventoDellaVita>> GetEventiDellaVitaAsync(int idDomanda)
        {
            using (this._logger.BeginScope("Recupero eventi della vita per la domanda {idDomanda}", idDomanda))
            {

                try
                {
                    var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);

                    var codiceEnte = domanda.ReadInterface.Ssu.CodiceEnte;

                    var eventi = await this._apiClient.GetEventiDellaVitaAsync(codiceEnte);

                    this._logger.LogDebug("Recuperati {count} eventi della vita per la domanda {idDomanda}", eventi.Count, idDomanda);

                    return eventi;
                }
                catch (Exception ex)
                {
                    this._logger.LogError(ex, "Errore durante il recupero degli eventi della vita per la domanda {idDomanda}: {ex}", idDomanda, ex);

                    throw;
                }
            }
        }
    }
}
