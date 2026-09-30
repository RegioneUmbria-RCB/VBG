
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
 *         &lt;element name="NumeraDeterminaStringResult" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "numeraDeterminaStringResult"
})
@XmlRootElement(name = "NumeraDeterminaStringResponse")
public class NumeraDeterminaStringResponse {

    @XmlElement(name = "NumeraDeterminaStringResult", required = true)
    protected String numeraDeterminaStringResult;

    /**
     * Gets the value of the numeraDeterminaStringResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeraDeterminaStringResult() {
        return numeraDeterminaStringResult;
    }

    /**
     * Sets the value of the numeraDeterminaStringResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeraDeterminaStringResult(String value) {
        this.numeraDeterminaStringResult = value;
    }

}
