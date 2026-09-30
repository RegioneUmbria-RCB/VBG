
package it.gruppoinit.sigeprosecurity.schema;

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
 *         &lt;element name="valid" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="tokenInfo" type="{http://sigeprosecurity.gruppoinit.it/schema}TokenInfoType" minOccurs="0"/>
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
    "valid",
    "tokenInfo"
})
@XmlRootElement(name = "CheckTokenResponse")
public class CheckTokenResponse {

    protected boolean valid;
    protected TokenInfoType tokenInfo;

    /**
     * Gets the value of the valid property.
     * 
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Sets the value of the valid property.
     * 
     */
    public void setValid(boolean value) {
        this.valid = value;
    }

    /**
     * Gets the value of the tokenInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TokenInfoType }
     *     
     */
    public TokenInfoType getTokenInfo() {
        return tokenInfo;
    }

    /**
     * Sets the value of the tokenInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TokenInfoType }
     *     
     */
    public void setTokenInfo(TokenInfoType value) {
        this.tokenInfo = value;
    }

}
