
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
 *         &lt;element name="getPersonaFisicaResult" type="{http://init.sigepro.it}Anagrafe" minOccurs="0"/>
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
    "getPersonaFisicaResult"
})
@XmlRootElement(name = "getPersonaFisicaResponse")
public class GetPersonaFisicaResponse {

    protected Anagrafe getPersonaFisicaResult;

    /**
     * Gets the value of the getPersonaFisicaResult property.
     * 
     * @return
     *     possible object is
     *     {@link Anagrafe }
     *     
     */
    public Anagrafe getGetPersonaFisicaResult() {
        return getPersonaFisicaResult;
    }

    /**
     * Sets the value of the getPersonaFisicaResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Anagrafe }
     *     
     */
    public void setGetPersonaFisicaResult(Anagrafe value) {
        this.getPersonaFisicaResult = value;
    }

}
