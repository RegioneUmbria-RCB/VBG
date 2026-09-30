using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using System.Diagnostics;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Text.Json;
using System.Text.Json.Serialization;
using VBG.AppLogic.SSU.Configurazione;

namespace VBG.AppLogic.SSU.APICatalogoServizi.Client
{

    // ==================== MODELS ====================

    public class EntityBase
    {
        public int Id { get; set; }
        public string Descrizione { get; set; } = string.Empty;
    }

    public class EventoDellaVita
    {
        public int Id { get; set; }
        public string Descrizione { get; set; } = string.Empty;
    }

    public class Tipologia
    {
        public int Id { get; set; }
        public string Descrizione { get; set; } = string.Empty;
    }

    public class Procedimento
    {
        public int Id { get; set; }
        public string Descrizione { get; set; } = string.Empty;
        public string DescrizioneEstesa { get; set; } = string.Empty;
    }

    public class DettaglioProcedimentoSsuRidotto
    {
        public string Id { get; set; } = "";
        public string Descrizione { get; set; } = string.Empty;
    }

    public class Fattispecie
    {
        public int Id { get; set; }
        public string Descrizione { get; set; } = string.Empty;
        public string DescrizioneEstesa { get; set; } = string.Empty;
        public bool Obbligatoria { get; set; }
    }

    public class GetFattispeciePrimariaByIdResponse
    {
        public Fattispecie Fattispecie { get; set; } = new();
        public List<Fattispecie> Secondarie { get; set; } = new();
    }

    public class ElementoListaFattispecieSecondarie
    {
        public EntityBase Primaria { get; set; } = new();
        public List<Fattispecie> Secondarie { get; set; } = new();
    }

    public class AllegatoProcedimento
    {
        public int Id { get; set; }
        public string Descrizione { get; set; } = string.Empty;
        public string? DescrizioneEstesa { get; set; }
        public bool Obbligatorio { get; set; }
        public bool RichiedeFirma { get; set; }
        public uint? DomensioneMassimaInKb { get; set; }
        public List<string>? EstensioniAmmesse { get; set; }
    }

    public class ElementoListaAllegatiProcedimento
    {
        public EntityBase Procedimento { get; set; } = new();
        public List<AllegatoProcedimento>? Allegati { get; set; }
    }

    public class ModalitaPagamentoOnere
    {
        public int Id { get; set; }
        public string Descrizione { get; set; } = string.Empty;
    }

    public class OnereProcedimento
    {
        public int Id { get; set; }
        public string Descrizione { get; set; } = string.Empty;
        public string? DescrizioneEstesa { get; set; }
        public double? Importo { get; set; }
        public List<ModalitaPagamentoOnere>? ModalitaPagamento { get; set; }
        public bool PermetteneNonDovuto { get; set; }
        public bool PermetteModificaImporto { get; set; }
        public string? CampoDinamicoImporto { get; set; }
    }

    public class ElementoListaOneriProcedimento
    {
        public EntityBase Procedimento { get; set; } = new();
        public List<OnereProcedimento>? Oneri { get; set; }
    }

    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum TipoPersonaEnum
    {
        Fisica,
        Giuridica
    }

    public class RangeOfInt
    {
        public int? Min { get; set; }
        public int? Max { get; set; }
    }


    public enum FlagRuoloSoggettoEnum
    {
        Richiedente,
        Tecnico,
        Azienda,
        AltroSoggetto
    }

    public class TipoSoggetto
    {
        public int Id { get; set; }
        public string Descrizione { get; set; } = string.Empty;
        public string? DescrizioneEstesa { get; set; }
        public bool Obbligatorio { get; set; }
        public int? OccorrenzeMax { get; set; }
        public TipoPersonaEnum TipoPersona { get; set; }
        public FlagRuoloSoggettoEnum Ruolo { get; set; }
        public bool RichiedeAnagraficaCollegata { get; set; }
        public bool RichiedeDatiAlbo { get; set; }
    }

