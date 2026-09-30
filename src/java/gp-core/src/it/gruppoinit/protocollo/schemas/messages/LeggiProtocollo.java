
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
 *         &lt;element name="leggiProtocolloRequest" type="{http://schemas.datacontract.org/2004/07/Init.SIGePro.Protocollo.WsDataClass}LeggiProtocolloRequest" minOccurs="0"/>
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
    "leggiProtocolloRequest"
})
@XmlRootElement(name = "LeggiProtocollo")
public class LeggiProtocollo {

    @XmlElement(nillable = true)
    protected LeggiProtocolloRequest leggiProtocolloRequest;

    /**
     * Gets the value of the leggiProtocolloRequest property.
     * 
     * @return
     *     possible object is
     *     {@link LeggiProtocolloRequest }
     *     
     */
    public LeggiProtocolloRequest getLeggiProtocolloRequest() {
        return leggiProtocolloRequest;
    }

    /**
     * Sets the value of the leggiProtocolloRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link LeggiProtocolloRequest }
     *     
     */
    public void setLeggiProtocolloRequest(LeggiProtocolloRequest value) {
        this.leggiProtocolloRequest = value;
    }

}
