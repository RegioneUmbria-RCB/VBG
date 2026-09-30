
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
 *         &lt;element name="FascicolazioneIstanzaResult" type="{http://it.gruppoinit/Protocollazione}DatiFascicoloResponseType" minOccurs="0"/>
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
    "fascicolazioneIstanzaResult"
})
@XmlRootElement(name = "FascicolazioneIstanzaResponse")
public class FascicolazioneIstanzaResponse {

    @XmlElement(name = "FascicolazioneIstanzaResult", nillable = true)
    protected DatiFascicoloResponseType fascicolazioneIstanzaResult;

    /**
     * Gets the value of the fascicolazioneIstanzaResult property.
     * 
     * @return
     *     possible object is
     *     {@link DatiFascicoloResponseType }
     *     
     */
    public DatiFascicoloResponseType getFascicolazioneIstanzaResult() {
        return fascicolazioneIstanzaResult;
    }

    /**
     * Sets the value of the fascicolazioneIstanzaResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiFascicoloResponseType }
     *     
     */
    public void setFascicolazioneIstanzaResult(DatiFascicoloResponseType value) {
        this.fascicolazioneIstanzaResult = value;
    }

}
