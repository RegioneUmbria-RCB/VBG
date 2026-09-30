
package it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paInviaCarrelloPosizioniListaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paInviaCarrelloPosizioniListaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="spontaneo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identificativoBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ccp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codicePa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceServizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoIdPagatore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identificativoPagatore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="anagraficaPagatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzoPagatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="civicoPagatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="capPagatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localitaPagatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provinciaPagatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceNazionePagatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailPagatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataScadenzaPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importoPagamento" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="tipoFirmaRicevuta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoRiferimentoCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceRiferimentoCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativoUnivocoVersamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoContabilita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceContabilita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datiSingoloVersamento" type="{http://services.sia.eu/}datiSingoloVersamentoListaType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paInviaCarrelloPosizioniListaType", propOrder = {
    "spontaneo",
    "identificativoBeneficiario",
    "ccp",
    "codicePa",
    "codiceServizio",
    "tipoIdPagatore",
    "identificativoPagatore",
    "anagraficaPagatore",
    "indirizzoPagatore",
    "civicoPagatore",
    "capPagatore",
    "localitaPagatore",
    "provinciaPagatore",
    "codiceNazionePagatore",
    "emailPagatore",
    "dataScadenzaPagamento",
    "importoPagamento",
    "tipoFirmaRicevuta",
    "tipoRiferimentoCreditore",
    "codiceRiferimentoCreditore",
    "identificativoUnivocoVersamento",
    "tipoContabilita",
    "codiceContabilita",
    "datiSingoloVersamento"
})
public class PaInviaCarrelloPosizioniListaType {

    @XmlElement(name = "spontaneo", required = true)
    protected String spontaneo;
    @XmlElement(name="identificativoBeneficiario", required = true)
    protected String identificativoBeneficiario;
    @XmlElement(name="ccp")
    protected String ccp;
    @XmlElement(name="codicePa")
    protected String codicePa;
    @XmlElement(name="codiceServizio")
    protected String codiceServizio;
    @XmlElement(required = true)
    protected String tipoIdPagatore;
    @XmlElement(required = true)
    protected String identificativoPagatore;
    protected String anagraficaPagatore;
    protected String indirizzoPagatore;
    protected String civicoPagatore;
    protected String capPagatore;
    protected String localitaPagatore;
    protected String provinciaPagatore;
    protected String codiceNazionePagatore;
    protected String emailPagatore;
    protected String dataScadenzaPagamento;
    @XmlElement(required = true)
    protected BigDecimal importoPagamento;
    @XmlElement(required = true)
    protected String tipoFirmaRicevuta;
    protected String tipoRiferimentoCreditore;
    protected String codiceRiferimentoCreditore;
    protected String identificativoUnivocoVersamento;
    protected String tipoContabilita;
    protected String codiceContabilita;
    @XmlElement(required = true)
    protected List<DatiSingoloVersamentoListaType> datiSingoloVersamento;

