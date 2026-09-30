
package it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dyn2BasecontestiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dyn2BasecontestiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idDyn2Basecontesti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dyn2BasecontestiType", propOrder = {
    "idDyn2Basecontesti",
    "contesto"
})
public class Dyn2BasecontestiType {

    @XmlElement(required = true)
    protected String idDyn2Basecontesti;
    @XmlElement(required = true)
    protected String contesto;

    /**
     * Gets the value of the idDyn2Basecontesti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDyn2Basecontesti() {
        return idDyn2Basecontesti;
    }

    /**
     * Sets the value of the idDyn2Basecontesti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDyn2Basecontesti(String value) {
        this.idDyn2Basecontesti = value;
    }

    /**
     * Gets the value of the contesto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContesto() {
        return contesto;
    }

    /**
     * Sets the value of the contesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContesto(String value) {
        this.contesto = value;
    }

}