    public static class TipoSoggettoExtensions
    {
        public static Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto.TipoSoggetto? ToTipoSoggettoStandard(this TipoSoggetto? tipoSoggetto)
        {
            if (tipoSoggetto == null)
            {
                return null;
            }

            var tipoSoggettoDto = new Init.SIGePro.Manager.DTO.TipiSoggetto.TipoSoggettoDto()
            {
                Id = tipoSoggetto.Id,
                Descrizione = tipoSoggetto.Descrizione,
                DescrizioneEstesa = tipoSoggetto.DescrizioneEstesa ?? "",
                TipoAnagrafe = tipoSoggetto.TipoPersona == TipoPersonaEnum.Fisica ? "F" : "G",
                Richiesto = tipoSoggetto.Obbligatorio,
                OccorrenzeMax = tipoSoggetto.OccorrenzeMax ?? int.MaxValue,
                FlagTipoDato = tipoSoggetto.Ruolo switch
                {
                    FlagRuoloSoggettoEnum.Richiedente => "R",
                    FlagRuoloSoggettoEnum.Tecnico => "T",
                    FlagRuoloSoggettoEnum.Azienda => "A",
                    _ => ""
                },
                RichiedeAnagraficaCollegata = tipoSoggetto.RichiedeAnagraficaCollegata,
                RichiedeDatiAlbo = tipoSoggetto.RichiedeDatiAlbo
            };

            return new Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto.TipoSoggetto(tipoSoggettoDto);
        }

        public static IEnumerable<Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto.TipoSoggetto> ToTipiSoggettoStandard(this IEnumerable<TipoSoggetto> tipiSoggetto)
        {
            if (tipiSoggetto == null)
            {
                return Enumerable.Empty<Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto.TipoSoggetto>();
            }

            return tipiSoggetto
                .Where(x => x != null)
                .Select(x => x.ToTipoSoggettoStandard()!)
                .ToList();
        }

    }

    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum TipoCampoDinamicoEnum
    {
        Testo,
        NumericoDouble,
        Checkbox,
        Lista,
        Data,
        NumericoIntero,
        Ricerca,
        Upload,
        ListaSIGePro,
        MultiLista,
        CampoNascosto,
        Bottone,
        TestoInSolaLettura,
        RadioButtons,
        Localizzazione
    }

    public class ProprietaCampoDinamico
    {
        public string Chiave { get; set; } = string.Empty;
        public string Valore { get; set; } = string.Empty;
    }

    public class CampoDinamico
    {
        public int Id { get; set; }
        public required string Nome { get; set; }
        public required string Etichetta { get; set; }
        public required TipoCampoDinamicoEnum Tipo { get; set; }
        public List<ProprietaCampoDinamico> Proprieta { get; set; } = new();
        public string Note { get; set; } = "";
        public bool Obbligatorio { get; set; }
    }

    public enum TipoTestoEnum
    {
        Titolo,
        TestoEsteso
    }

    public class CampoTestuale
    {
        public int Id { get; set; }
        public string Testo { get; set; } = "";
        public TipoTestoEnum TipoTesto { get; set; } = TipoTestoEnum.TestoEsteso;
    }

    public class ColonnaScheda
    {
        public int? PosizioneOrizzontale { get; set; }
        public CampoDinamico? Campo { get; set; }
        public CampoTestuale? Testo { get; set; }
    }

    public class RigaScheda
    {
        public int? PosizioneVerticale { get; set; }
        public bool? Multipla { get; set; }
        public bool? InterrompeTabella { get; set; }
        public List<ColonnaScheda>? Colonne { get; set; }
    }

    public class ScriptScheda
    {
        public string? Using { get; set; }
        public string? Inject { get; set; }
        public string? FunzioniCondivise { get; set; }
        public string? Caricamento { get; set; }
        public string? Modifica { get; set; }
        public string? Salvataggio { get; set; }
    }

    public class StrutturaSchedaDinamica
    {
        public ScriptScheda Script { get; set; } = new();
        public List<RigaScheda>? Righe { get; set; }
    }

    public enum TipoFirmaSchedaEnum
    {
        NonRichiedeFirma,
        FirmaInteroModello,
        FirmaABlocchi
    }

    public class SchedaDinamica
    {
        public int? Id { get; set; }
        public string? Titolo { get; set; }
        public TipoFirmaSchedaEnum TipoFirma { get; set; } = TipoFirmaSchedaEnum.NonRichiedeFirma;
        public bool Obbligatoria { get; set; }
        public StrutturaSchedaDinamica Struttura { get; set; } = new();
    }

