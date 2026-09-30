using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.STC.Adapter;
using Microsoft.Extensions.Logging;

namespace VBG.AppLogic.SSU.MapperValidator
{
    public class EsitoValidazioneDomanda
    {
        private readonly string[] _errori = Array.Empty<string>();


        public EsitoValidazioneDomanda(IEnumerable<string> errori)
        {
            this._errori = errori.ToArray();
        }

        public bool ValidazioneRiuscita => this._errori.Length == 0;
        public IEnumerable<string> Errori => this._errori;
    }


    public class SsuValidatorService
    {
        private readonly IIstanzaStcAdapter _istanzaStcAdapter;
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;
        private readonly IValidatorApiClient _apiClient;
        private readonly ILogger<SsuValidatorService> _logger;

        public SsuValidatorService(IIstanzaStcAdapter istanzaStcAdapter, ISalvataggioDomandaStrategy persistenzaStrategy, IValidatorApiClient apiClient, ILogger<SsuValidatorService> logger)
        {
            this._istanzaStcAdapter = istanzaStcAdapter;
            this._persistenzaStrategy = persistenzaStrategy;
            this._apiClient = apiClient;
            this._logger = logger;
        }

        public async Task<EsitoValidazioneDomanda> ValidaDomandaAsync(int idDomanda)
        {
            try
            {
                using var scope = this._logger.BeginScope("ValidaDomandaAsync - IdDomanda: {IdDomanda}", idDomanda);

                var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
                var domandaStc = this._istanzaStcAdapter.Adatta(domanda);

                this._logger.LogDebug("Inizio validazione per la domanda con id {IdDomanda}", idDomanda);

                var esitoValidazione = await this._apiClient.ValidateAsync(domandaStc);

                if (esitoValidazione.IsSuccess)
                {
                    this._logger.LogDebug("La validazione è riuscita per la domanda con id {IdDomanda}", idDomanda);

                    return new EsitoValidazioneDomanda(Enumerable.Empty<string>());
                }

                this._logger.LogDebug("La validazione è fallita per la domanda con id {IdDomanda}. Errori: {Errori}", idDomanda, esitoValidazione.HasValidationErrors ? string.Join(", ", esitoValidazione.ValidationErrors!) : esitoValidazione.ErrorMessage);

                return new EsitoValidazioneDomanda(esitoValidazione.HasValidationErrors ? esitoValidazione.ValidationErrors! : [esitoValidazione.ErrorMessage!]);
            }
            catch (Exception ex)
            {
                this._logger.LogError("Si è verificato un errore durante la validazione della domanda con id {IdDomanda}: {ex}", idDomanda, ex);

                throw;
            }
        }

        public async Task<EsitoValidazioneDomanda> ValidaESalvaDomandaAsync(int idDomanda)
        {
            try
            {
                using var scope = this._logger.BeginScope("ValidaESalvaDomandaAsync - IdDomanda: {IdDomanda}", idDomanda);

                var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
                var domandaStc = this._istanzaStcAdapter.Adatta(domanda);

                this._logger.LogDebug("Inizio validazione e salvataggio per la domanda con id {IdDomanda}", idDomanda);

                var esitoValidazione = await this._apiClient.ValidateAndSaveAsync(domandaStc);

                if (esitoValidazione.IsSuccess)
                {
                    this._logger.LogDebug("La validazione e salvataggio sono riusciti per la domanda con id {IdDomanda}", idDomanda);

                    return new EsitoValidazioneDomanda(Enumerable.Empty<string>());
                }

                this._logger.LogDebug("La validazione e salvataggio sono falliti per la domanda con id {IdDomanda}. Errori: {Errori}", idDomanda, esitoValidazione.HasValidationErrors ? string.Join(", ", esitoValidazione.ValidationErrors!) : esitoValidazione.ErrorMessage);

                return new EsitoValidazioneDomanda(esitoValidazione.HasValidationErrors ? esitoValidazione.ValidationErrors! : [esitoValidazione.ErrorMessage!]);
            }
            catch (Exception ex)
            {
                this._logger.LogError("Si è verificato un errore durante la validazione della domanda con id {IdDomanda}: {ex}", idDomanda, ex);

                throw;
            }
        }
    }
}
