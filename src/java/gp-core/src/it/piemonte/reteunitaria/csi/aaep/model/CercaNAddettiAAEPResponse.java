
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
 *         &lt;element name="cercaNAddettiAAEPReturn" type="{urn:AAEPCSI}NAddettiAAEP" minOccurs="0"/>
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
    "cercaNAddettiAAEPReturn"
})
@XmlRootElement(name = "cercaNAddettiAAEPResponse")
public class CercaNAddettiAAEPResponse {

    protected NAddettiAAEP cercaNAddettiAAEPReturn;

    /**
     * Gets the value of the cercaNAddettiAAEPReturn property.
     * 
     * @return
     *     possible object is
     *     {@link NAddettiAAEP }
     *     
     */
    public NAddettiAAEP getCercaNAddettiAAEPReturn() {
        return cercaNAddettiAAEPReturn;
    }

    /**
     * Sets the value of the cercaNAddettiAAEPReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link NAddettiAAEP }
     *     
     */
    public void setCercaNAddettiAAEPReturn(NAddettiAAEP value) {
        this.cercaNAddettiAAEPReturn = value;
    }

}
