
package it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for protocollo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="protocollo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alboProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="annoProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtProtocolloRi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroRea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoProtocollazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ufficioRi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "protocollo", propOrder = {
    "alboProtocollo",
    "annoProtocollo",
    "dtProtocolloRi",
    "numeroProtocollo",
    "numeroRea",
    "tipoProtocollazione",
    "tipoProtocollo",
    "ufficioRi"
})
public class Protocollo {

    protected String alboProtocollo;
    protected String annoProtocollo;
    protected String dtProtocolloRi;
    protected String numeroProtocollo;
    protected String numeroRea;
    protected String tipoProtocollazione;
    protected String tipoProtocollo;
    protected String ufficioRi;

    /**
     * Gets the value of the alboProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlboProtocollo() {
        return alboProtocollo;
    }

    /**
     * Sets the value of the alboProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlboProtocollo(String value) {
        this.alboProtocollo = value;
    }

    /**
     * Gets the value of the annoProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoProtocollo() {
        return annoProtocollo;
    }

    /**
     * Sets the value of the annoProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoProtocollo(String value) {
        this.annoProtocollo = value;
    }

    /**
     * Gets the value of the dtProtocolloRi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtProtocolloRi() {
        return dtProtocolloRi;
    }

    /**
     * Sets the value of the dtProtocolloRi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtProtocolloRi(String value) {
        this.dtProtocolloRi = value;
    }

    /**
     * Gets the value of the numeroProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    /**
     * Sets the value of the numeroProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocollo(String value) {
        this.numeroProtocollo = value;
    }

    /**
     * Gets the value of the numeroRea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroRea() {
        return numeroRea;
    }

    /**
     * Sets the value of the numeroRea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroRea(String value) {
        this.numeroRea = value;
    }

    /**
     * Gets the value of the tipoProtocollazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoProtocollazione() {
        return tipoProtocollazione;
    }

    /**
     * Sets the value of the tipoProtocollazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoProtocollazione(String value) {
        this.tipoProtocollazione = value;
    }

    /**
     * Gets the value of the tipoProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoProtocollo() {
        return tipoProtocollo;
    }

    /**
     * Sets the value of the tipoProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoProtocollo(String value) {
        this.tipoProtocollo = value;
    }

    /**
     * Gets the value of the ufficioRi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUfficioRi() {
        return ufficioRi;
    }

    /**
     * Sets the value of the ufficioRi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUfficioRi(String value) {
        this.ufficioRi = value;
    }

}
