package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per PosizioneDebitoriaInAttesa complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioneDebitoriaInAttesa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AnnoImposta" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Sezionale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RiferimentoPraticaEsterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportoDovuto" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Annullato" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="GestioneIva" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CodiceSottoservizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SoggettoVersante" type="{http://entranext.it/}SoggettoVersante" minOccurs="0"/&gt;
 *         &lt;element name="SoggettoPagatore" type="{http://entranext.it/}SoggettoPagatore" minOccurs="0"/&gt;
 *         &lt;element name="SezioniParametriSpecifici" type="{http://entranext.it/}SezioneParametriSpecifici" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="NumeroDocumentoEsterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Dettagli" type="{http://entranext.it/}PosizioneDebitoriaInAttesa_Dettaglio" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="AllegaDocumentoDebitorio" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="AllegaDocumentoDiPagamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioneDebitoriaInAttesa", propOrder = { "descrizione", "annoImposta", "numero", "sezionale", "note", "riferimentoPraticaEsterna",
	"importoDovuto", "annullato", "gestioneIva", "codiceSottoservizio", "soggettoVersante", "soggettoPagatore", "sezioniParametriSpecifici",
	"numeroDocumentoEsterno", "dettagli", "allegaDocumentoDebitorio", "allegaDocumentoDiPagamento" })
