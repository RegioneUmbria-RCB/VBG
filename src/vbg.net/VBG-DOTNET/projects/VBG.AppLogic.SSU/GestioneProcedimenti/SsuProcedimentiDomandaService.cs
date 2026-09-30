using Init.Sigepro.FrontEnd.AppLogic.GestioneDatiExtra;
using Microsoft.Extensions.Logging;
using System.Text.Json;

namespace VBG.AppLogic.SSU.GestioneProcedimenti
{
    public class SsuProcedimentiDomandaService
    {
        internal static class Constants
        {
            public const string DATO_EXTRA_PROCEDIMENTI_SSU = "SSU_PROCEDIMENTI";
        }

        private readonly IDatiExtraService _datiExtraService;
        private readonly ILogger<SsuProcedimentiDomandaService> _logger;

        public SsuProcedimentiDomandaService(IDatiExtraService datiExtraService, ILogger<SsuProcedimentiDomandaService> logger)
        {
            this._datiExtraService = datiExtraService;
            this._logger = logger;
        }

        public async Task<SsuProcedimentiDomanda> GetProcedimentiAsync(int idDomanda)
        {
            using (this._logger.BeginScope("Recupero procedimenti associati alla domanda {idDomanda}", idDomanda))
            {
                try
                {

                    var procedimentiDomanda = await this._datiExtraService.GetAsync<SsuProcedimentiDomanda>(idDomanda, Constants.DATO_EXTRA_PROCEDIMENTI_SSU);

                    return procedimentiDomanda;
                }
                catch (Exception ex)
                {
                    this._logger.LogError(ex, "Errore durante il recupero dei procedimenti associati alla domanda {idDomanda}: {ex}", idDomanda, ex);

                    throw;
                }
            }
        }

        public async Task SaveProcedimentiAsync(int idDomanda, SsuProcedimentiDomanda procedimenti)
        {
            using (this._logger.BeginScope("Salvataggio procedimenti associati alla domanda {idDomanda}", idDomanda))
            {

                if (this._logger.IsEnabled(LogLevel.Debug))
                {
                    var str = JsonSerializer.Serialize(procedimenti, new JsonSerializerOptions() { WriteIndented = true });
                    this._logger.LogDebug("Procedimenti da salvare: {str}", str);
                }

                try
                {
                    await this._datiExtraService.SetAsync(idDomanda, Constants.DATO_EXTRA_PROCEDIMENTI_SSU, procedimenti);
                }
                catch (Exception ex)
                {
                    this._logger.LogError(ex, "Errore durante il salvataggio dei procedimenti associati alla domanda {idDomanda}: {ex}", idDomanda, ex);

                    throw;
                }
            }
        }
    }
}
