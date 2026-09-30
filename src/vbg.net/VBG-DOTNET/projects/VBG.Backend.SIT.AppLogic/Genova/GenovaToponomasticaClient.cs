using log4net;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
using VBG.Backend.SIT.AppLogic.Genova.Authentication;
using VBG.Backend.SIT.AppLogic.Genova.Dto;

namespace VBG.Backend.SIT.AppLogic.Genova
{
    /// <summary>
    /// Client REST per i servizi di Toponomastica del Comune di Genova
    /// (risorse rstGetElenco*/rstValida* esposte dall'endpoint georef_toponomastica).
    /// Richiede sempre risposte in formato json (FORMATO_RISPOSTA != xml).
    /// </summary>
    internal class GenovaToponomasticaClient
    {
        private static readonly ILog _log = LogManager.GetLogger(typeof(GenovaToponomasticaClient));

        private readonly RestClient _client;
        private readonly string _urlBase;

        public GenovaToponomasticaClient(string urlBase, IWso2Authenticator wso2Authenticator)
        {
            if (String.IsNullOrWhiteSpace(urlBase))
            {
                throw new ArgumentException($"'{nameof(urlBase)}' non può essere null o vuoto.", nameof(urlBase));
            }

            if (wso2Authenticator is null)
            {
                throw new ArgumentNullException(nameof(wso2Authenticator));
            }

            this._urlBase = urlBase.TrimEnd('/');

            var options = new RestClientOptions(this._urlBase)
            {
                Authenticator = new RestSharp.Authenticators.JwtAuthenticator(wso2Authenticator.GetToken().AccessToken)
            };

            this._client = new RestClient(options);
            // this._client.AddDefaultHeader("Authorization", $"bearer {Convert.ToBase64String(System.Text.Encoding)
        }

        public IEnumerable<StradaDto> GetElencoStrade()
        {
            var dto = this.Esegui<ElencoStradeRispostaDto>(GenovaConstants.Risorse.GetElencoStrade, []);
            return dto.Risposta.Strade;
        }

        public GenovaResultSet GetElencoCivici(string codiceStrada)
            => this.GetElenco(GenovaConstants.Risorse.GetElencoCivici, GenovaConstants.Campi.NumeroCivico,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada)).SortAsNumber();

