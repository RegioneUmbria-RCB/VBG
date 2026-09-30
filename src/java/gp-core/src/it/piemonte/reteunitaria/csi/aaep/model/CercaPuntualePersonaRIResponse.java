
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="cercaPuntualePersonaRIReturn" type="{urn:AAEPCSI}PersonaRIInfoc" minOccurs="0"/>
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
    "cercaPuntualePersonaRIReturn"
})
@XmlRootElement(name = "cercaPuntualePersonaRIResponse")
public class CercaPuntualePersonaRIResponse {

    protected PersonaRIInfoc cercaPuntualePersonaRIReturn;

    /**
     * Gets the value of the cercaPuntualePersonaRIReturn property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaRIInfoc }
     *     
     */
    public PersonaRIInfoc getCercaPuntualePersonaRIReturn() {
        return cercaPuntualePersonaRIReturn;
    }

    /**
     * Sets the value of the cercaPuntualePersonaRIReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaRIInfoc }
     *     
     */
    public void setCercaPuntualePersonaRIReturn(PersonaRIInfoc value) {
        this.cercaPuntualePersonaRIReturn = value;
    }

}
