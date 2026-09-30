
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per PosizioneDebitoriaOSAP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioneDebitoriaOSAP"&gt;
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
 *         &lt;element name="NumeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="DataProtocollo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataEmissione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataInizioPeriodo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataFinePeriodo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TipoDocumento" type="{http://entranext.it/}TipiDocumentiSDI"/&gt;
 *         &lt;element name="Contribuente" type="{http://entranext.it/}Contribuente" minOccurs="0"/&gt;
 *         &lt;element name="Rate" type="{http://entranext.it/}PosizioneDebitoria_Rata" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Dettagli" type="{http://entranext.it/}PosizioneDebitoria_DettaglioOSAP" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="RiepilogoIva" type="{http://entranext.it/}ArrayOfPosizioneDebitoria_RiepilogoIva" minOccurs="0"/&gt;
 *         &lt;element name="SezioniParametriSpecifici" type="{http://entranext.it/}SezioneParametriSpecifici" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ID_RUOLO" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="NomeFileAcquisito" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RiferimentoPraticaEsternaPrecedente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Documento" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="ScadenzaSoluzioneUnica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RiferimentoRuoloEsterno" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="QuintoCampoSoluzioneUnica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IUVSoluzioneUnica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TipoDocumentoPagamento" type="{http://entranext.it/}TipiDocumentoPagamento" minOccurs="0"/&gt;
 *         &lt;element name="ModalitaPagamentoContestuale" type="{http://entranext.it/}ModalitaPagamentoFuoriNodo"/&gt;
 *         &lt;element name="DataNotificaImportazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="Pagamenti" type="{http://entranext.it/}PagamentoFuoriNodo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="NumeroDocumentoEsterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioneDebitoriaOSAP", propOrder = {
    "descrizione",
    "annoImposta",
    "numero",
    "sezionale",
    "note",
    "riferimentoPraticaEsterna",
    "importoDovuto",
    "annullato",
    "gestioneIva",
    "numeroProtocollo",
    "dataProtocollo",
    "dataEmissione",
    "dataInizioPeriodo",
    "dataFinePeriodo",
    "tipoDocumento",
    "contribuente",
    "rate",
    "dettagli",
    "riepilogoIva",
    "sezioniParametriSpecifici",
    "idruolo",
    "nomeFileAcquisito",
    "riferimentoPraticaEsternaPrecedente",
    "documento",
    "scadenzaSoluzioneUnica",
    "riferimentoRuoloEsterno",
    "quintoCampoSoluzioneUnica",
    "iuvSoluzioneUnica",
    "tipoDocumentoPagamento",
    "modalitaPagamentoContestuale",
    "dataNotificaImportazione",
    "pagamenti",
    "numeroDocumentoEsterno"
})
public class PosizioneDebitoriaOSAP {

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
    @XmlElementRef(name = "NumeroProtocollo", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> numeroProtocollo;
    @XmlElement(name = "DataProtocollo", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataProtocollo;
    @XmlElement(name = "DataEmissione", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEmissione;
    @XmlElement(name = "DataInizioPeriodo", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioPeriodo;
    @XmlElement(name = "DataFinePeriodo", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFinePeriodo;
    @XmlElement(name = "TipoDocumento", required = true)
    @XmlSchemaType(name = "string")
    protected TipiDocumentiSDI tipoDocumento;
    @XmlElement(name = "Contribuente")
    protected Contribuente contribuente;
    @XmlElement(name = "Rate")
    protected List<PosizioneDebitoriaRata> rate;
    @XmlElement(name = "Dettagli")
    protected List<PosizioneDebitoriaDettaglioOSAP> dettagli;
    @XmlElement(name = "RiepilogoIva")
    protected ArrayOfPosizioneDebitoriaRiepilogoIva riepilogoIva;
    @XmlElement(name = "SezioniParametriSpecifici", nillable = true)
    protected List<SezioneParametriSpecifici> sezioniParametriSpecifici;
    @XmlElementRef(name = "ID_RUOLO", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> idruolo;
    @XmlElement(name = "NomeFileAcquisito", required = true, nillable = true)
    protected String nomeFileAcquisito;
    @XmlElement(name = "RiferimentoPraticaEsternaPrecedente", required = true, nillable = true)
    protected String riferimentoPraticaEsternaPrecedente;
    @XmlElement(name = "Documento", required = true, nillable = true)
    protected byte[] documento;
    @XmlElement(name = "ScadenzaSoluzioneUnica", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar scadenzaSoluzioneUnica;
    @XmlElement(name = "RiferimentoRuoloEsterno", required = true, nillable = true)
    protected String riferimentoRuoloEsterno;
    @XmlElement(name = "QuintoCampoSoluzioneUnica")
    protected String quintoCampoSoluzioneUnica;
    @XmlElement(name = "IUVSoluzioneUnica")
    protected String iuvSoluzioneUnica;
    @XmlElement(name = "TipoDocumentoPagamento")
    @XmlSchemaType(name = "string")
    protected TipiDocumentoPagamento tipoDocumentoPagamento;
    @XmlElement(name = "ModalitaPagamentoContestuale", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ModalitaPagamentoFuoriNodo modalitaPagamentoContestuale;
    @XmlElement(name = "DataNotificaImportazione")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNotificaImportazione;
    @XmlElement(name = "Pagamenti", nillable = true)
    protected List<PagamentoFuoriNodo> pagamenti;
    @XmlElement(name = "NumeroDocumentoEsterno")
    protected String numeroDocumentoEsterno;

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
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSezionale() {
        return sezionale;
    }

    /**
     * Imposta il valore della proprietà sezionale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSezionale(String value) {
        this.sezionale = value;
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
     * Recupera il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoPraticaEsterna() {
        return riferimentoPraticaEsterna;
    }

    /**
     * Imposta il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoPraticaEsterna(String value) {
        this.riferimentoPraticaEsterna = value;
    }

    /**
     * Recupera il valore della proprietà importoDovuto.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoDovuto() {
        return importoDovuto;
    }

    /**
     * Imposta il valore della proprietà importoDovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
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
     * Recupera il valore della proprietà numeroProtocollo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getNumeroProtocollo() {
        return numeroProtocollo;
    }

    /**
     * Imposta il valore della proprietà numeroProtocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setNumeroProtocollo(JAXBElement<Integer> value) {
        this.numeroProtocollo = value;
    }

    /**
     * Recupera il valore della proprietà dataProtocollo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataProtocollo() {
        return dataProtocollo;
    }

    /**
     * Imposta il valore della proprietà dataProtocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataProtocollo(XMLGregorianCalendar value) {
        this.dataProtocollo = value;
    }

    /**
     * Recupera il valore della proprietà dataEmissione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEmissione() {
        return dataEmissione;
    }

    /**
     * Imposta il valore della proprietà dataEmissione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEmissione(XMLGregorianCalendar value) {
        this.dataEmissione = value;
    }

    /**
     * Recupera il valore della proprietà dataInizioPeriodo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioPeriodo() {
        return dataInizioPeriodo;
    }

    /**
     * Imposta il valore della proprietà dataInizioPeriodo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioPeriodo(XMLGregorianCalendar value) {
        this.dataInizioPeriodo = value;
    }

    /**
     * Recupera il valore della proprietà dataFinePeriodo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFinePeriodo() {
        return dataFinePeriodo;
    }

    /**
     * Imposta il valore della proprietà dataFinePeriodo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFinePeriodo(XMLGregorianCalendar value) {
        this.dataFinePeriodo = value;
    }

    /**
     * Recupera il valore della proprietà tipoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link TipiDocumentiSDI }
     *     
     */
    public TipiDocumentiSDI getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Imposta il valore della proprietà tipoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipiDocumentiSDI }
     *     
     */
    public void setTipoDocumento(TipiDocumentiSDI value) {
        this.tipoDocumento = value;
    }

    /**
     * Recupera il valore della proprietà contribuente.
     * 
     * @return
     *     possible object is
     *     {@link Contribuente }
     *     
     */
    public Contribuente getContribuente() {
        return contribuente;
    }

    /**
     * Imposta il valore della proprietà contribuente.
     * 
     * @param value
     *     allowed object is
     *     {@link Contribuente }
     *     
     */
    public void setContribuente(Contribuente value) {
        this.contribuente = value;
    }

    /**
     * Gets the value of the rate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioneDebitoriaRata }
     * 
     * 
     */
    public List<PosizioneDebitoriaRata> getRate() {
        if (rate == null) {
            rate = new ArrayList<PosizioneDebitoriaRata>();
        }
        return this.rate;
    }

    /**
     * Gets the value of the dettagli property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dettagli property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDettagli().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioneDebitoriaDettaglioOSAP }
     * 
     * 
     */
    public List<PosizioneDebitoriaDettaglioOSAP> getDettagli() {
        if (dettagli == null) {
            dettagli = new ArrayList<PosizioneDebitoriaDettaglioOSAP>();
        }
        return this.dettagli;
    }

    /**
     * Recupera il valore della proprietà riepilogoIva.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPosizioneDebitoriaRiepilogoIva }
     *     
     */
    public ArrayOfPosizioneDebitoriaRiepilogoIva getRiepilogoIva() {
        return riepilogoIva;
    }

    /**
     * Imposta il valore della proprietà riepilogoIva.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPosizioneDebitoriaRiepilogoIva }
     *     
     */
    public void setRiepilogoIva(ArrayOfPosizioneDebitoriaRiepilogoIva value) {
        this.riepilogoIva = value;
    }

    /**
     * Gets the value of the sezioniParametriSpecifici property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sezioniParametriSpecifici property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSezioniParametriSpecifici().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SezioneParametriSpecifici }
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
     * Recupera il valore della proprietà idruolo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getIDRUOLO() {
        return idruolo;
    }

    /**
     * Imposta il valore della proprietà idruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setIDRUOLO(JAXBElement<Integer> value) {
        this.idruolo = value;
    }

    /**
     * Recupera il valore della proprietà nomeFileAcquisito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFileAcquisito() {
        return nomeFileAcquisito;
    }

    /**
     * Imposta il valore della proprietà nomeFileAcquisito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFileAcquisito(String value) {
        this.nomeFileAcquisito = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoPraticaEsternaPrecedente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoPraticaEsternaPrecedente() {
        return riferimentoPraticaEsternaPrecedente;
    }

    /**
     * Imposta il valore della proprietà riferimentoPraticaEsternaPrecedente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoPraticaEsternaPrecedente(String value) {
        this.riferimentoPraticaEsternaPrecedente = value;
    }

    /**
     * Recupera il valore della proprietà documento.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocumento() {
        return documento;
    }

    /**
     * Imposta il valore della proprietà documento.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocumento(byte[] value) {
        this.documento = value;
    }

    /**
     * Recupera il valore della proprietà scadenzaSoluzioneUnica.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getScadenzaSoluzioneUnica() {
        return scadenzaSoluzioneUnica;
    }

    /**
     * Imposta il valore della proprietà scadenzaSoluzioneUnica.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setScadenzaSoluzioneUnica(XMLGregorianCalendar value) {
        this.scadenzaSoluzioneUnica = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoRuoloEsterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoRuoloEsterno() {
        return riferimentoRuoloEsterno;
    }

    /**
     * Imposta il valore della proprietà riferimentoRuoloEsterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoRuoloEsterno(String value) {
        this.riferimentoRuoloEsterno = value;
    }

    /**
     * Recupera il valore della proprietà quintoCampoSoluzioneUnica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuintoCampoSoluzioneUnica() {
        return quintoCampoSoluzioneUnica;
    }

    /**
     * Imposta il valore della proprietà quintoCampoSoluzioneUnica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuintoCampoSoluzioneUnica(String value) {
        this.quintoCampoSoluzioneUnica = value;
    }

    /**
     * Recupera il valore della proprietà iuvSoluzioneUnica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUVSoluzioneUnica() {
        return iuvSoluzioneUnica;
    }

    /**
     * Imposta il valore della proprietà iuvSoluzioneUnica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIUVSoluzioneUnica(String value) {
        this.iuvSoluzioneUnica = value;
    }

    /**
     * Recupera il valore della proprietà tipoDocumentoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link TipiDocumentoPagamento }
     *     
     */
    public TipiDocumentoPagamento getTipoDocumentoPagamento() {
        return tipoDocumentoPagamento;
    }

    /**
     * Imposta il valore della proprietà tipoDocumentoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipiDocumentoPagamento }
     *     
     */
    public void setTipoDocumentoPagamento(TipiDocumentoPagamento value) {
        this.tipoDocumentoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà modalitaPagamentoContestuale.
     * 
     * @return
     *     possible object is
     *     {@link ModalitaPagamentoFuoriNodo }
     *     
     */
    public ModalitaPagamentoFuoriNodo getModalitaPagamentoContestuale() {
        return modalitaPagamentoContestuale;
    }

    /**
     * Imposta il valore della proprietà modalitaPagamentoContestuale.
     * 
     * @param value
     *     allowed object is
     *     {@link ModalitaPagamentoFuoriNodo }
     *     
     */
    public void setModalitaPagamentoContestuale(ModalitaPagamentoFuoriNodo value) {
        this.modalitaPagamentoContestuale = value;
    }

    /**
     * Recupera il valore della proprietà dataNotificaImportazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNotificaImportazione() {
        return dataNotificaImportazione;
    }

    /**
     * Imposta il valore della proprietà dataNotificaImportazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNotificaImportazione(XMLGregorianCalendar value) {
        this.dataNotificaImportazione = value;
    }

    /**
     * Gets the value of the pagamenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pagamenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPagamenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PagamentoFuoriNodo }
     * 
     * 
     */
    public List<PagamentoFuoriNodo> getPagamenti() {
        if (pagamenti == null) {
            pagamenti = new ArrayList<PagamentoFuoriNodo>();
        }
        return this.pagamenti;
    }

    /**
     * Recupera il valore della proprietà numeroDocumentoEsterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroDocumentoEsterno() {
        return numeroDocumentoEsterno;
    }

    /**
     * Imposta il valore della proprietà numeroDocumentoEsterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroDocumentoEsterno(String value) {
        this.numeroDocumentoEsterno = value;
    }

}
