
package it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice;

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
 *         &lt;element name="albero" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}Albero"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="aggiorna" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "albero",
    "software",
    "token",
    "aggiorna"
})
@XmlRootElement(name = "AlberoRequest")
public class AlberoRequest {

    @XmlElement(required = true)
    protected Albero albero;
    @XmlElement(required = true)
    protected String software;
    @XmlElement(required = true)
    protected String token;
    protected boolean aggiorna;

    /**
     * Gets the value of the albero property.
     * 
     * @return
     *     possible object is
     *     {@link Albero }
     *     
     */
    public Albero getAlbero() {
        return albero;
    }

    /**
     * Sets the value of the albero property.
     * 
     * @param value
     *     allowed object is
     *     {@link Albero }
     *     
     */
    public void setAlbero(Albero value) {
        this.albero = value;
    }

    /**
     * Gets the value of the software property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftware() {
        return software;
    }

    /**
     * Sets the value of the software property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftware(String value) {
        this.software = value;
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

    /**
     * Gets the value of the aggiorna property.
     * 
     */
    public boolean isAggiorna() {
        return aggiorna;
    }

    /**
     * Sets the value of the aggiorna property.
     * 
     */
    public void setAggiorna(boolean value) {
        this.aggiorna = value;
    }

}