    public class ElementoListaSchedePerProcedimento
    {
        public EntityBase Procedimento { get; set; } = new();
        public List<SchedaDinamica>? Schede { get; set; }
    }

    public class RegimeAmministrativo
    {
        public int Id { get; set; }
        public string Descrizione { get; set; } = string.Empty;
    }

    public class RiepilogoDomanda
    {
        public string? TemplateXsl { get; set; }
        public bool RichiedeFirma { get; set; }
    }

    public class TemplateRicevutaDomandaXsl
    {
        public string? TemplateXsl { get; set; }
    }

    public class TemplateRicevutaDomandaEmail
    {
        public string? OggettoXsl { get; set; }
        public string? CorpoXsl { get; set; }
    }

    public class TemplateRicevutaDomandaIo
    {
        public string? Oggetto { get; set; }
        public string? Corpo { get; set; }
    }

    public class PagedItemsOfProcedimento
    {
        public List<Procedimento> Items { get; set; } = new();
        public int TotalCount { get; set; }
        public int? Page { get; set; }
        public int? PageSize { get; set; }
        public int? TotalPages { get; set; }
    }

    public class PagedItemsOfTipologia
    {
        public List<Tipologia>? Items { get; set; }
        public int TotalCount { get; set; }
        public int? Page { get; set; }
        public int? PageSize { get; set; }
        public int? TotalPages { get; set; }
    }

    public class ProblemDetails
    {
        public string? Type { get; set; }
        public string? Title { get; set; }
        public int? Status { get; set; }
        public string? Detail { get; set; }
        public string? Instance { get; set; }
    }

    // ==================== CLIENT ====================

    public class SsuCatalogoServiziClient
    {
        //private readonly HttpClient _httpClient;
        private readonly JsonSerializerOptions _jsonOptions;
        private readonly IConfigurazione<ParametriSsu> _configurazioneSsu;
        private readonly ITokenResolver _tokenResolver;

        public SsuCatalogoServiziClient(IConfigurazione<ParametriSsu> configurazioneSsu, ITokenResolver tokenResolver)
        {
            this._jsonOptions = new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true,
                DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull
            };
            this._jsonOptions.Converters.Add(new JsonStringEnumConverter());

            this._configurazioneSsu = configurazioneSsu;
            this._tokenResolver = tokenResolver;
        }

        private async Task<T?> GetAsync<T>(string url, CancellationToken cancellationToken = default)
        {
            var baseAddress = this._configurazioneSsu.Parametri.BaseUrlSsu;

            var request = new HttpRequestMessage(HttpMethod.Get, url);
            request.Headers.Authorization = new AuthenticationHeaderValue("Bearer", this.GetAuthToken());

            using var httpClient = new HttpClient();

            httpClient.BaseAddress = new Uri(baseAddress);

            var response = await httpClient.SendAsync(request, cancellationToken);

            response.EnsureSuccessStatusCode();
#if DEBUG
            Debug.WriteLine(await response.Content.ReadAsStringAsync());
#endif
            var result = await response.Content.ReadFromJsonAsync<T>(this._jsonOptions, cancellationToken);

            return result;

        }

        private string? GetAuthToken() => this._tokenResolver.Token;

        // Eventi Vita
        public async Task<List<EventoDellaVita>> GetEventiDellaVitaAsync(
            string codiceEnte,
            CancellationToken cancellationToken = default)
        {
            return await this.GetAsync<List<EventoDellaVita>>(
                $"api/v1/{codiceEnte}/eventi-della-vita",
                cancellationToken) ?? new List<EventoDellaVita>();

        }

        // Tipologie
        public async Task<PagedItemsOfTipologia> GetTipologieAsync(
            string codiceEnte,
            string? q = null,
            int? page = null,
            CancellationToken cancellationToken = default)
        {
            var queryParams = new List<string>();
            if (!string.IsNullOrEmpty(q)) queryParams.Add($"q={Uri.EscapeDataString(q)}");
            if (page.HasValue) queryParams.Add($"page={page}");

            var queryString = queryParams.Count > 0 ? "?" + string.Join("&", queryParams) : "";

            return await this.GetAsync<PagedItemsOfTipologia>(
                $"api/v1/{codiceEnte}/tipologie{queryString}",
                cancellationToken) ?? new();
        }

