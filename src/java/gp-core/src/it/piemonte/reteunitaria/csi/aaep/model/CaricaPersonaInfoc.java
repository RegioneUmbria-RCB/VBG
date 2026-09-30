
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CaricaPersonaInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CaricaPersonaInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="progrPersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFineCarica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="progrCarica" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiceCarica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioCarica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="descrCarica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numAnniEsercCarica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrDurataCarica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceDurataCarica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dataPresentazCarica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaricaPersonaInfoc", propOrder = {
    "progrPersona",
    "dataFineCarica",
    "idAAEPAzienda",
    "progrCarica",
    "codiceCarica",
    "dataInizioCarica",
    "descrCarica",
    "numAnniEsercCarica",
    "descrDurataCarica",
    "codiceDurataCarica",
    "idAAEPFonteDato",
    "dataPresentazCarica"
})
public class CaricaPersonaInfoc {

    @XmlElement(required = true, nillable = true)
    protected String progrPersona;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineCarica;
    protected long idAAEPAzienda;
    protected long progrCarica;
    @XmlElement(required = true, nillable = true)
    protected String codiceCarica;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioCarica;
    @XmlElement(required = true, nillable = true)
    protected String descrCarica;
    @XmlElement(required = true, nillable = true)
    protected String numAnniEsercCarica;
    @XmlElement(required = true, nillable = true)
    protected String descrDurataCarica;
    @XmlElement(required = true, nillable = true)
    protected String codiceDurataCarica;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPresentazCarica;

    /**
     * Gets the value of the progrPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgrPersona() {
        return progrPersona;
    }

    /**
     * Sets the value of the progrPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgrPersona(String value) {
        this.progrPersona = value;
    }

    /**
     * Gets the value of the dataFineCarica property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineCarica() {
        return dataFineCarica;
    }

    /**
     * Sets the value of the dataFineCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineCarica(XMLGregorianCalendar value) {
        this.dataFineCarica = value;
    }

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
     * Gets the value of the progrCarica property.
     * 
     */
    public long getProgrCarica() {
        return progrCarica;
    }

    /**
     * Sets the value of the progrCarica property.
     * 
     */
    public void setProgrCarica(long value) {
        this.progrCarica = value;
    }

    /**
     * Gets the value of the codiceCarica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCarica() {
        return codiceCarica;
    }

    /**
     * Sets the value of the codiceCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCarica(String value) {
        this.codiceCarica = value;
    }

    /**
     * Gets the value of the dataInizioCarica property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioCarica() {
        return dataInizioCarica;
    }

    /**
     * Sets the value of the dataInizioCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioCarica(XMLGregorianCalendar value) {
        this.dataInizioCarica = value;
    }

    /**
     * Gets the value of the descrCarica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCarica() {
        return descrCarica;
    }

    /**
     * Sets the value of the descrCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCarica(String value) {
        this.descrCarica = value;
    }

    /**
     * Gets the value of the numAnniEsercCarica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumAnniEsercCarica() {
        return numAnniEsercCarica;
    }

    /**
     * Sets the value of the numAnniEsercCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumAnniEsercCarica(String value) {
        this.numAnniEsercCarica = value;
    }

    /**
     * Gets the value of the descrDurataCarica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrDurataCarica() {
        return descrDurataCarica;
    }

    /**
     * Sets the value of the descrDurataCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrDurataCarica(String value) {
        this.descrDurataCarica = value;
    }

    /**
     * Gets the value of the codiceDurataCarica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceDurataCarica() {
        return codiceDurataCarica;
    }

    /**
     * Sets the value of the codiceDurataCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceDurataCarica(String value) {
        this.codiceDurataCarica = value;
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
     * Gets the value of the dataPresentazCarica property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPresentazCarica() {
        return dataPresentazCarica;
    }

    /**
     * Sets the value of the dataPresentazCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPresentazCarica(XMLGregorianCalendar value) {
        this.dataPresentazCarica = value;
    }

}
