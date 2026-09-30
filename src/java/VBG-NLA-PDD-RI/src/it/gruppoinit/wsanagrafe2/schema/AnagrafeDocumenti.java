
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AnagrafeDocumenti complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AnagrafeDocumenti">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}BaseDataClass">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDCOMUNE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODICEANAGRAFE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDTIPODOCUMENTO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DATAREGISTRAZIONE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CODICEISTANZA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODICEOGGETTO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DATAINIZIOVALIDITA" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="RIFDOCUMENTO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DATAFINEVALIDITA" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ANNOTAZIONI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Oggetto" type="{http://init.sigepro.it}Oggetti" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagrafeDocumenti", propOrder = {
    "id",
    "idcomune",
    "codiceanagrafe",
    "idtipodocumento",
    "dataregistrazione",
    "codiceistanza",
    "codiceoggetto",
    "datainiziovalidita",
    "rifdocumento",
    "datafinevalidita",
    "annotazioni",
    "oggetto"
})
public class AnagrafeDocumenti
    extends BaseDataClass
{

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "IDCOMUNE")
    protected String idcomune;
    @XmlElement(name = "CODICEANAGRAFE")
    protected String codiceanagrafe;
    @XmlElement(name = "IDTIPODOCUMENTO")
    protected String idtipodocumento;
    @XmlElement(name = "DATAREGISTRAZIONE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataregistrazione;
    @XmlElement(name = "CODICEISTANZA")
    protected String codiceistanza;
    @XmlElement(name = "CODICEOGGETTO")
    protected String codiceoggetto;
    @XmlElement(name = "DATAINIZIOVALIDITA", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datainiziovalidita;
    @XmlElement(name = "RIFDOCUMENTO")
    protected String rifdocumento;
    @XmlElement(name = "DATAFINEVALIDITA", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datafinevalidita;
    @XmlElement(name = "ANNOTAZIONI")
    protected String annotazioni;
    @XmlElement(name = "Oggetto")
    protected Oggetti oggetto;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
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
     * Gets the value of the idtipodocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDTIPODOCUMENTO() {
        return idtipodocumento;
    }

    /**
     * Sets the value of the idtipodocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDTIPODOCUMENTO(String value) {
        this.idtipodocumento = value;
    }

    /**
     * Gets the value of the dataregistrazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAREGISTRAZIONE() {
        return dataregistrazione;
    }

    /**
     * Sets the value of the dataregistrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAREGISTRAZIONE(XMLGregorianCalendar value) {
        this.dataregistrazione = value;
    }

    /**
     * Gets the value of the codiceistanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEISTANZA() {
        return codiceistanza;
    }

    /**
     * Sets the value of the codiceistanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEISTANZA(String value) {
        this.codiceistanza = value;
    }

    /**
     * Gets the value of the codiceoggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEOGGETTO() {
        return codiceoggetto;
    }

    /**
     * Sets the value of the codiceoggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEOGGETTO(String value) {
        this.codiceoggetto = value;
    }

    /**
     * Gets the value of the datainiziovalidita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAINIZIOVALIDITA() {
        return datainiziovalidita;
    }

    /**
     * Sets the value of the datainiziovalidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAINIZIOVALIDITA(XMLGregorianCalendar value) {
        this.datainiziovalidita = value;
    }

    /**
     * Gets the value of the rifdocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRIFDOCUMENTO() {
        return rifdocumento;
    }

    /**
     * Sets the value of the rifdocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRIFDOCUMENTO(String value) {
        this.rifdocumento = value;
    }

    /**
     * Gets the value of the datafinevalidita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAFINEVALIDITA() {
        return datafinevalidita;
    }

    /**
     * Sets the value of the datafinevalidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAFINEVALIDITA(XMLGregorianCalendar value) {
        this.datafinevalidita = value;
    }

    /**
     * Gets the value of the annotazioni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getANNOTAZIONI() {
        return annotazioni;
    }

    /**
     * Sets the value of the annotazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setANNOTAZIONI(String value) {
        this.annotazioni = value;
    }

    /**
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link Oggetti }
     *     
     */
    public Oggetti getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Oggetti }
     *     
     */
    public void setOggetto(Oggetti value) {
        this.oggetto = value;
    }

}
