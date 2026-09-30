
package it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RiferimentiAnagrafeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RiferimentiAnagrafeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceanagrafe" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="idcomune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiferimentiAnagrafeType", propOrder = {
    "codiceanagrafe",
    "idcomune"
})
public class RiferimentiAnagrafeType {

    @XmlElement(required = true)
    protected BigInteger codiceanagrafe;
    @XmlElement(required = true)
    protected String idcomune;

    /**
     * Gets the value of the codiceanagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodiceanagrafe() {
        return codiceanagrafe;
    }

    /**
     * Sets the value of the codiceanagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodiceanagrafe(BigInteger value) {
        this.codiceanagrafe = value;
    }

    /**
     * Gets the value of the idcomune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdcomune() {
        return idcomune;
    }

    /**
     * Sets the value of the idcomune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdcomune(String value) {
        this.idcomune = value;
    }

}