        public GenovaResultSet GetElencoEsponenti(string codiceStrada, string numeroCivico)
            => this.GetElenco(GenovaConstants.Risorse.GetElencoEsponenti, GenovaConstants.Campi.Esponente,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada),
                    (GenovaConstants.Parametri.NumeroCivico, numeroCivico));

        public GenovaResultSet GetElencoColori(string codiceStrada, string numeroCivico, string esponente)
            => this.GetElenco(GenovaConstants.Risorse.GetElencoColori, GenovaConstants.Campi.Colore,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada),
                    (GenovaConstants.Parametri.NumeroCivico, numeroCivico),
                    (GenovaConstants.Parametri.Esponente, Normalizza(esponente)));

        public GenovaResultSet GetElencoScale(string codiceStrada, string numeroCivico, string esponente, string colore)
            => this.GetElenco(GenovaConstants.Risorse.GetElencoScale, GenovaConstants.Campi.Scala,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada),
                    (GenovaConstants.Parametri.NumeroCivico, numeroCivico),
                    (GenovaConstants.Parametri.Esponente, Normalizza(esponente)),
                    (GenovaConstants.Parametri.Colore, Normalizza(colore)));

        public GenovaResultSet GetElencoInterni(string codiceStrada, string numeroCivico, string esponente, string colore, string scala)
            => this.GetElenco(GenovaConstants.Risorse.GetElencoInterni, GenovaConstants.Campi.Interno,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada),
                    (GenovaConstants.Parametri.NumeroCivico, numeroCivico),
                    (GenovaConstants.Parametri.Esponente, Normalizza(esponente)),
                    (GenovaConstants.Parametri.Colore, Normalizza(colore)),
                    (GenovaConstants.Parametri.Scala, Normalizza(scala)));

        public GenovaResultSet GetElencoLettInterno(string codiceStrada, string numeroCivico, string esponente, string colore, string scala, string interno)
            => this.GetElenco(GenovaConstants.Risorse.GetElencoLettInterno, GenovaConstants.Campi.LettInterno,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada),
                    (GenovaConstants.Parametri.NumeroCivico, numeroCivico),
                    (GenovaConstants.Parametri.Esponente, Normalizza(esponente)),
                    (GenovaConstants.Parametri.Colore, Normalizza(colore)),
                    (GenovaConstants.Parametri.Scala, Normalizza(scala)),
                    (GenovaConstants.Parametri.Interno, interno));

        public bool ValidaCivico(string codiceStrada, string numeroCivico)
            => this.Valida(GenovaConstants.Risorse.ValidaCivico,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada),
                    (GenovaConstants.Parametri.NumeroCivico, numeroCivico));

        public bool ValidaEsponente(string codiceStrada, string numeroCivico, string esponente)
            => this.Valida(GenovaConstants.Risorse.ValidaEsponente,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada),
                    (GenovaConstants.Parametri.NumeroCivico, numeroCivico),
                    (GenovaConstants.Parametri.Esponente, Normalizza(esponente)));

        public bool ValidaColore(string codiceStrada, string numeroCivico, string esponente, string colore)
            => this.Valida(GenovaConstants.Risorse.ValidaColore,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada),
                    (GenovaConstants.Parametri.NumeroCivico, numeroCivico),
                    (GenovaConstants.Parametri.Esponente, Normalizza(esponente)),
                    (GenovaConstants.Parametri.Colore, Normalizza(colore)));

        public bool ValidaScala(string codiceStrada, string numeroCivico, string esponente, string colore, string scala)
            => this.Valida(GenovaConstants.Risorse.ValidaScala,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada),
                    (GenovaConstants.Parametri.NumeroCivico, numeroCivico),
                    (GenovaConstants.Parametri.Esponente, Normalizza(esponente)),
                    (GenovaConstants.Parametri.Colore, Normalizza(colore)),
                    (GenovaConstants.Parametri.Scala, Normalizza(scala)));

        public bool ValidaInterno(string codiceStrada, string numeroCivico, string esponente, string colore, string scala, string interno)
            => this.Valida(GenovaConstants.Risorse.ValidaInterno,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada),
                    (GenovaConstants.Parametri.NumeroCivico, numeroCivico),
                    (GenovaConstants.Parametri.Esponente, Normalizza(esponente)),
                    (GenovaConstants.Parametri.Colore, Normalizza(colore)),
                    (GenovaConstants.Parametri.Scala, Normalizza(scala)),
                    (GenovaConstants.Parametri.Interno, interno));

        public bool ValidaLettInterno(string codiceStrada, string numeroCivico, string esponente, string colore, string scala, string interno, string lettInterno)
            => this.Valida(GenovaConstants.Risorse.ValidaLettInterno,
                    (GenovaConstants.Parametri.CodiceStrada, codiceStrada),
                    (GenovaConstants.Parametri.NumeroCivico, numeroCivico),
                    (GenovaConstants.Parametri.Esponente, Normalizza(esponente)),
                    (GenovaConstants.Parametri.Colore, Normalizza(colore)),
                    (GenovaConstants.Parametri.Scala, Normalizza(scala)),
                    (GenovaConstants.Parametri.Interno, interno),
                    (GenovaConstants.Parametri.LettInterno, lettInterno));



        private GenovaResultSet GetElenco(string risorsa, string campoRisposta, params (string Nome, string Valore)[] parametri)
        {
            var dto = this.Esegui<ElencoCiviciRispostaDto>(risorsa, parametri);

            var valori = dto.Risposta.Civici
                .Select(c => EstraiValore(c, campoRisposta))
                .Where(v => v is not null)
                .Select(v => v! == GenovaConstants.ValoreNonPresente ? "" : v!)
                .ToList();

            return new GenovaResultSet(valori);
        }

        private bool Valida(string risorsa, params (string Nome, string Valore)[] parametri)
        {
            var dto = this.Esegui<ValidazioneRispostaDto>(risorsa, parametri);

            return String.Equals(dto.Risposta.EsitoValidazione.Trim(), GenovaConstants.Esiti.Valido, StringComparison.OrdinalIgnoreCase);
        }

        private T Esegui<T>(string risorsa, params (string Nome, string Valore)[] parametri)
        {
            var request = new RestRequest(risorsa, Method.Get);
            request.AddQueryParameter(GenovaConstants.Parametri.FormatoRisposta, "json");

            foreach (var (nome, valore) in parametri)
            {
                if (!String.IsNullOrEmpty(valore))
                {
                    request.AddQueryParameter(nome, valore);
                }
            }

            if (_log.IsDebugEnabled)
            {
                _log.Debug($"Invocazione {risorsa} con parametri: {String.Join(", ", parametri.Select(p => $"{p.Nome}={p.Valore}"))}");
            }

            var response = this._client.ExecuteGet(request);

            if (!response.IsSuccessful || response.Content is null)
            {
                var dettaglio = EstraiDettaglioErrore(response.Content);

                if (String.IsNullOrEmpty(dettaglio))
                {
                    dettaglio = response.ErrorMessage;
                }


                if (String.IsNullOrEmpty(dettaglio))
                {
                    dettaglio = response.Content;
                }

                var messaggio = $"Errore nella chiamata al servizio di toponomastica ({risorsa}): {dettaglio} (HTTP {(int)response.StatusCode})";

                _log.Error(messaggio);

                throw new Exception(messaggio);
            }

            return JsonSerializer.Deserialize<T>(response.Content)
                ?? throw new Exception($"Risposta vuota o non deserializzabile dal servizio di toponomastica Genova ({risorsa})");
        }

        private static string? EstraiValore(ElementoCivicoDto elemento, string campo)
        {
            if (elemento.Valori is null)
            {
                return null;
            }

            return elemento.Valori.TryGetValue(campo, out var valore) && valore.ValueKind == JsonValueKind.String
                ? valore.GetString()
                : null;
        }

        private static string EstraiDettaglioErrore(string? content)
        {
            if (String.IsNullOrEmpty(content))
            {
                return null;
            }

            try
            {
                return JsonSerializer.Deserialize<ErroreDto>(content).Errore.Detail;
            }
            catch (JsonException)
            {
                return null;
            }
        }

        /// <summary>
        /// I parametri ESPONENTE/COLORE/SCALA usano "-" per indicare "non presente".
        /// </summary>
        private static string Normalizza(string? valore)
            => String.IsNullOrEmpty(valore) ? GenovaConstants.ValoreNonPresente : valore!;
    }
}
