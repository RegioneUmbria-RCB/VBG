
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EseguiAccettazioneResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EseguiAccettazioneResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://it.gruppoinit/Protocollazione}EnumStatusType" minOccurs="0"/>
 *         &lt;element name="ErroreProtocollo" type="{http://it.gruppoinit/Protocollazione}ErroreProtocolloType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EseguiAccettazioneResponseType", propOrder = {
    "status",
    "erroreProtocollo"
})
public class EseguiAccettazioneResponseType {

    @XmlElement(name = "Status")
    protected EnumStatusType status;
    @XmlElement(name = "ErroreProtocollo", nillable = true)
    protected ErroreProtocolloType erroreProtocollo;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link EnumStatusType }
     *     
     */
    public EnumStatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumStatusType }
     *     
     */
    public void setStatus(EnumStatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the erroreProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public ErroreProtocolloType getErroreProtocollo() {
        return erroreProtocollo;
    }

    /**
     * Sets the value of the erroreProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public void setErroreProtocollo(ErroreProtocolloType value) {
        this.erroreProtocollo = value;
    }

}
