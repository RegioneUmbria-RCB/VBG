using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ConnectedServices.SIC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using VBG.Shared.Infrastructure.Caching;
using log4net;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC
{
    public static class SitCartograficoServiceExtensions
    {
        public static string RecuperaValoreProprieta(this ParametriResponse response, string nomeProprieta)
        {
            return response?.Parametri?.FirstOrDefault()?.AdditionalInfo?.FirstOrDefault(x => x.Chiave.Equals(nomeProprieta, StringComparison.InvariantCultureIgnoreCase))?.Valore ?? "";
        }
    }


    public class SitCartograficoService : ISitCartograficoService
    {
        private static class Constants
        {
            public const string SICHttpClientName = "sicHttpClient";
            public const string IdentificativoChiamante = "FRONTEND";
            public const string NomePorpertyKm = "Km";
            public const string NomePorpertyStrada = "Strada";
        }

        private readonly IHttpClientFactory _httpClientFactory;
        private readonly IComuniService _comuniService;
        private readonly LocalizzazioniService _localizzazioniService;
        private readonly IConfigurazione<ParametriSigeproSecurity> _configurazioneSecurity;
        private readonly ILog _logger = LogManager.GetLogger(typeof(SitCartograficoService));
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IApplicationCache _applicationCache;

        public SitCartograficoService(IHttpClientFactory httpClientFactory, IComuniService ComuniService,
            LocalizzazioniService localizzazioniService,
            ITokenApplicazioneService tokenApplicazioneService, IConfigurazione<ParametriSigeproSecurity> configurazioneSecurity,
            IAliasSoftwareResolver aliasSoftwareResolver, IApplicationCache applicationCache)
        {
            this._localizzazioniService = localizzazioniService;
            this._httpClientFactory = httpClientFactory;
            this._comuniService = ComuniService;
            this._localizzazioniService = localizzazioniService;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._configurazioneSecurity = configurazioneSecurity;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._applicationCache = applicationCache;
        }

        private HttpClient InizializzaHttpClient()
        {
            var httpClient = this._httpClientFactory.CreateClient(Constants.SICHttpClientName);
            var token = this._tokenApplicazioneService.GetToken();
            httpClient.DefaultRequestHeaders.Add("Authorization", $"Bearer {token}");
            httpClient.DefaultRequestHeaders.ExpectContinue = false;
            return httpClient;
        }

        public async Task<GeneraURLMappaResponse> GeneraURLMappaListaPraticheAsync(GeneraURLMappaListaPraticheRequest request)
        {
            var url = this._configurazioneSecurity.Parametri.UrlServizioCartografico;

            if (String.IsNullOrEmpty(url))
            {
                return GeneraURLMappaResponse.FromKO("Non è presente la url del servizio cartografico da utilizzare");
            }

            using (var httpClient = this.InizializzaHttpClient())
            {
                var client = new SICRestClient(httpClient)
                {
                    BaseUrl = url
                };

                var urlMappaRequest = new InnescoIstanzeUUidRequest
                {
                    Callback = new Callback
                    {
                        CallbackURL = request.CallbackUrl,
                        CancelURL = request.CancelUrl
                    },
                    Chiamante = Constants.IdentificativoChiamante,
                    UuidIstanze = request.UuidIstanze,
                    Utilizzo = InnescoIstanzeUUidRequestUtilizzo.ELENCO
                };

                var json = JsonConvert.SerializeObject(urlMappaRequest);

                try
                {
                    this._logger.Debug($"Chiamata alla url {url} per la richiesta della mappa, request: {json}");

                    var response = await client.InnescoUuidAsync(this._aliasSoftwareResolver.Software, urlMappaRequest);

                    this._logger.Debug($"Risposta dalla url {url} per la richiesta della mappa, response: {JsonConvert.SerializeObject(response)}");

                    return GeneraURLMappaResponse.FromOK(response);
                }
                catch (ApiException ex)
                {
                    var messaggio = ex.Response;
                    if (ex.Response.Contains("E404"))
                    {
                        messaggio = "L'indirizzo indicato o il km specificato non sono presenti nel catasto dell'ente";
                    }
                    return GeneraURLMappaResponse.FromKO(messaggio);
                }
                catch (Exception ex)
                {
                    return GeneraURLMappaResponse.FromKO(ex.Message);
                }
            }
        }

        public async Task<GeneraURLMappaResponse> GeneraURLMappaAsync(GeneraURLMappaLocalizzazioneRequest request)
        {
            var comune = this._comuniService.GetByCodiceComune(request.CodiceComune);
            var localizzazione = this._localizzazioniService.GetById(request.CodiceStradario);
            var url = this._configurazioneSecurity.Parametri.UrlServizioCartografico;

            if (String.IsNullOrEmpty(url))
            {
                return GeneraURLMappaResponse.FromKO("Non è presente la url del servizio cartografico da utilizzare");
            }

            using (var httpClient = this.InizializzaHttpClient())
            {
                var client = new SICRestClient(httpClient)
                {
                    BaseUrl = url
                };

                var innescoRequest = new InnescoIstanzeRequest
                {
                    Callback = new Callback
                    {
                        CallbackURL = request.CallbackUrl,
                        CancelURL = request.CancelUrl
                    },
                    Chiamante = Constants.IdentificativoChiamante,
                    Comune = new Comune
                    {
                        CodiceIstat = comune.CodiceISTAT,
                        Nome = comune.Comune
                    },
                    Localizzazioni = new List<Localizzazione>()
                    {
                        new Localizzazione
                        {
                            Civico = request.Civico,
                            CodViario = localizzazione.CodViario,
                            Descrizione = localizzazione.NomeVia,
                            Identificativo = request.RiferimentoPratica,
                            Km = request.Km?.Replace(',','+'),
                            Latitudine = request.Latitudine?.Replace(',', '.'),
                            Longitudine = request.Longitudine?.Replace(',', '.'),
                            Uuid = request.UuidLocalizzazione
                        }
                    },
                    Utilizzo = InnescoIstanzeRequestUtilizzo.MODIFICA
                };

                var json = JsonConvert.SerializeObject(innescoRequest);

                try
                {
                    this._logger.Info($"Chiamata alla url {url} per la richiesta della mappa, request: {json}");

                    var response = await client.Innesco3Async(this._aliasSoftwareResolver.Software, innescoRequest);

                    this._logger.Info($"Risposta dalla url {url} per la richiesta della mappa, response: {JsonConvert.SerializeObject(response)}");

                    return GeneraURLMappaResponse.FromOK(response);
                }
                catch (ApiException ex)
                {
                    return GeneraURLMappaResponse.FromKO(ex.Response);
                }
                catch (Exception ex)
                {
                    return GeneraURLMappaResponse.FromKO(ex.Message);
                }
            }
        }

        public async Task<InformazioniAggiuntive> RecuperaInformazioniAggiuntiveAsync(RecuperaInformazioniAggiuntiveRequest request)
        {
            var url = this._configurazioneSecurity.Parametri.UrlServizioCartografico;

            using (var httpClient = this.InizializzaHttpClient())
            {
                try
                {
                    var client = new SICRestClient(httpClient)
                    {
                        BaseUrl = url
                    };
                    this._logger.Debug($"Chiamata alla url {url} per la richiesta dei parametri");

                    var response = await client.ParametriAsync(this._aliasSoftwareResolver.Software, request.UuidLocalizzazione);

                    this._logger.Debug($"Risposta dalla url {url}:{JsonConvert.SerializeObject(response)}");

                    if (Esito1.KO.Equals(response.Esito?.Esito1))
                    {
                        return InformazioniAggiuntive.FromKO(response.Esito.Exceptions);
                    }

                    var codViario = response.RecuperaValoreProprieta(Constants.NomePorpertyStrada);

                    if (String.IsNullOrEmpty(codViario))
                    {
                        return InformazioniAggiuntive.FromKO(new List<String> { $"Il sistema di integrazione non ha restituito il codice viario della strada." });
                    }

                    var localizzazione = this._localizzazioniService.GetIndirizzoByCodViario(codViario);

                    if (localizzazione == null)
                    {
                        return InformazioniAggiuntive.FromKO(new List<String> { $"La localizzazione con codice {codViario} non è censita." });
                    }

                    var parametri = response.Parametri.First();

                    var retVal = new InformazioniAggiuntive
                    {
                        GeoJSON = parametri.Geojson,
                        AdditionalInfo = parametri.AdditionalInfo,
                        Latitudine = parametri.Geojson?.Geometry?.Coordinates?.FirstOrDefault()?[0].ToString().Replace(",", ".") ?? "",
                        Longitudine = parametri.Geojson?.Geometry?.Coordinates?.FirstOrDefault()?[1].ToString().Replace(",", ".") ?? "",
                        CodiceStradario = localizzazione.CodiceStradario,
                        Stradario = localizzazione.NomeVia,
                        Km = response.RecuperaValoreProprieta(Constants.NomePorpertyKm),
                        Uuid = request.UuidLocalizzazione,
                        Exceptions = new List<string>()
                    };

                    return retVal;
                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore in risposta dalla url {url}: {ex.Message}");
                    throw;
                }
            }
        }

        private async Task<Utilizzo> GetUtilizzoDaSICAsync()
        {
            var url = this._configurazioneSecurity.Parametri.UrlServizioCartografico;
            if (String.IsNullOrEmpty(url))
            {
                return new Utilizzo();
            }
            using (var httpClient = this.InizializzaHttpClient())
            {
                try
                {
                    var client = new SICRestClient(httpClient)
                    {
                        BaseUrl = url
                    };

                    this._logger.Debug($"Chiamata alla url {url} per la richiesta info connettore cartografico");
                    var response = await client.InfoAsync(this._aliasSoftwareResolver.Software);
                    this._logger.Debug($"Risposta dalla url {url} per la richiesta info connettore cartografico: {JsonConvert.SerializeObject(response)}");

                    return response.Utilizzo;

                }
                catch (Exception ex)
                {
                    this._logger.Error($"Errore in risposta dalla url {url}: {ex.Message}");
                    throw;
                }
            }
        }

        public async Task<SICFeatures> GetFeaturesAsync()
        {
            try
            {
                var key = $"{this._aliasSoftwareResolver.AliasComune}.{this._aliasSoftwareResolver.Software}.Cartografico.Utilizzo";

                if (!this._applicationCache.TryGetValue(key, out Utilizzo utilizzo))
                {
                    utilizzo = await this.GetUtilizzoDaSICAsync();

                    this._applicationCache.Set(key, utilizzo);
                }

                return new SICFeatures(utilizzo);
            }
            catch (Exception ex)
            {
                this._logger.Error($"Errore in GetFeaturesAsync: {ex.Message}");
                return new SICFeatures(new Utilizzo
                {
                    Istanze = new Metodi
                    {
                        Elenco = false,
                        Inserimento = false
                    },
                    Attivita = new Metodi
                    {
                        Elenco = false,
                        Inserimento = false
                    },
                    Autorizzazioni = new Metodi
                    {
                        Elenco = false,
                        Inserimento = false
                    }

                });
            }
        }
    }
}
