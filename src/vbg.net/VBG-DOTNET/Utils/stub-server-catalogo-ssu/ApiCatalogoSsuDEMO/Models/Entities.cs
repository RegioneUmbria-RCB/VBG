using System.Text.Json.Serialization;

namespace ApiCatalogoSsuDEMO.Models
{
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

    public enum TipoPersonaEnum
    {
        Fisica,
        Giuridica
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
    }

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
        [JsonConverter(typeof(JsonStringEnumConverter))]
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
        public List<Procedimento>? Items { get; set; }
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

}
