using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneRiepilogoDomanda
{
    public class SsuRiepilogoDomandaService
    {
        private readonly SsuCatalogoServiziClient _apiClient;
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;
        private readonly ILogger<SsuRiepilogoDomandaService> _logger;

        public SsuRiepilogoDomandaService(SsuCatalogoServiziClient apiClient, ISalvataggioDomandaStrategy persistenzaStrategy, ILogger<SsuRiepilogoDomandaService> logger)
        {
            this._persistenzaStrategy = persistenzaStrategy;
            this._logger = logger;
            this._apiClient = apiClient;
        }

        public async Task<RiepilogoDomanda> GetModelloRiepilogoAsync(int idDomanda)
        {
            using (this._logger.BeginScope("Recupero riepilogo domanda {idDomanda}", idDomanda))
            {
                try
                {
                    var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
                    var codiceEnte = domanda.ReadInterface.Ssu.CodiceEnte;
                    var idProcedimenti = domanda.ReadInterface.Ssu.Procedimenti?.Select(x => x.Id).ToArray();

                    this._logger.LogDebug("Recupero regime amministrativo per domanda {idDomanda} ({count} procedimenti): {idProcedimenti}", idDomanda, idProcedimenti.Length, String.Join(",", idProcedimenti));

                    var regimeAmministrativo = await this._apiClient.GetRegimiAmministrativiAsync(codiceEnte, idProcedimenti);

                    this._logger.LogDebug("Recuperato regime amministrativo per domanda {idDomanda}: {regimeAmministrativo}", idDomanda, regimeAmministrativo.Id);

                    var riepilogo = await this._apiClient.GetRiepilogoDomandaAsync(codiceEnte, regimeAmministrativo.Id.ToString());

                    this._logger.LogDebug("Riepilogo domanda {idDomanda} recuperato con successo", idDomanda);

                    if (riepilogo == null)
                    {
                        throw new Exception($"Non è stato trovato un regime amministrativo per codiceEnte={codiceEnte} e regime amministrativo={regimeAmministrativo.Id}");
                    }

                    return riepilogo!;
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore durante il recupero del riepilogo per la domanda {idDomanda}: {ex}", idDomanda, ex);
                    throw;
                }
            }
        }
    }
}
