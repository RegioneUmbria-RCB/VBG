
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
 *         &lt;element name="FascicolazioneIstanzaXmlResult" type="{http://it.gruppoinit/Protocollazione}DatiFascicoloResponseType" minOccurs="0"/>
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
    "fascicolazioneIstanzaXmlResult"
})
@XmlRootElement(name = "FascicolazioneIstanzaXmlResponse")
public class FascicolazioneIstanzaXmlResponse {

    @XmlElement(name = "FascicolazioneIstanzaXmlResult", nillable = true)
    protected DatiFascicoloResponseType fascicolazioneIstanzaXmlResult;

    /**
     * Gets the value of the fascicolazioneIstanzaXmlResult property.
     * 
     * @return
     *     possible object is
     *     {@link DatiFascicoloResponseType }
     *     
     */
    public DatiFascicoloResponseType getFascicolazioneIstanzaXmlResult() {
        return fascicolazioneIstanzaXmlResult;
    }

    /**
     * Sets the value of the fascicolazioneIstanzaXmlResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiFascicoloResponseType }
     *     
     */
    public void setFascicolazioneIstanzaXmlResult(DatiFascicoloResponseType value) {
        this.fascicolazioneIstanzaXmlResult = value;
    }

}
