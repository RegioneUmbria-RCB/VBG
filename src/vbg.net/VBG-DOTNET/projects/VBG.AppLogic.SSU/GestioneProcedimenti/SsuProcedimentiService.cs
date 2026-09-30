using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneProcedimenti
{
    public class SsuProcedimentiService
    {
        private readonly SsuCatalogoServiziClient _apiClient;
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;
        private readonly ILogger<SsuProcedimentiService> _logger;

        public SsuProcedimentiService(SsuCatalogoServiziClient apiClient, ISalvataggioDomandaStrategy persistenzaStrategy, ILogger<SsuProcedimentiService> logger)
        {
            this._persistenzaStrategy = persistenzaStrategy;
            this._logger = logger;
            this._apiClient = apiClient;
        }

        public async Task<PagedItemsOfProcedimento> GetProcedimentiAsync(int idDomanda, int? idEventoVita, int? idTipologia, string partial, int page = 0)
        {
            using (this._logger.BeginScope("Recupero procedimenti per la domanda {idDomanda}, idEventoVita={idEventoVita}, idTipologia={idTipologia}, partial={partial}, page={page}", idDomanda, idEventoVita, idTipologia, partial, page))
            {
                try
                {
                    var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
                    var codiceEnte = domanda.ReadInterface.Ssu.CodiceEnte;
                    var procedimenti = await this._apiClient.GetProcedimentiAsync(codiceEnte, idEventoVita, idTipologia, partial, page);
                    this._logger.LogDebug("Recuperati {count} di {totalCount} procedimenti per la domanda {idDomanda}", procedimenti.Items?.Count ?? 0, procedimenti.TotalCount, idDomanda);
                    return procedimenti;
                }
                catch (Exception ex)
                {
                    this._logger.LogError(ex, "Errore durante il recupero dei procedimenti per la domanda {@idDomanda}: {@ex}", idDomanda, ex);
                    throw;
                }
            }
        }

        public async Task<DettaglioProcedimentoSsuRidotto?> GetDettaglioProcedimentoAsync(string codiceEnte, int idProcedimento)
        {
            using (this._logger.BeginScope("Recupero dettaglio procedimento {idProcedimento} per l'ente {codiceEnte}", idProcedimento, codiceEnte))
            {
                try
                {
                    var dettaglio = await this._apiClient.GetProcedimentoByIdAsync(codiceEnte, idProcedimento);
                    this._logger.LogDebug("Recuperato dettaglio procedimento {idProcedimento} per l'ente {codiceEnte}", idProcedimento, codiceEnte);
                    return dettaglio;
                }
                catch (Exception ex)
                {
                    this._logger.LogError(ex, "Errore durante il recupero del dettaglio del procedimento {idProcedimento} per l'ente {codiceEnte}: {@ex}", idProcedimento, codiceEnte, ex);
                    throw;
                }
            }
        }
    }
}
