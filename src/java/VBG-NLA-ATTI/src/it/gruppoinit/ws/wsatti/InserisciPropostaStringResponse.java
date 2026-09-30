
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
 *         &lt;element name="InserisciPropostaStringResult" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "inserisciPropostaStringResult"
})
@XmlRootElement(name = "InserisciPropostaStringResponse")
public class InserisciPropostaStringResponse {

    @XmlElement(name = "InserisciPropostaStringResult", required = true)
    protected String inserisciPropostaStringResult;

    /**
     * Gets the value of the inserisciPropostaStringResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInserisciPropostaStringResult() {
        return inserisciPropostaStringResult;
    }

    /**
     * Sets the value of the inserisciPropostaStringResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInserisciPropostaStringResult(String value) {
        this.inserisciPropostaStringResult = value;
    }

}
