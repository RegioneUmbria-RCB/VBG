//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ctRiconciliazionePagamentoSingolo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctRiconciliazionePagamentoSingolo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdentificativoDominio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodiceContestoPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdentificativoMessaggioRicevuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataOraMessaggioRicevuta" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="CodiceTipoDebito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdentificativoUnivocoRiscossione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SingoloImportoPagato" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stImporto"/>
 *         &lt;element name="DatiVersante" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctSoggetto" minOccurs="0"/>
 *         &lt;element name="DatiDebitore" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctSoggetto" minOccurs="0"/>
 *         &lt;element name="CodiceEsitoSingoloPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataEsitoSingoloPagamento" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="CausaleSingoloPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Debito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctRiconciliazioneDebito" minOccurs="0"/>
 *         &lt;element name="EsitoRiconciliazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Messaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctRiconciliazionePagamentoSingolo", propOrder = {
    "identificativoUnivocoVersamento",
    "identificativoDominio",
    "codiceContestoPagamento",
    "identificativoMessaggioRicevuta",
    "dataOraMessaggioRicevuta",
    "codiceTipoDebito",
    "identificativoUnivocoRiscossione",
    "singoloImportoPagato",
    "datiVersante",
    "datiDebitore",
    "codiceEsitoSingoloPagamento",
    "dataEsitoSingoloPagamento",
    "causaleSingoloPagamento",
    "debito",
    "esitoRiconciliazione",
    "messaggio"
})
public class CtRiconciliazionePagamentoSingolo {

