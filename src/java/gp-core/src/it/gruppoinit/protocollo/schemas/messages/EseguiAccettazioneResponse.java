
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
 *         &lt;element name="EseguiAccettazioneResult" type="{http://it.gruppoinit/Protocollazione}EseguiAccettazioneResponseType" minOccurs="0"/>
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
    "eseguiAccettazioneResult"
})
@XmlRootElement(name = "EseguiAccettazioneResponse")
public class EseguiAccettazioneResponse {

    @XmlElement(name = "EseguiAccettazioneResult", nillable = true)
    protected EseguiAccettazioneResponseType eseguiAccettazioneResult;

    /**
     * Gets the value of the eseguiAccettazioneResult property.
     * 
     * @return
     *     possible object is
     *     {@link EseguiAccettazioneResponseType }
     *     
     */
    public EseguiAccettazioneResponseType getEseguiAccettazioneResult() {
        return eseguiAccettazioneResult;
    }

    /**
     * Sets the value of the eseguiAccettazioneResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EseguiAccettazioneResponseType }
     *     
     */
    public void setEseguiAccettazioneResult(EseguiAccettazioneResponseType value) {
        this.eseguiAccettazioneResult = value;
    }

}