        // Procedimenti
        public async Task<PagedItemsOfProcedimento> GetProcedimentiAsync(
            string codiceEnte,
            int? evento = null,
            int? tipologia = null,
            string? q = null,
            int? page = null,
            CancellationToken cancellationToken = default)
        {
            var queryParams = new List<string>();
            if (evento.HasValue) queryParams.Add($"evento={evento}");
            if (tipologia.HasValue) queryParams.Add($"tipologia={tipologia}");
            if (!string.IsNullOrEmpty(q)) queryParams.Add($"q={Uri.EscapeDataString(q)}");
            if (page.HasValue) queryParams.Add($"page={page}");

            var queryString = queryParams.Count > 0 ? "?" + string.Join("&", queryParams) : "";

            return await this.GetAsync<PagedItemsOfProcedimento>(
                $"api/v1/{codiceEnte}/procedimenti{queryString}",
                cancellationToken) ?? new();
        }

        public async Task<DettaglioProcedimentoSsuRidotto?> GetProcedimentoByIdAsync(
            string codiceEnte,
            int idProcedimento,
            CancellationToken cancellationToken = default)
        {
            return await this.GetAsync<DettaglioProcedimentoSsuRidotto>(
                $"api/v1/{codiceEnte}/procedimenti/{idProcedimento}",
                cancellationToken);
        }

        // Fattispecie Primarie
        public async Task<List<Fattispecie>> GetFattispeciePrimarieAsync(
            string codiceEnte,
            int procedimento,
            CancellationToken cancellationToken = default)
        {
            return await this.GetAsync<List<Fattispecie>>(
                $"api/v1/{codiceEnte}/fattispecie/primarie?procedimento={procedimento}",
                cancellationToken) ?? new();
        }

        public async Task<GetFattispeciePrimariaByIdResponse> GetFattispeciePrimariaByIdAsync(
            string codiceEnte,
            int idFattispeciePrimaria,
            CancellationToken cancellationToken = default)
        {
            return await this.GetAsync<GetFattispeciePrimariaByIdResponse>(
                $"api/v1/{codiceEnte}/fattispecie/primarie/{idFattispeciePrimaria}",
                cancellationToken) ?? new();
        }

        // Fattispecie Secondarie
        public async Task<List<ElementoListaFattispecieSecondarie>> GetFattispecieSecondarieAsync(
            string codiceEnte,
            int[] primarie,
            CancellationToken cancellationToken = default)
        {
            var queryString = string.Join("&", Array.ConvertAll(primarie, id => $"primarie={id}"));

            return await this.GetAsync<List<ElementoListaFattispecieSecondarie>>(
                $"api/v1/{codiceEnte}/fattispecie/secondarie?{queryString}",
                cancellationToken) ?? new();
        }

        // Allegati
        public async Task<List<ElementoListaAllegatiProcedimento>> GetAllegatiAsync(
            string codiceEnte,
            int[] procedimenti,
            CancellationToken cancellationToken = default)
        {
            var queryString = string.Join("&", Array.ConvertAll(procedimenti, id => $"procedimenti={id}"));

            return await this.GetAsync<List<ElementoListaAllegatiProcedimento>>(
                $"api/v1/{codiceEnte}/allegati?{queryString}",
                cancellationToken) ?? new();
        }

        // Oneri
        public async Task<List<ElementoListaOneriProcedimento>> GetOneriAsync(
            string codiceEnte,
            int[] procedimenti,
            CancellationToken cancellationToken = default)
        {
            var queryString = string.Join("&", Array.ConvertAll(procedimenti, id => $"procedimenti={id}"));

            return await this.GetAsync<List<ElementoListaOneriProcedimento>>(
                $"api/v1/{codiceEnte}/oneri?{queryString}",
                cancellationToken) ?? new();
        }

        // Tipi Soggetto
        public async Task<List<TipoSoggetto>> GetTipiSoggettoAsync(
            string codiceEnte,
            int[] procedimenti,
            CancellationToken cancellationToken = default)
        {
            var queryString = string.Join("&", Array.ConvertAll(procedimenti, id => $"procedimenti={id}"));

            return await this.GetAsync<List<TipoSoggetto>>(
                $"api/v1/{codiceEnte}/tipi-soggetto?{queryString}",
                cancellationToken) ?? new();
        }

