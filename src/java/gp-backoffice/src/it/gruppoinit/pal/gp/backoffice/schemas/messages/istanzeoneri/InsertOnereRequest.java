
package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="riferimentoIstanza" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="riferimentoCausale" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="riferimentoEndoprocedimento" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="codiceresponsabile" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="datascadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataPagamento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modalitaPagamento" type="{http://gruppoinit.it/sigepro/schemas/messages/istanzeoneri}ModalitaPagamentoType" minOccurs="0"/>
 *         &lt;element name="importopagato" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="codiceamministrazioni" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="numerorata" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="importoInteresse" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="onereRateizzato" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="riferimentiPagamento" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="note" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="4000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "token",
    "riferimentoIstanza",
    "importo",
    "riferimentoCausale",
    "riferimentoEndoprocedimento",
    "codiceresponsabile",
    "datascadenza",
    "dataPagamento",
    "modalitaPagamento",
    "importopagato",
    "codiceamministrazioni",
    "numerorata",
    "importoInteresse",
    "onereRateizzato",
    "riferimentiPagamento",
    "note"
})
@XmlRootElement(name = "InsertOnereRequest")
public class InsertOnereRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected BigInteger riferimentoIstanza;
    @XmlElement(required = true)
    protected BigDecimal importo;
    @XmlElement(required = true)
    protected BigInteger riferimentoCausale;
    protected BigInteger riferimentoEndoprocedimento;
    protected BigInteger codiceresponsabile;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datascadenza;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPagamento;
    protected ModalitaPagamentoType modalitaPagamento;
    protected BigDecimal importopagato;
    protected BigInteger codiceamministrazioni;
    protected BigInteger numerorata;
    protected BigDecimal importoInteresse;
    protected Boolean onereRateizzato;
    protected String riferimentiPagamento;
    protected String note;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the riferimentoIstanza property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRiferimentoIstanza() {
        return riferimentoIstanza;
    }

    /**
     * Sets the value of the riferimentoIstanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRiferimentoIstanza(BigInteger value) {
        this.riferimentoIstanza = value;
    }

    /**
     * Gets the value of the importo property.
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
     * Sets the value of the importo property.
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
     * Gets the value of the riferimentoCausale property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRiferimentoCausale() {
        return riferimentoCausale;
    }

    /**
     * Sets the value of the riferimentoCausale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRiferimentoCausale(BigInteger value) {
        this.riferimentoCausale = value;
    }

    /**
     * Gets the value of the riferimentoEndoprocedimento property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRiferimentoEndoprocedimento() {
        return riferimentoEndoprocedimento;
    }

    /**
     * Sets the value of the riferimentoEndoprocedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRiferimentoEndoprocedimento(BigInteger value) {
        this.riferimentoEndoprocedimento = value;
    }

    /**
     * Gets the value of the codiceresponsabile property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodiceresponsabile() {
        return codiceresponsabile;
    }

    /**
     * Sets the value of the codiceresponsabile property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodiceresponsabile(BigInteger value) {
        this.codiceresponsabile = value;
    }

    /**
     * Gets the value of the datascadenza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatascadenza() {
        return datascadenza;
    }

    /**
     * Sets the value of the datascadenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatascadenza(XMLGregorianCalendar value) {
        this.datascadenza = value;
    }

    /**
     * Gets the value of the dataPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPagamento() {
        return dataPagamento;
    }

    /**
     * Sets the value of the dataPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPagamento(XMLGregorianCalendar value) {
        this.dataPagamento = value;
    }

    /**
     * Gets the value of the modalitaPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link ModalitaPagamentoType }
     *     
     */
    public ModalitaPagamentoType getModalitaPagamento() {
        return modalitaPagamento;
    }

    /**
     * Sets the value of the modalitaPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModalitaPagamentoType }
     *     
     */
    public void setModalitaPagamento(ModalitaPagamentoType value) {
        this.modalitaPagamento = value;
    }

    /**
     * Gets the value of the importopagato property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportopagato() {
        return importopagato;
    }

    /**
     * Sets the value of the importopagato property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportopagato(BigDecimal value) {
        this.importopagato = value;
    }

    /**
     * Gets the value of the codiceamministrazioni property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodiceamministrazioni() {
        return codiceamministrazioni;
    }

    /**
     * Sets the value of the codiceamministrazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodiceamministrazioni(BigInteger value) {
        this.codiceamministrazioni = value;
    }

    /**
     * Gets the value of the numerorata property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumerorata() {
        return numerorata;
    }

    /**
     * Sets the value of the numerorata property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumerorata(BigInteger value) {
        this.numerorata = value;
    }

    /**
     * Gets the value of the importoInteresse property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoInteresse() {
        return importoInteresse;
    }

    /**
     * Sets the value of the importoInteresse property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoInteresse(BigDecimal value) {
        this.importoInteresse = value;
    }

    /**
     * Gets the value of the onereRateizzato property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOnereRateizzato() {
        return onereRateizzato;
    }

    /**
     * Sets the value of the onereRateizzato property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOnereRateizzato(Boolean value) {
        this.onereRateizzato = value;
    }

    /**
     * Gets the value of the riferimentiPagamento property.
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
     * Sets the value of the riferimentiPagamento property.
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
     * Gets the value of the note property.
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
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

}
