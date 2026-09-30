
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="StampaEtichetteResult" type="{http://it.gruppoinit/Protocollazione}EtichetteResponseType" minOccurs="0"/>
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
    "stampaEtichetteResult"
})
@XmlRootElement(name = "StampaEtichetteResponse")
public class StampaEtichetteResponse {

    @XmlElement(name = "StampaEtichetteResult", nillable = true)
    protected EtichetteResponseType stampaEtichetteResult;

    /**
     * Gets the value of the stampaEtichetteResult property.
     * 
     * @return
     *     possible object is
     *     {@link EtichetteResponseType }
     *     
     */
    public EtichetteResponseType getStampaEtichetteResult() {
        return stampaEtichetteResult;
    }

    /**
     * Sets the value of the stampaEtichetteResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EtichetteResponseType }
     *     
     */
    public void setStampaEtichetteResult(EtichetteResponseType value) {
        this.stampaEtichetteResult = value;
    }

}
