
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
 *         &lt;element name="IsFascicolatoResult" type="{http://it.gruppoinit/Protocollazione}DatiProtocolloFascicolatoResponseType" minOccurs="0"/>
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
    "isFascicolatoResult"
})
@XmlRootElement(name = "IsFascicolatoResponse")
public class IsFascicolatoResponse {

    @XmlElement(name = "IsFascicolatoResult", nillable = true)
    protected DatiProtocolloFascicolatoResponseType isFascicolatoResult;

    /**
     * Gets the value of the isFascicolatoResult property.
     * 
     * @return
     *     possible object is
     *     {@link DatiProtocolloFascicolatoResponseType }
     *     
     */
    public DatiProtocolloFascicolatoResponseType getIsFascicolatoResult() {
        return isFascicolatoResult;
    }

    /**
     * Sets the value of the isFascicolatoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiProtocolloFascicolatoResponseType }
     *     
     */
    public void setIsFascicolatoResult(DatiProtocolloFascicolatoResponseType value) {
        this.isFascicolatoResult = value;
    }

}
