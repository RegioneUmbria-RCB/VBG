
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per OSAP_Oggetto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="OSAP_Oggetto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DataRilevazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Larghezza" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Profondita" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Esente" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="NomeCategoria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Temporanea" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DataInizio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataFine" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Giorni" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Utenti" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Tariffa" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OSAP_Oggetto", propOrder = {
    "dataRilevazione",
    "note",
    "larghezza",
    "profondita",
    "numero",
    "esente",
    "nomeCategoria",
    "temporanea",
    "dataInizio",
    "dataFine",
    "giorni",
    "utenti",
    "tariffa"
})
public class OSAPOggetto {

    @XmlElement(name = "DataRilevazione", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRilevazione;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "Larghezza", required = true)
    protected BigDecimal larghezza;
    @XmlElement(name = "Profondita", required = true)
    protected BigDecimal profondita;
    @XmlElement(name = "Numero")
    protected int numero;
    @XmlElement(name = "Esente")
    protected boolean esente;
    @XmlElement(name = "NomeCategoria")
    protected String nomeCategoria;
    @XmlElement(name = "Temporanea")
    protected boolean temporanea;
    @XmlElement(name = "DataInizio", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizio;
    @XmlElement(name = "DataFine", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFine;
    @XmlElement(name = "Giorni", required = true, type = Integer.class, nillable = true)
    protected Integer giorni;
    @XmlElement(name = "Utenti", required = true, type = Integer.class, nillable = true)
    protected Integer utenti;
    @XmlElement(name = "Tariffa", required = true)
    protected BigDecimal tariffa;

    /**
     * Recupera il valore della proprietà dataRilevazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRilevazione() {
        return dataRilevazione;
    }

    /**
     * Imposta il valore della proprietà dataRilevazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRilevazione(XMLGregorianCalendar value) {
        this.dataRilevazione = value;
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
     * Recupera il valore della proprietà larghezza.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLarghezza() {
        return larghezza;
    }

    /**
     * Imposta il valore della proprietà larghezza.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLarghezza(BigDecimal value) {
        this.larghezza = value;
    }

    /**
     * Recupera il valore della proprietà profondita.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getProfondita() {
        return profondita;
    }

    /**
     * Imposta il valore della proprietà profondita.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setProfondita(BigDecimal value) {
        this.profondita = value;
    }

    /**
     * Recupera il valore della proprietà numero.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Imposta il valore della proprietà numero.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

    /**
     * Recupera il valore della proprietà esente.
     * 
     */
    public boolean isEsente() {
        return esente;
    }

    /**
     * Imposta il valore della proprietà esente.
     * 
     */
    public void setEsente(boolean value) {
        this.esente = value;
    }

    /**
     * Recupera il valore della proprietà nomeCategoria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeCategoria() {
        return nomeCategoria;
    }

    /**
     * Imposta il valore della proprietà nomeCategoria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeCategoria(String value) {
        this.nomeCategoria = value;
    }

    /**
     * Recupera il valore della proprietà temporanea.
     * 
     */
    public boolean isTemporanea() {
        return temporanea;
    }

    /**
     * Imposta il valore della proprietà temporanea.
     * 
     */
    public void setTemporanea(boolean value) {
        this.temporanea = value;
    }

    /**
     * Recupera il valore della proprietà dataInizio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizio() {
        return dataInizio;
    }

    /**
     * Imposta il valore della proprietà dataInizio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizio(XMLGregorianCalendar value) {
        this.dataInizio = value;
    }

    /**
     * Recupera il valore della proprietà dataFine.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFine() {
        return dataFine;
    }

    /**
     * Imposta il valore della proprietà dataFine.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFine(XMLGregorianCalendar value) {
        this.dataFine = value;
    }

    /**
     * Recupera il valore della proprietà giorni.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGiorni() {
        return giorni;
    }

    /**
     * Imposta il valore della proprietà giorni.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGiorni(Integer value) {
        this.giorni = value;
    }

    /**
     * Recupera il valore della proprietà utenti.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUtenti() {
        return utenti;
    }

    /**
     * Imposta il valore della proprietà utenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUtenti(Integer value) {
        this.utenti = value;
    }

    /**
     * Recupera il valore della proprietà tariffa.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTariffa() {
        return tariffa;
    }

    /**
     * Imposta il valore della proprietà tariffa.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTariffa(BigDecimal value) {
        this.tariffa = value;
    }

}
