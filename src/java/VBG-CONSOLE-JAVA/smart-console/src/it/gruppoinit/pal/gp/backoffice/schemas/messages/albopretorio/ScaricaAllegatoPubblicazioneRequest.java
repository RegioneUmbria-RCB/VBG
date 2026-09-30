
package it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio;

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
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codicePubblicazione" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiceOggetto" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlRootElement(name = "ScaricaAllegatoPubblicazioneRequest")
public class ScaricaAllegatoPubblicazioneRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String software;
    protected int codicePubblicazione;
    protected int codiceOggetto;

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
     * Gets the value of the codicePubblicazione property.
     * 
     */
    public int getCodicePubblicazione() {
        return codicePubblicazione;
    }

    /**
     * Sets the value of the codicePubblicazione property.
     * 
     */
    public void setCodicePubblicazione(int value) {
        this.codicePubblicazione = value;
    }

    /**
     * Gets the value of the codiceOggetto property.
     * 
     */
    public int getCodiceOggetto() {
        return codiceOggetto;
    }

    /**
     * Sets the value of the codiceOggetto property.
     * 
     */
    public void setCodiceOggetto(int value) {
        this.codiceOggetto = value;
    }

}
