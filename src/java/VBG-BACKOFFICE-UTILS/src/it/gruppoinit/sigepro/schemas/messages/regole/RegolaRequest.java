
package it.gruppoinit.sigepro.schemas.messages.regole;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RegolaRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RegolaRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nomeRegola" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="recuperaParametri" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="codiceComune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegolaRequest", propOrder = {
    "token",
    "software",
    "nomeRegola",
    "recuperaParametri",
    "codiceComune"
})
public class RegolaRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String software;
    @XmlElement(required = true)
    protected String nomeRegola;
    protected Boolean recuperaParametri;
    protected String codiceComune;

    /**
     * Recupera il valore della proprietà token.
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
     * Imposta il valore della proprietà token.
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
     * Recupera il valore della proprietà software.
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
     * Imposta il valore della proprietà software.
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
     * Recupera il valore della proprietà nomeRegola.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeRegola() {
        return nomeRegola;
    }

    /**
     * Imposta il valore della proprietà nomeRegola.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeRegola(String value) {
        this.nomeRegola = value;
    }

    /**
     * Recupera il valore della proprietà recuperaParametri.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRecuperaParametri() {
        return recuperaParametri;
    }

    /**
     * Imposta il valore della proprietà recuperaParametri.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRecuperaParametri(Boolean value) {
        this.recuperaParametri = value;
    }

    /**
     * Recupera il valore della proprietà codiceComune.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceComune() {
        return codiceComune;
    }

    /**
     * Imposta il valore della proprietà codiceComune.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceComune(String value) {
        this.codiceComune = value;
    }

}
