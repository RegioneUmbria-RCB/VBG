using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneFattispecie
{
    public class SsuFattispecieService
    {
        private readonly SsuCatalogoServiziClient _apiClient;
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;
        private readonly ILogger<SsuFattispecieService> _logger;

        public SsuFattispecieService(SsuCatalogoServiziClient apiClient, ISalvataggioDomandaStrategy persistenzaStrategy, ILogger<SsuFattispecieService> logger)
        {
            this._persistenzaStrategy = persistenzaStrategy;
            this._logger = logger;
            this._apiClient = apiClient;
        }


        public async Task<List<Fattispecie>> GetFattispeciePrimarieAsync(int idDomanda, int idProcedimento)
        {
            using (this._logger.BeginScope("Recupero fattispecie primarie per la domanda {idDomanda} e procedimento {idProcedimento}", idDomanda, idProcedimento))
            {

                try
                {
                    var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
                    var codiceEnte = domanda.ReadInterface.Ssu.CodiceEnte;
                    var fattispecie = await this._apiClient.GetFattispeciePrimarieAsync(codiceEnte, idProcedimento);

                    this._logger.LogDebug("Recuperate {count} fattispecie primarie per la domanda {idDomanda} e procedimento {idProcedimento}", fattispecie?.Count ?? 0, idDomanda, idProcedimento);

                    return fattispecie!;
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore durante il recupero delle fattispecie primarie per la domanda {idDomanda} e procedimento {idProcedimento}: {ex}", idDomanda, idProcedimento, ex);

                    throw;
                }
            }
        }

        public async Task<List<ElementoListaFattispecieSecondarie>> GetFattispecieSecondarieAsync(int idDomanda, int[] primarie)
        {
            using (this._logger.BeginScope("Recupero fattispecie secondarie per la domanda {idDomanda} e fattispecie primarie {primarie}", idDomanda, string.Join(", ", primarie)))
            {
                try
                {
                    var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
                    var codiceEnte = domanda.ReadInterface.Ssu.CodiceEnte;

                    var fattispecie = await this._apiClient.GetFattispecieSecondarieAsync(codiceEnte, primarie);
                    this._logger.LogDebug("Recuperate {count} fattispecie secondarie per l'ente {codiceEnte} e fattispecie primarie {primarie}", fattispecie?.Count ?? 0, codiceEnte, string.Join(", ", primarie));
                    return fattispecie!;
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore durante il recupero delle fattispecie secondarie per la domanda {idDomanda}  e fattispecie primarie {primarie}: {ex}", idDomanda, string.Join(", ", primarie), ex);
                    throw;
                }
            }
        }

        public async Task<Procedimento> GetProcedimentoByIdFattispecieAsync(int idDomanda, int idFattispecie)
        {
            using (this._logger.BeginScope("Recupero procedimento per la domanda {idDomanda} e fattispecie {idFattispecie}", idDomanda, idFattispecie))
            {
                try
                {
                    var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
                    var codiceEnte = domanda.ReadInterface.Ssu.CodiceEnte;
                    var procedimento = await this._apiClient.GetProcedimentoByIdFattispecieAsync(codiceEnte, idFattispecie);
                    this._logger.LogDebug("Recuperato procedimento {procedimentoId} per l'ente {codiceEnte} e fattispecie {idFattispecie}", procedimento.Id, codiceEnte, idFattispecie);
                    return procedimento!;
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore durante il recupero del procedimento per la domanda {idDomanda} e fattispecie {idFattispecie}: {ex}", idDomanda, idFattispecie, ex);
                    throw;
                }
            }
        }
    }
}
