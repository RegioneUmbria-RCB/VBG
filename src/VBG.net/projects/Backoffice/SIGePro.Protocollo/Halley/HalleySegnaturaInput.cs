using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.ComponentModel;

namespace Init.SIGePro.Protocollo.Halley
{
    #region Classi usate per realizzare il file segnatura.xml
    /// <remarks/>
    [XmlRoot(Namespace = "", ElementName = "Segnatura", IsNullable = false)]
    public class HalleySegnaturaInput
    {

        /// <remarks/>
        [XmlElement()]
        public Intestazione Intestazione;

        /// <remarks/>
        [XmlElement()]
        public Descrizione Descrizione;

        /// <remarks/>
        [XmlElement()]
        public ApplicativoProtocollo ApplicativoProtocollo;
    }

    /// <remarks/>
    public class Intestazione
    {
        /// <remarks/>
        [XmlElement()]
        public string Oggetto;

        /// <remarks/>
        [XmlElement()]
        public Identificatore Identificatore;

        /// <remarks/>
        [XmlElement()]
        public Mittente[] Mittente;

        /// <remarks/>
        [XmlElement()]
        public Destinatario[] Destinatario;

        /// <remarks/>
        [XmlElement()]
        public Classifica Classifica;

        /// <remarks/>
        [XmlElement()]
        public Fascicolo Fascicolo;
    }

    /// <remarks/>
    public class Identificatore
    {

        /// <remarks/>
        [XmlElement()]
        public string CodiceAmministrazione;

        /// <remarks/>
        [XmlElement()]
        public string CodiceAOO;

        /// <remarks/>
        [XmlElement()]
        public string NumeroRegistrazione;

        /// <remarks/>
        [XmlElement()]
        public string DataRegistrazione;

        /// <remarks/>
        [XmlElement()]
        public string Flusso;
    }

    /// <remarks/>
    public class Parametro
    {

        /// <remarks/>
        [XmlAttribute()]
        public string nome;

        /// <remarks/>
        [XmlAttribute()]
        public string valore;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class ApplicativoProtocollo
    {

        /// <remarks/>
        [XmlElement("Parametro")]
        public Parametro[] Parametro;

        /// <remarks/>
        [XmlAttribute()]
        public string nome;
    }

