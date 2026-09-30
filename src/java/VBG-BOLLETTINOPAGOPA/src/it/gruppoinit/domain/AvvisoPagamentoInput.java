//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.12.11 alle 10:28:18 AM CET 
//


package it.gruppoinit.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AvvisoPagamentoInput complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AvvisoPagamentoInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="logo_ente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oggetto_del_pagamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oggetto_del_pagamento_rata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oggetto_del_pagamento_bollettino" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cf_ente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cf_destinatario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nome_cognome_destinatario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ente_creditore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="settore_ente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="indirizzo_destinatario_1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="indirizzo_destinatario_2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="info_ente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="del_tuo_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="di_poste" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="qr_code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cbill" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codice_avviso" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codice_avviso_postale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numero_cc_postale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="intestatario_conto_corrente_postale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="autorizzazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="data_matrix" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AvvisoPagamentoInput", propOrder = {
    "logoEnte",
    "oggettoDelPagamento",
    "oggettoDelPagamentoRata",
    "oggettoDelPagamentoBollettino",
    "cfEnte",
    "cfDestinatario",
    "nomeCognomeDestinatario",
    "enteCreditore",
    "settoreEnte",
    "indirizzoDestinatario1",
    "indirizzoDestinatario2",
    "infoEnte",
    "delTuoEnte",
    "diPoste",
    "importo",
    "data",
    "qrCode",
    "cbill",
    "codiceAvviso",
    "codiceAvvisoPostale",
    "numeroCcPostale",
    "intestatarioContoCorrentePostale",
    "autorizzazione",
    "dataMatrix"
})
public class AvvisoPagamentoInput {

    @XmlElement(name = "logo_ente", required = true)
    protected String logoEnte;
    @XmlElement(name = "oggetto_del_pagamento", required = true)
    protected String oggettoDelPagamento;
    @XmlElement(name = "oggetto_del_pagamento_rata", required = true)
    protected String oggettoDelPagamentoRata;
    @XmlElement(name = "oggetto_del_pagamento_bollettino", required = true)
    protected String oggettoDelPagamentoBollettino;
    @XmlElement(name = "cf_ente", required = true)
    protected String cfEnte;
    @XmlElement(name = "cf_destinatario", required = true)
    protected String cfDestinatario;
    @XmlElement(name = "nome_cognome_destinatario", required = true)
    protected String nomeCognomeDestinatario;
    @XmlElement(name = "ente_creditore", required = true)
    protected String enteCreditore;
    @XmlElement(name = "settore_ente", required = true)
    protected String settoreEnte;
    @XmlElement(name = "indirizzo_destinatario_1", required = true)
    protected String indirizzoDestinatario1;
    @XmlElement(name = "indirizzo_destinatario_2", required = true)
    protected String indirizzoDestinatario2;
    @XmlElement(name = "info_ente", required = true)
    protected String infoEnte;
    @XmlElement(name = "del_tuo_ente")
    protected String delTuoEnte;
    @XmlElement(name = "di_poste")
    protected String diPoste;
    protected double importo;
    @XmlElement(required = true)
    protected String data;
    @XmlElement(name = "qr_code", required = true)
    protected String qrCode;
    @XmlElement(required = true)
    protected String cbill;
    @XmlElement(name = "codice_avviso", required = true)
    protected String codiceAvviso;
    @XmlElement(name = "codice_avviso_postale", required = true)
    protected String codiceAvvisoPostale;
    @XmlElement(name = "numero_cc_postale", required = true)
    protected String numeroCcPostale;
    @XmlElement(name = "intestatario_conto_corrente_postale", required = true)
    protected String intestatarioContoCorrentePostale;
    @XmlElement(required = true)
    protected String autorizzazione;
    @XmlElement(name = "data_matrix", required = true)
    protected String dataMatrix;

