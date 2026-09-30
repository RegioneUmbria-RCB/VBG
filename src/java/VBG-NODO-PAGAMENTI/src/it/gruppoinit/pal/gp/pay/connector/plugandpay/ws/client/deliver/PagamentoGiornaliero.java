
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <pClasse Java per PagamentoGiornaliero complex type.
 * 
 * <pIl seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre
 * &lt;complexType name="PagamentoGiornaliero"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CanaleDiPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Causale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceFiscalePartitaIva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceRiferimentoCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DataDiPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DataRegistrazionePagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DataRegolamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Esito" type="{http://schemas.datacontract.org/2004/07/PlugAndPay.DigitBusNodoPA.Erogazione.QueryStack.Model}EsitoPagamento" minOccurs="0"/&gt;
 *         &lt;element name="IdCanaleDiPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IdFlussoRiversamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IdRiscossione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IdentificativoPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportoPagato" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="ModalitaDiPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NominativoDebitore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OraRegistrazionePagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TipoRiferimentoCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PagamentoGiornaliero", propOrder = {
    "canaleDiPagamento",
    "causale",
    "codiceFiscalePartitaIva",
    "codiceRiferimentoCreditore",
    "dataDiPagamento",
    "dataRegistrazionePagamento",
    "dataRegolamento",
    "esito",
    "idCanaleDiPagamento",
    "idFlussoRiversamento",
    "idRiscossione",
    "identificativoPagamento",
    "importoPagato",
    "modalitaDiPagamento",
    "nominativoDebitore",
    "oraRegistrazionePagamento",
    "tipoRiferimentoCreditore"
})
public class PagamentoGiornaliero {

    @XmlElement(name = "CanaleDiPagamento", required = false)
    protected String canaleDiPagamento;
    @XmlElement(name = "Causale", required = false)
    protected String causale;
    @XmlElement(name = "CodiceFiscalePartitaIva", required = false)
    protected String codiceFiscalePartitaIva;
    @XmlElement(name = "CodiceRiferimentoCreditore", required = false)
    protected String codiceRiferimentoCreditore;
    @XmlElement(name = "DataDiPagamento", required = false)
    protected String dataDiPagamento;
    @XmlElement(name = "DataRegistrazionePagamento", required = false)
    protected String dataRegistrazionePagamento;
    @XmlElement(name = "DataRegolamento", required = false)
    protected String dataRegolamento;
    @XmlElement(name = "Esito", required = false)
    protected EsitoPagamento esito;
    @XmlElement(name = "IdCanaleDiPagamento", required = false)
    protected String idCanaleDiPagamento;
    @XmlElement(name = "IdFlussoRiversamento", required = false)
    protected String idFlussoRiversamento;
    @XmlElement(name = "IdRiscossione", required = false)
    protected String idRiscossione;
    @XmlElement(name = "IdentificativoPagamento", required = false)
    protected String identificativoPagamento;
    @XmlElement(name = "ImportoPagato")
    protected Long importoPagato;
    @XmlElement(name = "ModalitaDiPagamento", required = false)
    protected String modalitaDiPagamento;
    @XmlElement(name = "NominativoDebitore", required = false)
    protected String nominativoDebitore;
    @XmlElement(name = "OraRegistrazionePagamento", required = false)
    protected String oraRegistrazionePagamento;
    @XmlElement(name = "TipoRiferimentoCreditore", required = false)
    protected String tipoRiferimentoCreditore;

