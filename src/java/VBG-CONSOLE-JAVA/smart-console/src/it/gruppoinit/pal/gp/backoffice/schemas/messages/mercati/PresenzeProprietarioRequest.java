
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
 *         &lt;element name="codIstanza" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="estremiAut" type="{http://gruppoinit.it/sigepro/schemas/messages/mercati}EstremiAut"/>
 *         &lt;element name="catMerc" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlRootElement(name = "PresenzeProprietarioRequest")
public class PresenzeProprietarioRequest {

    @XmlElement(required = true)
    protected String token;
    protected int codIstanza;
    @XmlElement(required = true)
    protected EstremiAut estremiAut;
    @XmlElement(required = true)
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
     * Gets the value of the codIstanza property.
     * 
     */
    public int getCodIstanza() {
        return codIstanza;
    }

    /**
     * Sets the value of the codIstanza property.
     * 
     */
    public void setCodIstanza(int value) {
        this.codIstanza = value;
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
