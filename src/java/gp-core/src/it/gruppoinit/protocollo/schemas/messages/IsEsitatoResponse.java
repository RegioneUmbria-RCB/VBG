
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
 *         &lt;element name="IsEsitatoResult" type="{http://it.gruppoinit/Protocollazione}DatiProtocolloEsitatoResponseType" minOccurs="0"/>
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
    "isEsitatoResult"
})
@XmlRootElement(name = "IsEsitatoResponse")
public class IsEsitatoResponse {

    @XmlElement(name = "IsEsitatoResult", nillable = true)
    protected DatiProtocolloEsitatoResponseType isEsitatoResult;

    /**
     * Gets the value of the isEsitatoResult property.
     * 
     * @return
     *     possible object is
     *     {@link DatiProtocolloEsitatoResponseType }
     *     
     */
    public DatiProtocolloEsitatoResponseType getIsEsitatoResult() {
        return isEsitatoResult;
    }

    /**
     * Sets the value of the isEsitatoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiProtocolloEsitatoResponseType }
     *     
     */
    public void setIsEsitatoResult(DatiProtocolloEsitatoResponseType value) {
        this.isEsitatoResult = value;
    }

}