    /**
     * Recupera il valore della proprietà canaleDiPagamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getCanaleDiPagamento() {
        return canaleDiPagamento;
    }

    /**
     * Imposta il valore della proprietà canaleDiPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setCanaleDiPagamento(String value) {
        this.canaleDiPagamento = value;
    }

    /**
     * Recupera il valore della proprietà causale.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getCausale() {
        return causale;
    }

    /**
     * Imposta il valore della proprietà causale.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setCausale(String value) {
        this.causale = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscalePartitaIva.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getCodiceFiscalePartitaIva() {
        return codiceFiscalePartitaIva;
    }

    /**
     * Imposta il valore della proprietà codiceFiscalePartitaIva.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setCodiceFiscalePartitaIva(String value) {
        this.codiceFiscalePartitaIva = value;
    }

    /**
     * Recupera il valore della proprietà codiceRiferimentoCreditore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getCodiceRiferimentoCreditore() {
        return codiceRiferimentoCreditore;
    }

    /**
     * Imposta il valore della proprietà codiceRiferimentoCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setCodiceRiferimentoCreditore(String value) {
        this.codiceRiferimentoCreditore = value;
    }

    /**
     * Recupera il valore della proprietà dataDiPagamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getDataDiPagamento() {
        return dataDiPagamento;
    }

    /**
     * Imposta il valore della proprietà dataDiPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setDataDiPagamento(String value) {
        this.dataDiPagamento = value;
    }

    /**
     * Recupera il valore della proprietà dataRegistrazionePagamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getDataRegistrazionePagamento() {
        return dataRegistrazionePagamento;
    }

    /**
     * Imposta il valore della proprietà dataRegistrazionePagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setDataRegistrazionePagamento(String value) {
        this.dataRegistrazionePagamento = value;
    }

    /**
     * Recupera il valore della proprietà dataRegolamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getDataRegolamento() {
        return dataRegolamento;
    }

    /**
     * Imposta il valore della proprietà dataRegolamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setDataRegolamento(String value) {
        this.dataRegolamento = value;
    }

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link EsitoPagamento }{@code }
     *     
     */
    public EsitoPagamento getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link EsitoPagamento }{@code }
     *     
     */
    public void setEsito(EsitoPagamento value) {
        this.esito = value;
    }

    /**
     * Recupera il valore della proprietà idCanaleDiPagamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getIdCanaleDiPagamento() {
        return idCanaleDiPagamento;
    }

    /**
     * Imposta il valore della proprietà idCanaleDiPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setIdCanaleDiPagamento(String value) {
        this.idCanaleDiPagamento = value;
    }

    /**
     * Recupera il valore della proprietà idFlussoRiversamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getIdFlussoRiversamento() {
        return idFlussoRiversamento;
    }

    /**
     * Imposta il valore della proprietà idFlussoRiversamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setIdFlussoRiversamento(String value) {
        this.idFlussoRiversamento = value;
    }

    /**
     * Recupera il valore della proprietà idRiscossione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getIdRiscossione() {
        return idRiscossione;
    }

    /**
     * Imposta il valore della proprietà idRiscossione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setIdRiscossione(String value) {
        this.idRiscossione = value;
    }

    /**
     * Recupera il valore della proprietà identificativoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getIdentificativoPagamento() {
        return identificativoPagamento;
    }

    /**
     * Imposta il valore della proprietà identificativoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setIdentificativoPagamento(String value) {
        this.identificativoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà importoPagato.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getImportoPagato() {
        return importoPagato;
    }

    /**
     * Imposta il valore della proprietà importoPagato.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setImportoPagato(Long value) {
        this.importoPagato = value;
    }

    /**
     * Recupera il valore della proprietà modalitaDiPagamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getModalitaDiPagamento() {
        return modalitaDiPagamento;
    }

    /**
     * Imposta il valore della proprietà modalitaDiPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setModalitaDiPagamento(String value) {
        this.modalitaDiPagamento = value;
    }

    /**
     * Recupera il valore della proprietà nominativoDebitore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getNominativoDebitore() {
        return nominativoDebitore;
    }

    /**
     * Imposta il valore della proprietà nominativoDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setNominativoDebitore(String value) {
        this.nominativoDebitore = value;
    }

    /**
     * Recupera il valore della proprietà oraRegistrazionePagamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getOraRegistrazionePagamento() {
        return oraRegistrazionePagamento;
    }

    /**
     * Imposta il valore della proprietà oraRegistrazionePagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setOraRegistrazionePagamento(String value) {
        this.oraRegistrazionePagamento = value;
    }

    /**
     * Recupera il valore della proprietà tipoRiferimentoCreditore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getTipoRiferimentoCreditore() {
        return tipoRiferimentoCreditore;
    }

    /**
     * Imposta il valore della proprietà tipoRiferimentoCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setTipoRiferimentoCreditore(String value) {
        this.tipoRiferimentoCreditore = value;
    }

}
