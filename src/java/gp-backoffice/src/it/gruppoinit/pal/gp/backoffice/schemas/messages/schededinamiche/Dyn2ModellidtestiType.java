
package it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dyn2ModellidtestiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dyn2ModellidtestiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idModellidtesti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="testo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dyn2BasetipitestoType" type="{http://gruppoinit.it/sigepro/schemas/messages/schedeDinamiche}Dyn2BasetipitestoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dyn2ModellidtestiType", propOrder = {
    "idModellidtesti",
    "testo",
    "dyn2BasetipitestoType"
})
public class Dyn2ModellidtestiType {

    @XmlElement(required = true)
    protected String idModellidtesti;
    protected String testo;
    protected Dyn2BasetipitestoType dyn2BasetipitestoType;

    /**
     * Gets the value of the idModellidtesti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdModellidtesti() {
        return idModellidtesti;
    }

    /**
     * Sets the value of the idModellidtesti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdModellidtesti(String value) {
        this.idModellidtesti = value;
    }

    /**
     * Gets the value of the testo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Sets the value of the testo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTesto(String value) {
        this.testo = value;
    }

    /**
     * Gets the value of the dyn2BasetipitestoType property.
     * 
     * @return
     *     possible object is
     *     {@link Dyn2BasetipitestoType }
     *     
     */
    public Dyn2BasetipitestoType getDyn2BasetipitestoType() {
        return dyn2BasetipitestoType;
    }

    /**
     * Sets the value of the dyn2BasetipitestoType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dyn2BasetipitestoType }
     *     
     */
    public void setDyn2BasetipitestoType(Dyn2BasetipitestoType value) {
        this.dyn2BasetipitestoType = value;
    }

}
