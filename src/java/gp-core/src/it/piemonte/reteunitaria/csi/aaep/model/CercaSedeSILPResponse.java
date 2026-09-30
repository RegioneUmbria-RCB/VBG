
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
 *         &lt;element name="cercaSedeSILPReturn" type="{urn:AAEPCSI}SedeSILP" minOccurs="0"/>
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
    "cercaSedeSILPReturn"
})
@XmlRootElement(name = "cercaSedeSILPResponse")
public class CercaSedeSILPResponse {

    protected SedeSILP cercaSedeSILPReturn;

    /**
     * Gets the value of the cercaSedeSILPReturn property.
     * 
     * @return
     *     possible object is
     *     {@link SedeSILP }
     *     
     */
    public SedeSILP getCercaSedeSILPReturn() {
        return cercaSedeSILPReturn;
    }

    /**
     * Sets the value of the cercaSedeSILPReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link SedeSILP }
     *     
     */
    public void setCercaSedeSILPReturn(SedeSILP value) {
        this.cercaSedeSILPReturn = value;
    }

}