    /**
     * Recupera il valore della proprietà logoEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogoEnte() {
        return logoEnte;
    }

    /**
     * Imposta il valore della proprietà logoEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogoEnte(String value) {
        this.logoEnte = value;
    }

    /**
     * Recupera il valore della proprietà oggettoDelPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoDelPagamento() {
        return oggettoDelPagamento;
    }

    /**
     * Imposta il valore della proprietà oggettoDelPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoDelPagamento(String value) {
        this.oggettoDelPagamento = value;
    }

    /**
     * Recupera il valore della proprietà oggettoDelPagamentoRata.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoDelPagamentoRata() {
        return oggettoDelPagamentoRata;
    }

    /**
     * Imposta il valore della proprietà oggettoDelPagamentoRata.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoDelPagamentoRata(String value) {
        this.oggettoDelPagamentoRata = value;
    }

    /**
     * Recupera il valore della proprietà oggettoDelPagamentoBollettino.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoDelPagamentoBollettino() {
        return oggettoDelPagamentoBollettino;
    }

    /**
     * Imposta il valore della proprietà oggettoDelPagamentoBollettino.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoDelPagamentoBollettino(String value) {
        this.oggettoDelPagamentoBollettino = value;
    }

    /**
     * Recupera il valore della proprietà cfEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfEnte() {
        return cfEnte;
    }

    /**
     * Imposta il valore della proprietà cfEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfEnte(String value) {
        this.cfEnte = value;
    }

    /**
     * Recupera il valore della proprietà cfDestinatario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfDestinatario() {
        return cfDestinatario;
    }

    /**
     * Imposta il valore della proprietà cfDestinatario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfDestinatario(String value) {
        this.cfDestinatario = value;
    }

    /**
     * Recupera il valore della proprietà nomeCognomeDestinatario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeCognomeDestinatario() {
        return nomeCognomeDestinatario;
    }

    /**
     * Imposta il valore della proprietà nomeCognomeDestinatario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeCognomeDestinatario(String value) {
        this.nomeCognomeDestinatario = value;
    }

    /**
     * Recupera il valore della proprietà enteCreditore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnteCreditore() {
        return enteCreditore;
    }

    /**
     * Imposta il valore della proprietà enteCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnteCreditore(String value) {
        this.enteCreditore = value;
    }

    /**
     * Recupera il valore della proprietà settoreEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSettoreEnte() {
        return settoreEnte;
    }

    /**
     * Imposta il valore della proprietà settoreEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSettoreEnte(String value) {
        this.settoreEnte = value;
    }

    /**
     * Recupera il valore della proprietà indirizzoDestinatario1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoDestinatario1() {
        return indirizzoDestinatario1;
    }

    /**
     * Imposta il valore della proprietà indirizzoDestinatario1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoDestinatario1(String value) {
        this.indirizzoDestinatario1 = value;
    }

    /**
     * Recupera il valore della proprietà indirizzoDestinatario2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoDestinatario2() {
        return indirizzoDestinatario2;
    }

    /**
     * Imposta il valore della proprietà indirizzoDestinatario2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoDestinatario2(String value) {
        this.indirizzoDestinatario2 = value;
    }

    /**
     * Recupera il valore della proprietà infoEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfoEnte() {
        return infoEnte;
    }

    /**
     * Imposta il valore della proprietà infoEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfoEnte(String value) {
        this.infoEnte = value;
    }

    /**
     * Recupera il valore della proprietà delTuoEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelTuoEnte() {
        return delTuoEnte;
    }

    /**
     * Imposta il valore della proprietà delTuoEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelTuoEnte(String value) {
        this.delTuoEnte = value;
    }

    /**
     * Recupera il valore della proprietà diPoste.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiPoste() {
        return diPoste;
    }

    /**
     * Imposta il valore della proprietà diPoste.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiPoste(String value) {
        this.diPoste = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     */
    public double getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     */
    public void setImporto(double value) {
        this.importo = value;
    }

    /**
     * Recupera il valore della proprietà data.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getData() {
        return data;
    }

    /**
     * Imposta il valore della proprietà data.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(String value) {
        this.data = value;
    }

    /**
     * Recupera il valore della proprietà qrCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQrCode() {
        return qrCode;
    }

    /**
     * Imposta il valore della proprietà qrCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQrCode(String value) {
        this.qrCode = value;
    }

    /**
     * Recupera il valore della proprietà cbill.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCbill() {
        return cbill;
    }

    /**
     * Imposta il valore della proprietà cbill.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCbill(String value) {
        this.cbill = value;
    }

    /**
     * Recupera il valore della proprietà codiceAvviso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAvviso() {
        return codiceAvviso;
    }

    /**
     * Imposta il valore della proprietà codiceAvviso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAvviso(String value) {
        this.codiceAvviso = value;
    }

    /**
     * Recupera il valore della proprietà codiceAvvisoPostale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAvvisoPostale() {
        return codiceAvvisoPostale;
    }

    /**
     * Imposta il valore della proprietà codiceAvvisoPostale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAvvisoPostale(String value) {
        this.codiceAvvisoPostale = value;
    }

    /**
     * Recupera il valore della proprietà numeroCcPostale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCcPostale() {
        return numeroCcPostale;
    }

    /**
     * Imposta il valore della proprietà numeroCcPostale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCcPostale(String value) {
        this.numeroCcPostale = value;
    }

    /**
     * Recupera il valore della proprietà intestatarioContoCorrentePostale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntestatarioContoCorrentePostale() {
        return intestatarioContoCorrentePostale;
    }

    /**
     * Imposta il valore della proprietà intestatarioContoCorrentePostale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntestatarioContoCorrentePostale(String value) {
        this.intestatarioContoCorrentePostale = value;
    }

    /**
     * Recupera il valore della proprietà autorizzazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutorizzazione() {
        return autorizzazione;
    }

    /**
     * Imposta il valore della proprietà autorizzazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutorizzazione(String value) {
        this.autorizzazione = value;
    }

    /**
     * Recupera il valore della proprietà dataMatrix.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataMatrix() {
        return dataMatrix;
    }

    /**
     * Imposta il valore della proprietà dataMatrix.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataMatrix(String value) {
        this.dataMatrix = value;
    }

}
