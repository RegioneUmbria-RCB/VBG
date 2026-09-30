
package it.gruppoinit.sigepro.schemas.messages.regole;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ComuniESoftwarePerRegolaRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ComuniESoftwarePerRegolaRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nomeRegola" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nomeParametro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComuniESoftwarePerRegolaRequest", propOrder = {
    "token",
    "nomeRegola",
    "nomeParametro"
})
public class ComuniESoftwarePerRegolaRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String nomeRegola;
    protected String nomeParametro;

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
     * Recupera il valore della proprietà nomeParametro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeParametro() {
        return nomeParametro;
    }

    /**
     * Imposta il valore della proprietà nomeParametro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeParametro(String value) {
        this.nomeParametro = value;
    }

}
