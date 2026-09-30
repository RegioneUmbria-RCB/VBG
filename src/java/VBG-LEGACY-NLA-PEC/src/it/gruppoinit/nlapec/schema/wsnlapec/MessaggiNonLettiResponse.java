
package it.gruppoinit.nlapec.schema.wsnlapec;

import java.math.BigInteger;
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
 *         &lt;element name="proprietarioCasella" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numMessaggi" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idaccount" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
    "proprietarioCasella",
    "numMessaggi",
    "idaccount"
})
@XmlRootElement(name = "MessaggiNonLettiResponse")
public class MessaggiNonLettiResponse {

    @XmlElement(required = true)
    protected String proprietarioCasella;
    protected int numMessaggi;
    protected BigInteger idaccount;

    /**
     * Gets the value of the proprietarioCasella property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProprietarioCasella() {
        return proprietarioCasella;
    }

    /**
     * Sets the value of the proprietarioCasella property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProprietarioCasella(String value) {
        this.proprietarioCasella = value;
    }

    /**
     * Gets the value of the numMessaggi property.
     * 
     */
    public int getNumMessaggi() {
        return numMessaggi;
    }

    /**
     * Sets the value of the numMessaggi property.
     * 
     */
    public void setNumMessaggi(int value) {
        this.numMessaggi = value;
    }

    /**
     * Gets the value of the idaccount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdaccount() {
        return idaccount;
    }

    /**
     * Sets the value of the idaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdaccount(BigInteger value) {
        this.idaccount = value;
    }

}
