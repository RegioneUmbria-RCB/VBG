
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
 *         &lt;element name="GetClassificheResult" type="{http://it.gruppoinit/Protocollazione}ListaTipiClassificaType" minOccurs="0"/>
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
    "getClassificheResult"
})
@XmlRootElement(name = "GetClassificheResponse")
public class GetClassificheResponse {

    @XmlElement(name = "GetClassificheResult", nillable = true)
    protected ListaTipiClassificaType getClassificheResult;

    /**
     * Gets the value of the getClassificheResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListaTipiClassificaType }
     *     
     */
    public ListaTipiClassificaType getGetClassificheResult() {
        return getClassificheResult;
    }

    /**
     * Sets the value of the getClassificheResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaTipiClassificaType }
     *     
     */
    public void setGetClassificheResult(ListaTipiClassificaType value) {
        this.getClassificheResult = value;
    }

}
