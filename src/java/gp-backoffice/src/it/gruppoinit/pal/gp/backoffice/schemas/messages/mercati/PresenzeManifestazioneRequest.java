
package it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati;

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
 *         &lt;element name="codiceIstanza" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiceManifestazione" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiceUso" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="estremiAut" type="{http://gruppoinit.it/sigepro/schemas/messages/mercati}EstremiAut"/>
 *         &lt;element name="catMerc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inserisciAutSeNonTrovata" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlRootElement(name = "PresenzeManifestazioneRequest")
public class PresenzeManifestazioneRequest {

    @XmlElement(required = true)
    protected String token;
    protected Integer codiceIstanza;
    protected int codiceManifestazione;
    protected Integer codiceUso;
    @XmlElement(required = true)
    protected EstremiAut estremiAut;
    protected String catMerc;
    protected boolean inserisciAutSeNonTrovata;

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
     * Gets the value of the codiceIstanza property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiceIstanza() {
        return codiceIstanza;
    }

    /**
     * Sets the value of the codiceIstanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiceIstanza(Integer value) {
        this.codiceIstanza = value;
    }

    /**
     * Gets the value of the codiceManifestazione property.
     * 
     */
    public int getCodiceManifestazione() {
        return codiceManifestazione;
    }

    /**
     * Sets the value of the codiceManifestazione property.
     * 
     */
    public void setCodiceManifestazione(int value) {
        this.codiceManifestazione = value;
    }

    /**
     * Gets the value of the codiceUso property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiceUso() {
        return codiceUso;
    }

    /**
     * Sets the value of the codiceUso property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiceUso(Integer value) {
        this.codiceUso = value;
    }

    /**
     * Gets the value of the estremiAut property.
     * 
     * @return
     *     possible object is
     *     {@link EstremiAut }
     *     
     */
    public EstremiAut getEstremiAut() {
        return estremiAut;
    }

    /**
     * Sets the value of the estremiAut property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiAut }
     *     
     */
    public void setEstremiAut(EstremiAut value) {
        this.estremiAut = value;
    }

    /**
     * Gets the value of the catMerc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatMerc() {
        return catMerc;
    }

    /**
     * Sets the value of the catMerc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatMerc(String value) {
        this.catMerc = value;
    }

    /**
     * Gets the value of the inserisciAutSeNonTrovata property.
     * 
     */
    public boolean isInserisciAutSeNonTrovata() {
        return inserisciAutSeNonTrovata;
    }

    /**
     * Sets the value of the inserisciAutSeNonTrovata property.
     * 
     */
    public void setInserisciAutSeNonTrovata(boolean value) {
        this.inserisciAutSeNonTrovata = value;
    }

}
