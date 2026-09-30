
package it.gruppoinit.wssit;

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
 *         &lt;element name="GetCampiGestitiResult" type="{http://init.sigepro.it}ArrayOfString" minOccurs="0"/>
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
    "getCampiGestitiResult"
})
@XmlRootElement(name = "GetCampiGestitiResponse")
public class GetCampiGestitiResponse {

    @XmlElement(name = "GetCampiGestitiResult")
    protected ArrayOfString getCampiGestitiResult;

    /**
     * Gets the value of the getCampiGestitiResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getGetCampiGestitiResult() {
        return getCampiGestitiResult;
    }

    /**
     * Sets the value of the getCampiGestitiResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setGetCampiGestitiResult(ArrayOfString value) {
        this.getCampiGestitiResult = value;
    }

}
