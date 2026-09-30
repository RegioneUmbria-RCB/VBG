using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneDatiDinamici
{
    public class SsuDatiDinamiciService
    {
        private readonly SsuCatalogoServiziClient _apiClient;
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;
        private readonly ILogger<SsuDatiDinamiciService> _logger;

        public SsuDatiDinamiciService(SsuCatalogoServiziClient apiClient, ISalvataggioDomandaStrategy persistenzaStrategy, ILogger<SsuDatiDinamiciService> logger)
        {
            this._persistenzaStrategy = persistenzaStrategy;
            this._logger = logger;
            this._apiClient = apiClient;
        }

        public async Task<List<ElementoListaSchedePerProcedimento>> GetDatiDinamiciAsync(int idDomanda)
        {
            using (this._logger.BeginScope("Recupero dati dinamici per la domanda {idDomanda} ", idDomanda))
            {
                try
                {
                    var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
                    var idProcedimenti = domanda.ReadInterface.Ssu.Procedimenti?.Select(p => p.Id).ToArray() ?? Array.Empty<int>();

                    this._logger.LogDebug("La domanda {idDomanda} contiene {count} procedimenti: {idProcedimenti}", idDomanda, idProcedimenti.Length, String.Join(",", idProcedimenti));

                    var codiceEnte = domanda.ReadInterface.Ssu.CodiceEnte;
                    var datiDinamici = await this._apiClient.GetSchedeDinamicheAsync(codiceEnte, idProcedimenti);

                    if (datiDinamici is null)
                    {
                        datiDinamici = new();
                    }

                    this._logger.LogDebug("Recuperati {count} dati dinamici per la domanda {idDomanda} e procedimento {idProcedimenti}", datiDinamici.Count, idDomanda, String.Join(",", idProcedimenti));

                    return datiDinamici;
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore durante il recupero dei dati dinamici per la domanda {idDomanda}: {ex}", idDomanda, ex);
                    throw;
                }
            }
        }

    }
}
