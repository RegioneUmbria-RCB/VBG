
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
 *         &lt;element name="cercaProspDisabSILPReturn" type="{urn:AAEPCSI}ProspDisabSILP" minOccurs="0"/>
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
    "cercaProspDisabSILPReturn"
})
@XmlRootElement(name = "cercaProspDisabSILPResponse")
public class CercaProspDisabSILPResponse {

    protected ProspDisabSILP cercaProspDisabSILPReturn;

    /**
     * Gets the value of the cercaProspDisabSILPReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ProspDisabSILP }
     *     
     */
    public ProspDisabSILP getCercaProspDisabSILPReturn() {
        return cercaProspDisabSILPReturn;
    }

    /**
     * Sets the value of the cercaProspDisabSILPReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProspDisabSILP }
     *     
     */
    public void setCercaProspDisabSILPReturn(ProspDisabSILP value) {
        this.cercaProspDisabSILPReturn = value;
    }

}