        // Schede Dinamiche
        public async Task<List<ElementoListaSchedePerProcedimento>> GetSchedeDinamicheAsync(
            string codiceEnte,
            int[]? procedimenti = null,
            CancellationToken cancellationToken = default)
        {
            var queryString = procedimenti != null && procedimenti.Length > 0
                ? "?procedimenti=" + string.Join(",", Array.ConvertAll(procedimenti, id => id.ToString()))
                : "";

            return await this.GetAsync<List<ElementoListaSchedePerProcedimento>>(
                $"api/v1/{codiceEnte}/schede-dinamiche{queryString}",
                cancellationToken) ?? new();
        }

        // Configurazione - Regimi Amministrativi
        public async Task<RegimeAmministrativo> GetRegimiAmministrativiAsync(
            string codiceEnte,
            int[] procedimenti,
            CancellationToken cancellationToken = default)
        {
            var queryString = string.Join("&", Array.ConvertAll(procedimenti, id => $"procedimenti={id}"));

            return await this.GetAsync<RegimeAmministrativo>(
                $"api/v1/{codiceEnte}/configurazione/regimi-amministrativi?{queryString}",
                cancellationToken) ??
                throw new InvalidOperationException($"Impossibile recuperare un regime amministrativo per l'ente {codiceEnte} e id procedimenti {String.Join(",", procedimenti)}");
        }

        public async Task<RiepilogoDomanda> GetRiepilogoDomandaAsync(
            string codiceEnte,
            string idRegimeAmministrativo,
            CancellationToken cancellationToken = default)
        {
            return await this.GetAsync<RiepilogoDomanda>(
                $"api/v1/{codiceEnte}/configurazione/regimi-amministrativi/{idRegimeAmministrativo}/riepilogo-domanda",
                cancellationToken) ??
                throw new InvalidOperationException($"Impossibile recuperare il riepilogo domanda per l'ente {codiceEnte} e idRegimeAmministrativo {idRegimeAmministrativo}");
        }

        public async Task<TemplateRicevutaDomandaXsl> GetRicevutaDomandaXslAsync(
            string codiceEnte,
            CancellationToken cancellationToken = default)
        {
            return await this.GetAsync<TemplateRicevutaDomandaXsl>(
                $"api/v1/{codiceEnte}/configurazione/ricevuta-domanda/xsl",
                cancellationToken) ??
                throw new InvalidOperationException($"Impossibile recuperare il template di ricevuta domanda xsl per l'ente {codiceEnte}"); ;
        }

        public async Task<TemplateRicevutaDomandaEmail> GetRicevutaDomandaEmailAsync(
            string codiceEnte,
            CancellationToken cancellationToken = default)
        {
            return await this.GetAsync<TemplateRicevutaDomandaEmail>(
                $"api/v1/{codiceEnte}/configurazione/ricevuta-domanda/email",
                cancellationToken) ??
                throw new InvalidOperationException($"Impossibile recuperare il template di ricevuta domanda Email per l'ente {codiceEnte}"); ;
        }

        public async Task<TemplateRicevutaDomandaIo> GetRicevutaDomandaIoAsync(
            string codiceEnte,
            CancellationToken cancellationToken = default)
        {
            return await this.GetAsync<TemplateRicevutaDomandaIo>(
                $"api/v1/{codiceEnte}/configurazione/ricevuta-domanda/io",
                cancellationToken) ??
                throw new InvalidOperationException($"Impossibile recuperare il template di ricevuta domanda IO per l'ente {codiceEnte}");

        }

        internal async Task<Procedimento> GetProcedimentoByIdFattispecieAsync(string codiceEnte, int idFattispecie)
        {
            return await this.GetAsync<Procedimento>(
                $"api/v1/{codiceEnte}/fattispecie/{idFattispecie}/procedimento",
                CancellationToken.None) ??
                throw new InvalidOperationException($"Impossibile recuperare il procedimento per l'ente {codiceEnte} e fattispecie {idFattispecie}");
        }
    }
}
