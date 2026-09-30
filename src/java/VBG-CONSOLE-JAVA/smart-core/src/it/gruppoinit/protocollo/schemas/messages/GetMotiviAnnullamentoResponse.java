
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
 *         &lt;element name="GetMotiviAnnullamentoResult" type="{http://it.gruppoinit/Protocollazione}ListaMotiviAnnullamentoResponseType" minOccurs="0"/>
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
    "getMotiviAnnullamentoResult"
})
@XmlRootElement(name = "GetMotiviAnnullamentoResponse")
public class GetMotiviAnnullamentoResponse {

    @XmlElement(name = "GetMotiviAnnullamentoResult", nillable = true)
    protected ListaMotiviAnnullamentoResponseType getMotiviAnnullamentoResult;

    /**
     * Gets the value of the getMotiviAnnullamentoResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListaMotiviAnnullamentoResponseType }
     *     
     */
    public ListaMotiviAnnullamentoResponseType getGetMotiviAnnullamentoResult() {
        return getMotiviAnnullamentoResult;
    }

    /**
     * Sets the value of the getMotiviAnnullamentoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaMotiviAnnullamentoResponseType }
     *     
     */
    public void setGetMotiviAnnullamentoResult(ListaMotiviAnnullamentoResponseType value) {
        this.getMotiviAnnullamentoResult = value;
    }

}
