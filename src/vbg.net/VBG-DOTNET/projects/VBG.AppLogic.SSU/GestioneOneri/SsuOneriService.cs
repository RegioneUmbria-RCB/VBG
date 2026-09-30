using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneOneri
{
    public class SsuOneriService
    {
        private readonly SsuCatalogoServiziClient _apiClient;
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;
        private readonly ILogger<SsuOneriService> _logger;

        public SsuOneriService(SsuCatalogoServiziClient apiClient, ISalvataggioDomandaStrategy persistenzaStrategy, ILogger<SsuOneriService> logger)
        {
            this._persistenzaStrategy = persistenzaStrategy;
            this._logger = logger;
            this._apiClient = apiClient;
        }

        public async Task<List<ElementoListaOneriProcedimento>> GetOneriAsync(int idDomanda)
        {
            var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
            var codiceEnte = domanda.ReadInterface.Ssu.CodiceEnte;
            var idProcedimenti = domanda.ReadInterface.Ssu.Procedimenti?.Select(x => x.Id).ToArray() ?? Array.Empty<int>();

            using (this._logger.BeginScope("Recupero oneri per la domanda {idDomanda} e procedimenti {idProcedimenti}", idDomanda, String.Join(",", idProcedimenti)))
            {
                try
                {
                    var oneri = await this._apiClient.GetOneriAsync(codiceEnte, idProcedimenti);
                    this._logger.LogDebug("Recuperati {count} oneri per la domanda {idDomanda} e procedimenti {idProcedimenti}", oneri?.Count ?? 0, idDomanda, String.Join(",", idProcedimenti));
                    return oneri!;
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore durante il recupero degli oneri per la domanda {idDomanda} e procedimenti {idProcedimenti}: {ex}", idDomanda, String.Join(",", idProcedimenti), ex);
                    throw;
                }
            }
        }

        public async Task SincronizzaOneriAsync(int idDomanda)
        {
            try
            {
                this._logger.LogDebug("inizio sincronizzazione oneri domanda ssu");
                var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);

                await new SsuLogicaSincronizzazioneOneri(domanda, this).SincronizzaOneriAsync();

                await this._persistenzaStrategy.SalvaAsync(domanda);
                this._logger.LogDebug("sincronizzazione oneri domanda ssu terminata");
            }
            catch (Exception ex) 
            {
                this._logger.LogError("Errore durante la sincronizzazione degli oneri della domanda: {0}", ex.ToString());
            }
        }
    }
}
