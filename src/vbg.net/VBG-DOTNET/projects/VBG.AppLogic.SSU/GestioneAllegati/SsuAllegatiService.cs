using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;


namespace VBG.AppLogic.SSU.GestioneAllegati
{
    public class SsuAllegatiService
    {
        private readonly SsuCatalogoServiziClient _apiClient;
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;
        private readonly ILogger<SsuAllegatiService> _logger;

        public SsuAllegatiService(SsuCatalogoServiziClient apiClient, ISalvataggioDomandaStrategy persistenzaStrategy, ILogger<SsuAllegatiService> logger)
        {
            this._persistenzaStrategy = persistenzaStrategy;
            this._logger = logger;
            this._apiClient = apiClient;
        }

        public async Task<List<ElementoListaAllegatiProcedimento>> GetAllegatiAsync(int idDomanda)
        {
            var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
            var codiceEnte = domanda.ReadInterface.Ssu.CodiceEnte;
            var idProcedimenti = domanda.ReadInterface.Ssu.Procedimenti?.Select(x => x.Id).ToArray() ?? Array.Empty<int>();

            using (this._logger.BeginScope("Recupero allegati per la domanda {idDomanda} e procedimenti {idProcedimenti}", idDomanda, String.Join(",", idProcedimenti)))
            {
                try
                {
                    var allegati = await this._apiClient.GetAllegatiAsync(codiceEnte, idProcedimenti);
                    this._logger.LogDebug("Recuperati {count} allegati per la domanda {idDomanda} e procedimenti {idProcedimenti}", allegati?.Count ?? 0, idDomanda, String.Join(",", idProcedimenti));
                    return allegati!;
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore durante il recupero degli allegati per la domanda {idDomanda} e procedimenti {idProcedimenti}: {ex}", idDomanda, String.Join(",", idProcedimenti), ex);
                    throw;
                }
            }
        }

        public async Task SincronizzaAllegatiAsync(int idDomanda)
        {
            var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);

            await new SsuLogicaSincronizzazioneAllegatiEndo(domanda, this).SincronizzaAsync();

            await this._persistenzaStrategy.SalvaAsync(domanda);
        }
    }
}
