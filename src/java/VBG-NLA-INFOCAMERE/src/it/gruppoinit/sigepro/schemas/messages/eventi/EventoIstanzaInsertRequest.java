
package it.gruppoinit.sigepro.schemas.messages.eventi;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.sigepro.schemas.messages.base.CategorieEventiBaseType;


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
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="categoriaEvento" type="{http://gruppoinit.it/sigepro/schemas/messages/base}CategorieEventiBaseType" minOccurs="0"/&gt;
 *         &lt;element name="codiceistanza" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="messaggio"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="0"/&gt;
 *               &lt;maxLength value="4000"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
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
    "token",
    "categoriaEvento",
    "codiceistanza",
    "messaggio"
})
@XmlRootElement(name = "EventoIstanzaInsertRequest")
public class EventoIstanzaInsertRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlSchemaType(name = "string")
    protected CategorieEventiBaseType categoriaEvento;
    @XmlElement(required = true)
    protected BigInteger codiceistanza;
    @XmlElement(required = true)
    protected String messaggio;

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
     * Recupera il valore della proprietà categoriaEvento.
     * 
     * @return
     *     possible object is
     *     {@link CategorieEventiBaseType }
     *     
     */
    public CategorieEventiBaseType getCategoriaEvento() {
        return categoriaEvento;
    }

    /**
     * Imposta il valore della proprietà categoriaEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link CategorieEventiBaseType }
     *     
     */
    public void setCategoriaEvento(CategorieEventiBaseType value) {
        this.categoriaEvento = value;
    }

    /**
     * Recupera il valore della proprietà codiceistanza.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodiceistanza() {
        return codiceistanza;
    }

    /**
     * Imposta il valore della proprietà codiceistanza.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodiceistanza(BigInteger value) {
        this.codiceistanza = value;
    }

    /**
     * Recupera il valore della proprietà messaggio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * Imposta il valore della proprietà messaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessaggio(String value) {
        this.messaggio = value;
    }

}
