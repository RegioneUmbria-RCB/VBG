
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SezSpecInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SezSpecInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codSezione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="codiceSezSpec" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFine" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="codAlbo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="progrSpe" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="flColtDir" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SezSpecInfoc", propOrder = {
    "idAAEPAzienda",
    "codSezione",
    "dataInizio",
    "codiceSezSpec",
    "dataFine",
    "codAlbo",
    "idAAEPFonteDato",
    "progrSpe",
    "flColtDir"
})
public class SezSpecInfoc {

    protected long idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String codSezione;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizio;
    @XmlElement(required = true, nillable = true)
    protected String codiceSezSpec;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFine;
    @XmlElement(required = true, nillable = true)
    protected String codAlbo;
    protected long idAAEPFonteDato;
    protected long progrSpe;
    @XmlElement(required = true, nillable = true)
    protected String flColtDir;

    /**
     * Gets the value of the idAAEPAzienda property.
     * 
     */
    public long getIdAAEPAzienda() {
        return idAAEPAzienda;
    }

    /**
     * Sets the value of the idAAEPAzienda property.
     * 
     */
    public void setIdAAEPAzienda(long value) {
        this.idAAEPAzienda = value;
    }

    /**
     * Gets the value of the codSezione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodSezione() {
        return codSezione;
    }

    /**
     * Sets the value of the codSezione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodSezione(String value) {
        this.codSezione = value;
    }

    /**
     * Gets the value of the dataInizio property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizio() {
        return dataInizio;
    }

    /**
     * Sets the value of the dataInizio property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizio(XMLGregorianCalendar value) {
        this.dataInizio = value;
    }

    /**
     * Gets the value of the codiceSezSpec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSezSpec() {
        return codiceSezSpec;
    }

    /**
     * Sets the value of the codiceSezSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSezSpec(String value) {
        this.codiceSezSpec = value;
    }

    /**
     * Gets the value of the dataFine property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFine() {
        return dataFine;
    }

    /**
     * Sets the value of the dataFine property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFine(XMLGregorianCalendar value) {
        this.dataFine = value;
    }

    /**
     * Gets the value of the codAlbo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAlbo() {
        return codAlbo;
    }

    /**
     * Sets the value of the codAlbo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAlbo(String value) {
        this.codAlbo = value;
    }

    /**
     * Gets the value of the idAAEPFonteDato property.
     * 
     */
    public long getIdAAEPFonteDato() {
        return idAAEPFonteDato;
    }

    /**
     * Sets the value of the idAAEPFonteDato property.
     * 
     */
    public void setIdAAEPFonteDato(long value) {
        this.idAAEPFonteDato = value;
    }

    /**
     * Gets the value of the progrSpe property.
     * 
     */
    public long getProgrSpe() {
        return progrSpe;
    }

    /**
     * Sets the value of the progrSpe property.
     * 
     */
    public void setProgrSpe(long value) {
        this.progrSpe = value;
    }

    /**
     * Gets the value of the flColtDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlColtDir() {
        return flColtDir;
    }

    /**
     * Sets the value of the flColtDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlColtDir(String value) {
        this.flColtDir = value;
    }

}
