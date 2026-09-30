using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneTipiSoggetto
{
    public class SsuTipiSoggettoService
    {
        private readonly SsuCatalogoServiziClient _apiClient;
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;
        private readonly ILogger<SsuTipiSoggettoService> _logger;

        public SsuTipiSoggettoService(SsuCatalogoServiziClient apiClient, ISalvataggioDomandaStrategy persistenzaStrategy, ILogger<SsuTipiSoggettoService> logger)
        {
            this._persistenzaStrategy = persistenzaStrategy;
            this._logger = logger;
            this._apiClient = apiClient;
        }

        public async Task<List<TipoSoggetto>> GetTipiSoggettoAsync(int idDomanda)
        {
            using (this._logger.BeginScope("Recupero tipi soggetto per la domanda {idDomanda}", idDomanda))
            {
                var idProcedimenti = Array.Empty<int>();
                try
                {
                    var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
                    var codiceEnte = domanda.ReadInterface.Ssu.CodiceEnte;

                    idProcedimenti = domanda.ReadInterface.Ssu.Procedimenti?.Select(x => x.Id).ToArray() ?? Array.Empty<int>();

                    var tipiSoggetto = await this._apiClient.GetTipiSoggettoAsync(codiceEnte, idProcedimenti);
                    this._logger.LogDebug("Recuperati {count} tipi soggetto per la domanda {idDomanda} e procedimento {idProcedimenti}", tipiSoggetto?.Count ?? 0, idDomanda, String.Join(",", idProcedimenti));
                    return tipiSoggetto!;
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore durante il recupero dei tipi soggetto per la domanda {idDomanda} e procedimento {idProcedimenti}: {ex}", idDomanda, String.Join(",", idProcedimenti), ex);
                    throw;
                }
            }
        }
    }
}
