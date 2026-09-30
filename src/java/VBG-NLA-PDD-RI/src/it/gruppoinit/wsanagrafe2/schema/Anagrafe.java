
package it.gruppoinit.wsanagrafe2.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Anagrafe complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Anagrafe">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}BaseDataClass">
 *       &lt;sequence>
 *         &lt;element name="CODICEANAGRAFE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDCOMUNE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NOMINATIVO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FORMAGIURIDICA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TIPOLOGIA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INDIRIZZO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CITTA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CAP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROVINCIA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TELEFONO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TELEFONOCELLULARE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PARTITAIVA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODICEFISCALE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NOTE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EMAIL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="REGDITTE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="REGTRIB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODCOMREGDITTE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODCOMREGTRIB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODCOMNASCITA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DATANASCITA" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DATAREGDITTE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DATAREGTRIB" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="INVIOEMAIL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SESSO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NOME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TITOLO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TIPOANAGRAFE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DATANOMINATIVO" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="INVIOEMAILTEC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODICECITTADINANZA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COMUNERESIDENZA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PASSWORD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INDIRIZZOCORRISPONDENZA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CITTACORRISPONDENZA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CAPCORRISPONDENZA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROVINCIACORRISPONDENZA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COMUNECORRISPONDENZA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROVINCIAREA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NUMISCRREA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DATAISCRREA" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FLAG_NOPROFIT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FLAG_DISABILITATO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DATA_DISABILITATO" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODICEELENCOPRO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NUMEROELENCOPRO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROVINCIAELENCOPRO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FoUtenteTester" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TitoloClass" type="{http://init.sigepro.it}Titoli" minOccurs="0"/>
 *         &lt;element name="ElencoProfessionale" type="{http://init.sigepro.it}ElenchiProfessionaliBase" minOccurs="0"/>
 *         &lt;element name="FormaGiuridicaClass" type="{http://init.sigepro.it}FormeGiuridiche" minOccurs="0"/>
 *         &lt;element name="AnagrafeDocumenti" type="{http://init.sigepro.it}AnagrafeDocumenti" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AnagrafeDyn2ModelliT" type="{http://init.sigepro.it}AnagrafeDyn2ModelliT" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AnagrafeDyn2Dati" type="{http://init.sigepro.it}AnagrafeDyn2Dati" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ComuneNascita" type="{http://init.sigepro.it}Comuni" minOccurs="0"/>
 *         &lt;element name="ComuneRegDitte" type="{http://init.sigepro.it}Comuni" minOccurs="0"/>
 *         &lt;element name="ComuneRegTrib" type="{http://init.sigepro.it}Comuni" minOccurs="0"/>
 *         &lt;element name="ComuneCorrispondenza" type="{http://init.sigepro.it}Comuni" minOccurs="0"/>
 *         &lt;element name="ComuneResidenza" type="{http://init.sigepro.it}Comuni" minOccurs="0"/>
 *         &lt;element name="Cittadinanza" type="{http://init.sigepro.it}Cittadinanza" minOccurs="0"/>
 *         &lt;element name="PresenzeStoriche" type="{http://init.sigepro.it}MercatiPresenzeStorico" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="InpsMatricola" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InpsCodiceSede" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InailMatricola" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InailCodiceSede" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SedeInps" type="{http://init.sigepro.it}ElencoInpsBase" minOccurs="0"/>
 *         &lt;element name="SedeInail" type="{http://init.sigepro.it}ElencoInailBase" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Anagrafe", propOrder = {
    "codiceanagrafe",
    "idcomune",
    "nominativo",
    "formagiuridica",
    "tipologia",
    "indirizzo",
    "citta",
    "cap",
    "provincia",
    "telefono",
    "telefonocellulare",
    "fax",
    "partitaiva",
    "codicefiscale",
    "note",
    "email",
    "regditte",
    "regtrib",
    "codcomregditte",
    "codcomregtrib",
    "codcomnascita",
    "datanascita",
    "dataregditte",
    "dataregtrib",
    "invioemail",
    "sesso",
    "nome",
    "titolo",
    "tipoanagrafe",
    "datanominativo",
    "invioemailtec",
    "codicecittadinanza",
    "comuneresidenza",
    "password",
    "indirizzocorrispondenza",
    "cittacorrispondenza",
    "capcorrispondenza",
    "provinciacorrispondenza",
    "comunecorrispondenza",
    "provinciarea",
    "numiscrrea",
    "dataiscrrea",
    "flagnoprofit",
    "flagdisabilitato",
    "datadisabilitato",
    "username",
    "codiceelencopro",
    "numeroelencopro",
    "provinciaelencopro",
    "pec",
    "foUtenteTester",
    "titoloClass",
    "elencoProfessionale",
    "formaGiuridicaClass",
    "anagrafeDocumenti",
    "anagrafeDyn2ModelliT",
    "anagrafeDyn2Dati",
    "comuneNascita",
    "comuneRegDitte",
    "comuneRegTrib",
    "comuneCorrispondenza",
    "comuneResidenza",
    "cittadinanza",
    "presenzeStoriche",
    "inpsMatricola",
    "inpsCodiceSede",
    "inailMatricola",
    "inailCodiceSede",
    "sedeInps",
    "sedeInail"
})
public class Anagrafe
    extends BaseDataClass
{

    @XmlElement(name = "CODICEANAGRAFE")
    protected String codiceanagrafe;
    @XmlElement(name = "IDCOMUNE")
    protected String idcomune;
    @XmlElement(name = "NOMINATIVO")
    protected String nominativo;
    @XmlElement(name = "FORMAGIURIDICA")
    protected String formagiuridica;
    @XmlElement(name = "TIPOLOGIA")
    protected String tipologia;
    @XmlElement(name = "INDIRIZZO")
    protected String indirizzo;
    @XmlElement(name = "CITTA")
    protected String citta;
    @XmlElement(name = "CAP")
    protected String cap;
    @XmlElement(name = "PROVINCIA")
    protected String provincia;
    @XmlElement(name = "TELEFONO")
    protected String telefono;
    @XmlElement(name = "TELEFONOCELLULARE")
    protected String telefonocellulare;
    @XmlElement(name = "FAX")
    protected String fax;
    @XmlElement(name = "PARTITAIVA")
    protected String partitaiva;
    @XmlElement(name = "CODICEFISCALE")
    protected String codicefiscale;
    @XmlElement(name = "NOTE")
    protected String note;
    @XmlElement(name = "EMAIL")
    protected String email;
    @XmlElement(name = "REGDITTE")
    protected String regditte;
    @XmlElement(name = "REGTRIB")
    protected String regtrib;
    @XmlElement(name = "CODCOMREGDITTE")
    protected String codcomregditte;
    @XmlElement(name = "CODCOMREGTRIB")
    protected String codcomregtrib;
    @XmlElement(name = "CODCOMNASCITA")
    protected String codcomnascita;
    @XmlElement(name = "DATANASCITA", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datanascita;
    @XmlElement(name = "DATAREGDITTE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataregditte;
    @XmlElement(name = "DATAREGTRIB", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataregtrib;
    @XmlElement(name = "INVIOEMAIL")
    protected String invioemail;
    @XmlElement(name = "SESSO")
    protected String sesso;
    @XmlElement(name = "NOME")
    protected String nome;
    @XmlElement(name = "TITOLO")
    protected String titolo;
    @XmlElement(name = "TIPOANAGRAFE")
    protected String tipoanagrafe;
    @XmlElement(name = "DATANOMINATIVO", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datanominativo;
    @XmlElement(name = "INVIOEMAILTEC")
    protected String invioemailtec;
    @XmlElement(name = "CODICECITTADINANZA")
    protected String codicecittadinanza;
    @XmlElement(name = "COMUNERESIDENZA")
    protected String comuneresidenza;
    @XmlElement(name = "PASSWORD")
    protected String password;
    @XmlElement(name = "INDIRIZZOCORRISPONDENZA")
    protected String indirizzocorrispondenza;
    @XmlElement(name = "CITTACORRISPONDENZA")
    protected String cittacorrispondenza;
    @XmlElement(name = "CAPCORRISPONDENZA")
    protected String capcorrispondenza;
    @XmlElement(name = "PROVINCIACORRISPONDENZA")
    protected String provinciacorrispondenza;
    @XmlElement(name = "COMUNECORRISPONDENZA")
    protected String comunecorrispondenza;
    @XmlElement(name = "PROVINCIAREA")
    protected String provinciarea;
    @XmlElement(name = "NUMISCRREA")
    protected String numiscrrea;
    @XmlElement(name = "DATAISCRREA", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataiscrrea;
    @XmlElement(name = "FLAG_NOPROFIT")
    protected String flagnoprofit;
    @XmlElement(name = "FLAG_DISABILITATO")
    protected String flagdisabilitato;
    @XmlElement(name = "DATA_DISABILITATO", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datadisabilitato;
    @XmlElement(name = "Username")
    protected String username;
    @XmlElement(name = "CODICEELENCOPRO")
    protected String codiceelencopro;
    @XmlElement(name = "NUMEROELENCOPRO")
    protected String numeroelencopro;
    @XmlElement(name = "PROVINCIAELENCOPRO")
    protected String provinciaelencopro;
    @XmlElement(name = "Pec")
    protected String pec;
    @XmlElement(name = "FoUtenteTester", required = true, type = Integer.class, nillable = true)
    protected Integer foUtenteTester;
    @XmlElement(name = "TitoloClass")
    protected Titoli titoloClass;
    @XmlElement(name = "ElencoProfessionale")
    protected ElenchiProfessionaliBase elencoProfessionale;
    @XmlElement(name = "FormaGiuridicaClass")
    protected FormeGiuridiche formaGiuridicaClass;
    @XmlElement(name = "AnagrafeDocumenti")
    protected List<AnagrafeDocumenti> anagrafeDocumenti;
    @XmlElement(name = "AnagrafeDyn2ModelliT")
    protected List<AnagrafeDyn2ModelliT> anagrafeDyn2ModelliT;
    @XmlElement(name = "AnagrafeDyn2Dati")
    protected List<AnagrafeDyn2Dati> anagrafeDyn2Dati;
    @XmlElement(name = "ComuneNascita")
    protected Comuni comuneNascita;
    @XmlElement(name = "ComuneRegDitte")
    protected Comuni comuneRegDitte;
    @XmlElement(name = "ComuneRegTrib")
    protected Comuni comuneRegTrib;
    @XmlElement(name = "ComuneCorrispondenza")
    protected Comuni comuneCorrispondenza;
    @XmlElement(name = "ComuneResidenza")
    protected Comuni comuneResidenza;
    @XmlElement(name = "Cittadinanza")
    protected Cittadinanza cittadinanza;
    @XmlElement(name = "PresenzeStoriche")
    protected List<MercatiPresenzeStorico> presenzeStoriche;
    @XmlElement(name = "InpsMatricola")
    protected String inpsMatricola;
    @XmlElement(name = "InpsCodiceSede")
    protected String inpsCodiceSede;
    @XmlElement(name = "InailMatricola")
    protected String inailMatricola;
    @XmlElement(name = "InailCodiceSede")
    protected String inailCodiceSede;
    @XmlElement(name = "SedeInps")
    protected ElencoInpsBase sedeInps;
    @XmlElement(name = "SedeInail")
    protected ElencoInailBase sedeInail;

    /**
     * Gets the value of the codiceanagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEANAGRAFE() {
        return codiceanagrafe;
    }

    /**
     * Sets the value of the codiceanagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEANAGRAFE(String value) {
        this.codiceanagrafe = value;
    }

    /**
     * Gets the value of the idcomune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCOMUNE() {
        return idcomune;
    }

    /**
     * Sets the value of the idcomune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCOMUNE(String value) {
        this.idcomune = value;
    }

    /**
     * Gets the value of the nominativo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMINATIVO() {
        return nominativo;
    }

    /**
     * Sets the value of the nominativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMINATIVO(String value) {
        this.nominativo = value;
    }

    /**
     * Gets the value of the formagiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFORMAGIURIDICA() {
        return formagiuridica;
    }

    /**
     * Sets the value of the formagiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFORMAGIURIDICA(String value) {
        this.formagiuridica = value;
    }

    /**
     * Gets the value of the tipologia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOLOGIA() {
        return tipologia;
    }

    /**
     * Sets the value of the tipologia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOLOGIA(String value) {
        this.tipologia = value;
    }

    /**
     * Gets the value of the indirizzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDIRIZZO() {
        return indirizzo;
    }

    /**
     * Sets the value of the indirizzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDIRIZZO(String value) {
        this.indirizzo = value;
    }

    /**
     * Gets the value of the citta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCITTA() {
        return citta;
    }

    /**
     * Sets the value of the citta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCITTA(String value) {
        this.citta = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAP() {
        return cap;
    }

    /**
     * Sets the value of the cap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAP(String value) {
        this.cap = value;
    }

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIA() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIA(String value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONO() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONO(String value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the telefonocellulare property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONOCELLULARE() {
        return telefonocellulare;
    }

    /**
     * Sets the value of the telefonocellulare property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONOCELLULARE(String value) {
        this.telefonocellulare = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAX() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAX(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the partitaiva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARTITAIVA() {
        return partitaiva;
    }

    /**
     * Sets the value of the partitaiva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARTITAIVA(String value) {
        this.partitaiva = value;
    }

    /**
     * Gets the value of the codicefiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEFISCALE() {
        return codicefiscale;
    }

    /**
     * Sets the value of the codicefiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEFISCALE(String value) {
        this.codicefiscale = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOTE() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOTE(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMAIL() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMAIL(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the regditte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGDITTE() {
        return regditte;
    }

    /**
     * Sets the value of the regditte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGDITTE(String value) {
        this.regditte = value;
    }

    /**
     * Gets the value of the regtrib property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGTRIB() {
        return regtrib;
    }

    /**
     * Sets the value of the regtrib property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGTRIB(String value) {
        this.regtrib = value;
    }

    /**
     * Gets the value of the codcomregditte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODCOMREGDITTE() {
        return codcomregditte;
    }

    /**
     * Sets the value of the codcomregditte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODCOMREGDITTE(String value) {
        this.codcomregditte = value;
    }

    /**
     * Gets the value of the codcomregtrib property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODCOMREGTRIB() {
        return codcomregtrib;
    }

    /**
     * Sets the value of the codcomregtrib property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODCOMREGTRIB(String value) {
        this.codcomregtrib = value;
    }

    /**
     * Gets the value of the codcomnascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODCOMNASCITA() {
        return codcomnascita;
    }

    /**
     * Sets the value of the codcomnascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODCOMNASCITA(String value) {
        this.codcomnascita = value;
    }

    /**
     * Gets the value of the datanascita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATANASCITA() {
        return datanascita;
    }

    /**
     * Sets the value of the datanascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATANASCITA(XMLGregorianCalendar value) {
        this.datanascita = value;
    }

    /**
     * Gets the value of the dataregditte property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAREGDITTE() {
        return dataregditte;
    }

    /**
     * Sets the value of the dataregditte property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAREGDITTE(XMLGregorianCalendar value) {
        this.dataregditte = value;
    }

    /**
     * Gets the value of the dataregtrib property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAREGTRIB() {
        return dataregtrib;
    }

    /**
     * Sets the value of the dataregtrib property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAREGTRIB(XMLGregorianCalendar value) {
        this.dataregtrib = value;
    }

    /**
     * Gets the value of the invioemail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINVIOEMAIL() {
        return invioemail;
    }

    /**
     * Sets the value of the invioemail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINVIOEMAIL(String value) {
        this.invioemail = value;
    }

    /**
     * Gets the value of the sesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSESSO() {
        return sesso;
    }

    /**
     * Sets the value of the sesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSESSO(String value) {
        this.sesso = value;
    }

    /**
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOME() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOME(String value) {
        this.nome = value;
    }

    /**
     * Gets the value of the titolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTITOLO() {
        return titolo;
    }

    /**
     * Sets the value of the titolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTITOLO(String value) {
        this.titolo = value;
    }

    /**
     * Gets the value of the tipoanagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOANAGRAFE() {
        return tipoanagrafe;
    }

    /**
     * Sets the value of the tipoanagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOANAGRAFE(String value) {
        this.tipoanagrafe = value;
    }

    /**
     * Gets the value of the datanominativo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATANOMINATIVO() {
        return datanominativo;
    }

    /**
     * Sets the value of the datanominativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATANOMINATIVO(XMLGregorianCalendar value) {
        this.datanominativo = value;
    }

    /**
     * Gets the value of the invioemailtec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINVIOEMAILTEC() {
        return invioemailtec;
    }

    /**
     * Sets the value of the invioemailtec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINVIOEMAILTEC(String value) {
        this.invioemailtec = value;
    }

    /**
     * Gets the value of the codicecittadinanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICECITTADINANZA() {
        return codicecittadinanza;
    }

    /**
     * Sets the value of the codicecittadinanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICECITTADINANZA(String value) {
        this.codicecittadinanza = value;
    }

    /**
     * Gets the value of the comuneresidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMUNERESIDENZA() {
        return comuneresidenza;
    }

    /**
     * Sets the value of the comuneresidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMUNERESIDENZA(String value) {
        this.comuneresidenza = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPASSWORD() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPASSWORD(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the indirizzocorrispondenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDIRIZZOCORRISPONDENZA() {
        return indirizzocorrispondenza;
    }

    /**
     * Sets the value of the indirizzocorrispondenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDIRIZZOCORRISPONDENZA(String value) {
        this.indirizzocorrispondenza = value;
    }

    /**
     * Gets the value of the cittacorrispondenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCITTACORRISPONDENZA() {
        return cittacorrispondenza;
    }

    /**
     * Sets the value of the cittacorrispondenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCITTACORRISPONDENZA(String value) {
        this.cittacorrispondenza = value;
    }

    /**
     * Gets the value of the capcorrispondenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAPCORRISPONDENZA() {
        return capcorrispondenza;
    }

    /**
     * Sets the value of the capcorrispondenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAPCORRISPONDENZA(String value) {
        this.capcorrispondenza = value;
    }

    /**
     * Gets the value of the provinciacorrispondenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIACORRISPONDENZA() {
        return provinciacorrispondenza;
    }

    /**
     * Sets the value of the provinciacorrispondenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIACORRISPONDENZA(String value) {
        this.provinciacorrispondenza = value;
    }

    /**
     * Gets the value of the comunecorrispondenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMUNECORRISPONDENZA() {
        return comunecorrispondenza;
    }

    /**
     * Sets the value of the comunecorrispondenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMUNECORRISPONDENZA(String value) {
        this.comunecorrispondenza = value;
    }

    /**
     * Gets the value of the provinciarea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIAREA() {
        return provinciarea;
    }

    /**
     * Sets the value of the provinciarea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIAREA(String value) {
        this.provinciarea = value;
    }

    /**
     * Gets the value of the numiscrrea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMISCRREA() {
        return numiscrrea;
    }

    /**
     * Sets the value of the numiscrrea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMISCRREA(String value) {
        this.numiscrrea = value;
    }

    /**
     * Gets the value of the dataiscrrea property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAISCRREA() {
        return dataiscrrea;
    }

    /**
     * Sets the value of the dataiscrrea property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAISCRREA(XMLGregorianCalendar value) {
        this.dataiscrrea = value;
    }

    /**
     * Gets the value of the flagnoprofit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAGNOPROFIT() {
        return flagnoprofit;
    }

    /**
     * Sets the value of the flagnoprofit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAGNOPROFIT(String value) {
        this.flagnoprofit = value;
    }

    /**
     * Gets the value of the flagdisabilitato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAGDISABILITATO() {
        return flagdisabilitato;
    }

    /**
     * Sets the value of the flagdisabilitato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAGDISABILITATO(String value) {
        this.flagdisabilitato = value;
    }

    /**
     * Gets the value of the datadisabilitato property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATADISABILITATO() {
        return datadisabilitato;
    }

    /**
     * Sets the value of the datadisabilitato property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATADISABILITATO(XMLGregorianCalendar value) {
        this.datadisabilitato = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the codiceelencopro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEELENCOPRO() {
        return codiceelencopro;
    }

    /**
     * Sets the value of the codiceelencopro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEELENCOPRO(String value) {
        this.codiceelencopro = value;
    }

    /**
     * Gets the value of the numeroelencopro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROELENCOPRO() {
        return numeroelencopro;
    }

    /**
     * Sets the value of the numeroelencopro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROELENCOPRO(String value) {
        this.numeroelencopro = value;
    }

    /**
     * Gets the value of the provinciaelencopro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIAELENCOPRO() {
        return provinciaelencopro;
    }

    /**
     * Sets the value of the provinciaelencopro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIAELENCOPRO(String value) {
        this.provinciaelencopro = value;
    }

    /**
     * Gets the value of the pec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPec() {
        return pec;
    }

    /**
     * Sets the value of the pec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPec(String value) {
        this.pec = value;
    }

    /**
     * Gets the value of the foUtenteTester property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFoUtenteTester() {
        return foUtenteTester;
    }

    /**
     * Sets the value of the foUtenteTester property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFoUtenteTester(Integer value) {
        this.foUtenteTester = value;
    }

    /**
     * Gets the value of the titoloClass property.
     * 
     * @return
     *     possible object is
     *     {@link Titoli }
     *     
     */
    public Titoli getTitoloClass() {
        return titoloClass;
    }

    /**
     * Sets the value of the titoloClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link Titoli }
     *     
     */
    public void setTitoloClass(Titoli value) {
        this.titoloClass = value;
    }

    /**
     * Gets the value of the elencoProfessionale property.
     * 
     * @return
     *     possible object is
     *     {@link ElenchiProfessionaliBase }
     *     
     */
    public ElenchiProfessionaliBase getElencoProfessionale() {
        return elencoProfessionale;
    }

    /**
     * Sets the value of the elencoProfessionale property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElenchiProfessionaliBase }
     *     
     */
    public void setElencoProfessionale(ElenchiProfessionaliBase value) {
        this.elencoProfessionale = value;
    }

    /**
     * Gets the value of the formaGiuridicaClass property.
     * 
     * @return
     *     possible object is
     *     {@link FormeGiuridiche }
     *     
     */
    public FormeGiuridiche getFormaGiuridicaClass() {
        return formaGiuridicaClass;
    }

    /**
     * Sets the value of the formaGiuridicaClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormeGiuridiche }
     *     
     */
    public void setFormaGiuridicaClass(FormeGiuridiche value) {
        this.formaGiuridicaClass = value;
    }

    /**
     * Gets the value of the anagrafeDocumenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anagrafeDocumenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnagrafeDocumenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnagrafeDocumenti }
     * 
     * 
     */
    public List<AnagrafeDocumenti> getAnagrafeDocumenti() {
        if (anagrafeDocumenti == null) {
            anagrafeDocumenti = new ArrayList<AnagrafeDocumenti>();
        }
        return this.anagrafeDocumenti;
    }

    /**
     * Gets the value of the anagrafeDyn2ModelliT property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anagrafeDyn2ModelliT property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnagrafeDyn2ModelliT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnagrafeDyn2ModelliT }
     * 
     * 
     */
    public List<AnagrafeDyn2ModelliT> getAnagrafeDyn2ModelliT() {
        if (anagrafeDyn2ModelliT == null) {
            anagrafeDyn2ModelliT = new ArrayList<AnagrafeDyn2ModelliT>();
        }
        return this.anagrafeDyn2ModelliT;
    }

    /**
     * Gets the value of the anagrafeDyn2Dati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anagrafeDyn2Dati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnagrafeDyn2Dati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnagrafeDyn2Dati }
     * 
     * 
     */
    public List<AnagrafeDyn2Dati> getAnagrafeDyn2Dati() {
        if (anagrafeDyn2Dati == null) {
            anagrafeDyn2Dati = new ArrayList<AnagrafeDyn2Dati>();
        }
        return this.anagrafeDyn2Dati;
    }

    /**
     * Gets the value of the comuneNascita property.
     * 
     * @return
     *     possible object is
     *     {@link Comuni }
     *     
     */
    public Comuni getComuneNascita() {
        return comuneNascita;
    }

    /**
     * Sets the value of the comuneNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comuni }
     *     
     */
    public void setComuneNascita(Comuni value) {
        this.comuneNascita = value;
    }

    /**
     * Gets the value of the comuneRegDitte property.
     * 
     * @return
     *     possible object is
     *     {@link Comuni }
     *     
     */
    public Comuni getComuneRegDitte() {
        return comuneRegDitte;
    }

    /**
     * Sets the value of the comuneRegDitte property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comuni }
     *     
     */
    public void setComuneRegDitte(Comuni value) {
        this.comuneRegDitte = value;
    }

    /**
     * Gets the value of the comuneRegTrib property.
     * 
     * @return
     *     possible object is
     *     {@link Comuni }
     *     
     */
    public Comuni getComuneRegTrib() {
        return comuneRegTrib;
    }

    /**
     * Sets the value of the comuneRegTrib property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comuni }
     *     
     */
    public void setComuneRegTrib(Comuni value) {
        this.comuneRegTrib = value;
    }

    /**
     * Gets the value of the comuneCorrispondenza property.
     * 
     * @return
     *     possible object is
     *     {@link Comuni }
     *     
     */
    public Comuni getComuneCorrispondenza() {
        return comuneCorrispondenza;
    }

    /**
     * Sets the value of the comuneCorrispondenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comuni }
     *     
     */
    public void setComuneCorrispondenza(Comuni value) {
        this.comuneCorrispondenza = value;
    }

    /**
     * Gets the value of the comuneResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link Comuni }
     *     
     */
    public Comuni getComuneResidenza() {
        return comuneResidenza;
    }

    /**
     * Sets the value of the comuneResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comuni }
     *     
     */
    public void setComuneResidenza(Comuni value) {
        this.comuneResidenza = value;
    }

    /**
     * Gets the value of the cittadinanza property.
     * 
     * @return
     *     possible object is
     *     {@link Cittadinanza }
     *     
     */
    public Cittadinanza getCittadinanza() {
        return cittadinanza;
    }

    /**
     * Sets the value of the cittadinanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cittadinanza }
     *     
     */
    public void setCittadinanza(Cittadinanza value) {
        this.cittadinanza = value;
    }

    /**
     * Gets the value of the presenzeStoriche property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the presenzeStoriche property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPresenzeStoriche().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MercatiPresenzeStorico }
     * 
     * 
     */
    public List<MercatiPresenzeStorico> getPresenzeStoriche() {
        if (presenzeStoriche == null) {
            presenzeStoriche = new ArrayList<MercatiPresenzeStorico>();
        }
        return this.presenzeStoriche;
    }

    /**
     * Gets the value of the inpsMatricola property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInpsMatricola() {
        return inpsMatricola;
    }

    /**
     * Sets the value of the inpsMatricola property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInpsMatricola(String value) {
        this.inpsMatricola = value;
    }

    /**
     * Gets the value of the inpsCodiceSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInpsCodiceSede() {
        return inpsCodiceSede;
    }

    /**
     * Sets the value of the inpsCodiceSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInpsCodiceSede(String value) {
        this.inpsCodiceSede = value;
    }

    /**
     * Gets the value of the inailMatricola property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInailMatricola() {
        return inailMatricola;
    }

    /**
     * Sets the value of the inailMatricola property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInailMatricola(String value) {
        this.inailMatricola = value;
    }

    /**
     * Gets the value of the inailCodiceSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInailCodiceSede() {
        return inailCodiceSede;
    }

    /**
     * Sets the value of the inailCodiceSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInailCodiceSede(String value) {
        this.inailCodiceSede = value;
    }

    /**
     * Gets the value of the sedeInps property.
     * 
     * @return
     *     possible object is
     *     {@link ElencoInpsBase }
     *     
     */
    public ElencoInpsBase getSedeInps() {
        return sedeInps;
    }

    /**
     * Sets the value of the sedeInps property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoInpsBase }
     *     
     */
    public void setSedeInps(ElencoInpsBase value) {
        this.sedeInps = value;
    }

    /**
     * Gets the value of the sedeInail property.
     * 
     * @return
     *     possible object is
     *     {@link ElencoInailBase }
     *     
     */
    public ElencoInailBase getSedeInail() {
        return sedeInail;
    }

    /**
     * Sets the value of the sedeInail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoInailBase }
     *     
     */
    public void setSedeInail(ElencoInailBase value) {
        this.sedeInail = value;
    }

}
