using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Configurazione;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.DatiDinamici
{
    public class DatiDinamiciOnceOnlyClient
    {
        public static class Constants
        {
            public const string ServiceEndpointDatiDinamici = "/rest/dato-once-only";
        }

        private readonly ILogger<DatiDinamiciOnceOnlyClient> _logger;
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly IConfigurazione<ParametriOnceOnly> _configurazione;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;
        private readonly string _serviceEndpoint;

        public DatiDinamiciOnceOnlyClient(ILoggerFactory loggerFactory, IHttpClientFactory httpClientFactory, IConfigurazione<ParametriOnceOnly> configurazione, IAuthenticationDataResolver authenticationDataResolver)
        {
            this._logger = loggerFactory.CreateLogger<DatiDinamiciOnceOnlyClient>();
            this._httpClientFactory = httpClientFactory;
            this._configurazione = configurazione;
            this._authenticationDataResolver = authenticationDataResolver;
            this._serviceEndpoint = $"{this._configurazione.Parametri.OnceOnlyBaseUrl}{Constants.ServiceEndpointDatiDinamici}";
        }

        public class PostDatoOnceOnlyRequest
        {
            public class PostDatoOnceOnlyFonti
            {
                public string FonteEsterna { get; set; }
                public string FonteInterna { get; set; }
            }

            public string CFUtente { get; set; }
            public List<PostDatoOnceOnlyFonti> Fonti { get; set; } = new List<PostDatoOnceOnlyFonti>();
        }

        public class PostDatoOnceOnlyResponse
        {
            public class PostDatoOnceOnlyFonteResponse
            {
                [JsonPropertyName("fonteInterna")]
                public string FonteInterna { get; set; } = string.Empty;
                [JsonPropertyName("valori")]
                public List<PostDatoOnceOnlyValoriResponse> Valori { get; set; } = new List<PostDatoOnceOnlyValoriResponse>();
            }

            public class PostDatoOnceOnlyValoriResponse
            {
                [JsonPropertyName("indice")]
                public int Indice { get; set; } = 0;
                [JsonPropertyName("indiceMolteplicita")]
                public int IndiceMolteplicita { get; set; } = 0;
                [JsonPropertyName("valore")]
                public string Valore { get; set; } = string.Empty;
                [JsonPropertyName("valoreDecodificato")]
                public string ValoreDecodificato { get; set; } = string.Empty;
            }

            [JsonPropertyName("campi")]
            public PostDatoOnceOnlyFonteResponse[] Campi { get; set; } = Array.Empty<PostDatoOnceOnlyFonteResponse>();
            [JsonPropertyName("statusCode")]
            public int StatusCode { get; set; }
            [JsonPropertyName("errorMessage")]
            public string ErrorMessage { get; set; } = string.Empty;
        }



        public async Task<PostDatoOnceOnlyResponse> LeggiDatiDinamiciOnceOnlyAsync(PostDatoOnceOnlyRequest request)
        {
            if (!this._configurazione.Parametri.AttivaCompilazioneOnceOnly)
            {
                throw new Exception("Il servizio di precompilazione OnceOnly non è attivo");
            }

            var url = this._serviceEndpoint;

            using (var client = this._httpClientFactory.CreateClient("OnceOnly"))
            {
                var requestContent = JsonSerializer.Serialize(request);

                var postRequest = new HttpRequestMessage(
                    HttpMethod.Post,
                    url)
                {
                    Headers =
                {
                    { "Token", this._authenticationDataResolver.DatiAutenticazione?.Token}
                },
                    Content = new StringContent(
                        requestContent,
                        Encoding.UTF8,
                        "application/json")
                };

                this._logger.LogDebug("LeggiDatiDinamiciOnceOnlyAsync: invio della richiesta {requestContent}", requestContent);

                var httpResponseMessage = await client.SendAsync(postRequest);

                if (httpResponseMessage.IsSuccessStatusCode)
                {
                    using (var contentStream = await httpResponseMessage.Content.ReadAsStreamAsync())
                    {

                        var response = await JsonSerializer.DeserializeAsync<PostDatoOnceOnlyResponse>(contentStream);

                        return new PostDatoOnceOnlyResponse
                        {
                            ErrorMessage = response.ErrorMessage,
                            StatusCode = response.StatusCode,
                            Campi = response.Campi.Where(x => (x.Valori?.Any() ?? false)).ToArray()
                        };
                    }
                }

                this._logger.LogDebug("LeggiDatiDinamiciOnceOnlyAsync: il servizio non ha restituito un codice di successo {statusCode}", httpResponseMessage.StatusCode);

                // il servizio ha restituito un codice di errore
                throw new Exception("Errore restituito dal servizio OnceOnly, consultare i logs per i dettagli");
            }
        }


        public class PutDatoOnceOnlyRequest
        {
            [JsonPropertyName("cfUtente")]
            public string CFUtente { get; set; }

            [JsonPropertyName("dati")]
            public List<PutDatoOnceOnlyDatiRequest> Dati { get; set; } = new List<PutDatoOnceOnlyDatiRequest>();
        }

        public class PutDatoOnceOnlyDatiRequest
        {
            [JsonPropertyName("fonteInterna")]
            public string FonteInterna { get; set; } = string.Empty;
            [JsonPropertyName("isUpload")]
            public bool IsUpload { get; set; } = false;
            [JsonPropertyName("valori")]
            public List<PutDatoOnceOnlyValoriRequest> Valori { get; set; } = new List<PutDatoOnceOnlyValoriRequest>();
        }

        public class PutDatoOnceOnlyValoriRequest
        {
            [JsonPropertyName("indice")]
            public int Indice { get; set; } = 0;
            [JsonPropertyName("indiceMolteplicita")]
            public int IndiceMolteplicita { get; set; } = 0;
            [JsonPropertyName("valore")]
            public string Valore { get; set; } = string.Empty;
            [JsonPropertyName("valoreDecodificato")]
            public string ValoreDecodificato { get; set; } = string.Empty;
        }

        public class PutDatoOnceOnlyResponse
        {
            public int StatusCode { get; set; }
            public string ErrorMessage { get; set; } = string.Empty;
        }

        public async Task SalvaDatiDinamiciOnceOnlyAsync(PutDatoOnceOnlyRequest request)
        {
            if (!this._configurazione.Parametri.AttivaCompilazioneOnceOnly)
            {
                return;
            }

            var url = this._serviceEndpoint;

            using (var client = this._httpClientFactory.CreateClient("OnceOnly"))
            {

                var requestContent = JsonSerializer.Serialize(request);

                var postRequest = new HttpRequestMessage(
                    HttpMethod.Put,
                    url)
                {
                    Headers =
                {
                    { "Token", this._authenticationDataResolver.DatiAutenticazione?.Token}
                },
                    Content = new StringContent(
                        requestContent,
                        Encoding.UTF8,
                        "application/json")
                };


                this._logger.LogDebug("SalvaDatiDinamiciOnceOnlyAsync: invio della richiesta {requestContent}", requestContent);

                var httpResponseMessage = await client.SendAsync(postRequest);

                if (!httpResponseMessage.IsSuccessStatusCode)
                {
                    var responseString = await httpResponseMessage.Content.ReadAsStringAsync();

                    this._logger.LogError("SalvaDatiDinamiciOnceOnlyAsync: il servizio ha restituito un codice d'errore: {error}", responseString);

                    throw new Exception("Errore restituito dal servizio OnceOnly, consultare i logs per i dettagli");
                }
            }
        }
    }
}