    /// <remarks/>
    public class TipoDocumento
    {

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class DescrizioneDocumento
    {

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Documento
    {

        /// <remarks/>
        [XmlElement()]
        public DescrizioneDocumento DescrizioneDocumento;

        /// <remarks/>
        [XmlElement()]
        public TipoDocumento TipoDocumento;

        /// <remarks/>
        [XmlAttribute()]
        public string nome;

        /// <remarks/>
        [XmlAttribute()]
        public long id;
    }

    /// <remarks/>
    public class Descrizione
    {

        /// <remarks/>
        [XmlElement()]
        public Documento Documento;

        /// <remarks/>
        [XmlArrayItem(IsNullable = false)]
        public Documento[] Allegati;
    }

    /// <remarks/>
    public class Fascicolo
    {

        /// <remarks/>
        [XmlAttribute()]
        public string numero;

        /// <remarks/>
        [XmlAttribute()]
        public string anno;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Classifica
    {

        /// <remarks/>
        [XmlElement()]
        public string CodiceAmministrazione;

        /// <remarks/>
        [XmlElement()]
        public string CodiceAOO;

        /// <remarks/>
        [XmlElement()]
        public string CodiceTitolario;
    }

    /// <remarks/>
    public class Destinatario
    {
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Amministrazione", typeof(Amministrazione))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("AOO", typeof(AOO))]
        public object[] Items;

        /// <remarks/>
        [XmlElement()]
        public IndirizzoTelematico IndirizzoTelematico;

        /// <remarks/>
        [XmlElement("Telefono")]
        public string[] Telefono;

        /// <remarks/>
        [XmlElement("Fax")]
        public string[] Fax;

        /// <remarks/>
        [XmlElement()]
        public string IndirizzoPostale;
    }

    /// <remarks/>
    public class Amministrazione
    {

        /// <remarks/>
        [XmlElement()]
        public string Denominazione;

        /// <remarks/>
        [XmlElement()]
        public string CodiceAmministrazione;

        /// <remarks/>
        [XmlElement()]
        public IndirizzoTelematico IndirizzoTelematico;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("IndirizzoPostale", typeof(string))]
        [System.Xml.Serialization.XmlElementAttribute("UnitaOrganizzativa", typeof(UnitaOrganizzativa))]
        [System.Xml.Serialization.XmlElementAttribute("Fax", typeof(string))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("Telefono", typeof(string))]
        [System.Xml.Serialization.XmlElementAttribute("Ruolo", typeof(string))]
        [System.Xml.Serialization.XmlChoiceIdentifierAttribute("ItemElementName")]
        public object[] Items;

        /// <remarks/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public ItemChoiceType[] ItemElementName;
    }


    /// <remarks/>
    [System.Xml.Serialization.XmlTypeAttribute(IncludeInSchema = false)]
    public enum ItemChoiceType
    {

        /// <remarks/>
        IndirizzoPostale,

        /// <remarks/>
        UnitaOrganizzativa,

        /// <remarks/>
        Fax,

        /// <remarks/>
        Persona,

        /// <remarks/>
        Telefono,

        /// <remarks/>
        Ruolo,
    }


    /// <remarks/>
    public class IndirizzoTelematico
    {

        /// <remarks/>
        [XmlAttribute(DataType = "NMTOKEN")]
        [DefaultValue("smtp")]
        public string tipo = "smtp";

        /// <remarks/>
        [XmlAttribute()]
        public string note;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class UnitaOrganizzativa
    {

        /// <remarks/>
        [XmlAttribute()]
        public string id;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Persona
    {

        /// <remarks/>
        [XmlElement()]
        public string Nome;

        /// <remarks/>
        [XmlElement()]
        public string Cognome;

        /// <remarks/>
        [XmlElement()]
        public string Titolo;

        /// <remarks/>
        [XmlElement()]
        public string CodiceFiscale;

        /// <remarks/>
        [XmlElement()]
        public string Identificativo;

        /// <remarks/>
        [XmlElement()]
        public string Denominazione;

        /// <remarks/>
        [XmlElement()]
        public IndirizzoTelematico IndirizzoTelematico;

        /// <remarks/>
        [XmlAttribute()]
        public string id;
    }



    /// <remarks/>
    public class AOO
    {

        /// <remarks/>
        [XmlElement()]
        public string CodiceAOO;

        /// <remarks/>
        [XmlElement()]
        public string Denominazione;
    }

    /// <remarks/>
    public class Mittente
    {
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Amministrazione", typeof(Amministrazione))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("AOO", typeof(AOO))]
        public object[] Items;
    }

    /// <remarks/>
    public class Civico
    {

        /// <remarks/>
        [XmlElement()]
        public object[] Items;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class CAP
    {

        /// <remarks/>
        [XmlElement()]
        public object[] Items;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Comune
    {

        /// <remarks/>
        [XmlElement()]
        public object[] Items;

        /// <remarks/>
        [XmlText()]
        public string[] Text;

        /// <remarks/>
        [XmlAttribute()]
        public string codiceISTAT;
    }

    /// <remarks/>
    public class Provincia
    {

        /// <remarks/>
        [XmlElement()]
        public object[] Items;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Nazione
    {

        /// <remarks/>
        [XmlElement()]
        public object[] Items;

        /// <remarks/>
        [XmlText()]
        public string[] Text;
    }

    /// <remarks/>
    public class Allegati
    {

        /// <remarks/>
        [XmlElement("Documento")]
        public Documento[] Documento;
    }
    #endregion
}
