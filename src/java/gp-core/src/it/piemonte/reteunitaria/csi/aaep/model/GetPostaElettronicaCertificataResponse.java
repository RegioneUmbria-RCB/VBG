
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="getPostaElettronicaCertificataReturn" type="{urn:AAEPCSI}PostaCertificata" minOccurs="0"/>
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
    "getPostaElettronicaCertificataReturn"
})
@XmlRootElement(name = "getPostaElettronicaCertificataResponse")
public class GetPostaElettronicaCertificataResponse {

    protected PostaCertificata getPostaElettronicaCertificataReturn;

    /**
     * Gets the value of the getPostaElettronicaCertificataReturn property.
     * 
     * @return
     *     possible object is
     *     {@link PostaCertificata }
     *     
     */
    public PostaCertificata getGetPostaElettronicaCertificataReturn() {
        return getPostaElettronicaCertificataReturn;
    }

    /**
     * Sets the value of the getPostaElettronicaCertificataReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostaCertificata }
     *     
     */
    public void setGetPostaElettronicaCertificataReturn(PostaCertificata value) {
        this.getPostaElettronicaCertificataReturn = value;
    }

}
