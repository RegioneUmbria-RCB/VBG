
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.include.Allegato;


/**
 * <p>Classe Java per Pagamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Pagamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RiferimentoPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}RiferimentoPagamento"/&gt;
 *         &lt;element name="DataOraPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime"/&gt;
 *         &lt;element name="DataScadenzaPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime" minOccurs="0"/&gt;
 *         &lt;element name="Importo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Importo"/&gt;
 *         &lt;element name="RiferimentoDebitore" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp" minOccurs="0"/&gt;
 *         &lt;element name="Esito" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}TipoNotifica"/&gt;
 *         &lt;element name="Pagante" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}Pagante" minOccurs="0"/&gt;
 *         &lt;element name="Transazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}Transazione" minOccurs="0"/&gt;
 *         &lt;element name="FlagQuietanzaCartacea" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="Note" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max2000Text" minOccurs="0"/&gt;
 *         &lt;element name="Allegato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Allegato" minOccurs="0"/&gt;
 *         &lt;element name="RiferimentoDebito" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}RiferimentoDebito" minOccurs="0"/&gt;
 *         &lt;element name="DescrizioneCausale" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max256Text" minOccurs="0"/&gt;
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pagamento", propOrder = {
    "riferimentoPagamento",
    "dataOraPagamento",
    "dataScadenzaPagamento",
    "importo",
    "riferimentoDebitore",
    "esito",
    "pagante",
    "transazione",
    "flagQuietanzaCartacea",
    "note",
    "allegato",
    "riferimentoDebito",
    "descrizioneCausale",
    "identificativoUnivocoVersamento"
})
public class Pagamento {

    @XmlElement(name = "RiferimentoPagamento", required = true)
    protected RiferimentoPagamento riferimentoPagamento;
    @XmlElement(name = "DataOraPagamento", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraPagamento;
    @XmlElement(name = "DataScadenzaPagamento")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadenzaPagamento;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "RiferimentoDebitore")
    protected String riferimentoDebitore;
    @XmlElement(name = "Esito", required = true)
    @XmlSchemaType(name = "string")
    protected TipoNotifica esito;
    @XmlElement(name = "Pagante")
    protected Pagante pagante;
    @XmlElement(name = "Transazione")
    protected Transazione transazione;
    @XmlElement(name = "FlagQuietanzaCartacea")
    protected Boolean flagQuietanzaCartacea;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "Allegato")
    protected Allegato allegato;
    @XmlElement(name = "RiferimentoDebito")
    protected RiferimentoDebito riferimentoDebito;
    @XmlElement(name = "DescrizioneCausale")
    protected String descrizioneCausale;
    @XmlElement(name = "IdentificativoUnivocoVersamento")
    protected String identificativoUnivocoVersamento;

    /**
     * Recupera il valore della proprietà riferimentoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentoPagamento }
     *     
     */
    public RiferimentoPagamento getRiferimentoPagamento() {
        return riferimentoPagamento;
    }

    /**
     * Imposta il valore della proprietà riferimentoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentoPagamento }
     *     
     */
    public void setRiferimentoPagamento(RiferimentoPagamento value) {
        this.riferimentoPagamento = value;
    }

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
     * Recupera il valore della proprietà riferimentoDebitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoDebitore() {
        return riferimentoDebitore;
    }

    /**
     * Imposta il valore della proprietà riferimentoDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoDebitore(String value) {
        this.riferimentoDebitore = value;
    }

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link TipoNotifica }
     *     
     */
    public TipoNotifica getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoNotifica }
     *     
     */
    public void setEsito(TipoNotifica value) {
        this.esito = value;
    }

    /**
     * Recupera il valore della proprietà pagante.
     * 
     * @return
     *     possible object is
     *     {@link Pagante }
     *     
     */
    public Pagante getPagante() {
        return pagante;
    }

    /**
     * Imposta il valore della proprietà pagante.
     * 
     * @param value
     *     allowed object is
     *     {@link Pagante }
     *     
     */
    public void setPagante(Pagante value) {
        this.pagante = value;
    }

    /**
     * Recupera il valore della proprietà transazione.
     * 
     * @return
     *     possible object is
     *     {@link Transazione }
     *     
     */
    public Transazione getTransazione() {
        return transazione;
    }

    /**
     * Imposta il valore della proprietà transazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Transazione }
     *     
     */
    public void setTransazione(Transazione value) {
        this.transazione = value;
    }

    /**
     * Recupera il valore della proprietà flagQuietanzaCartacea.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlagQuietanzaCartacea() {
        return flagQuietanzaCartacea;
    }

    /**
     * Imposta il valore della proprietà flagQuietanzaCartacea.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagQuietanzaCartacea(Boolean value) {
        this.flagQuietanzaCartacea = value;
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
     * Recupera il valore della proprietà allegato.
     * 
     * @return
     *     possible object is
     *     {@link Allegato }
     *     
     */
    public Allegato getAllegato() {
        return allegato;
    }

    /**
     * Imposta il valore della proprietà allegato.
     * 
     * @param value
     *     allowed object is
     *     {@link Allegato }
     *     
     */
    public void setAllegato(Allegato value) {
        this.allegato = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoDebito.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentoDebito }
     *     
     */
    public RiferimentoDebito getRiferimentoDebito() {
        return riferimentoDebito;
    }

    /**
     * Imposta il valore della proprietà riferimentoDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentoDebito }
     *     
     */
    public void setRiferimentoDebito(RiferimentoDebito value) {
        this.riferimentoDebito = value;
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

}