    @XmlElement(name = "IdentificativoUnivocoVersamento", required = true)
    protected String identificativoUnivocoVersamento;
    @XmlElement(name = "IdentificativoDominio")
    protected String identificativoDominio;
    @XmlElement(name = "CodiceContestoPagamento")
    protected String codiceContestoPagamento;
    @XmlElement(name = "IdentificativoMessaggioRicevuta")
    protected String identificativoMessaggioRicevuta;
    @XmlElement(name = "DataOraMessaggioRicevuta")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataOraMessaggioRicevuta;
    @XmlElement(name = "CodiceTipoDebito")
    protected String codiceTipoDebito;
    @XmlElement(name = "IdentificativoUnivocoRiscossione", required = true)
    protected String identificativoUnivocoRiscossione;
    @XmlElement(name = "SingoloImportoPagato", required = true)
    protected BigDecimal singoloImportoPagato;
    @XmlElement(name = "DatiVersante")
    protected CtSoggetto datiVersante;
    @XmlElement(name = "DatiDebitore")
    protected CtSoggetto datiDebitore;
    @XmlElement(name = "CodiceEsitoSingoloPagamento")
    protected String codiceEsitoSingoloPagamento;
    @XmlElement(name = "DataEsitoSingoloPagamento", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataEsitoSingoloPagamento;
    @XmlElement(name = "CausaleSingoloPagamento")
    protected String causaleSingoloPagamento;
    @XmlElement(name = "Debito")
    protected CtRiconciliazioneDebito debito;
    @XmlElement(name = "EsitoRiconciliazione")
    protected String esitoRiconciliazione;
    @XmlElement(name = "Messaggio")
    protected String messaggio;

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
     * Recupera il valore della proprietà identificativoDominio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoDominio() {
        return identificativoDominio;
    }

    /**
     * Imposta il valore della proprietà identificativoDominio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoDominio(String value) {
        this.identificativoDominio = value;
    }

    /**
     * Recupera il valore della proprietà codiceContestoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceContestoPagamento() {
        return codiceContestoPagamento;
    }

    /**
     * Imposta il valore della proprietà codiceContestoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceContestoPagamento(String value) {
        this.codiceContestoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà identificativoMessaggioRicevuta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoMessaggioRicevuta() {
        return identificativoMessaggioRicevuta;
    }

    /**
     * Imposta il valore della proprietà identificativoMessaggioRicevuta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoMessaggioRicevuta(String value) {
        this.identificativoMessaggioRicevuta = value;
    }

    /**
     * Recupera il valore della proprietà dataOraMessaggioRicevuta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraMessaggioRicevuta() {
        return dataOraMessaggioRicevuta;
    }

    /**
     * Imposta il valore della proprietà dataOraMessaggioRicevuta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraMessaggioRicevuta(XMLGregorianCalendar value) {
        this.dataOraMessaggioRicevuta = value;
    }

    /**
     * Recupera il valore della proprietà codiceTipoDebito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceTipoDebito() {
        return codiceTipoDebito;
    }

    /**
     * Imposta il valore della proprietà codiceTipoDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceTipoDebito(String value) {
        this.codiceTipoDebito = value;
    }

    /**
     * Recupera il valore della proprietà identificativoUnivocoRiscossione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoRiscossione() {
        return identificativoUnivocoRiscossione;
    }

    /**
     * Imposta il valore della proprietà identificativoUnivocoRiscossione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoRiscossione(String value) {
        this.identificativoUnivocoRiscossione = value;
    }

    /**
     * Recupera il valore della proprietà singoloImportoPagato.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSingoloImportoPagato() {
        return singoloImportoPagato;
    }

    /**
     * Imposta il valore della proprietà singoloImportoPagato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSingoloImportoPagato(BigDecimal value) {
        this.singoloImportoPagato = value;
    }

    /**
     * Recupera il valore della proprietà datiVersante.
     * 
     * @return
     *     possible object is
     *     {@link CtSoggetto }
     *     
     */
    public CtSoggetto getDatiVersante() {
        return datiVersante;
    }

    /**
     * Imposta il valore della proprietà datiVersante.
     * 
     * @param value
     *     allowed object is
     *     {@link CtSoggetto }
     *     
     */
    public void setDatiVersante(CtSoggetto value) {
        this.datiVersante = value;
    }

    /**
     * Recupera il valore della proprietà datiDebitore.
     * 
     * @return
     *     possible object is
     *     {@link CtSoggetto }
     *     
     */
    public CtSoggetto getDatiDebitore() {
        return datiDebitore;
    }

    /**
     * Imposta il valore della proprietà datiDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link CtSoggetto }
     *     
     */
    public void setDatiDebitore(CtSoggetto value) {
        this.datiDebitore = value;
    }

    /**
     * Recupera il valore della proprietà codiceEsitoSingoloPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEsitoSingoloPagamento() {
        return codiceEsitoSingoloPagamento;
    }

    /**
     * Imposta il valore della proprietà codiceEsitoSingoloPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEsitoSingoloPagamento(String value) {
        this.codiceEsitoSingoloPagamento = value;
    }

    /**
     * Recupera il valore della proprietà dataEsitoSingoloPagamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsitoSingoloPagamento() {
        return dataEsitoSingoloPagamento;
    }

    /**
     * Imposta il valore della proprietà dataEsitoSingoloPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsitoSingoloPagamento(XMLGregorianCalendar value) {
        this.dataEsitoSingoloPagamento = value;
    }

    /**
     * Recupera il valore della proprietà causaleSingoloPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausaleSingoloPagamento() {
        return causaleSingoloPagamento;
    }

    /**
     * Imposta il valore della proprietà causaleSingoloPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausaleSingoloPagamento(String value) {
        this.causaleSingoloPagamento = value;
    }

    /**
     * Recupera il valore della proprietà debito.
     * 
     * @return
     *     possible object is
     *     {@link CtRiconciliazioneDebito }
     *     
     */
    public CtRiconciliazioneDebito getDebito() {
        return debito;
    }

    /**
     * Imposta il valore della proprietà debito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtRiconciliazioneDebito }
     *     
     */
    public void setDebito(CtRiconciliazioneDebito value) {
        this.debito = value;
    }

    /**
     * Recupera il valore della proprietà esitoRiconciliazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitoRiconciliazione() {
        return esitoRiconciliazione;
    }

    /**
     * Imposta il valore della proprietà esitoRiconciliazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitoRiconciliazione(String value) {
        this.esitoRiconciliazione = value;
    }

    /**
     * Recupera il valore della proprietà messaggio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * Imposta il valore della proprietà messaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessaggio(String value) {
        this.messaggio = value;
    }

}
