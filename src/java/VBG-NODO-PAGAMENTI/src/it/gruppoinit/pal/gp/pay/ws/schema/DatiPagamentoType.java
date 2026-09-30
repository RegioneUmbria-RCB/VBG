
package it.gruppoinit.pal.gp.pay.ws.schema;

import java.math.BigDecimal;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per DatiPagamentoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DatiPagamentoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataOraPagamento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="importoPagato" type="{http://www.paevolution.com/ws/pagamenti_types/}ImportoType"/&gt;
 *         &lt;element name="riferimentiPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="modalitaPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descrizioneCausale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataOraInizioTransazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataOraAutorizzazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="importoTransato" type="{http://www.paevolution.com/ws/pagamenti_types/}ImportoType" minOccurs="0"/&gt;
 *         &lt;element name="importoCommissioni" type="{http://www.paevolution.com/ws/pagamenti_types/}ImportoType" minOccurs="0"/&gt;
 *         &lt;element name="soggettoPagatore" type="{http://www.paevolution.com/ws/pagamenti_types/}SoggettoDebitoreType" minOccurs="0"/&gt;
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idPSP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ragioneSocialePSP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="iuv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="iur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiPagamentoType", propOrder = {
    "dataOraPagamento",
    "importoPagato",
    "riferimentiPagamento",
    "modalitaPagamento",
    "descrizioneCausale",
    "dataOraInizioTransazione",
    "dataOraAutorizzazione",
    "importoTransato",
    "importoCommissioni",
    "soggettoPagatore",
    "note",
    "idPSP",
    "ragioneSocialePSP",
    "iuv",
    "iur"
})
public class DatiPagamentoType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraPagamento;
    @XmlElement(required = true)
    protected BigDecimal importoPagato;
    protected String riferimentiPagamento;
    protected String modalitaPagamento;
    protected String descrizioneCausale;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraInizioTransazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraAutorizzazione;
    protected BigDecimal importoTransato;
    protected BigDecimal importoCommissioni;
    protected SoggettoDebitoreType soggettoPagatore;
    protected String note;
    protected String idPSP;
    protected String ragioneSocialePSP;
    protected String iuv;
    protected String iur;
    @XmlTransient
    protected DataHandler ricevutaXml;

    /**
     * Recupera il valore della proprietà dataOraPagamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraPagamento() {
        return dataOraPagamento;
    }

    /**
     * Imposta il valore della proprietà dataOraPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraPagamento(XMLGregorianCalendar value) {
        this.dataOraPagamento = value;
    }

    /**
     * Recupera il valore della proprietà importoPagato.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoPagato() {
        return importoPagato;
    }

    /**
     * Imposta il valore della proprietà importoPagato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoPagato(BigDecimal value) {
        this.importoPagato = value;
    }

    /**
     * Recupera il valore della proprietà riferimentiPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentiPagamento() {
        return riferimentiPagamento;
    }

    /**
     * Imposta il valore della proprietà riferimentiPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentiPagamento(String value) {
        this.riferimentiPagamento = value;
    }

    /**
     * Recupera il valore della proprietà modalitaPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModalitaPagamento() {
        return modalitaPagamento;
    }

    /**
     * Imposta il valore della proprietà modalitaPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModalitaPagamento(String value) {
        this.modalitaPagamento = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneCausale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneCausale() {
        return descrizioneCausale;
    }

    /**
     * Imposta il valore della proprietà descrizioneCausale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneCausale(String value) {
        this.descrizioneCausale = value;
    }

    /**
     * Recupera il valore della proprietà dataOraInizioTransazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraInizioTransazione() {
        return dataOraInizioTransazione;
    }

    /**
     * Imposta il valore della proprietà dataOraInizioTransazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraInizioTransazione(XMLGregorianCalendar value) {
        this.dataOraInizioTransazione = value;
    }

    /**
     * Recupera il valore della proprietà dataOraAutorizzazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraAutorizzazione() {
        return dataOraAutorizzazione;
    }

    /**
     * Imposta il valore della proprietà dataOraAutorizzazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraAutorizzazione(XMLGregorianCalendar value) {
        this.dataOraAutorizzazione = value;
    }

    /**
     * Recupera il valore della proprietà importoTransato.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTransato() {
        return importoTransato;
    }

    /**
     * Imposta il valore della proprietà importoTransato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTransato(BigDecimal value) {
        this.importoTransato = value;
    }

    /**
     * Recupera il valore della proprietà importoCommissioni.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoCommissioni() {
        return importoCommissioni;
    }

    /**
     * Imposta il valore della proprietà importoCommissioni.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoCommissioni(BigDecimal value) {
        this.importoCommissioni = value;
    }

    /**
     * Recupera il valore della proprietà soggettoPagatore.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoDebitoreType }
     *     
     */
    public SoggettoDebitoreType getSoggettoPagatore() {
        return soggettoPagatore;
    }

    /**
     * Imposta il valore della proprietà soggettoPagatore.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoDebitoreType }
     *     
     */
    public void setSoggettoPagatore(SoggettoDebitoreType value) {
        this.soggettoPagatore = value;
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
     * Recupera il valore della proprietà idPSP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPSP() {
        return idPSP;
    }

    /**
     * Imposta il valore della proprietà idPSP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPSP(String value) {
        this.idPSP = value;
    }

    /**
     * Recupera il valore della proprietà ragioneSocialePSP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagioneSocialePSP() {
        return ragioneSocialePSP;
    }

    /**
     * Imposta il valore della proprietà ragioneSocialePSP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagioneSocialePSP(String value) {
        this.ragioneSocialePSP = value;
    }

    /**
     * Recupera il valore della proprietà iuv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIuv() {
        return iuv;
    }

    /**
     * Imposta il valore della proprietà iuv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIuv(String value) {
        this.iuv = value;
    }

    /**
     * Recupera il valore della proprietà iur.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIur() {
        return iur;
    }

    /**
     * Imposta il valore della proprietà iur.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIur(String value) {

	this.iur = value;
    }

    /**
     * @return the ricevutaXml
     */
    public DataHandler getRicevutaXml() {

	return ricevutaXml;
    }

    /**
     * @param ricevutaXml
     *            the ricevutaXml to set
     */
    public void setRicevutaXml(DataHandler ricevutaXml) {

	this.ricevutaXml = ricevutaXml;
    }
}
