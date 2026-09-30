
package it.gruppoinit.wsanagrafe2.schema;

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
 *         &lt;element name="getPersonaGiuridicaResult" type="{http://init.sigepro.it}Anagrafe" minOccurs="0"/>
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
    "getPersonaGiuridicaResult"
})
@XmlRootElement(name = "getPersonaGiuridicaResponse")
public class GetPersonaGiuridicaResponse {

    protected Anagrafe getPersonaGiuridicaResult;

    /**
     * Gets the value of the getPersonaGiuridicaResult property.
     * 
     * @return
     *     possible object is
     *     {@link Anagrafe }
     *     
     */
    public Anagrafe getGetPersonaGiuridicaResult() {
        return getPersonaGiuridicaResult;
    }

    /**
     * Sets the value of the getPersonaGiuridicaResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Anagrafe }
     *     
     */
    public void setGetPersonaGiuridicaResult(Anagrafe value) {
        this.getPersonaGiuridicaResult = value;
    }

}
