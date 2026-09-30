
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ICP_Oggetto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ICP_Oggetto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TipoStruttura" type="{http://entranext.it/}ICP_TipiStrutture"/&gt;
 *         &lt;element name="TipoRilevazione" type="{http://entranext.it/}ICP_TipiRilevazione"/&gt;
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DataRilevazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataIniziale" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataFinale" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Mesi" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Larghezza" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Altezza" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Luminoso" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Rimorchio" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Temporaneo" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="TipologiaVeicoloInGenere" type="{http://entranext.it/}ICP_TipologiaVeicoloInGenere"/&gt;
 *         &lt;element name="TipologiaVeicoloContoProprio" type="{http://entranext.it/}ICP_TipologiaVeicoloContoProprio"/&gt;
 *         &lt;element name="ClassificazioneOggetto" type="{http://entranext.it/}ICP_ClassificazioneOggetti"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Targa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TipoRiduzione" type="{http://entranext.it/}ICP_TipiRiduzioni"/&gt;
 *         &lt;element name="CategoriaSpeciale" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
@XmlType(name = "ICP_Oggetto", propOrder = {
    "tipoStruttura",
    "tipoRilevazione",
    "numero",
    "dataRilevazione",
    "dataIniziale",
    "dataFinale",
    "mesi",
    "larghezza",
    "altezza",
    "luminoso",
    "rimorchio",
    "note",
    "temporaneo",
    "tipologiaVeicoloInGenere",
    "tipologiaVeicoloContoProprio",
    "classificazioneOggetto",
    "descrizione",
    "targa",
    "tipoRiduzione",
    "categoriaSpeciale",
    "tariffa"
})
public class ICPOggetto {

    @XmlElement(name = "TipoStruttura", required = true)
    @XmlSchemaType(name = "string")
    protected ICPTipiStrutture tipoStruttura;
    @XmlElement(name = "TipoRilevazione", required = true)
    @XmlSchemaType(name = "string")
    protected ICPTipiRilevazione tipoRilevazione;
    @XmlElement(name = "Numero")
    protected int numero;
    @XmlElement(name = "DataRilevazione", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRilevazione;
    @XmlElement(name = "DataIniziale", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataIniziale;
    @XmlElement(name = "DataFinale", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFinale;
    @XmlElement(name = "Mesi", required = true, type = Integer.class, nillable = true)
    protected Integer mesi;
    @XmlElement(name = "Larghezza", required = true, nillable = true)
    protected BigDecimal larghezza;
    @XmlElement(name = "Altezza", required = true, nillable = true)
    protected BigDecimal altezza;
    @XmlElement(name = "Luminoso")
    protected boolean luminoso;
    @XmlElement(name = "Rimorchio")
    protected boolean rimorchio;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "Temporaneo")
    protected boolean temporaneo;
    @XmlElement(name = "TipologiaVeicoloInGenere", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ICPTipologiaVeicoloInGenere tipologiaVeicoloInGenere;
    @XmlElement(name = "TipologiaVeicoloContoProprio", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ICPTipologiaVeicoloContoProprio tipologiaVeicoloContoProprio;
    @XmlElement(name = "ClassificazioneOggetto", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ICPClassificazioneOggetti classificazioneOggetto;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "Targa")
    protected String targa;
    @XmlElement(name = "TipoRiduzione", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ICPTipiRiduzioni tipoRiduzione;
    @XmlElement(name = "CategoriaSpeciale")
    protected boolean categoriaSpeciale;
    @XmlElement(name = "Tariffa", required = true)
    protected BigDecimal tariffa;

    /**
     * Recupera il valore della proprietà tipoStruttura.
     * 
     * @return
     *     possible object is
     *     {@link ICPTipiStrutture }
     *     
     */
    public ICPTipiStrutture getTipoStruttura() {
        return tipoStruttura;
    }

    /**
     * Imposta il valore della proprietà tipoStruttura.
     * 
     * @param value
     *     allowed object is
     *     {@link ICPTipiStrutture }
     *     
     */
    public void setTipoStruttura(ICPTipiStrutture value) {
        this.tipoStruttura = value;
    }

    /**
     * Recupera il valore della proprietà tipoRilevazione.
     * 
     * @return
     *     possible object is
     *     {@link ICPTipiRilevazione }
     *     
     */
    public ICPTipiRilevazione getTipoRilevazione() {
        return tipoRilevazione;
    }

    /**
     * Imposta il valore della proprietà tipoRilevazione.
     * 
     * @param value
     *     allowed object is
     *     {@link ICPTipiRilevazione }
     *     
     */
    public void setTipoRilevazione(ICPTipiRilevazione value) {
        this.tipoRilevazione = value;
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
     * Recupera il valore della proprietà dataIniziale.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataIniziale() {
        return dataIniziale;
    }

    /**
     * Imposta il valore della proprietà dataIniziale.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataIniziale(XMLGregorianCalendar value) {
        this.dataIniziale = value;
    }

    /**
     * Recupera il valore della proprietà dataFinale.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFinale() {
        return dataFinale;
    }

    /**
     * Imposta il valore della proprietà dataFinale.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFinale(XMLGregorianCalendar value) {
        this.dataFinale = value;
    }

    /**
     * Recupera il valore della proprietà mesi.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMesi() {
        return mesi;
    }

    /**
     * Imposta il valore della proprietà mesi.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMesi(Integer value) {
        this.mesi = value;
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
     * Recupera il valore della proprietà altezza.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAltezza() {
        return altezza;
    }

    /**
     * Imposta il valore della proprietà altezza.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAltezza(BigDecimal value) {
        this.altezza = value;
    }

    /**
     * Recupera il valore della proprietà luminoso.
     * 
     */
    public boolean isLuminoso() {
        return luminoso;
    }

    /**
     * Imposta il valore della proprietà luminoso.
     * 
     */
    public void setLuminoso(boolean value) {
        this.luminoso = value;
    }

    /**
     * Recupera il valore della proprietà rimorchio.
     * 
     */
    public boolean isRimorchio() {
        return rimorchio;
    }

    /**
     * Imposta il valore della proprietà rimorchio.
     * 
     */
    public void setRimorchio(boolean value) {
        this.rimorchio = value;
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
     * Recupera il valore della proprietà temporaneo.
     * 
     */
    public boolean isTemporaneo() {
        return temporaneo;
    }

    /**
     * Imposta il valore della proprietà temporaneo.
     * 
     */
    public void setTemporaneo(boolean value) {
        this.temporaneo = value;
    }

    /**
     * Recupera il valore della proprietà tipologiaVeicoloInGenere.
     * 
     * @return
     *     possible object is
     *     {@link ICPTipologiaVeicoloInGenere }
     *     
     */
    public ICPTipologiaVeicoloInGenere getTipologiaVeicoloInGenere() {
        return tipologiaVeicoloInGenere;
    }

    /**
     * Imposta il valore della proprietà tipologiaVeicoloInGenere.
     * 
     * @param value
     *     allowed object is
     *     {@link ICPTipologiaVeicoloInGenere }
     *     
     */
    public void setTipologiaVeicoloInGenere(ICPTipologiaVeicoloInGenere value) {
        this.tipologiaVeicoloInGenere = value;
    }

    /**
     * Recupera il valore della proprietà tipologiaVeicoloContoProprio.
     * 
     * @return
     *     possible object is
     *     {@link ICPTipologiaVeicoloContoProprio }
     *     
     */
    public ICPTipologiaVeicoloContoProprio getTipologiaVeicoloContoProprio() {
        return tipologiaVeicoloContoProprio;
    }

    /**
     * Imposta il valore della proprietà tipologiaVeicoloContoProprio.
     * 
     * @param value
     *     allowed object is
     *     {@link ICPTipologiaVeicoloContoProprio }
     *     
     */
    public void setTipologiaVeicoloContoProprio(ICPTipologiaVeicoloContoProprio value) {
        this.tipologiaVeicoloContoProprio = value;
    }

    /**
     * Recupera il valore della proprietà classificazioneOggetto.
     * 
     * @return
     *     possible object is
     *     {@link ICPClassificazioneOggetti }
     *     
     */
    public ICPClassificazioneOggetti getClassificazioneOggetto() {
        return classificazioneOggetto;
    }

    /**
     * Imposta il valore della proprietà classificazioneOggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link ICPClassificazioneOggetti }
     *     
     */
    public void setClassificazioneOggetto(ICPClassificazioneOggetti value) {
        this.classificazioneOggetto = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà targa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarga() {
        return targa;
    }

    /**
     * Imposta il valore della proprietà targa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarga(String value) {
        this.targa = value;
    }

    /**
     * Recupera il valore della proprietà tipoRiduzione.
     * 
     * @return
     *     possible object is
     *     {@link ICPTipiRiduzioni }
     *     
     */
    public ICPTipiRiduzioni getTipoRiduzione() {
        return tipoRiduzione;
    }

    /**
     * Imposta il valore della proprietà tipoRiduzione.
     * 
     * @param value
     *     allowed object is
     *     {@link ICPTipiRiduzioni }
     *     
     */
    public void setTipoRiduzione(ICPTipiRiduzioni value) {
        this.tipoRiduzione = value;
    }

    /**
     * Recupera il valore della proprietà categoriaSpeciale.
     * 
     */
    public boolean isCategoriaSpeciale() {
        return categoriaSpeciale;
    }

    /**
     * Imposta il valore della proprietà categoriaSpeciale.
     * 
     */
    public void setCategoriaSpeciale(boolean value) {
        this.categoriaSpeciale = value;
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
