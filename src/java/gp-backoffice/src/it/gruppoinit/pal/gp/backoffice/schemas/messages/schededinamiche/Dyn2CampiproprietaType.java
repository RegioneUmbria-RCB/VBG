
package it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dyn2CampiproprietaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dyn2CampiproprietaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proprieta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dyn2CampiproprietaType", propOrder = {
    "proprieta",
    "valore"
})
public class Dyn2CampiproprietaType {

    protected String proprieta;
    protected String valore;

    /**
     * Gets the value of the proprieta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProprieta() {
        return proprieta;
    }

    /**
     * Sets the value of the proprieta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProprieta(String value) {
        this.proprieta = value;
    }

    /**
     * Gets the value of the valore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValore() {
        return valore;
    }

    /**
     * Sets the value of the valore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValore(String value) {
        this.valore = value;
    }

}
