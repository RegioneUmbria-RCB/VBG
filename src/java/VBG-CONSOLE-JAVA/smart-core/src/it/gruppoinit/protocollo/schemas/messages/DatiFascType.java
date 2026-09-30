
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiFascType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiFascType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumeroFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClassificaFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OggettoFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AnnoFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiFascType", propOrder = {
    "numeroFascicolo",
    "dataFascicolo",
    "classificaFascicolo",
    "oggettoFascicolo",
    "annoFascicolo"
})
public class DatiFascType {

    @XmlElement(name = "NumeroFascicolo", nillable = true)
    protected String numeroFascicolo;
    @XmlElement(name = "DataFascicolo", nillable = true)
    protected String dataFascicolo;
    @XmlElement(name = "ClassificaFascicolo", nillable = true)
    protected String classificaFascicolo;
    @XmlElement(name = "OggettoFascicolo", nillable = true)
    protected String oggettoFascicolo;
    @XmlElement(name = "AnnoFascicolo", nillable = true)
    protected String annoFascicolo;

    /**
     * Gets the value of the numeroFascicolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroFascicolo() {
        return numeroFascicolo;
    }

    /**
     * Sets the value of the numeroFascicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroFascicolo(String value) {
        this.numeroFascicolo = value;
    }

    /**
     * Gets the value of the dataFascicolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFascicolo() {
        return dataFascicolo;
    }

    /**
     * Sets the value of the dataFascicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFascicolo(String value) {
        this.dataFascicolo = value;
    }

    /**
     * Gets the value of the classificaFascicolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassificaFascicolo() {
        return classificaFascicolo;
    }

    /**
     * Sets the value of the classificaFascicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassificaFascicolo(String value) {
        this.classificaFascicolo = value;
    }

    /**
     * Gets the value of the oggettoFascicolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoFascicolo() {
        return oggettoFascicolo;
    }

    /**
     * Sets the value of the oggettoFascicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoFascicolo(String value) {
        this.oggettoFascicolo = value;
    }

    /**
     * Gets the value of the annoFascicolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoFascicolo() {
        return annoFascicolo;
    }

    /**
     * Sets the value of the annoFascicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoFascicolo(String value) {
        this.annoFascicolo = value;
    }

}
