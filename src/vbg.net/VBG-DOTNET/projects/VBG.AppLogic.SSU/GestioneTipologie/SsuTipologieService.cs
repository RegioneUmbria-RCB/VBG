using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneTipologie
{
    public class SsuTipologieService
    {
        private readonly SsuCatalogoServiziClient _apiClient;
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;
        private readonly ILogger<SsuTipologieService> _logger;

        public SsuTipologieService(SsuCatalogoServiziClient apiClient, ISalvataggioDomandaStrategy persistenzaStrategy, ILogger<SsuTipologieService> logger)
        {
            this._persistenzaStrategy = persistenzaStrategy;
            this._logger = logger;
            this._apiClient = apiClient;
        }

        public async Task<PagedItemsOfTipologia> GetTipologieAsync(int idDomanda, string partialString)
        {
            using (this._logger.BeginScope("Recupero tipologie per la domanda {idDomanda}", idDomanda))
            {

                try
                {
                    var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
                    var codiceEnte = domanda.ReadInterface.Ssu.CodiceEnte;
                    var tipologie = await this._apiClient.GetTipologieAsync(codiceEnte, partialString);

                    this._logger.LogDebug("Recuperate {count} di {totalCount} tipologie per la domanda {idDomanda}", tipologie.Items?.Count ?? 0, tipologie.TotalCount, idDomanda);

                    return tipologie;
                }
                catch (Exception)
                {
                    this._logger.LogError("Errore durante il recupero delle tipologie per la domanda {idDomanda}", idDomanda);

                    throw;
                }
            }
        }
    }
}
