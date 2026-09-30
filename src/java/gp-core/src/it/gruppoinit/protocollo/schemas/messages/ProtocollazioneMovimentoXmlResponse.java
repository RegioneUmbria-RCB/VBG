
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
 *         &lt;element name="ProtocollazioneMovimentoXmlResult" type="{http://it.gruppoinit/Protocollazione}DatiProtocolloResponseType" minOccurs="0"/>
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
    "protocollazioneMovimentoXmlResult"
})
@XmlRootElement(name = "ProtocollazioneMovimentoXmlResponse")
public class ProtocollazioneMovimentoXmlResponse {

    @XmlElement(name = "ProtocollazioneMovimentoXmlResult", nillable = true)
    protected DatiProtocolloResponseType protocollazioneMovimentoXmlResult;

    /**
     * Gets the value of the protocollazioneMovimentoXmlResult property.
     * 
     * @return
     *     possible object is
     *     {@link DatiProtocolloResponseType }
     *     
     */
    public DatiProtocolloResponseType getProtocollazioneMovimentoXmlResult() {
        return protocollazioneMovimentoXmlResult;
    }

    /**
     * Sets the value of the protocollazioneMovimentoXmlResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiProtocolloResponseType }
     *     
     */
    public void setProtocollazioneMovimentoXmlResult(DatiProtocolloResponseType value) {
        this.protocollazioneMovimentoXmlResult = value;
    }

}
