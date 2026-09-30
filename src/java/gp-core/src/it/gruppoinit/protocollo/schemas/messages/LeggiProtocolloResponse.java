
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
 *         &lt;element name="LeggiProtocolloResult" type="{http://it.gruppoinit/Protocollazione}ArrayOfDatiProtocolloLettoResponseType" minOccurs="0"/>
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
    "leggiProtocolloResult"
})
@XmlRootElement(name = "LeggiProtocolloResponse")
public class LeggiProtocolloResponse {

    @XmlElement(name = "LeggiProtocolloResult", nillable = true)
    protected ArrayOfDatiProtocolloLettoResponseType leggiProtocolloResult;

    /**
     * Gets the value of the leggiProtocolloResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDatiProtocolloLettoResponseType }
     *     
     */
    public ArrayOfDatiProtocolloLettoResponseType getLeggiProtocolloResult() {
        return leggiProtocolloResult;
    }

    /**
     * Sets the value of the leggiProtocolloResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDatiProtocolloLettoResponseType }
     *     
     */
    public void setLeggiProtocolloResult(ArrayOfDatiProtocolloLettoResponseType value) {
        this.leggiProtocolloResult = value;
    }

}
