using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Microsoft.Extensions.Logging;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Text.Json;
using System.Text.Json.Serialization;
using VBG.AppLogic.SSU.Configurazione;

namespace VBG.AppLogic.SSU.MapperValidator
{
    // ==================== REQUEST MODELS ====================

    /// <summary>
    /// Rappresenta una pratica con il contenuto XML codificato in Base64.
    /// </summary>
    public class PraticaRequest
    {
        /// <summary>
        /// XML della pratica codificato in Base64.
        /// </summary>
        [JsonPropertyName("praticaXml")]
        public required string PraticaXml { get; set; }
    }

    // ==================== RESPONSE MODELS ====================

    /// <summary>
    /// Risposta di successo per la validazione.
    /// </summary>
    public class SuccessResponse
    {
        [JsonPropertyName("esito")]
        public string Esito { get; set; } = string.Empty;

        [JsonPropertyName("messaggio")]
        public string Messaggio { get; set; } = string.Empty;
    }

    /// <summary>
    /// Risposta per la validazione e salvataggio della pratica.
    /// </summary>
    public class ValidateAndSaveResponse
    {
        [JsonPropertyName("esito")]
        public string Esito { get; set; } = string.Empty;

        /// <summary>
        /// Identificativo del file InstanceDescriptor.json salvato.
        /// </summary>
        [JsonPropertyName("idOggetto")]
        public long IdOggetto { get; set; }

        [JsonPropertyName("messaggio")]
        public string Messaggio { get; set; } = string.Empty;
    }

    /// <summary>
    /// Risposta di errore generico.
    /// </summary>
    public class ErrorResponse
    {
        [JsonPropertyName("errore")]
        public string? Errore { get; set; }
    }

    /// <summary>
    /// Risposta con lista di errori di validazione.
    /// </summary>
    public class ValidationErrorsResponse
    {
        [JsonPropertyName("errori")]
        public List<string> Errori { get; set; } = [];
    }

    /// <summary>
    /// Risposta contenente i file associati ad una domanda.
    /// </summary>
    public class FilesResponse
    {
        [JsonPropertyName("filesByIdDomanda")]
        public Dictionary<string, List<string>> FilesByIdDomanda { get; set; } = [];
    }

    // ==================== RESULT TYPES ====================

    /// <summary>
    /// Risultato generico di una chiamata API.
    /// </summary>
    /// <typeparam name="T">Tipo del risultato in caso di successo.</typeparam>
    public class ValidatorApiResult<T>
    {
        public bool IsSuccess { get; init; }
        public T? Data { get; init; }
        public string? ErrorMessage { get; init; }
        public List<string>? ValidationErrors { get; init; }
        public int StatusCode { get; init; }

        public bool HasValidationErrors => this.ValidationErrors != null && this.ValidationErrors.Count > 0;

        public static ValidatorApiResult<T> Success(T data, int statusCode) => new()
        {
            IsSuccess = true,
            Data = data,
            StatusCode = statusCode
        };

        public static ValidatorApiResult<T> Error(string errorMessage, int statusCode) => new()
        {
            IsSuccess = false,
            ErrorMessage = errorMessage,
            StatusCode = statusCode
        };

        public static ValidatorApiResult<T> ValidationError(List<string> errors, int statusCode) => new()
        {
            IsSuccess = false,
            ValidationErrors = errors,
            StatusCode = statusCode
        };
    }

    // ==================== CLIENT INTERFACE ====================

    /// <summary>
    /// Interfaccia per il client dell'API Validator SSU.
    /// </summary>
    public interface IValidatorApiClient
    {
        /// <summary>
        /// Valida una pratica XML codificata in Base64.
        /// </summary>
        /// <param name="praticaXml">XML della pratica codificato in Base64.</param>
        /// <param name="cancellationToken">Token di cancellazione.</param>
        /// <returns>Risultato della validazione.</returns>
        Task<ValidatorApiResult<SuccessResponse>> ValidateAsync(DettaglioPraticaType pratica, CancellationToken cancellationToken = default);

