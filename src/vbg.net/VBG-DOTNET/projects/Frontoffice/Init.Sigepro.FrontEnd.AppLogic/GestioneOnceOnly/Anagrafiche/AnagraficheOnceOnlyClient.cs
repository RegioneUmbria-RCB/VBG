using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using VBG.Frontend.AppLogic.WsAnagraficheService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Anagrafiche
{
    public class AnagraficheOnceOnlyClient
    {
        private readonly ILogger<DatiDinamiciOnceOnlyClient> _logger;
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly IConfigurazione<ParametriOnceOnly> _configurazione;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        public AnagraficheOnceOnlyClient(ILoggerFactory loggerFactory, IHttpClientFactory httpClientFactory, IConfigurazione<ParametriOnceOnly> configurazione, IAuthenticationDataResolver authenticationDataResolver)
        {
            this._logger = loggerFactory.CreateLogger<DatiDinamiciOnceOnlyClient>();
            this._httpClientFactory = httpClientFactory;
            this._configurazione = configurazione;
            this._authenticationDataResolver = authenticationDataResolver;
        }

        public async Task SalvaAnagrafichePerUtenteCorrenteAsync(IEnumerable<AggiungiARubricaRequest> listaAnagrafiche)
        {
            if (!this._configurazione.Parametri.AttivaCompilazioneOnceOnly)
            {
                return;
            }

            using (var client = this._httpClientFactory.CreateClient("OnceOnlyAnagrafiche"))
            {
                var url = $"{this._configurazione.Parametri.OnceOnlyBaseUrl}/rest/anagrafiche/{this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale}/rubrica";
                var requestContent = JsonSerializer.Serialize(listaAnagrafiche.ToArray());

                var postRequest = new HttpRequestMessage(
                    HttpMethod.Post,
                    url)
                {
                    Headers =
                {
                    { "Authorization", $"Bearer {this._authenticationDataResolver.DatiAutenticazione?.Token}"}
                },
                    Content = new StringContent(
                        requestContent,
                        Encoding.UTF8,
                        "application/json")
                };

                this._logger.LogDebug("SalvaAnagrafichePerUtenteCorrenteAsync: invio della richiesta {requestContent}", requestContent);

                var httpResponseMessage = await client.SendAsync(postRequest);

                if (!httpResponseMessage.IsSuccessStatusCode)
                {
                    var responseContent = await httpResponseMessage.Content.ReadAsStringAsync();

                    this._logger.LogError("Errore durante il salvataggio dei dati once only per l'utente {@cfUtente}: corpo della risposta ({@statusCode}) {@corpoRisposta}",
                        this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale,
                        httpResponseMessage.StatusCode,
                        responseContent);

                    return;
                }

                this._logger.LogDebug("SalvaAnagrafichePerUtenteCorrenteAsync: Salvataggio riuscito con status 200");
            }
        }

        public async Task<Anagrafe> TrovaAnagraficaByCodiceFiscaleAsync(TipoPersonaEnum tipoPersona, string cfCercato, string software)
        {
            if (!this._configurazione.Parametri.AttivaCompilazioneOnceOnly)
            {
                return null;
            }

            using (var client = this._httpClientFactory.CreateClient("OnceOnlyAnagrafiche"))
            {
                var cfUtenteLoggato = this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale;
                var tipoAnagrafe = tipoPersona == TipoPersonaEnum.Fisica ? "F" : "G";
                var url = $"{this._configurazione.Parametri.OnceOnlyBaseUrl}/rest/anagrafiche/{cfUtenteLoggato}/rubrica/{cfCercato}/{software}?tipoAnagrafe={tipoAnagrafe}";

                var postRequest = new HttpRequestMessage(
                    HttpMethod.Get,
                    url)
                {
                    Headers =
                        {
                            { "Authorization", $"bearer {this._authenticationDataResolver.DatiAutenticazione?.Token}"}
                        }
                };

                this._logger.LogDebug("Inizio della ricerca del cf {@cfCercato} da parte di {@cfUtenteLoggato}",
                    cfCercato,
                    cfUtenteLoggato
                    );

                var httpResponseMessage = await client.SendAsync(postRequest);

                if (!httpResponseMessage.IsSuccessStatusCode)
                {
                    this._logger.LogError("Ricerca del cf {@cfCercato} da parte di {@cfUtenteLoggato} fallita con status {@statusCode}. Contenuto della risposta: {@content}",
                        cfCercato,
                        cfUtenteLoggato,
                        httpResponseMessage.StatusCode,
                        await httpResponseMessage.Content.ReadAsStringAsync());

                    return null;
                }

                var responseStream = await httpResponseMessage.Content.ReadAsStreamAsync();

                var anagrafe = await JsonSerializer.DeserializeAsync<Anagrafe>(responseStream, new JsonSerializerOptions
                {
                    PropertyNamingPolicy = null
                });

                if (anagrafe == null)
                {
                    this._logger.LogDebug("Il codice fiscale {@cfCercato} non è stato trovato per l'utente {@cfUtenteLoggato}", cfCercato, cfUtenteLoggato);
                }
                else
                {
                    this._logger.LogDebug("Codice fiscale {@cfCercato} trovato per l'utente {@cfUtenteLoggato}", cfCercato, cfUtenteLoggato);
                }

                return anagrafe;
            }
        }

        public async Task<ElementoRubricaOnceOnly[]> GetAnagraficheInRubricaAsync(TipoPersonaEnum tipoPersona)
        {
            if (!this._configurazione.Parametri.AttivaCompilazioneOnceOnly)
            {
                return Array.Empty<ElementoRubricaOnceOnly>();
            }

            var cfUtenteLoggato = this._authenticationDataResolver.DatiAutenticazione?.DatiUtente?.Codicefiscale?.ToUpper() ?? "";

            if (string.IsNullOrEmpty(cfUtenteLoggato))
            {
                this._logger.LogError("Impossibile recuperare il cf dell'utente corrente");

                return Array.Empty<ElementoRubricaOnceOnly>();
            }


            var tipoAnagrafe = tipoPersona == TipoPersonaEnum.Fisica ? "F" : "G";
            var url = $"{this._configurazione.Parametri.OnceOnlyBaseUrl}/rest/anagrafiche/{cfUtenteLoggato}/rubrica?tipoAnagrafe={tipoAnagrafe}";

            using (var client = this._httpClientFactory.CreateClient("OnceOnlyAnagrafiche"))
            {
                var request = new HttpRequestMessage(
                    HttpMethod.Get,
                    url)
                {
                    Headers =
                        {
                            { "Authorization", $"bearer {this._authenticationDataResolver.DatiAutenticazione?.Token}"}
                        }
                };

                this._logger.LogDebug("Lettura delle anagrafiche in rubrica da parte di {@cfUtenteLoggato}", cfUtenteLoggato);

                var httpResponseMessage = await client.SendAsync(request);

                if (!httpResponseMessage.IsSuccessStatusCode)
                {
                    this._logger.LogError("Ricerca delle anagrafiche in rubrica da parte di {@cfUtenteLoggato} fallita con status {@statusCode}. Contenuto della risposta: {@content}",
                        cfUtenteLoggato,
                        httpResponseMessage.StatusCode,
                        await httpResponseMessage.Content.ReadAsStringAsync());

                    return Array.Empty<ElementoRubricaOnceOnly>();
                }

                var responseStream = await httpResponseMessage.Content.ReadAsStreamAsync();

                var anagrafiche = await JsonSerializer.DeserializeAsync<ElementoRubricaOnceOnly[]>(responseStream, new JsonSerializerOptions
                {
                    PropertyNamingPolicy = JsonNamingPolicy.CamelCase
                });

                if (anagrafiche == null)
                {
                    this._logger.LogDebug("Non sono state trovate anagrafiche in rubrica per l'utente {@cfUtenteLoggato}", cfUtenteLoggato);
                }

                return anagrafiche ?? Array.Empty<ElementoRubricaOnceOnly>();
            }
        }
    }
}
