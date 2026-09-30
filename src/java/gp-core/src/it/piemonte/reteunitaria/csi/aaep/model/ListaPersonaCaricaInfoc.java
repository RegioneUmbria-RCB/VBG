
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ListaPersonaCaricaInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaPersonaCaricaInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrCarica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codFiscalePersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFineCarica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataInizioCarica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="codiceCarica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codFiscaleAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaPersonaCaricaInfoc", propOrder = {
    "descrCarica",
    "codFiscalePersona",
    "descrAzienda",
    "dataFineCarica",
    "dataInizioCarica",
    "codiceCarica",
    "codFiscaleAzienda"
})
public class ListaPersonaCaricaInfoc {

    @XmlElement(required = true, nillable = true)
    protected String descrCarica;
    @XmlElement(required = true, nillable = true)
    protected String codFiscalePersona;
    @XmlElement(required = true, nillable = true)
    protected String descrAzienda;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineCarica;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioCarica;
    @XmlElement(required = true, nillable = true)
    protected String codiceCarica;
    @XmlElement(required = true, nillable = true)
    protected String codFiscaleAzienda;

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
     * Gets the value of the codFiscalePersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodFiscalePersona() {
        return codFiscalePersona;
    }

    /**
     * Sets the value of the codFiscalePersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodFiscalePersona(String value) {
        this.codFiscalePersona = value;
    }

    /**
     * Gets the value of the descrAzienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrAzienda() {
        return descrAzienda;
    }

    /**
     * Sets the value of the descrAzienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrAzienda(String value) {
        this.descrAzienda = value;
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
     * Gets the value of the codFiscaleAzienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodFiscaleAzienda() {
        return codFiscaleAzienda;
    }

    /**
     * Sets the value of the codFiscaleAzienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodFiscaleAzienda(String value) {
        this.codFiscaleAzienda = value;
    }

}
