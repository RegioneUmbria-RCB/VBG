//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ctInfoPagamentoTelematico complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctInfoPagamentoTelematico">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificativoDominio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stIUV"/>
 *         &lt;element name="CodiceContestoApplicativo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TipoVersamento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NumeroAvviso" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stNumeroAvviso18" minOccurs="0"/>
 *         &lt;element name="StatoTecnicoPagamento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EsitoRichiestaPagamento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ImportoTotaleRichiesta" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stImporto" minOccurs="0"/>
 *         &lt;element name="ImportoTotalePagato" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stImporto" minOccurs="0"/>
 *         &lt;element name="DataPagamento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DataAccredito" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="NumeroVersamentiSingoli" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="InfoVersamentoSingolo" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctInfoVersamentoSingolo" maxOccurs="5"/>
 *         &lt;element name="FlussoRicevuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DatiVersante" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctSoggetto" minOccurs="0"/>
 *         &lt;element name="DatiDebitore" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctSoggetto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctInfoPagamentoTelematico", propOrder = {
    "identificativoDominio",
    "identificativoUnivocoVersamento",
    "codiceContestoApplicativo",
    "tipoVersamento",
    "numeroAvviso",
    "statoTecnicoPagamento",
    "esitoRichiestaPagamento",
    "importoTotaleRichiesta",
    "importoTotalePagato",
    "dataPagamento",
    "dataAccredito",
    "numeroVersamentiSingoli",
    "infoVersamentoSingolo",
    "flussoRicevuta",
    "datiVersante",
    "datiDebitore"
})
public class CtInfoPagamentoTelematico {

    @XmlElement(name = "IdentificativoDominio", required = true)
    protected String identificativoDominio;
    @XmlElement(name = "IdentificativoUnivocoVersamento", required = true)
    protected String identificativoUnivocoVersamento;
    @XmlElement(name = "CodiceContestoApplicativo", required = true)
    protected String codiceContestoApplicativo;
    @XmlElement(name = "TipoVersamento", required = true)
    protected String tipoVersamento;
    @XmlElement(name = "NumeroAvviso")
    protected String numeroAvviso;
    @XmlElement(name = "StatoTecnicoPagamento", required = true)
    protected String statoTecnicoPagamento;
    @XmlElement(name = "EsitoRichiestaPagamento", required = true)
    protected String esitoRichiestaPagamento;
    @XmlElement(name = "ImportoTotaleRichiesta")
    protected BigDecimal importoTotaleRichiesta;
    @XmlElement(name = "ImportoTotalePagato")
    protected BigDecimal importoTotalePagato;
    @XmlElement(name = "DataPagamento")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPagamento;
    @XmlElement(name = "DataAccredito")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAccredito;
    @XmlElement(name = "NumeroVersamentiSingoli", required = true)
    protected BigInteger numeroVersamentiSingoli;
    @XmlElement(name = "InfoVersamentoSingolo", required = true)
    protected List<CtInfoVersamentoSingolo> infoVersamentoSingolo;
    @XmlElement(name = "FlussoRicevuta")
    protected String flussoRicevuta;
    @XmlElement(name = "DatiVersante")
    protected CtSoggetto datiVersante;
    @XmlElement(name = "DatiDebitore")
    protected CtSoggetto datiDebitore;

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
     * Recupera il valore della proprietà codiceContestoApplicativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceContestoApplicativo() {
        return codiceContestoApplicativo;
    }

    /**
     * Imposta il valore della proprietà codiceContestoApplicativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceContestoApplicativo(String value) {
        this.codiceContestoApplicativo = value;
    }

    /**
     * Recupera il valore della proprietà tipoVersamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoVersamento() {
        return tipoVersamento;
    }

    /**
     * Imposta il valore della proprietà tipoVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoVersamento(String value) {
        this.tipoVersamento = value;
    }

    /**
     * Recupera il valore della proprietà numeroAvviso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroAvviso() {
        return numeroAvviso;
    }

    /**
     * Imposta il valore della proprietà numeroAvviso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroAvviso(String value) {
        this.numeroAvviso = value;
    }

    /**
     * Recupera il valore della proprietà statoTecnicoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoTecnicoPagamento() {
        return statoTecnicoPagamento;
    }

    /**
     * Imposta il valore della proprietà statoTecnicoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoTecnicoPagamento(String value) {
        this.statoTecnicoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà esitoRichiestaPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitoRichiestaPagamento() {
        return esitoRichiestaPagamento;
    }

    /**
     * Imposta il valore della proprietà esitoRichiestaPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitoRichiestaPagamento(String value) {
        this.esitoRichiestaPagamento = value;
    }

    /**
     * Recupera il valore della proprietà importoTotaleRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotaleRichiesta() {
        return importoTotaleRichiesta;
    }

    /**
     * Imposta il valore della proprietà importoTotaleRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotaleRichiesta(BigDecimal value) {
        this.importoTotaleRichiesta = value;
    }

    /**
     * Recupera il valore della proprietà importoTotalePagato.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotalePagato() {
        return importoTotalePagato;
    }

    /**
     * Imposta il valore della proprietà importoTotalePagato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotalePagato(BigDecimal value) {
        this.importoTotalePagato = value;
    }

    /**
     * Recupera il valore della proprietà dataPagamento.
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
     * Imposta il valore della proprietà dataPagamento.
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
     * Recupera il valore della proprietà dataAccredito.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAccredito() {
        return dataAccredito;
    }

    /**
     * Imposta il valore della proprietà dataAccredito.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAccredito(XMLGregorianCalendar value) {
        this.dataAccredito = value;
    }

    /**
     * Recupera il valore della proprietà numeroVersamentiSingoli.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroVersamentiSingoli() {
        return numeroVersamentiSingoli;
    }

    /**
     * Imposta il valore della proprietà numeroVersamentiSingoli.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroVersamentiSingoli(BigInteger value) {
        this.numeroVersamentiSingoli = value;
    }

    /**
     * Gets the value of the infoVersamentoSingolo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infoVersamentoSingolo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfoVersamentoSingolo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtInfoVersamentoSingolo }
     * 
     * 
     */
    public List<CtInfoVersamentoSingolo> getInfoVersamentoSingolo() {
        if (infoVersamentoSingolo == null) {
            infoVersamentoSingolo = new ArrayList<CtInfoVersamentoSingolo>();
        }
        return this.infoVersamentoSingolo;
    }

    /**
     * Recupera il valore della proprietà flussoRicevuta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlussoRicevuta() {
        return flussoRicevuta;
    }

    /**
     * Imposta il valore della proprietà flussoRicevuta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlussoRicevuta(String value) {
        this.flussoRicevuta = value;
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

}
