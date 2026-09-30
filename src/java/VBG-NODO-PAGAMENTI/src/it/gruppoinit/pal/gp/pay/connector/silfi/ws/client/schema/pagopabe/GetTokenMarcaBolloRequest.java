
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceEnte"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="hashDocumento"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="70"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagCfPiva"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="16"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagDenominazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="anagNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="anagProvincia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="urlRitorno" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codiceEnte",
    "importo",
    "hashDocumento",
    "anagCfPiva",
    "anagDenominazione",
    "anagNaturaGiuridica",
    "anagProvincia",
    "urlRitorno"
})
@XmlRootElement(name = "getTokenMarcaBolloRequest")
public class GetTokenMarcaBolloRequest {

    @XmlElement(required = true)
    protected String codiceEnte;
    @XmlElement(required = true)
    protected BigDecimal importo;
    @XmlElement(required = true)
    protected String hashDocumento;
    @XmlElement(required = true)
    protected String anagCfPiva;
    @XmlElement(required = true)
    protected String anagDenominazione;
    @XmlElement(required = true)
    protected String anagNaturaGiuridica;
    @XmlElement(required = true)
    protected String anagProvincia;
    @XmlElement(required = true)
    protected String urlRitorno;

    /**
     * Recupera il valore della proprietà codiceEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEnte() {
        return codiceEnte;
    }

    /**
     * Imposta il valore della proprietà codiceEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEnte(String value) {
        this.codiceEnte = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

    /**
     * Recupera il valore della proprietà hashDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashDocumento() {
        return hashDocumento;
    }

    /**
     * Imposta il valore della proprietà hashDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashDocumento(String value) {
        this.hashDocumento = value;
    }

    /**
     * Recupera il valore della proprietà anagCfPiva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagCfPiva() {
        return anagCfPiva;
    }

    /**
     * Imposta il valore della proprietà anagCfPiva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagCfPiva(String value) {
        this.anagCfPiva = value;
    }

    /**
     * Recupera il valore della proprietà anagDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagDenominazione() {
        return anagDenominazione;
    }

    /**
     * Imposta il valore della proprietà anagDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagDenominazione(String value) {
        this.anagDenominazione = value;
    }

    /**
     * Recupera il valore della proprietà anagNaturaGiuridica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagNaturaGiuridica() {
        return anagNaturaGiuridica;
    }

    /**
     * Imposta il valore della proprietà anagNaturaGiuridica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagNaturaGiuridica(String value) {
        this.anagNaturaGiuridica = value;
    }

    /**
     * Recupera il valore della proprietà anagProvincia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagProvincia() {
        return anagProvincia;
    }

    /**
     * Imposta il valore della proprietà anagProvincia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagProvincia(String value) {
        this.anagProvincia = value;
    }

    /**
     * Recupera il valore della proprietà urlRitorno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlRitorno() {
        return urlRitorno;
    }

    /**
     * Imposta il valore della proprietà urlRitorno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlRitorno(String value) {
        this.urlRitorno = value;
    }

}
