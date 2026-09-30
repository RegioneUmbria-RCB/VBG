
package it.gruppoinit.schemas.messages.utilitypagopa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="logoEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oggettoDelPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oggettoDelPagamentoRata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oggettoDelPagamentoBollettino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cfEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cfDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nomeCognomeDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="enteCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="settoreEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="indirizzoDestinatario1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="indirizzoDestinatario2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="infoEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="delTuoEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="diPoste" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="qrCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cbill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codiceAvviso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codiceAvvisoPostale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroCcPostale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="intestatarioContoCorrentePostale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="autorizzazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataMatrix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
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
@XmlRootElement(name = "BollettinopagopaRequest")
public class BollettinopagopaRequest {

    protected String logoEnte;
    protected String oggettoDelPagamento;
    protected String oggettoDelPagamentoRata;
    protected String oggettoDelPagamentoBollettino;
    protected String cfEnte;
    protected String cfDestinatario;
    protected String nomeCognomeDestinatario;
    protected String enteCreditore;
    protected String settoreEnte;
    protected String indirizzoDestinatario1;
    protected String indirizzoDestinatario2;
    protected String infoEnte;
    protected String delTuoEnte;
    protected String diPoste;
    protected String importo;
    protected String data;
    protected String qrCode;
    protected String cbill;
    protected String codiceAvviso;
    protected String codiceAvvisoPostale;
    protected String numeroCcPostale;
    protected String intestatarioContoCorrentePostale;
    protected String autorizzazione;
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
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporto(String value) {
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
