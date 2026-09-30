
package it.gruppoinit.pal.gp.pay.connector.easypa.ws.gestoreposizioni.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per inserimentoPosizioneInputType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="inserimentoPosizioneInputType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identificativo_beneficiario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codice_servizio" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="tipo_riferimento_creditore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codice_riferimento_creditore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codice_identificativo_presentazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="identificativo_univoco_versamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="data_scadenza_pagamento" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="causale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipo_id_debitore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="identificativo_debitore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="anagrafica_debitore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="indirizzo_debitore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="civico_debitore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cap_debitore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="localita_debitore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="provincia_debitore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nazione_debitore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="email_debitore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="savv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ckey_5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ckey_6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inserimentoPosizioneInputType", propOrder = {
    "identificativoBeneficiario",
    "codiceServizio",
    "tipoRiferimentoCreditore",
    "codiceRiferimentoCreditore",
    "codiceIdentificativoPresentazione",
    "identificativoUnivocoVersamento",
    "dataScadenzaPagamento",
    "importo",
    "causale",
    "tipoIdDebitore",
    "identificativoDebitore",
    "anagraficaDebitore",
    "indirizzoDebitore",
    "civicoDebitore",
    "capDebitore",
    "localitaDebitore",
    "provinciaDebitore",
    "nazioneDebitore",
    "emailDebitore",
    "savv",
    "ckey5",
    "ckey6"
})
public class InserimentoPosizioneInputType {

    @XmlElement(name = "identificativo_beneficiario", required = true)
    protected String identificativoBeneficiario;
    @XmlElement(name = "codice_servizio")
    protected int codiceServizio;
    @XmlElement(name = "tipo_riferimento_creditore", required = true)
    protected String tipoRiferimentoCreditore;
    @XmlElement(name = "codice_riferimento_creditore", required = true)
    protected String codiceRiferimentoCreditore;
    @XmlElement(name = "codice_identificativo_presentazione")
    protected String codiceIdentificativoPresentazione;
    @XmlElement(name = "identificativo_univoco_versamento")
    protected String identificativoUnivocoVersamento;
    @XmlElement(name = "data_scadenza_pagamento")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataScadenzaPagamento;
    @XmlElement(required = true)
    protected BigDecimal importo;
    @XmlElement(required = true)
    protected String causale;
    @XmlElement(name = "tipo_id_debitore", required = true)
    protected String tipoIdDebitore;
    @XmlElement(name = "identificativo_debitore")
    protected String identificativoDebitore;
    @XmlElement(name = "anagrafica_debitore")
    protected String anagraficaDebitore;
    @XmlElement(name = "indirizzo_debitore")
    protected String indirizzoDebitore;
    @XmlElement(name = "civico_debitore")
    protected String civicoDebitore;
    @XmlElement(name = "cap_debitore")
    protected String capDebitore;
    @XmlElement(name = "localita_debitore")
    protected String localitaDebitore;
    @XmlElement(name = "provincia_debitore")
    protected String provinciaDebitore;
    @XmlElement(name = "nazione_debitore")
    protected String nazioneDebitore;
    @XmlElement(name = "email_debitore")
    protected String emailDebitore;
    protected String savv;
    @XmlElement(name = "ckey_5")
    protected String ckey5;
    @XmlElement(name = "ckey_6")
    protected String ckey6;

    /**
     * Recupera il valore della proprietà identificativoBeneficiario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoBeneficiario() {
        return identificativoBeneficiario;
    }

    /**
     * Imposta il valore della proprietà identificativoBeneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoBeneficiario(String value) {
        this.identificativoBeneficiario = value;
    }

    /**
     * Recupera il valore della proprietà codiceServizio.
     * 
     */
    public int getCodiceServizio() {
        return codiceServizio;
    }

    /**
     * Imposta il valore della proprietà codiceServizio.
     * 
     */
    public void setCodiceServizio(int value) {
        this.codiceServizio = value;
    }

    /**
     * Recupera il valore della proprietà tipoRiferimentoCreditore.
     * 
     * @return
     *     possible object is
     *     {@link String }
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
     *     {@link String }
     *     
     */
    public void setTipoRiferimentoCreditore(String value) {
        this.tipoRiferimentoCreditore = value;
    }

    /**
     * Recupera il valore della proprietà codiceRiferimentoCreditore.
     * 
     * @return
     *     possible object is
     *     {@link String }
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
     *     {@link String }
     *     
     */
    public void setCodiceRiferimentoCreditore(String value) {
        this.codiceRiferimentoCreditore = value;
    }

