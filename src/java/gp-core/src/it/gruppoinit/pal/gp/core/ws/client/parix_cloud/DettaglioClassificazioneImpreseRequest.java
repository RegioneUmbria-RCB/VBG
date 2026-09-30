
package it.gruppoinit.pal.gp.core.ws.client.parix_cloud;

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
 *         &lt;element name="cciaa_regz" type="{http://parixgate.infocamere.it/services/gate/types}string2"/>
 *         &lt;element name="n_iscrizione_rea" type="{http://parixgate.infocamere.it/services/gate/types}int9"/>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "cciaaRegz",
    "nIscrizioneRea",
    "user",
    "password"
})
@XmlRootElement(name = "DettaglioClassificazioneImpreseRequest")
public class DettaglioClassificazioneImpreseRequest {

    @XmlElement(name = "cciaa_regz", required = true)
    protected String cciaaRegz;
    @XmlElement(name = "n_iscrizione_rea")
    protected int nIscrizioneRea;
    protected String user;
    protected String password;

    /**
     * Gets the value of the cciaaRegz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCciaaRegz() {
        return cciaaRegz;
    }

    /**
     * Sets the value of the cciaaRegz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCciaaRegz(String value) {
        this.cciaaRegz = value;
    }

    /**
     * Gets the value of the nIscrizioneRea property.
     * 
     */
    public int getNIscrizioneRea() {
        return nIscrizioneRea;
    }

    /**
     * Sets the value of the nIscrizioneRea property.
     * 
     */
    public void setNIscrizioneRea(int value) {
        this.nIscrizioneRea = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

}
