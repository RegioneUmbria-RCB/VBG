
package it.gruppoinit.pal.gp.backoffice.schemas.messages.movimenti;

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
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codicemovimento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="uuid_istanza" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "codicemovimento",
    "uuidIstanza"
})
@XmlRootElement(name = "MovimentiDownloadZipLogicoRequest")
public class MovimentiDownloadZipLogicoRequest {

    @XmlElement(required = true)
    protected String token;
    protected int codicemovimento;
    @XmlElement(name = "uuid_istanza", required = true)
    protected String uuidIstanza;

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
     * Gets the value of the codicemovimento property.
     * 
     */
    public int getCodicemovimento() {
        return codicemovimento;
    }

    /**
     * Sets the value of the codicemovimento property.
     * 
     */
    public void setCodicemovimento(int value) {
        this.codicemovimento = value;
    }

    /**
     * Gets the value of the uuidIstanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuidIstanza() {
        return uuidIstanza;
    }

    /**
     * Sets the value of the uuidIstanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuidIstanza(String value) {
        this.uuidIstanza = value;
    }

}
