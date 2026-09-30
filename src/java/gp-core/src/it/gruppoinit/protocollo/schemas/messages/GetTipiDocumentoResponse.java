
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
 *         &lt;element name="GetTipiDocumentoResult" type="{http://it.gruppoinit/Protocollazione}ListaTipiDocumentoResponseType" minOccurs="0"/>
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
    "getTipiDocumentoResult"
})
@XmlRootElement(name = "GetTipiDocumentoResponse")
public class GetTipiDocumentoResponse {

    @XmlElement(name = "GetTipiDocumentoResult", nillable = true)
    protected ListaTipiDocumentoResponseType getTipiDocumentoResult;

    /**
     * Gets the value of the getTipiDocumentoResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListaTipiDocumentoResponseType }
     *     
     */
    public ListaTipiDocumentoResponseType getGetTipiDocumentoResult() {
        return getTipiDocumentoResult;
    }

    /**
     * Sets the value of the getTipiDocumentoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaTipiDocumentoResponseType }
     *     
     */
    public void setGetTipiDocumentoResult(ListaTipiDocumentoResponseType value) {
        this.getTipiDocumentoResult = value;
    }

}
