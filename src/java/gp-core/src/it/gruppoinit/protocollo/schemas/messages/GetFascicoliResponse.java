
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
 *         &lt;element name="GetFascicoliResult" type="{http://it.gruppoinit/Protocollazione}ListaFascicoliResponseType" minOccurs="0"/>
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
    "getFascicoliResult"
})
@XmlRootElement(name = "GetFascicoliResponse")
public class GetFascicoliResponse {

    @XmlElement(name = "GetFascicoliResult", nillable = true)
    protected ListaFascicoliResponseType getFascicoliResult;

    /**
     * Gets the value of the getFascicoliResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListaFascicoliResponseType }
     *     
     */
    public ListaFascicoliResponseType getGetFascicoliResult() {
        return getFascicoliResult;
    }

    /**
     * Sets the value of the getFascicoliResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaFascicoliResponseType }
     *     
     */
    public void setGetFascicoliResult(ListaFascicoliResponseType value) {
        this.getFascicoliResult = value;
    }

}
