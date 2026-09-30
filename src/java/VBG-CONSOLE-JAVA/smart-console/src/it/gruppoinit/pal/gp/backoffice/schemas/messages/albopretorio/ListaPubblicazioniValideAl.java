
package it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ListaPubblicazioniValideAl complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaPubblicazioniValideAl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ALBO_CATEGORIE_ID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CODICEAMMINISTRAZIONE" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ALBO_CATEGORIE_DESCR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AMMINISTRAZIONE_DESCR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CODICEUFFICIO" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CODICERESPONSABILE" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NUMERO_PUBBLICAZIONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_PUBBLICAZIONE" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="DESCRIZIONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_CREAZIONE" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="VALIDA_DAL" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="VALIDA_AL" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="NUMERO_PROTOCOLLO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_PROTOCOLLO" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="NOTE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaPubblicazioniValideAl", propOrder = {

})
public class ListaPubblicazioniValideAl {

    @XmlElement(name = "ID")
    protected int id;
    @XmlElement(name = "ALBO_CATEGORIE_ID", required = true, type = Integer.class, nillable = true)
    protected Integer albocategorieid;
    @XmlElement(name = "CODICEAMMINISTRAZIONE", required = true, type = Integer.class, nillable = true)
    protected Integer codiceamministrazione;
    @XmlElement(name = "ALBO_CATEGORIE_DESCR", required = true, nillable = true)
    protected String albocategoriedescr;
    @XmlElement(name = "AMMINISTRAZIONE_DESCR", required = true, nillable = true)
    protected String amministrazionedescr;
    @XmlElement(name = "CODICEUFFICIO", required = true, type = Integer.class, nillable = true)
    protected Integer codiceufficio;
    @XmlElement(name = "CODICERESPONSABILE", required = true, type = Integer.class, nillable = true)
    protected Integer codiceresponsabile;
    @XmlElement(name = "NUMERO_PUBBLICAZIONE", required = true, nillable = true)
    protected String numeropubblicazione;
    @XmlElement(name = "DATA_PUBBLICAZIONE", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datapubblicazione;
    @XmlElement(name = "DESCRIZIONE", required = true, nillable = true)
    protected String descrizione;
    @XmlElement(name = "DATA_CREAZIONE", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datacreazione;
    @XmlElement(name = "VALIDA_DAL", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validadal;
    @XmlElement(name = "VALIDA_AL", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validaal;
    @XmlElement(name = "NUMERO_PROTOCOLLO", required = true, nillable = true)
    protected String numeroprotocollo;
    @XmlElement(name = "DATA_PROTOCOLLO", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataprotocollo;
    @XmlElement(name = "NOTE", required = true, nillable = true)
    protected String note;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setID(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the albocategorieid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getALBOCATEGORIEID() {
        return albocategorieid;
    }

    /**
     * Sets the value of the albocategorieid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setALBOCATEGORIEID(Integer value) {
        this.albocategorieid = value;
    }

    /**
     * Gets the value of the codiceamministrazione property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCODICEAMMINISTRAZIONE() {
        return codiceamministrazione;
    }

    /**
     * Sets the value of the codiceamministrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCODICEAMMINISTRAZIONE(Integer value) {
        this.codiceamministrazione = value;
    }

    /**
     * Gets the value of the albocategoriedescr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALBOCATEGORIEDESCR() {
        return albocategoriedescr;
    }

    /**
     * Sets the value of the albocategoriedescr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALBOCATEGORIEDESCR(String value) {
        this.albocategoriedescr = value;
    }

    /**
     * Gets the value of the amministrazionedescr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAMMINISTRAZIONEDESCR() {
        return amministrazionedescr;
    }

    /**
     * Sets the value of the amministrazionedescr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMMINISTRAZIONEDESCR(String value) {
        this.amministrazionedescr = value;
    }

    /**
     * Gets the value of the codiceufficio property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCODICEUFFICIO() {
        return codiceufficio;
    }

    /**
     * Sets the value of the codiceufficio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCODICEUFFICIO(Integer value) {
        this.codiceufficio = value;
    }

    /**
     * Gets the value of the codiceresponsabile property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCODICERESPONSABILE() {
        return codiceresponsabile;
    }

    /**
     * Sets the value of the codiceresponsabile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCODICERESPONSABILE(Integer value) {
        this.codiceresponsabile = value;
    }

    /**
     * Gets the value of the numeropubblicazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROPUBBLICAZIONE() {
        return numeropubblicazione;
    }

    /**
     * Sets the value of the numeropubblicazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROPUBBLICAZIONE(String value) {
        this.numeropubblicazione = value;
    }

    /**
     * Gets the value of the datapubblicazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAPUBBLICAZIONE() {
        return datapubblicazione;
    }

    /**
     * Sets the value of the datapubblicazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAPUBBLICAZIONE(XMLGregorianCalendar value) {
        this.datapubblicazione = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRIZIONE() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRIZIONE(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the datacreazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATACREAZIONE() {
        return datacreazione;
    }

    /**
     * Sets the value of the datacreazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATACREAZIONE(XMLGregorianCalendar value) {
        this.datacreazione = value;
    }

    /**
     * Gets the value of the validadal property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVALIDADAL() {
        return validadal;
    }

    /**
     * Sets the value of the validadal property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVALIDADAL(XMLGregorianCalendar value) {
        this.validadal = value;
    }

    /**
     * Gets the value of the validaal property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVALIDAAL() {
        return validaal;
    }

    /**
     * Sets the value of the validaal property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVALIDAAL(XMLGregorianCalendar value) {
        this.validaal = value;
    }

    /**
     * Gets the value of the numeroprotocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROPROTOCOLLO() {
        return numeroprotocollo;
    }

    /**
     * Sets the value of the numeroprotocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROPROTOCOLLO(String value) {
        this.numeroprotocollo = value;
    }

    /**
     * Gets the value of the dataprotocollo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAPROTOCOLLO() {
        return dataprotocollo;
    }

    /**
     * Sets the value of the dataprotocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAPROTOCOLLO(XMLGregorianCalendar value) {
        this.dataprotocollo = value;
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

}
