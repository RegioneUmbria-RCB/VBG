
package it.gruppoinit.ws.wsatti;

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
 *         &lt;element name="InserisciDeterminaStringResult" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "inserisciDeterminaStringResult"
})
@XmlRootElement(name = "InserisciDeterminaStringResponse")
public class InserisciDeterminaStringResponse {

    @XmlElement(name = "InserisciDeterminaStringResult", required = true)
    protected String inserisciDeterminaStringResult;

    /**
     * Gets the value of the inserisciDeterminaStringResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInserisciDeterminaStringResult() {
        return inserisciDeterminaStringResult;
    }

    /**
     * Sets the value of the inserisciDeterminaStringResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInserisciDeterminaStringResult(String value) {
        this.inserisciDeterminaStringResult = value;
    }

}
