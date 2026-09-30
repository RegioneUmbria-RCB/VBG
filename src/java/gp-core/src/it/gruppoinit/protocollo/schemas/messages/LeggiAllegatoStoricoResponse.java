
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
 *         &lt;element name="LeggiAllegatoStoricoResult" type="{http://it.gruppoinit/Protocollazione}AllegatoResponseType" minOccurs="0"/>
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
    "leggiAllegatoStoricoResult"
})
@XmlRootElement(name = "LeggiAllegatoStoricoResponse")
public class LeggiAllegatoStoricoResponse {

    @XmlElement(name = "LeggiAllegatoStoricoResult", nillable = true)
    protected AllegatoResponseType leggiAllegatoStoricoResult;

    /**
     * Gets the value of the leggiAllegatoStoricoResult property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatoResponseType }
     *     
     */
    public AllegatoResponseType getLeggiAllegatoStoricoResult() {
        return leggiAllegatoStoricoResult;
    }

    /**
     * Sets the value of the leggiAllegatoStoricoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllegatoResponseType }
     *     
     */
    public void setLeggiAllegatoStoricoResult(AllegatoResponseType value) {
        this.leggiAllegatoStoricoResult = value;
    }

}
