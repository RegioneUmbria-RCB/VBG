
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.include;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Allegato complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Allegato"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Titolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Codifica" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}MIMETypeCode"/&gt;
 *         &lt;element name="Contenuto" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="IdAntifalsificazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Tipo" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}ContentType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Allegato", propOrder = {
    "titolo",
    "codifica",
    "contenuto",
    "idAntifalsificazione"
})
public class Allegato {

    @XmlElement(name = "Titolo")
    protected String titolo;
    @XmlElement(name = "Codifica", required = true)
    @XmlSchemaType(name = "string")
    protected MIMETypeCode codifica;
    @XmlElement(name = "Contenuto", required = true)
    protected byte[] contenuto;
    @XmlElement(name = "IdAntifalsificazione")
    protected String idAntifalsificazione;
    @XmlAttribute(name = "Tipo", required = true)
    protected ContentType tipo;

    /**
     * Recupera il valore della proprietà titolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta il valore della proprietà titolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitolo(String value) {
        this.titolo = value;
    }

    /**
     * Recupera il valore della proprietà codifica.
     * 
     * @return
     *     possible object is
     *     {@link MIMETypeCode }
     *     
     */
    public MIMETypeCode getCodifica() {
        return codifica;
    }

    /**
     * Imposta il valore della proprietà codifica.
     * 
     * @param value
     *     allowed object is
     *     {@link MIMETypeCode }
     *     
     */
    public void setCodifica(MIMETypeCode value) {
        this.codifica = value;
    }

    /**
     * Recupera il valore della proprietà contenuto.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContenuto() {
        return contenuto;
    }

    /**
     * Imposta il valore della proprietà contenuto.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContenuto(byte[] value) {
        this.contenuto = value;
    }

    /**
     * Recupera il valore della proprietà idAntifalsificazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAntifalsificazione() {
        return idAntifalsificazione;
    }

    /**
     * Imposta il valore della proprietà idAntifalsificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAntifalsificazione(String value) {
        this.idAntifalsificazione = value;
    }

    /**
     * Recupera il valore della proprietà tipo.
     * 
     * @return
     *     possible object is
     *     {@link ContentType }
     *     
     */
    public ContentType getTipo() {
        return tipo;
    }

    /**
     * Imposta il valore della proprietà tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentType }
     *     
     */
    public void setTipo(ContentType value) {
        this.tipo = value;
    }

}
