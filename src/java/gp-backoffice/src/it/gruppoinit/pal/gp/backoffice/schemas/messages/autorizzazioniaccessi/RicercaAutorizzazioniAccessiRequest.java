package it.gruppoinit.pal.gp.backoffice.schemas.messages.autorizzazioniaccessi;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroAutorizzazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataAutorizzazione" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="cfImpresa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pivaImpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "token",
    "software",
    "numeroAutorizzazione",
    "dataAutorizzazione",
    "cfImpresa",
    "pivaImpresa"
})
@XmlRootElement(name = "RicercaAutorizzazioniAccessiRequest")
public class RicercaAutorizzazioniAccessiRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String software;
    @XmlElement(required = true)
    protected String numeroAutorizzazione;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataAutorizzazione;
    @XmlElement(required = true)
    protected String cfImpresa;
    protected String pivaImpresa;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the software property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftware() {
        return software;
    }

    /**
     * Sets the value of the software property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftware(String value) {
        this.software = value;
    }

    /**
     * Gets the value of the numeroAutorizzazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroAutorizzazione() {
        return numeroAutorizzazione;
    }

    /**
     * Sets the value of the numeroAutorizzazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroAutorizzazione(String value) {
        this.numeroAutorizzazione = value;
    }

    /**
     * Gets the value of the dataAutorizzazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAutorizzazione() {
        return dataAutorizzazione;
    }

    /**
     * Sets the value of the dataAutorizzazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAutorizzazione(XMLGregorianCalendar value) {
        this.dataAutorizzazione = value;
    }

    /**
     * Gets the value of the cfImpresa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfImpresa() {
        return cfImpresa;
    }

    /**
     * Sets the value of the cfImpresa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfImpresa(String value) {
        this.cfImpresa = value;
    }

    /**
     * Gets the value of the pivaImpresa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPivaImpresa() {
        return pivaImpresa;
    }

    /**
     * Sets the value of the pivaImpresa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPivaImpresa(String value) {
        this.pivaImpresa = value;
    }

}
