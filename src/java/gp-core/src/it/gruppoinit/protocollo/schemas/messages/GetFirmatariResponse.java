
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
 *         &lt;element name="GetFirmatariResult" type="{http://it.gruppoinit/Protocollazione}ListaFirmatari" minOccurs="0"/>
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
    "getFirmatariResult"
})
@XmlRootElement(name = "GetFirmatariResponse")
public class GetFirmatariResponse {

    @XmlElement(name = "GetFirmatariResult", nillable = true)
    protected ListaFirmatari getFirmatariResult;

    /**
     * Gets the value of the getFirmatariResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListaFirmatari }
     *     
     */
    public ListaFirmatari getGetFirmatariResult() {
        return getFirmatariResult;
    }

    /**
     * Sets the value of the getFirmatariResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaFirmatari }
     *     
     */
    public void setGetFirmatariResult(ListaFirmatari value) {
        this.getFirmatariResult = value;
    }

}
