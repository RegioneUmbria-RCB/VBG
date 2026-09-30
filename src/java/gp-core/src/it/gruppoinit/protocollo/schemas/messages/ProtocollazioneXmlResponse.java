
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
 *         &lt;element name="ProtocollazioneXmlResult" type="{http://it.gruppoinit/Protocollazione}DatiProtocolloResponseType" minOccurs="0"/>
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
    "protocollazioneXmlResult"
})
@XmlRootElement(name = "ProtocollazioneXmlResponse")
public class ProtocollazioneXmlResponse {

    @XmlElement(name = "ProtocollazioneXmlResult", nillable = true)
    protected DatiProtocolloResponseType protocollazioneXmlResult;

    /**
     * Gets the value of the protocollazioneXmlResult property.
     * 
     * @return
     *     possible object is
     *     {@link DatiProtocolloResponseType }
     *     
     */
    public DatiProtocolloResponseType getProtocollazioneXmlResult() {
        return protocollazioneXmlResult;
    }

    /**
     * Sets the value of the protocollazioneXmlResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiProtocolloResponseType }
     *     
     */
    public void setProtocollazioneXmlResult(DatiProtocolloResponseType value) {
        this.protocollazioneXmlResult = value;
    }

}
