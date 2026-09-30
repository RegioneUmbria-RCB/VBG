
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per VerificaStatoRuoloResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VerificaStatoRuoloResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="StatoRuolo" type="{http://entranext.it/}StatoRuolo"/&gt;
 *         &lt;element name="Annullato" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PosizioniApprovate" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ImportoPosizioniApprovate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="PosizioniAnnullate" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ImportoPosizioniAnnullate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="PosizioniTotali" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ImportoPosizioniTotali" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerificaStatoRuoloResponse", propOrder = {
    "statoRuolo",
    "annullato",
    "posizioniApprovate",
    "importoPosizioniApprovate",
    "posizioniAnnullate",
    "importoPosizioniAnnullate",
    "posizioniTotali",
    "importoPosizioniTotali"
})
public class VerificaStatoRuoloResponse
    extends LinkNextResponse
{

    @XmlElement(name = "StatoRuolo", required = true)
    @XmlSchemaType(name = "string")
    protected StatoRuolo statoRuolo;
    @XmlElement(name = "Annullato")
    protected boolean annullato;
    @XmlElement(name = "PosizioniApprovate")
    protected int posizioniApprovate;
    @XmlElement(name = "ImportoPosizioniApprovate", required = true)
    protected BigDecimal importoPosizioniApprovate;
    @XmlElement(name = "PosizioniAnnullate")
    protected int posizioniAnnullate;
    @XmlElement(name = "ImportoPosizioniAnnullate", required = true)
    protected BigDecimal importoPosizioniAnnullate;
    @XmlElement(name = "PosizioniTotali")
    protected int posizioniTotali;
    @XmlElement(name = "ImportoPosizioniTotali", required = true)
    protected BigDecimal importoPosizioniTotali;

    /**
     * Recupera il valore della proprietà statoRuolo.
     * 
     * @return
     *     possible object is
     *     {@link StatoRuolo }
     *     
     */
    public StatoRuolo getStatoRuolo() {
        return statoRuolo;
    }

    /**
     * Imposta il valore della proprietà statoRuolo.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoRuolo }
     *     
     */
    public void setStatoRuolo(StatoRuolo value) {
        this.statoRuolo = value;
    }

    /**
     * Recupera il valore della proprietà annullato.
     * 
     */
    public boolean isAnnullato() {
        return annullato;
    }

    /**
     * Imposta il valore della proprietà annullato.
     * 
     */
    public void setAnnullato(boolean value) {
        this.annullato = value;
    }

    /**
     * Recupera il valore della proprietà posizioniApprovate.
     * 
     */
    public int getPosizioniApprovate() {
        return posizioniApprovate;
    }

    /**
     * Imposta il valore della proprietà posizioniApprovate.
     * 
     */
    public void setPosizioniApprovate(int value) {
        this.posizioniApprovate = value;
    }

    /**
     * Recupera il valore della proprietà importoPosizioniApprovate.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoPosizioniApprovate() {
        return importoPosizioniApprovate;
    }

    /**
     * Imposta il valore della proprietà importoPosizioniApprovate.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoPosizioniApprovate(BigDecimal value) {
        this.importoPosizioniApprovate = value;
    }

    /**
     * Recupera il valore della proprietà posizioniAnnullate.
     * 
     */
    public int getPosizioniAnnullate() {
        return posizioniAnnullate;
    }

    /**
     * Imposta il valore della proprietà posizioniAnnullate.
     * 
     */
    public void setPosizioniAnnullate(int value) {
        this.posizioniAnnullate = value;
    }

    /**
     * Recupera il valore della proprietà importoPosizioniAnnullate.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoPosizioniAnnullate() {
        return importoPosizioniAnnullate;
    }

    /**
     * Imposta il valore della proprietà importoPosizioniAnnullate.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoPosizioniAnnullate(BigDecimal value) {
        this.importoPosizioniAnnullate = value;
    }

    /**
     * Recupera il valore della proprietà posizioniTotali.
     * 
     */
    public int getPosizioniTotali() {
        return posizioniTotali;
    }

    /**
     * Imposta il valore della proprietà posizioniTotali.
     * 
     */
    public void setPosizioniTotali(int value) {
        this.posizioniTotali = value;
    }

    /**
     * Recupera il valore della proprietà importoPosizioniTotali.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoPosizioniTotali() {
        return importoPosizioniTotali;
    }

    /**
     * Imposta il valore della proprietà importoPosizioniTotali.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoPosizioniTotali(BigDecimal value) {
        this.importoPosizioniTotali = value;
    }

}
