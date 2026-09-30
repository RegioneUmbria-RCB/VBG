
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per RuoloPosizioniDebitorie complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RuoloPosizioniDebitorie"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AnnoImposta" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DataInizioPeriodo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataFinePeriodo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TipoDocumento" type="{http://entranext.it/}TipiDocumentiSDI"/&gt;
 *         &lt;element name="Ruolo_CausaliImporti" type="{http://entranext.it/}ArrayOfRuolo_CausaleImporto" minOccurs="0"/&gt;
 *         &lt;element name="GestionePDFACaricoDelFornitore" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="FileAcquisizionePDF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportazioneStorico" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="AggiungiDocumentoPagamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RuoloPosizioniDebitorie", propOrder = {
    "descrizione",
    "annoImposta",
    "dataInizioPeriodo",
    "dataFinePeriodo",
    "note",
    "tipoDocumento",
    "ruoloCausaliImporti",
    "gestionePDFACaricoDelFornitore",
    "fileAcquisizionePDF",
    "importazioneStorico",
    "aggiungiDocumentoPagamento"
})
@XmlSeeAlso({
    RuoloPosizioniDebitorieResult.class,
    RuoloPosizioniNoteDiCredito.class,
    RuoloNoteDiCreditoResult.class
})
public class RuoloPosizioniDebitorie {

    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "AnnoImposta")
    protected int annoImposta;
    @XmlElement(name = "DataInizioPeriodo", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioPeriodo;
    @XmlElement(name = "DataFinePeriodo", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFinePeriodo;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "TipoDocumento", required = true)
    @XmlSchemaType(name = "string")
    protected TipiDocumentiSDI tipoDocumento;
    @XmlElement(name = "Ruolo_CausaliImporti")
    protected ArrayOfRuoloCausaleImporto ruoloCausaliImporti;
    @XmlElement(name = "GestionePDFACaricoDelFornitore")
    protected boolean gestionePDFACaricoDelFornitore;
    @XmlElement(name = "FileAcquisizionePDF")
    protected String fileAcquisizionePDF;
    @XmlElement(name = "ImportazioneStorico")
    protected Boolean importazioneStorico;
    @XmlElement(name = "AggiungiDocumentoPagamento")
    protected Boolean aggiungiDocumentoPagamento;

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
     * Recupera il valore della proprietà ruoloCausaliImporti.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRuoloCausaleImporto }
     *     
     */
    public ArrayOfRuoloCausaleImporto getRuoloCausaliImporti() {
        return ruoloCausaliImporti;
    }

    /**
     * Imposta il valore della proprietà ruoloCausaliImporti.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRuoloCausaleImporto }
     *     
     */
    public void setRuoloCausaliImporti(ArrayOfRuoloCausaleImporto value) {
        this.ruoloCausaliImporti = value;
    }

    /**
     * Recupera il valore della proprietà gestionePDFACaricoDelFornitore.
     * 
     */
    public boolean isGestionePDFACaricoDelFornitore() {
        return gestionePDFACaricoDelFornitore;
    }

    /**
     * Imposta il valore della proprietà gestionePDFACaricoDelFornitore.
     * 
     */
    public void setGestionePDFACaricoDelFornitore(boolean value) {
        this.gestionePDFACaricoDelFornitore = value;
    }

    /**
     * Recupera il valore della proprietà fileAcquisizionePDF.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileAcquisizionePDF() {
        return fileAcquisizionePDF;
    }

    /**
     * Imposta il valore della proprietà fileAcquisizionePDF.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileAcquisizionePDF(String value) {
        this.fileAcquisizionePDF = value;
    }

    /**
     * Recupera il valore della proprietà importazioneStorico.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isImportazioneStorico() {
        return importazioneStorico;
    }

    /**
     * Imposta il valore della proprietà importazioneStorico.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setImportazioneStorico(Boolean value) {
        this.importazioneStorico = value;
    }

    /**
     * Recupera il valore della proprietà aggiungiDocumentoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAggiungiDocumentoPagamento() {
        return aggiungiDocumentoPagamento;
    }

    /**
     * Imposta il valore della proprietà aggiungiDocumentoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAggiungiDocumentoPagamento(Boolean value) {
        this.aggiungiDocumentoPagamento = value;
    }

}