        /// <summary>
        /// Valida e salva una pratica XML codificata in Base64.
        /// </summary>
        /// <param name="praticaXml">XML della pratica codificato in Base64.</param>
        /// <param name="cancellationToken">Token di cancellazione.</param>
        /// <returns>Risultato della validazione e salvataggio con l'ID oggetto.</returns>
        Task<ValidatorApiResult<ValidateAndSaveResponse>> ValidateAndSaveAsync(DettaglioPraticaType pratica, CancellationToken cancellationToken = default);
    }

    // ==================== CLIENT IMPLEMENTATION ====================

    /// <summary>
    /// Client per l'API Validator SSU conforme alla specifica OpenAPI.
    /// </summary>
    public class ValidatorApiClient : IValidatorApiClient
    {
        private readonly JsonSerializerOptions _jsonOptions;
        private readonly IConfigurazione<ParametriSsu> _configurazioneSsu;
        private readonly ITokenResolver _tokenResolver;
        private readonly ILogger<ValidatorApiClient> _log;

        /// <summary>
        /// Crea una nuova istanza del client.
        /// </summary>
        public ValidatorApiClient(IConfigurazione<ParametriSsu> configurazioneSsu, ITokenResolver tokenResolver, ILogger<ValidatorApiClient> log)
        {
            this._jsonOptions = new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true,
                DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull
            };
            this._jsonOptions.Converters.Add(new JsonStringEnumConverter());
            this._configurazioneSsu = configurazioneSsu;
            this._tokenResolver = tokenResolver;
            this._log = log;
        }

        /// <inheritdoc/>
        public async Task<ValidatorApiResult<SuccessResponse>> ValidateAsync(DettaglioPraticaType pratica, CancellationToken cancellationToken = default)
        {
            this._log.LogInformation("Inizio validazione pratica");

            try
            {
                var request = new PraticaRequest { PraticaXml = this.ConvertToBase64(pratica) };

                using var httpClient = this.CreateHttpClient();

                var response = await httpClient.PostAsJsonAsync("api/v1/pratiche/validate", request, this._jsonOptions, cancellationToken);
                this._log.LogDebug("Ricevuta risposta dal validator con status code: {StatusCode}", (int)response.StatusCode);

                var result = await this.HandleResponseAsync<SuccessResponse>(response, cancellationToken);

                if (result.IsSuccess)
                {
                    this._log.LogInformation("Validazione pratica completata con successo");
                }
                else if (result.HasValidationErrors)
                {
                    this._log.LogWarning("Validazione pratica fallita con {Count} errori di validazione", result.ValidationErrors?.Count ?? 0);
                }
                else
                {
                    this._log.LogError("Validazione pratica fallita: {ErrorMessage}", result.ErrorMessage);
                }

                return result;
            }
            catch (Exception ex)
            {
                this._log.LogError(ex, "Errore durante la validazione della pratica");
                throw;
            }
        }

        private HttpClient CreateHttpClient()
        {
            var baseAddress = this._configurazioneSsu.Parametri.BaseUrlValidator;
            this._log.LogDebug("Creazione HttpClient per base address: {BaseAddress}", baseAddress);

            var httpClient = new HttpClient();
            // httpClient.DefaultRequestHeaders.Add("X-Security-Token", this._tokenResolver.Token);
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", this._tokenResolver.Token);
            httpClient.BaseAddress = new Uri(baseAddress);

            return httpClient;
        }

