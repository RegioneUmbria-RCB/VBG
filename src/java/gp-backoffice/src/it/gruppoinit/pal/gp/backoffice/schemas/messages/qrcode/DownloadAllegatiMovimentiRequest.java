
package it.gruppoinit.pal.gp.backoffice.schemas.messages.qrcode;

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
 *         &lt;element name="codiceMovimento" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="tipoAuth" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "codiceMovimento",
    "tipoAuth",
    "token"
})
@XmlRootElement(name = "DownloadAllegatiMovimentiRequest")
public class DownloadAllegatiMovimentiRequest {

    @XmlElement(required = true)
    protected BigInteger codiceMovimento;
    @XmlElement(required = true)
    protected String tipoAuth;
    @XmlElement(required = true)
    protected String token;

    /**
     * Gets the value of the codiceMovimento property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodiceMovimento() {
        return codiceMovimento;
    }

    /**
     * Sets the value of the codiceMovimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodiceMovimento(BigInteger value) {
        this.codiceMovimento = value;
    }

    /**
     * Gets the value of the tipoAuth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAuth() {
        return tipoAuth;
    }

    /**
     * Sets the value of the tipoAuth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAuth(String value) {
        this.tipoAuth = value;
    }

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

}
