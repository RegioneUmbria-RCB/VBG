
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
 *         &lt;element name="AttoInseritoOut" type="{http://tempuri.org/}AttoInseritoOut"/>
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
    "attoInseritoOut"
})
@XmlRootElement(name = "InserisciPropostaResponse")
public class InserisciPropostaResponse {

    @XmlElement(name = "AttoInseritoOut", required = true)
    protected AttoInseritoOut attoInseritoOut;

    /**
     * Gets the value of the attoInseritoOut property.
     * 
     * @return
     *     possible object is
     *     {@link AttoInseritoOut }
     *     
     */
    public AttoInseritoOut getAttoInseritoOut() {
        return attoInseritoOut;
    }

    /**
     * Sets the value of the attoInseritoOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttoInseritoOut }
     *     
     */
    public void setAttoInseritoOut(AttoInseritoOut value) {
        this.attoInseritoOut = value;
    }

}
