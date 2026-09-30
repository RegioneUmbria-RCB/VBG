
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
 *         &lt;element name="cercaPerCodiceFiscaleINFOCAMEREReturn" type="{urn:AAEPCSI}ImpresaInfocamere" minOccurs="0"/>
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
    "cercaPerCodiceFiscaleINFOCAMEREReturn"
})
@XmlRootElement(name = "cercaPerCodiceFiscaleINFOCAMEREResponse")
public class CercaPerCodiceFiscaleINFOCAMEREResponse {

    protected ImpresaInfocamere cercaPerCodiceFiscaleINFOCAMEREReturn;

    /**
     * Gets the value of the cercaPerCodiceFiscaleINFOCAMEREReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ImpresaInfocamere }
     *     
     */
    public ImpresaInfocamere getCercaPerCodiceFiscaleINFOCAMEREReturn() {
        return cercaPerCodiceFiscaleINFOCAMEREReturn;
    }

    /**
     * Sets the value of the cercaPerCodiceFiscaleINFOCAMEREReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImpresaInfocamere }
     *     
     */
    public void setCercaPerCodiceFiscaleINFOCAMEREReturn(ImpresaInfocamere value) {
        this.cercaPerCodiceFiscaleINFOCAMEREReturn = value;
    }

}
