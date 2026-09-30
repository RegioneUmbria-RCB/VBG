
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.TipoOperazione;


/**
 * <p>Classe Java per HeadPendenza complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="HeadPendenza"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdPendenza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Mittente" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Mittente"/&gt;
 *         &lt;element name="Destinatari" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Destinatari"/&gt;
 *         &lt;element name="CartellaDiPagamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="TipoPendenza" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="TipoOperazione" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}TipoOperazione" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeadPendenza", propOrder = {
    "idPendenza",
    "mittente",
    "destinatari",
    "cartellaDiPagamento",
    "note"
})
@XmlSeeAlso({
    Pendenza.class
})
public class HeadPendenza {

    @XmlElement(name = "IdPendenza", required = true)
    protected String idPendenza;
    @XmlElement(name = "Mittente", required = true)
    protected Mittente mittente;
    @XmlElement(name = "Destinatari", required = true)
    protected Destinatari destinatari;
    @XmlElement(name = "CartellaDiPagamento")
    protected Boolean cartellaDiPagamento;
    @XmlElement(name = "Note")
    protected String note;
    @XmlAttribute(name = "TipoPendenza", required = true)
    protected String tipoPendenza;
    @XmlAttribute(name = "TipoOperazione", required = true)
    protected TipoOperazione tipoOperazione;

    /**
     * Recupera il valore della proprietà idPendenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPendenza() {
        return idPendenza;
    }

    /**
     * Imposta il valore della proprietà idPendenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPendenza(String value) {
        this.idPendenza = value;
    }

    /**
     * Recupera il valore della proprietà mittente.
     * 
     * @return
     *     possible object is
     *     {@link Mittente }
     *     
     */
    public Mittente getMittente() {
        return mittente;
    }

    /**
     * Imposta il valore della proprietà mittente.
     * 
     * @param value
     *     allowed object is
     *     {@link Mittente }
     *     
     */
    public void setMittente(Mittente value) {
        this.mittente = value;
    }

    /**
     * Recupera il valore della proprietà destinatari.
     * 
     * @return
     *     possible object is
     *     {@link Destinatari }
     *     
     */
    public Destinatari getDestinatari() {
        return destinatari;
    }

    /**
     * Imposta il valore della proprietà destinatari.
     * 
     * @param value
     *     allowed object is
     *     {@link Destinatari }
     *     
     */
    public void setDestinatari(Destinatari value) {
        this.destinatari = value;
    }

    /**
     * Recupera il valore della proprietà cartellaDiPagamento.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCartellaDiPagamento() {
        return cartellaDiPagamento;
    }

    /**
     * Imposta il valore della proprietà cartellaDiPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCartellaDiPagamento(Boolean value) {
        this.cartellaDiPagamento = value;
    }

    /**
     * Recupera il valore della proprietà note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della proprietà note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Recupera il valore della proprietà tipoPendenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPendenza() {
        return tipoPendenza;
    }

    /**
     * Imposta il valore della proprietà tipoPendenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPendenza(String value) {
        this.tipoPendenza = value;
    }

    /**
     * Recupera il valore della proprietà tipoOperazione.
     * 
     * @return
     *     possible object is
     *     {@link TipoOperazione }
     *     
     */
    public TipoOperazione getTipoOperazione() {
        return tipoOperazione;
    }

    /**
     * Imposta il valore della proprietà tipoOperazione.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoOperazione }
     *     
     */
    public void setTipoOperazione(TipoOperazione value) {
        this.tipoOperazione = value;
    }

}