@XmlSeeAlso({ PosizioneDebitoriaInAttesaResult.class })
public class PosizioneDebitoriaInAttesa {

    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "AnnoImposta")
    protected int annoImposta;
    @XmlElement(name = "Numero")
    protected int numero;
    @XmlElement(name = "Sezionale")
    protected String sezionale;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "RiferimentoPraticaEsterna")
    protected String riferimentoPraticaEsterna;
    @XmlElement(name = "ImportoDovuto", required = true)
    protected BigDecimal importoDovuto;
    @XmlElement(name = "Annullato")
    protected boolean annullato;
    @XmlElement(name = "GestioneIva")
    protected boolean gestioneIva;
    @XmlElement(name = "CodiceSottoservizio")
    protected String codiceSottoservizio;
    @XmlElement(name = "SoggettoVersante")
    protected SoggettoVersante soggettoVersante;
    @XmlElement(name = "SoggettoPagatore")
    protected SoggettoPagatore soggettoPagatore;
    @XmlElement(name = "SezioniParametriSpecifici", nillable = true)
    protected List<SezioneParametriSpecifici> sezioniParametriSpecifici;
    @XmlElement(name = "NumeroDocumentoEsterno")
    protected String numeroDocumentoEsterno;
    @XmlElement(name = "Dettagli")
    protected List<PosizioneDebitoriaInAttesaDettaglio> dettagli;
    @XmlElement(name = "AllegaDocumentoDebitorio")
    protected Boolean allegaDocumentoDebitorio;
    @XmlElement(name = "AllegaDocumentoDiPagamento")
    protected Boolean allegaDocumentoDiPagamento;

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDescrizione() {

	return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDescrizione(String value) {

	this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà annoImposta.
     * 
     */
    public int getAnnoImposta() {

	return annoImposta;
    }

    /**
     * Imposta il valore della proprietà annoImposta.
     * 
     */
    public void setAnnoImposta(int value) {

	this.annoImposta = value;
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
     * Recupera il valore della proprietà sezionale.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSezionale() {

	return sezionale;
    }

    /**
     * Imposta il valore della proprietà sezionale.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSezionale(String value) {

	this.sezionale = value;
    }

    /**
     * Recupera il valore della proprietà note.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNote() {

	return note;
    }

    /**
     * Imposta il valore della proprietà note.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNote(String value) {

	this.note = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRiferimentoPraticaEsterna() {

	return riferimentoPraticaEsterna;
    }

    /**
     * Imposta il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRiferimentoPraticaEsterna(String value) {

	this.riferimentoPraticaEsterna = value;
    }

    /**
     * Recupera il valore della proprietà importoDovuto.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getImportoDovuto() {

	return importoDovuto;
    }

    /**
     * Imposta il valore della proprietà importoDovuto.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setImportoDovuto(BigDecimal value) {

	this.importoDovuto = value;
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
     * Recupera il valore della proprietà gestioneIva.
     * 
     */
    public boolean isGestioneIva() {

	return gestioneIva;
    }

    /**
     * Imposta il valore della proprietà gestioneIva.
     * 
     */
    public void setGestioneIva(boolean value) {

	this.gestioneIva = value;
    }

    /**
     * Recupera il valore della proprietà codiceSottoservizio.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCodiceSottoservizio() {

	return codiceSottoservizio;
    }

    /**
     * Imposta il valore della proprietà codiceSottoservizio.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCodiceSottoservizio(String value) {

	this.codiceSottoservizio = value;
    }

    /**
     * Recupera il valore della proprietà soggettoVersante.
     * 
     * @return possible object is {@link SoggettoVersante }
     * 
     */
    public SoggettoVersante getSoggettoVersante() {

	return soggettoVersante;
    }

    /**
     * Imposta il valore della proprietà soggettoVersante.
     * 
     * @param value
     *            allowed object is {@link SoggettoVersante }
     * 
     */
    public void setSoggettoVersante(SoggettoVersante value) {

	this.soggettoVersante = value;
    }

    /**
     * Recupera il valore della proprietà soggettoPagatore.
     * 
     * @return possible object is {@link SoggettoPagatore }
     * 
     */
    public SoggettoPagatore getSoggettoPagatore() {

	return soggettoPagatore;
    }

    /**
     * Imposta il valore della proprietà soggettoPagatore.
     * 
     * @param value
     *            allowed object is {@link SoggettoPagatore }
     * 
     */
    public void setSoggettoPagatore(SoggettoPagatore value) {

	this.soggettoPagatore = value;
    }

    /**
     * Gets the value of the sezioniParametriSpecifici property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the sezioniParametriSpecifici property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getSezioniParametriSpecifici().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link SezioneParametriSpecifici }
     * 
     * 
     */
    public List<SezioneParametriSpecifici> getSezioniParametriSpecifici() {

	if (sezioniParametriSpecifici == null) {
	    sezioniParametriSpecifici = new ArrayList<SezioneParametriSpecifici>();
	}
	return this.sezioniParametriSpecifici;
    }

    /**
     * Recupera il valore della proprietà numeroDocumentoEsterno.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumeroDocumentoEsterno() {

	return numeroDocumentoEsterno;
    }

    /**
     * Imposta il valore della proprietà numeroDocumentoEsterno.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNumeroDocumentoEsterno(String value) {

	this.numeroDocumentoEsterno = value;
    }

    /**
     * Gets the value of the dettagli property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the dettagli property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDettagli().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link PosizioneDebitoriaInAttesaDettaglio }
     * 
     * 
     */
    public List<PosizioneDebitoriaInAttesaDettaglio> getDettagli() {

	if (dettagli == null) {
	    dettagli = new ArrayList<PosizioneDebitoriaInAttesaDettaglio>();
	}
	return this.dettagli;
    }

    /**
     * Recupera il valore della proprietà allegaDocumentoDebitorio.
     * 
     * @return possible object is {@link Boolean }
     * 
     */
    public Boolean isAllegaDocumentoDebitorio() {

	return allegaDocumentoDebitorio;
    }

    /**
     * Imposta il valore della proprietà allegaDocumentoDebitorio.
     * 
     * @param value
     *            allowed object is {@link Boolean }
     * 
     */
    public void setAllegaDocumentoDebitorio(Boolean value) {

	this.allegaDocumentoDebitorio = value;
    }

    /**
     * Recupera il valore della proprietà allegaDocumentoDiPagamento.
     * 
     * @return possible object is {@link Boolean }
     * 
     */
    public Boolean isAllegaDocumentoDiPagamento() {

	return allegaDocumentoDiPagamento;
    }

    /**
     * Imposta il valore della proprietà allegaDocumentoDiPagamento.
     * 
     * @param value
     *            allowed object is {@link Boolean }
     * 
     */
    public void setAllegaDocumentoDiPagamento(Boolean value) {

	this.allegaDocumentoDiPagamento = value;
    }

    public void setDettagli(List<PosizioneDebitoriaInAttesaDettaglio> dettagli) {

	this.dettagli = dettagli;
    }
}