    /**
     * Recupera il valore della proprietà codiceIdentificativoPresentazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIdentificativoPresentazione() {
        return codiceIdentificativoPresentazione;
    }

    /**
     * Imposta il valore della proprietà codiceIdentificativoPresentazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIdentificativoPresentazione(String value) {
        this.codiceIdentificativoPresentazione = value;
    }

    /**
     * Recupera il valore della proprietà identificativoUnivocoVersamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoVersamento() {
        return identificativoUnivocoVersamento;
    }

    /**
     * Imposta il valore della proprietà identificativoUnivocoVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoVersamento(String value) {
        this.identificativoUnivocoVersamento = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenzaPagamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenzaPagamento() {
        return dataScadenzaPagamento;
    }

    /**
     * Imposta il valore della proprietà dataScadenzaPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenzaPagamento(XMLGregorianCalendar value) {
        this.dataScadenzaPagamento = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

    /**
     * Recupera il valore della proprietà causale.
     * 
     * @return
     *     possible object is
     *     {@link String }
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
     *     {@link String }
     *     
     */
    public void setCausale(String value) {
        this.causale = value;
    }

    /**
     * Recupera il valore della proprietà tipoIdDebitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoIdDebitore() {
        return tipoIdDebitore;
    }

    /**
     * Imposta il valore della proprietà tipoIdDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoIdDebitore(String value) {
        this.tipoIdDebitore = value;
    }

    /**
     * Recupera il valore della proprietà identificativoDebitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoDebitore() {
        return identificativoDebitore;
    }

    /**
     * Imposta il valore della proprietà identificativoDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoDebitore(String value) {
        this.identificativoDebitore = value;
    }

    /**
     * Recupera il valore della proprietà anagraficaDebitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagraficaDebitore() {
        return anagraficaDebitore;
    }

    /**
     * Imposta il valore della proprietà anagraficaDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagraficaDebitore(String value) {
        this.anagraficaDebitore = value;
    }

    /**
     * Recupera il valore della proprietà indirizzoDebitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoDebitore() {
        return indirizzoDebitore;
    }

    /**
     * Imposta il valore della proprietà indirizzoDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoDebitore(String value) {
        this.indirizzoDebitore = value;
    }

    /**
     * Recupera il valore della proprietà civicoDebitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivicoDebitore() {
        return civicoDebitore;
    }

    /**
     * Imposta il valore della proprietà civicoDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivicoDebitore(String value) {
        this.civicoDebitore = value;
    }

    /**
     * Recupera il valore della proprietà capDebitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapDebitore() {
        return capDebitore;
    }

    /**
     * Imposta il valore della proprietà capDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapDebitore(String value) {
        this.capDebitore = value;
    }

    /**
     * Recupera il valore della proprietà localitaDebitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalitaDebitore() {
        return localitaDebitore;
    }

    /**
     * Imposta il valore della proprietà localitaDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalitaDebitore(String value) {
        this.localitaDebitore = value;
    }

    /**
     * Recupera il valore della proprietà provinciaDebitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaDebitore() {
        return provinciaDebitore;
    }

    /**
     * Imposta il valore della proprietà provinciaDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaDebitore(String value) {
        this.provinciaDebitore = value;
    }

    /**
     * Recupera il valore della proprietà nazioneDebitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazioneDebitore() {
        return nazioneDebitore;
    }

    /**
     * Imposta il valore della proprietà nazioneDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazioneDebitore(String value) {
        this.nazioneDebitore = value;
    }

    /**
     * Recupera il valore della proprietà emailDebitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailDebitore() {
        return emailDebitore;
    }

    /**
     * Imposta il valore della proprietà emailDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailDebitore(String value) {
        this.emailDebitore = value;
    }

    /**
     * Recupera il valore della proprietà savv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSavv() {
        return savv;
    }

    /**
     * Imposta il valore della proprietà savv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSavv(String value) {
        this.savv = value;
    }

    /**
     * Recupera il valore della proprietà ckey5.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCkey5() {
        return ckey5;
    }

    /**
     * Imposta il valore della proprietà ckey5.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCkey5(String value) {
        this.ckey5 = value;
    }

    /**
     * Recupera il valore della proprietà ckey6.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCkey6() {
        return ckey6;
    }

    /**
     * Imposta il valore della proprietà ckey6.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCkey6(String value) {
        this.ckey6 = value;
    }

}
