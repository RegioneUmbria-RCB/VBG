
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
 *         &lt;element name="ValidateFieldResult" type="{http://init.sigepro.it}ValidateSit" minOccurs="0"/>
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
    "validateFieldResult"
})
@XmlRootElement(name = "ValidateFieldResponse")
public class ValidateFieldResponse {

    @XmlElement(name = "ValidateFieldResult")
    protected ValidateSit validateFieldResult;

    /**
     * Gets the value of the validateFieldResult property.
     * 
     * @return
     *     possible object is
     *     {@link ValidateSit }
     *     
     */
    public ValidateSit getValidateFieldResult() {
        return validateFieldResult;
    }

    /**
     * Sets the value of the validateFieldResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidateSit }
     *     
     */
    public void setValidateFieldResult(ValidateSit value) {
        this.validateFieldResult = value;
    }

}
