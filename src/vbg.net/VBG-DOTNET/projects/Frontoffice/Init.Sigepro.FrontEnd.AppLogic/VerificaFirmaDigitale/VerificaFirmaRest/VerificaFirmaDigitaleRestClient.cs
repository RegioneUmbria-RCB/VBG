using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading;
using System.Threading.Tasks;


namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.VerificaFirmaRest
{
    public class VerificaFirmaDigitaleRestClient
    {
        private readonly ILogger<VerificaFirmaDigitaleRestClient> _logger;
        private readonly IConfigurazione<ParametriSigeproSecurity> _configurazione;
        private readonly IHttpClientFactory _httpClientFactory;

        public VerificaFirmaDigitaleRestClient(ILoggerFactory loggerFactory, IConfigurazione<ParametriSigeproSecurity> configurazione, IHttpClientFactory httpClientFactory)
        {
            this._logger = loggerFactory.CreateLogger<VerificaFirmaDigitaleRestClient>();
            this._configurazione = configurazione;
            this._httpClientFactory = httpClientFactory;
        }

        public class EsitoFirmaDTO
        {
            [JsonPropertyName("esitofirma")]
            public bool EsitoFirma { get; set; }
        }

        public class ValidationCfResultDTO
        {
            [JsonPropertyName("cfpresenti")]
            public List<string> CfPresenti { get; set; } = new();

            [JsonPropertyName("cfassenti")]
            public List<string> CfAssenti { get; set; } = new();

            [JsonPropertyName("esitofirma")]
            public bool EsitoFirma { get; set; }

            [JsonPropertyName("simpleReportSummary")]
            public SimpleReportSummaryDTO? SimpleReportSummary { get; set; }
        }

        public class SimpleReportSummaryDTO
        {
            [JsonPropertyName("documentName")]
            public string? DocumentName { get; set; }

            [JsonPropertyName("validationTime")]
            public string ValidationTime { get; set; } = "";

            [JsonPropertyName("policyName")]
            public string? PolicyName { get; set; }

            [JsonPropertyName("validSignaturesCount")]
            public int ValidSignaturesCount { get; set; }

            [JsonPropertyName("signaturesCount")]
            public int SignaturesCount { get; set; }

            [JsonPropertyName("signatureSummaries")]
            public List<SignatureSummaryItemDTO> SignatureSummaries { get; set; } = new();
        }

        public class SignatureSummaryItemDTO
        {
            [JsonPropertyName("signatureId")]
            public string? SignatureId { get; set; }

            [JsonPropertyName("indication")]
            public string? Indication { get; set; }

            [JsonPropertyName("subIndication")]
            public string? SubIndication { get; set; }

            [JsonPropertyName("signatureFormat")]
            public string? SignatureFormat { get; set; }

            [JsonPropertyName("signedBy")]
            public string? SignedBy { get; set; }

            [JsonPropertyName("errorsSummary")]
            public string? ErrorsSummary { get; set; }

            [JsonPropertyName("warningsSummary")]
            public string? WarningsSummary { get; set; }
        }

        public class ValidationResultDTO
        {
            [JsonPropertyName("simpleReportXml")]
            public string? SimpleReportXml { get; set; }

            [JsonPropertyName("detailedReportXml")]
            public string? DetailedReportXml { get; set; }

            [JsonPropertyName("diagnosticDataXml")]
            public string? DiagnosticDataXml { get; set; }

            [JsonPropertyName("simpleReportSummary")]
            public SimpleReportSummaryDTO? SimpleReportSummary { get; set; }

            [JsonPropertyName("extractedContent")]
            public byte[]? ExtractedContent { get; set; }

            [JsonPropertyName("extractedContentFileName")]
            public string? ExtractedContentFileName { get; set; }

            [JsonPropertyName("validationErrorMessage")]
            public string? ValidationErrorMessage { get; set; }
        }


        public async Task<StatoVerificaFirma> VerificaFirmaDigitaleAsync(BinaryFile file, bool verificaAllaData = true, CancellationToken cancellationToken = default)
        {
            var baseUurl = this._configurazione.Parametri.UrlVerificaFirmaRestService;
            var url = $"{baseUurl}/api/checkfirma/verificaFirma";

            using (var client = this._httpClientFactory.CreateClient())
            {
                var fileContent = new ByteArrayContent(file.FileContent);
                fileContent.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");

                var postRequest = new HttpRequestMessage(HttpMethod.Post, url)
                {
                    Content = new MultipartFormDataContent
                    {
                        { fileContent, "documento", file.FileName },
                        { new StringContent(JsonSerializer.Serialize(verificaAllaData),
                            Encoding.UTF8,
                            "application/json"),
                        "verificaAllaData"}
                    }
                };

                this._logger.LogDebug("VerificaFirmaDigitaleAsync: invio della richiesta {requestContent}", postRequest.Content);

                var response = await client.SendAsync(postRequest, cancellationToken);

                if (!response.IsSuccessStatusCode)
                {
                    var responseContent = await response.Content.ReadAsStringAsync();

                    this._logger.LogError("Errore durante la verifica della firma: corpo della risposta ({StatusCode}): {Response}",
                    response.StatusCode,
                    responseContent);

                    response.EnsureSuccessStatusCode();
                }

                var result = await response.Content.ReadFromJsonAsync<EsitoFirmaDTO>(cancellationToken);

                if (result is null)
                    throw new InvalidOperationException("Risposta non valida.");

                this._logger.LogDebug("VerificaFirmaDigitaleAsync: firma digitale verificata con successo");

                return result.EsitoFirma ? StatoVerificaFirma.FirmaValida : StatoVerificaFirma.FirmaNonValida;
            }
        }

        public async Task<ValidationCfResultDTO> ValidaFirmatariAsync(BinaryFile file, IEnumerable<string> codiciFiscali, bool verificaAllaData = true, CancellationToken cancellationToken = default)
        {
            var baseUrl = this._configurazione.Parametri.UrlVerificaFirmaRestService;
            var url = $"{baseUrl}/api/checkfirma/validaFirmatari";

            using (var client = this._httpClientFactory.CreateClient())
            {

                var fileContent = new ByteArrayContent(file.FileContent);
                fileContent.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");

                var multipart = new MultipartFormDataContent
                {
                    { fileContent, "documento", file.FileName },
                    {
                        new StringContent(
                            JsonSerializer.Serialize(verificaAllaData),
                            Encoding.UTF8,
                            "application/json"),
                        "verificaAllaData"
                    },
                    {
                        new StringContent(
                            JsonSerializer.Serialize(codiciFiscali),
                            Encoding.UTF8,
                            "application/json"),
                        "cf"
                    }
                };


                var postRequest = new HttpRequestMessage(HttpMethod.Post, url)
                {
                    Content = multipart
                };

                this._logger.LogDebug("ValidaFirmatariAsync: invio della richiesta {requestContent}", postRequest.Content);

                var response = await client.SendAsync(postRequest, cancellationToken);

                if (!response.IsSuccessStatusCode)
                {
                    var responseContent = await response.Content.ReadAsStringAsync();

                    this._logger.LogError(
                        "Errore durante la validazione dei firmatari ({StatusCode}): {Response}",
                        response.StatusCode,
                        responseContent);

                    response.EnsureSuccessStatusCode();
                }

                var result = await response.Content.ReadFromJsonAsync<ValidationCfResultDTO>(cancellationToken);

                if (result is null)
                    throw new InvalidOperationException("Risposta non valida.");

                this._logger.LogDebug("ValidaFirmatariAsync completato con successo.");

                return result;
            }
        }


        public async Task<ValidationResultDTO> ReportAsync(BinaryFile file, bool verificaAllaData = true, bool estraiFileNonFirmato = false, CancellationToken cancellationToken = default)
        {
            var baseUrl = this._configurazione.Parametri.UrlVerificaFirmaRestService;
            var url = $"{baseUrl}/api/checkfirma/report";

            using (var client = this._httpClientFactory.CreateClient())
            {

                var fileContent = new ByteArrayContent(file.FileContent);
                fileContent.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");

                var postRequest = new HttpRequestMessage(HttpMethod.Post, url)
                {
                    Content = new MultipartFormDataContent
                    {
                        { fileContent, "documento", file.FileName },
                        {
                            new StringContent(
                                JsonSerializer.Serialize(verificaAllaData),
                                Encoding.UTF8,
                                "application/json"),
                            "verificaAllaData"
                        },
                        {
                            new StringContent(
                                JsonSerializer.Serialize(estraiFileNonFirmato),
                                Encoding.UTF8,
                                "application/json"),
                            "estraiFileNonFirmato"
                        }
                    }
                };

                this._logger.LogDebug("ReportAsync: invio della richiesta {requestContent}", postRequest.Content);

                var response = await client.SendAsync(postRequest, cancellationToken);

                if (!response.IsSuccessStatusCode)
                {
                    var responseContent = await response.Content.ReadAsStringAsync();

                    this._logger.LogError(
                        "Errore durante la generazione del report ({StatusCode}): {Response}",
                        response.StatusCode,
                        responseContent);

                    response.EnsureSuccessStatusCode();
                }

                var result = await response.Content.ReadFromJsonAsync<ValidationResultDTO>(cancellationToken);

                if (result is null)
                    throw new InvalidOperationException("Risposta non valida.");

                this._logger.LogDebug("ReportAsync completato con successo.");

                return result;
            }
        }

        public async Task<BinaryFile> ScaricaFileNonFirmatoAsync(BinaryFile file, CancellationToken cancellationToken = default)
        {
            var baseUrl = this._configurazione.Parametri.UrlVerificaFirmaRestService;
            var url = $"{baseUrl}/api/checkfirma/scaricaFileNonFirmato";

            using var client = this._httpClientFactory.CreateClient();

            var fileContent = new ByteArrayContent(file.FileContent);
            fileContent.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");

            var postRequest = new HttpRequestMessage(HttpMethod.Post, url)
            {
                Content = new MultipartFormDataContent
                {
                    { fileContent, "documento", file.FileName }
                }
            };

            this._logger.LogDebug("ScaricaFileNonFirmatoAsync: invio della richiesta {requestContent}", postRequest.Content);

            var response = await client.SendAsync(postRequest, cancellationToken);

            if (!response.IsSuccessStatusCode)
            {
                var responseContent = await response.Content.ReadAsStringAsync();

                this._logger.LogError(
                    "Errore durante l'estrazione del file ({StatusCode}): {Response}",
                    response.StatusCode,
                    responseContent);

                response.EnsureSuccessStatusCode();
            }

            var content = await response.Content.ReadAsByteArrayAsync();
            var fileName = response.Content.Headers.ContentDisposition?.FileName?.Trim('"');
            var mimeType = response.Content.Headers.ContentType?.MediaType;

            this._logger.LogDebug("ScaricaFileNonFirmatoAsync completato con successo.");

            return new BinaryFile(fileName, mimeType, content);
        }
    }
}