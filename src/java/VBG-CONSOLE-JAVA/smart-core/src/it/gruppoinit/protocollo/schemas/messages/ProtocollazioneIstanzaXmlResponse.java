
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
 *         &lt;element name="ProtocollazioneIstanzaXmlResult" type="{http://it.gruppoinit/Protocollazione}DatiProtocolloResponseType" minOccurs="0"/>
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
    "protocollazioneIstanzaXmlResult"
})
@XmlRootElement(name = "ProtocollazioneIstanzaXmlResponse")
public class ProtocollazioneIstanzaXmlResponse {

    @XmlElement(name = "ProtocollazioneIstanzaXmlResult", nillable = true)
    protected DatiProtocolloResponseType protocollazioneIstanzaXmlResult;

    /**
     * Gets the value of the protocollazioneIstanzaXmlResult property.
     * 
     * @return
     *     possible object is
     *     {@link DatiProtocolloResponseType }
     *     
     */
    public DatiProtocolloResponseType getProtocollazioneIstanzaXmlResult() {
        return protocollazioneIstanzaXmlResult;
    }

    /**
     * Sets the value of the protocollazioneIstanzaXmlResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiProtocolloResponseType }
     *     
     */
    public void setProtocollazioneIstanzaXmlResult(DatiProtocolloResponseType value) {
        this.protocollazioneIstanzaXmlResult = value;
    }

}
