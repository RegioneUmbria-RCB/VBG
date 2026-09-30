
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
 *         &lt;element name="IsAnnullatoResult" type="{http://it.gruppoinit/Protocollazione}DatiProtocolloAnnullatoResponseType" minOccurs="0"/>
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
    "isAnnullatoResult"
})
@XmlRootElement(name = "IsAnnullatoResponse")
public class IsAnnullatoResponse {

    @XmlElement(name = "IsAnnullatoResult", nillable = true)
    protected DatiProtocolloAnnullatoResponseType isAnnullatoResult;

    /**
     * Gets the value of the isAnnullatoResult property.
     * 
     * @return
     *     possible object is
     *     {@link DatiProtocolloAnnullatoResponseType }
     *     
     */
    public DatiProtocolloAnnullatoResponseType getIsAnnullatoResult() {
        return isAnnullatoResult;
    }

    /**
     * Sets the value of the isAnnullatoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiProtocolloAnnullatoResponseType }
     *     
     */
    public void setIsAnnullatoResult(DatiProtocolloAnnullatoResponseType value) {
        this.isAnnullatoResult = value;
    }

}
