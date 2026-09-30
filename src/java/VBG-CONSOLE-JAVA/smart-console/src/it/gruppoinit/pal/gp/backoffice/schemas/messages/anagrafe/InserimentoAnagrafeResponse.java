
package it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe;

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
 *       &lt;all>
 *         &lt;element name="riferimentiAnagrafe" type="{http://gruppoinit.it/sigepro/schemas/messages/anagrafe}RiferimentiAnagrafeType"/>
 *         &lt;element name="errori" type="{http://gruppoinit.it/sigepro/schemas/messages/anagrafe}ErroreType" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "InserimentoAnagrafeResponse")
public class InserimentoAnagrafeResponse {

    @XmlElement(required = true)
    protected RiferimentiAnagrafeType riferimentiAnagrafe;
    protected ErroreType errori;

    /**
     * Gets the value of the riferimentiAnagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentiAnagrafeType }
     *     
     */
    public RiferimentiAnagrafeType getRiferimentiAnagrafe() {
        return riferimentiAnagrafe;
    }

    /**
     * Sets the value of the riferimentiAnagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentiAnagrafeType }
     *     
     */
    public void setRiferimentiAnagrafe(RiferimentiAnagrafeType value) {
        this.riferimentiAnagrafe = value;
    }

    /**
     * Gets the value of the errori property.
     * 
     * @return
     *     possible object is
     *     {@link ErroreType }
     *     
     */
    public ErroreType getErrori() {
        return errori;
    }

    /**
     * Sets the value of the errori property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErroreType }
     *     
     */
    public void setErrori(ErroreType value) {
        this.errori = value;
    }

}
