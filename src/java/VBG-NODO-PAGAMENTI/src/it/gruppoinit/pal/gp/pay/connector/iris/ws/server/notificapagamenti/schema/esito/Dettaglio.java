
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.esito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Dettaglio complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Dettaglio"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/&gt;
 *         &lt;element name="Stato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}StatoDettaglio"/&gt;
 *         &lt;element name="Esiti" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}Esiti" minOccurs="0"/&gt;
 *         &lt;element name="Note" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max2000Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dettaglio", propOrder = {
    "id",
    "stato",
    "esiti",
    "note"
})
public class Dettaglio {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "Stato", required = true)
    @XmlSchemaType(name = "string")
    protected StatoDettaglio stato;
    @XmlElement(name = "Esiti")
    protected Esiti esiti;
    @XmlElement(name = "Note")
    protected String note;

    /**
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return
     *     possible object is
     *     {@link StatoDettaglio }
     *     
     */
    public StatoDettaglio getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoDettaglio }
     *     
     */
    public void setStato(StatoDettaglio value) {
        this.stato = value;
    }

    /**
     * Recupera il valore della proprietà esiti.
     * 
     * @return
     *     possible object is
     *     {@link Esiti }
     *     
     */
    public Esiti getEsiti() {
        return esiti;
    }

    /**
     * Imposta il valore della proprietà esiti.
     * 
     * @param value
     *     allowed object is
     *     {@link Esiti }
     *     
     */
    public void setEsiti(Esiti value) {
        this.esiti = value;
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

}