    /**
     * Gets the value of the spontaneo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpontaneo() {
        return spontaneo;
    }

    /**
     * Sets the value of the spontaneo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpontaneo(String value) {
        this.spontaneo = value;
    }

    /**
     * Gets the value of the identificativoBeneficiario property.
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
     * Sets the value of the identificativoBeneficiario property.
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
     * Gets the value of the ccp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcp() {
        return ccp;
    }

    /**
     * Sets the value of the ccp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcp(String value) {
        this.ccp = value;
    }

    /**
     * Gets the value of the codicePa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicePa() {
        return codicePa;
    }

    /**
     * Sets the value of the codicePa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicePa(String value) {
        this.codicePa = value;
    }

    /**
     * Gets the value of the codiceServizio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceServizio() {
        return codiceServizio;
    }

    /**
     * Sets the value of the codiceServizio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceServizio(String value) {
        this.codiceServizio = value;
    }

    /**
     * Gets the value of the tipoIdPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoIdPagatore() {
        return tipoIdPagatore;
    }

    /**
     * Sets the value of the tipoIdPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoIdPagatore(String value) {
        this.tipoIdPagatore = value;
    }

    /**
     * Gets the value of the identificativoPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPagatore() {
        return identificativoPagatore;
    }

    /**
     * Sets the value of the identificativoPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPagatore(String value) {
        this.identificativoPagatore = value;
    }

    /**
     * Gets the value of the anagraficaPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagraficaPagatore() {
        return anagraficaPagatore;
    }

    /**
     * Sets the value of the anagraficaPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagraficaPagatore(String value) {
        this.anagraficaPagatore = value;
    }

    /**
     * Gets the value of the indirizzoPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoPagatore() {
        return indirizzoPagatore;
    }

    /**
     * Sets the value of the indirizzoPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoPagatore(String value) {
        this.indirizzoPagatore = value;
    }

    /**
     * Gets the value of the civicoPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivicoPagatore() {
        return civicoPagatore;
    }

    /**
     * Sets the value of the civicoPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivicoPagatore(String value) {
        this.civicoPagatore = value;
    }

    /**
     * Gets the value of the capPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapPagatore() {
        return capPagatore;
    }

    /**
     * Sets the value of the capPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapPagatore(String value) {
        this.capPagatore = value;
    }

    /**
     * Gets the value of the localitaPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalitaPagatore() {
        return localitaPagatore;
    }

    /**
     * Sets the value of the localitaPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalitaPagatore(String value) {
        this.localitaPagatore = value;
    }

    /**
     * Gets the value of the provinciaPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaPagatore() {
        return provinciaPagatore;
    }

    /**
     * Sets the value of the provinciaPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaPagatore(String value) {
        this.provinciaPagatore = value;
    }

    /**
     * Gets the value of the codiceNazionePagatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceNazionePagatore() {
        return codiceNazionePagatore;
    }

    /**
     * Sets the value of the codiceNazionePagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceNazionePagatore(String value) {
        this.codiceNazionePagatore = value;
    }

    /**
     * Gets the value of the emailPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailPagatore() {
        return emailPagatore;
    }

    /**
     * Sets the value of the emailPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailPagatore(String value) {
        this.emailPagatore = value;
    }

    /**
     * Gets the value of the dataScadenzaPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataScadenzaPagamento() {
        return dataScadenzaPagamento;
    }

    /**
     * Sets the value of the dataScadenzaPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataScadenzaPagamento(String value) {
        this.dataScadenzaPagamento = value;
    }

    /**
     * Gets the value of the importoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoPagamento() {
        return importoPagamento;
    }

    /**
     * Sets the value of the importoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoPagamento(BigDecimal value) {
        this.importoPagamento = value;
    }

    /**
     * Gets the value of the tipoFirmaRicevuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoFirmaRicevuta() {
        return tipoFirmaRicevuta;
    }

    /**
     * Sets the value of the tipoFirmaRicevuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoFirmaRicevuta(String value) {
        this.tipoFirmaRicevuta = value;
    }

    /**
     * Gets the value of the tipoRiferimentoCreditore property.
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
     * Sets the value of the tipoRiferimentoCreditore property.
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
     * Gets the value of the codiceRiferimentoCreditore property.
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
     * Sets the value of the codiceRiferimentoCreditore property.
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
     * Gets the value of the identificativoUnivocoVersamento property.
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
     * Sets the value of the identificativoUnivocoVersamento property.
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
     * Gets the value of the tipoContabilita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoContabilita() {
        return tipoContabilita;
    }

    /**
     * Sets the value of the tipoContabilita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoContabilita(String value) {
        this.tipoContabilita = value;
    }

    /**
     * Gets the value of the codiceContabilita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceContabilita() {
        return codiceContabilita;
    }

    /**
     * Sets the value of the codiceContabilita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceContabilita(String value) {
        this.codiceContabilita = value;
    }

    /**
     * Gets the value of the datiSingoloVersamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiSingoloVersamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiSingoloVersamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiSingoloVersamentoListaType }
     * 
     * 
     */
    public List<DatiSingoloVersamentoListaType> getDatiSingoloVersamento() {
        if (datiSingoloVersamento == null) {
            datiSingoloVersamento = new ArrayList<DatiSingoloVersamentoListaType>();
        }
        return this.datiSingoloVersamento;
    }

}
