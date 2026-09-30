
package it.init.sigepro.rte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.init.sigepro.rte.types.SportelloType;


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
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sportello" type="{http://sigepro.init.it/rte/types}SportelloType"/>
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
    "token",
    "sportello"
})
@XmlRootElement(name = "DirezioneSportelloRequest")
public class DirezioneSportelloRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected SportelloType sportello;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the sportello property.
     * 
     * @return
     *     possible object is
     *     {@link SportelloType }
     *     
     */
    public SportelloType getSportello() {
        return sportello;
    }

    /**
     * Sets the value of the sportello property.
     * 
     * @param value
     *     allowed object is
     *     {@link SportelloType }
     *     
     */
    public void setSportello(SportelloType value) {
        this.sportello = value;
    }

}
