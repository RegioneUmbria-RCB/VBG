
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
 *         &lt;element name="AggiungiAllegatiStringResult" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "aggiungiAllegatiStringResult"
})
@XmlRootElement(name = "AggiungiAllegatiStringResponse")
public class AggiungiAllegatiStringResponse {

    @XmlElement(name = "AggiungiAllegatiStringResult", required = true)
    protected String aggiungiAllegatiStringResult;

    /**
     * Gets the value of the aggiungiAllegatiStringResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAggiungiAllegatiStringResult() {
        return aggiungiAllegatiStringResult;
    }

    /**
     * Sets the value of the aggiungiAllegatiStringResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAggiungiAllegatiStringResult(String value) {
        this.aggiungiAllegatiStringResult = value;
    }

}
