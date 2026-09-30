namespace Init.SIGePro.Protocollo.GeProt.Protocollazione
{
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class AggiornamentoConferma
    {
        /// <remarks/>
        public Identificatore Identificatore;

        /// <remarks/>
        public MessaggioRicevuto MessaggioRicevuto;

        /// <remarks/>
        public Riferimenti Riferimenti;

        /// <remarks/>
        public Descrizione Descrizione;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute("2005-03-29")]
        public string versione = "2005-03-29";
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Identificatore
    {

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione;

        /// <remarks/>
        public DescrizioneAmministrazione DescrizioneAmministrazione;

        /// <remarks/>
        public CodiceAOO CodiceAOO;

        /// <remarks/>
        public DescrizioneAOO DescrizioneAOO;

        /// <remarks/>
        public NumeroRegistrazione NumeroRegistrazione;

        /// <remarks/>
        public DataRegistrazione DataRegistrazione;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class CodiceAmministrazione
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Fax
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string note;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Telefono
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string note;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class IndirizzoTelematico
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public IndirizzoTelematicoTipo tipo = IndirizzoTelematicoTipo.smtp;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string note;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    public enum IndirizzoTelematicoTipo
    {

        /// <remarks/>
        smtp,

        /// <remarks/>
        uri,

        /// <remarks/>
        NMTOKEN,
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Nazione
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Provincia
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Comune
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string codiceISTAT;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class CAP
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Civico
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Toponimo
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string dug;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class IndirizzoPostale
    {
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Nazione", typeof(Nazione))]
        [System.Xml.Serialization.XmlElementAttribute("Denominazione", typeof(Denominazione))]
        [System.Xml.Serialization.XmlElementAttribute("Toponimo", typeof(Toponimo))]
        [System.Xml.Serialization.XmlElementAttribute("Provincia", typeof(Provincia))]
        [System.Xml.Serialization.XmlElementAttribute("Comune", typeof(Comune))]
        [System.Xml.Serialization.XmlElementAttribute("CAP", typeof(CAP))]
        [System.Xml.Serialization.XmlElementAttribute("Civico", typeof(Civico))]
        public object[] Items;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Denominazione
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Ruolo
    {

        /// <remarks/>
        public Denominazione Denominazione;

        /// <remarks/>
        public Identificativo Identificativo;

        /// <remarks/>
        public Persona Persona;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Identificativo
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Persona
    {
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Nome", typeof(Nome))]
        [System.Xml.Serialization.XmlElementAttribute("Cognome", typeof(Cognome))]
        [System.Xml.Serialization.XmlElementAttribute("Denominazione", typeof(Denominazione))]
        [System.Xml.Serialization.XmlElementAttribute("CodiceFiscale", typeof(CodiceFiscale))]
        [System.Xml.Serialization.XmlElementAttribute("Titolo", typeof(Titolo))]
        public object[] Items;

        /// <remarks/>
        public Identificativo Identificativo;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "IDREF")]
        public string rife;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "ID")]
        public string id;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Nome
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Cognome
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class CodiceFiscale
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Titolo
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class UnitaOrganizzativa
    {

        /// <remarks/>
        public Denominazione Denominazione;

        /// <remarks/>
        public Identificativo Identificativo;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("IndirizzoPostale", typeof(IndirizzoPostale))]
        [System.Xml.Serialization.XmlElementAttribute("UnitaOrganizzativa", typeof(UnitaOrganizzativa))]
        [System.Xml.Serialization.XmlElementAttribute("Fax", typeof(Fax))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("Telefono", typeof(Telefono))]
        [System.Xml.Serialization.XmlElementAttribute("IndirizzoTelematico", typeof(IndirizzoTelematico))]
        [System.Xml.Serialization.XmlElementAttribute("Ruolo", typeof(Ruolo))]
        public object[] Items;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public UnitaOrganizzativaTipo tipo = UnitaOrganizzativaTipo.permanente;
    }

    /// <remarks/>
    public enum UnitaOrganizzativaTipo
    {

        /// <remarks/>
        permanente,

        /// <remarks/>
        temporanea,
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Fascicolo
    {

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione;

        /// <remarks/>
        public CodiceAOO CodiceAOO;

        /// <remarks/>
        public UnitaOrganizzativa UnitaOrganizzativa;

        /// <remarks/>
        public Oggetto Oggetto;

        /// <remarks/>
        public Identificativo Identificativo;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Classifica")]
        public Classifica[] Classifica;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Procedimento")]
        public Procedimento[] Procedimento;

        /// <remarks/>
        public Note Note;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Documento", typeof(Documento))]
        [System.Xml.Serialization.XmlElementAttribute("Fascicolo", typeof(Fascicolo))]
        public object Item;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "IDREF")]
        public string rife;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string dataCreazione;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string autore;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "ID")]
        public string id;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class CodiceAOO
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Oggetto
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Classifica
    {

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione;

        /// <remarks/>
        public CodiceAOO CodiceAOO;

        /// <remarks/>
        public Denominazione Denominazione;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Livello")]
        public Livello[] Livello;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Livello
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string nome;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Procedimento
    {

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione;

        /// <remarks/>
        public CodiceAOO CodiceAOO;

        /// <remarks/>
        public Identificativo Identificativo;

        /// <remarks/>
        public TipoProcedimento TipoProcedimento;

        /// <remarks/>
        public Oggetto Oggetto;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Classifica")]
        public Classifica[] Classifica;

        /// <remarks/>
        public Responsabile Responsabile;

        /// <remarks/>
        public DataAvvio DataAvvio;

        /// <remarks/>
        public DataTermine DataTermine;

        /// <remarks/>
        public Note Note;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "IDREF")]
        public string rife;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "ID")]
        public string id;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class TipoProcedimento
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Responsabile
    {

        /// <remarks/>
        public Persona Persona;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class DataAvvio
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class DataTermine
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Note
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Documento
    {

        /// <remarks/>
        public CollocazioneTelematica CollocazioneTelematica;

        /// <remarks/>
        public Impronta Impronta;

        /// <remarks/>
        public TitoloDocumento TitoloDocumento;

        /// <remarks/>
        public PrimaRegistrazione PrimaRegistrazione;

        /// <remarks/>
        public TipoDocumento TipoDocumento;

        /// <remarks/>
        public Oggetto Oggetto;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Classifica")]
        public Classifica[] Classifica;

        /// <remarks/>
        public NumeroPagine NumeroPagine;

        /// <remarks/>
        public Note Note;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "IDREF")]
        public string rife;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(DocumentoTipoRiferimento.MIME)]
        public DocumentoTipoRiferimento tipoRiferimento = DocumentoTipoRiferimento.MIME;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string nome;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string tipoMIME;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "ID")]
        public string id;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class CollocazioneTelematica
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Impronta
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute("base64")]
        public string codifica = "base64";

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute("SHA-1")]
        public string algoritmo = "SHA-1";

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class TitoloDocumento
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class PrimaRegistrazione
    {

        /// <remarks/>
        public Identificatore Identificatore;

        /// <remarks/>
        public AutoreProtocollazione AutoreProtocollazione;

        /// <remarks/>
        public DataDocumento DataDocumento;

        /// <remarks/>
        public DataArrivo DataArrivo;

        /// <remarks/>
        public OraArrivo OraArrivo;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class AutoreProtocollazione
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class DataDocumento
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class DataArrivo
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class OraArrivo
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class TipoDocumento
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class NumeroPagine
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    public enum DocumentoTipoRiferimento
    {

        /// <remarks/>
        MIME,

        /// <remarks/>
        telematico,

        /// <remarks/>
        cartaceo,
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Allegati
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Documento", typeof(Documento))]
        [System.Xml.Serialization.XmlElementAttribute("Fascicolo", typeof(Fascicolo))]
        public object[] Items;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class TestoDelMessaggio
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "NMTOKEN")]
        [System.ComponentModel.DefaultValueAttribute("MIME")]
        public string tipoRiferimento = "MIME";

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string tipoMIME;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string id;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Descrizione
    {
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Documento", typeof(Documento))]
        [System.Xml.Serialization.XmlElementAttribute("TestoDelMessaggio", typeof(TestoDelMessaggio))]
        public object Item;

        /// <remarks/>
        public Allegati Allegati;

        /// <remarks/>
        public Note Note;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class TipoContestoProcedurale
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class ContestoProcedurale
    {

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione;

        /// <remarks/>
        public CodiceAOO CodiceAOO;

        /// <remarks/>
        public Identificativo Identificativo;

        /// <remarks/>
        public TipoContestoProcedurale TipoContestoProcedurale;

        /// <remarks/>
        public Oggetto Oggetto;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Classifica")]
        public Classifica[] Classifica;

        /// <remarks/>
        public DataAvvio DataAvvio;

        /// <remarks/>
        public Note Note;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "IDREF")]
        public string rife;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "ID")]
        public string id;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Messaggio
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("DescrizioneMessaggio", typeof(DescrizioneMessaggio))]
        [System.Xml.Serialization.XmlElementAttribute("Identificatore", typeof(Identificatore))]
        public object Item;

        /// <remarks/>
        public PrimaRegistrazione PrimaRegistrazione;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class DescrizioneMessaggio
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Riferimenti
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("ContestoProcedurale", typeof(ContestoProcedurale))]
        [System.Xml.Serialization.XmlElementAttribute("Procedimento", typeof(Procedimento))]
        [System.Xml.Serialization.XmlElementAttribute("Messaggio", typeof(Messaggio))]
        public object Item;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class MessaggioRicevuto
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("DescrizioneMessaggio", typeof(DescrizioneMessaggio))]
        [System.Xml.Serialization.XmlElementAttribute("Identificatore", typeof(Identificatore))]
        [System.Xml.Serialization.XmlElementAttribute("PrimaRegistrazione", typeof(PrimaRegistrazione))]
        public object Item;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class DataRegistrazione
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class NumeroRegistrazione
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class DescrizioneAOO
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class DescrizioneAmministrazione
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Amministrazione
    {

        /// <remarks/>
        public Denominazione Denominazione;

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("IndirizzoPostale", typeof(IndirizzoPostale))]
        [System.Xml.Serialization.XmlElementAttribute("UnitaOrganizzativa", typeof(UnitaOrganizzativa))]
        [System.Xml.Serialization.XmlElementAttribute("Fax", typeof(Fax))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("Telefono", typeof(Telefono))]
        [System.Xml.Serialization.XmlElementAttribute("IndirizzoTelematico", typeof(IndirizzoTelematico))]
        [System.Xml.Serialization.XmlElementAttribute("Ruolo", typeof(Ruolo))]
        public object[] Items;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class AnnullamentoProtocollazione
    {

        /// <remarks/>
        public Identificatore Identificatore;

        /// <remarks/>
        public Motivo Motivo;

        /// <remarks/>
        public Provvedimento Provvedimento;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute("2005-03-29")]
        public string versione = "2005-03-29";
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Motivo
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Provvedimento
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class AOO
    {

        /// <remarks/>
        public Denominazione Denominazione;

        /// <remarks/>
        public CodiceAOO CodiceAOO;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Assegnazione
    {

        /// <remarks/>
        public UnitaOrganizzativa UnitaOrganizzativa;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Assegnazione")]
        public Assegnazione[] Assegnazione1;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string stato = "";
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Assegnazioni
    {

        /// <remarks/>
        public Assegnazione Assegnazione;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class ConfermaRicezione
    {

        /// <remarks/>
        public Identificatore Identificatore;

        /// <remarks/>
        public MessaggioRicevuto MessaggioRicevuto;

        /// <remarks/>
        public Riferimenti Riferimenti;

        /// <remarks/>
        public Descrizione Descrizione;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute("2005-03-29")]
        public string versione = "2005-03-29";
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class CorpoEmail
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Destinatario
    {
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Amministrazione", typeof(Amministrazione))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("AOO", typeof(AOO))]
        public object[] Items;

        /// <remarks/>
        public IndirizzoTelematico IndirizzoTelematico;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Telefono")]
        public Telefono[] Telefono;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Fax")]
        public Fax[] Fax;

        /// <remarks/>
        public IndirizzoPostale IndirizzoPostale;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Destinazione
    {

        /// <remarks/>
        public IndirizzoTelematico IndirizzoTelematico;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Destinatario")]
        public Destinatario[] Destinatario;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(DestinazioneConfermaRicezione.no)]
        public DestinazioneConfermaRicezione confermaRicezione = DestinazioneConfermaRicezione.no;
    }

    /// <remarks/>
    public enum DestinazioneConfermaRicezione
    {

        /// <remarks/>
        si,

        /// <remarks/>
        no,
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class InterventoOperatore
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Intestazione
    {

        /// <remarks/>
        public Identificatore Identificatore;

        /// <remarks/>
        public PrimaRegistrazione PrimaRegistrazione;

        /// <remarks/>
        public OraRegistrazione OraRegistrazione;

        /// <remarks/>
        public Origine Origine;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Destinazione")]
        public Destinazione[] Destinazione;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("PerConoscenza")]
        public PerConoscenza[] PerConoscenza;

        /// <remarks/>
        public Risposta Risposta;

        /// <remarks/>
        public Riservato Riservato;

        /// <remarks/>
        public InterventoOperatore InterventoOperatore;

        /// <remarks/>
        public string RiferimentoDocumentiCartacei;

        /// <remarks/>
        public string RiferimentiTelematici;

        /// <remarks/>
        public Registro Registro;

        /// <remarks/>
        public Oggetto Oggetto;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Classifica")]
        public Classifica[] Classifica;

        /// <remarks/>
        public RiferimentoPadre RiferimentoPadre;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("RiferimentoFiglio")]
        public RiferimentoFiglio[] RiferimentoFiglio;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Assegnazioni")]
        public Assegnazioni[] Assegnazioni;

        /// <remarks/>
        public ListaFascicoli ListaFascicoli;

        /// <remarks/>
        public Note Note;

        /// <remarks/>
        public NoteProtocollazione NoteProtocollazione;

        /// <remarks/>
        public InvioEmail InvioEmail;

        /// <remarks/>
        public CorpoEmail CorpoEmail;

        /// <remarks/>
        public ModalitaTrasmissione ModalitaTrasmissione;

        /// <remarks/>
        [System.Xml.Serialization.XmlArrayItemAttribute(IsNullable = false)]
        public Parametro[] Parametri;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class OraRegistrazione
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(OraRegistrazioneTempo.locale)]
        public OraRegistrazioneTempo tempo = OraRegistrazioneTempo.locale;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    public enum OraRegistrazioneTempo
    {

        /// <remarks/>
        locale,

        /// <remarks/>
        rupa,

        /// <remarks/>
        NMTOKEN,
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Origine
    {
        /// <remarks/>
        public IndirizzoTelematico IndirizzoTelematico;

        /// <remarks/>
        public Mittente Mittente;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Mittente
    {
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Amministrazione", typeof(Amministrazione))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("Denominazione", typeof(Denominazione))]
        [System.Xml.Serialization.XmlElementAttribute("AOO", typeof(AOO))]
        public object[] Items;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class PerConoscenza
    {

        /// <remarks/>
        public IndirizzoTelematico IndirizzoTelematico;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Destinatario")]
        public Destinatario[] Destinatario;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(PerConoscenzaConfermaRicezione.no)]
        public PerConoscenzaConfermaRicezione confermaRicezione = PerConoscenzaConfermaRicezione.no;
    }

    /// <remarks/>
    public enum PerConoscenzaConfermaRicezione
    {

        /// <remarks/>
        si,

        /// <remarks/>
        no,
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Risposta
    {

        /// <remarks/>
        public IndirizzoTelematico IndirizzoTelematico;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Riservato
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Registro
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public RegistroTipo tipo = RegistroTipo.Arrivo;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    public enum RegistroTipo
    {

        /// <remarks/>
        Arrivo,

        /// <remarks/>
        Partenza,

        /// <remarks/>
        Interno,
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class RiferimentoPadre
    {

        /// <remarks/>
        public Identificatore Identificatore;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class RiferimentoFiglio
    {

        /// <remarks/>
        public Identificatore Identificatore;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class ListaFascicoli
    {

        /// <remarks/>
        public Fascicolo Fascicolo;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class NoteProtocollazione
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class InvioEmail
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class ModalitaTrasmissione
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Parametro
    {

        /// <remarks/>
        public NomeParametro NomeParametro;

        /// <remarks/>
        public ValoreParametro ValoreParametro;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class NomeParametro
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class ValoreParametro
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text;
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class NotificaEccezione
    {

        /// <remarks/>
        public Identificatore Identificatore;

        /// <remarks/>
        public MessaggioRicevuto MessaggioRicevuto;

        /// <remarks/>
        public Motivo Motivo;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute("2005-03-29")]
        public string versione = "2005-03-29";
    }

    /// <remarks/>
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public class Segnatura
    {

        /// <remarks/>
        public Intestazione Intestazione;

        /// <remarks/>
        public Riferimenti Riferimenti;

        /// <remarks/>
        public Descrizione Descrizione;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute("2005-03-29")]
        public string versione = "2005-03-29";
    }
}