        /// <inheritdoc/>
        public async Task<ValidatorApiResult<ValidateAndSaveResponse>> ValidateAndSaveAsync(DettaglioPraticaType pratica, CancellationToken cancellationToken = default)
        {
            this._log.LogInformation("Inizio validazione e salvataggio pratica");

            try
            {
                var request = new PraticaRequest { PraticaXml = this.ConvertToBase64(pratica) };

                using var httpClient = this.CreateHttpClient();
                // httpClient.DefaultRequestHeaders.Add("X-Security-Token", this._tokenResolver.Token);
                // httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", this._tokenResolver.Token);

                var response = await httpClient.PostAsJsonAsync("api/v1/pratiche/validate-and-save", request, this._jsonOptions, cancellationToken);
                this._log.LogDebug("Ricevuta risposta dal validator con status code: {StatusCode}", (int)response.StatusCode);

                var result = await this.HandleResponseAsync<ValidateAndSaveResponse>(response, cancellationToken);

                if (result.IsSuccess)
                {
                    this._log.LogInformation("Validazione e salvataggio pratica completati con successo. IdOggetto: {IdOggetto}", result.Data?.IdOggetto ?? 0);
                }
                else if (result.HasValidationErrors)
                {
                    this._log.LogWarning("Validazione e salvataggio pratica falliti con {Count} errori di validazione", result.ValidationErrors?.Count ?? 0);
                }
                else
                {
                    this._log.LogError("Validazione e salvataggio pratica falliti: {ErrorMessage}", result.ErrorMessage);
                }

                return result;
            }
            catch (Exception ex)
            {
                this._log.LogError(ex, "Errore durante la validazione e il salvataggio della pratica");
                throw;
            }
        }

        private string ConvertToBase64(DettaglioPraticaType pratica)
        {
            this._log.LogDebug("Conversione pratica in Base64");

            var xmlSerializer = new System.Xml.Serialization.XmlSerializer(typeof(DettaglioPraticaType));
            using var stringWriter = new StringWriter();
            xmlSerializer.Serialize(stringWriter, pratica);
            var xmlString = stringWriter.ToString();

            this._log.LogDebug("XML della pratica da inviare al validator: {xmlString}", xmlString);

            var base64String = Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes(xmlString));
            this._log.LogDebug("Pratica convertita in Base64, lunghezza: {Length} caratteri", base64String.Length);

            return base64String;
        }

        private async Task<ValidatorApiResult<T>> HandleResponseAsync<T>(HttpResponseMessage response, CancellationToken cancellationToken)
        {
            var statusCode = (int)response.StatusCode;
            this._log.LogDebug("Elaborazione risposta HTTP con status code: {StatusCode}", statusCode);

            if (response.IsSuccessStatusCode)
            {
                this._log.LogDebug("Risposta HTTP di successo, deserializzazione del contenuto");
                var data = await response.Content.ReadFromJsonAsync<T>(this._jsonOptions, cancellationToken);
                return ValidatorApiResult<T>.Success(data!, statusCode);
            }

            this._log.LogWarning("Risposta HTTP di errore con status code: {StatusCode}", statusCode);

            // Tentativo di leggere errori di validazione
            try
            {
                var content = await response.Content.ReadAsStringAsync(cancellationToken);
                this._log.LogDebug("Contenuto della risposta di errore: {Content}", content);

                // Prova a deserializzare come ValidationErrorsResponse
                var validationErrors = JsonSerializer.Deserialize<ValidationErrorsResponse>(content, this._jsonOptions);
                if (validationErrors?.Errori is { Count: > 0 })
                {
                    this._log.LogWarning("Rilevati {Count} errori di validazione dalla risposta", validationErrors.Errori.Count);
                    return ValidatorApiResult<T>.ValidationError(validationErrors.Errori, statusCode);
                }

                // Prova a deserializzare come ErrorResponse
                var errorResponse = JsonSerializer.Deserialize<ErrorResponse>(content, this._jsonOptions);
                if (!string.IsNullOrEmpty(errorResponse?.Errore))
                {
                    this._log.LogError("Errore dalla risposta: {Errore}", errorResponse.Errore);
                    return ValidatorApiResult<T>.Error(errorResponse.Errore, statusCode);
                }

                // Restituisce il contenuto raw se non è possibile deserializzare
                this._log.LogWarning("Impossibile deserializzare la risposta di errore, ritorno contenuto raw");
                return ValidatorApiResult<T>.Error(content, statusCode);
            }
            catch (JsonException ex)
            {
                this._log.LogError(ex, "Errore durante la deserializzazione della risposta di errore");
                return ValidatorApiResult<T>.Error($"Errore HTTP {statusCode}", statusCode);
            }
        }
    }
}
