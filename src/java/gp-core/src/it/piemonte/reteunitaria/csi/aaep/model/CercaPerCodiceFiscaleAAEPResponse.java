
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
 *         &lt;element name="cercaPerCodiceFiscaleAAEPReturn" type="{urn:AAEPCSI}AziendaAAEP" minOccurs="0"/>
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
    "cercaPerCodiceFiscaleAAEPReturn"
})
@XmlRootElement(name = "cercaPerCodiceFiscaleAAEPResponse")
public class CercaPerCodiceFiscaleAAEPResponse {

    protected AziendaAAEP cercaPerCodiceFiscaleAAEPReturn;

    /**
     * Gets the value of the cercaPerCodiceFiscaleAAEPReturn property.
     * 
     * @return
     *     possible object is
     *     {@link AziendaAAEP }
     *     
     */
    public AziendaAAEP getCercaPerCodiceFiscaleAAEPReturn() {
        return cercaPerCodiceFiscaleAAEPReturn;
    }

    /**
     * Sets the value of the cercaPerCodiceFiscaleAAEPReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link AziendaAAEP }
     *     
     */
    public void setCercaPerCodiceFiscaleAAEPReturn(AziendaAAEP value) {
        this.cercaPerCodiceFiscaleAAEPReturn = value;
    }

}
