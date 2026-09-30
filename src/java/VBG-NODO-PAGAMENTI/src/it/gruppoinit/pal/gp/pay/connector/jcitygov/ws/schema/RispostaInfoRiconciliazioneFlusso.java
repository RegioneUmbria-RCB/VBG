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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodiceIpaBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdentificativoFlusso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DataOraFlusso" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IdentificativoUnivocoRegolamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataRegolamento" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="TipoIdentificativoUnivocoMittente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CodiceIdentificativoUnivocoMittente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CodiceBicBancaDiRiversamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumeroTotalePagamentiRendicontati" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ImportoTotalePagamentiRendicontati" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stImporto"/>
 *         &lt;element name="PagamentiSingoli">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PagamentoSingolo" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctRiconciliazionePagamentoSingolo" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="StatoRiconciliazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "codiceIpaBeneficiario",
    "identificativoFlusso",
    "dataOraFlusso",
    "identificativoUnivocoRegolamento",
    "dataRegolamento",
    "tipoIdentificativoUnivocoMittente",
    "codiceIdentificativoUnivocoMittente",
    "codiceBicBancaDiRiversamento",
    "numeroTotalePagamentiRendicontati",
    "importoTotalePagamentiRendicontati",
    "pagamentiSingoli",
    "statoRiconciliazione"
})
@XmlRootElement(name = "RispostaInfoRiconciliazioneFlusso")
public class RispostaInfoRiconciliazioneFlusso {

    @XmlElement(name = "CodiceIpaBeneficiario", required = true)
    protected String codiceIpaBeneficiario;
    @XmlElement(name = "IdentificativoFlusso", required = true)
    protected String identificativoFlusso;
    @XmlElement(name = "DataOraFlusso", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraFlusso;
    @XmlElement(name = "IdentificativoUnivocoRegolamento")
    protected String identificativoUnivocoRegolamento;
    @XmlElement(name = "DataRegolamento", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataRegolamento;
    @XmlElement(name = "TipoIdentificativoUnivocoMittente", required = true)
    protected String tipoIdentificativoUnivocoMittente;
    @XmlElement(name = "CodiceIdentificativoUnivocoMittente", required = true)
    protected String codiceIdentificativoUnivocoMittente;
    @XmlElement(name = "CodiceBicBancaDiRiversamento")
    protected String codiceBicBancaDiRiversamento;
    @XmlElement(name = "NumeroTotalePagamentiRendicontati", required = true)
    protected BigInteger numeroTotalePagamentiRendicontati;
    @XmlElement(name = "ImportoTotalePagamentiRendicontati", required = true)
    protected BigDecimal importoTotalePagamentiRendicontati;
    @XmlElement(name = "PagamentiSingoli", required = true)
    protected RispostaInfoRiconciliazioneFlusso.PagamentiSingoli pagamentiSingoli;
    @XmlElement(name = "StatoRiconciliazione", required = true)
    protected String statoRiconciliazione;

    /**
     * Recupera il valore della proprietà codiceIpaBeneficiario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIpaBeneficiario() {
        return codiceIpaBeneficiario;
    }

    /**
     * Imposta il valore della proprietà codiceIpaBeneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIpaBeneficiario(String value) {
        this.codiceIpaBeneficiario = value;
    }

    /**
     * Recupera il valore della proprietà identificativoFlusso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoFlusso() {
        return identificativoFlusso;
    }

    /**
     * Imposta il valore della proprietà identificativoFlusso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoFlusso(String value) {
        this.identificativoFlusso = value;
    }

    /**
     * Recupera il valore della proprietà dataOraFlusso.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraFlusso() {
        return dataOraFlusso;
    }

    /**
     * Imposta il valore della proprietà dataOraFlusso.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraFlusso(XMLGregorianCalendar value) {
        this.dataOraFlusso = value;
    }

    /**
     * Recupera il valore della proprietà identificativoUnivocoRegolamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoRegolamento() {
        return identificativoUnivocoRegolamento;
    }

    /**
     * Imposta il valore della proprietà identificativoUnivocoRegolamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoRegolamento(String value) {
        this.identificativoUnivocoRegolamento = value;
    }

    /**
     * Recupera il valore della proprietà dataRegolamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegolamento() {
        return dataRegolamento;
    }

    /**
     * Imposta il valore della proprietà dataRegolamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegolamento(XMLGregorianCalendar value) {
        this.dataRegolamento = value;
    }

    /**
     * Recupera il valore della proprietà tipoIdentificativoUnivocoMittente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoIdentificativoUnivocoMittente() {
        return tipoIdentificativoUnivocoMittente;
    }

    /**
     * Imposta il valore della proprietà tipoIdentificativoUnivocoMittente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoIdentificativoUnivocoMittente(String value) {
        this.tipoIdentificativoUnivocoMittente = value;
    }

    /**
     * Recupera il valore della proprietà codiceIdentificativoUnivocoMittente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIdentificativoUnivocoMittente() {
        return codiceIdentificativoUnivocoMittente;
    }

    /**
     * Imposta il valore della proprietà codiceIdentificativoUnivocoMittente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIdentificativoUnivocoMittente(String value) {
        this.codiceIdentificativoUnivocoMittente = value;
    }

    /**
     * Recupera il valore della proprietà codiceBicBancaDiRiversamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceBicBancaDiRiversamento() {
        return codiceBicBancaDiRiversamento;
    }

    /**
     * Imposta il valore della proprietà codiceBicBancaDiRiversamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceBicBancaDiRiversamento(String value) {
        this.codiceBicBancaDiRiversamento = value;
    }

    /**
     * Recupera il valore della proprietà numeroTotalePagamentiRendicontati.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroTotalePagamentiRendicontati() {
        return numeroTotalePagamentiRendicontati;
    }

    /**
     * Imposta il valore della proprietà numeroTotalePagamentiRendicontati.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroTotalePagamentiRendicontati(BigInteger value) {
        this.numeroTotalePagamentiRendicontati = value;
    }

    /**
     * Recupera il valore della proprietà importoTotalePagamentiRendicontati.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotalePagamentiRendicontati() {
        return importoTotalePagamentiRendicontati;
    }

    /**
     * Imposta il valore della proprietà importoTotalePagamentiRendicontati.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotalePagamentiRendicontati(BigDecimal value) {
        this.importoTotalePagamentiRendicontati = value;
    }

    /**
     * Recupera il valore della proprietà pagamentiSingoli.
     * 
     * @return
     *     possible object is
     *     {@link RispostaInfoRiconciliazioneFlusso.PagamentiSingoli }
     *     
     */
    public RispostaInfoRiconciliazioneFlusso.PagamentiSingoli getPagamentiSingoli() {
        return pagamentiSingoli;
    }

    /**
     * Imposta il valore della proprietà pagamentiSingoli.
     * 
     * @param value
     *     allowed object is
     *     {@link RispostaInfoRiconciliazioneFlusso.PagamentiSingoli }
     *     
     */
    public void setPagamentiSingoli(RispostaInfoRiconciliazioneFlusso.PagamentiSingoli value) {
        this.pagamentiSingoli = value;
    }

    /**
     * Recupera il valore della proprietà statoRiconciliazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoRiconciliazione() {
        return statoRiconciliazione;
    }

    /**
     * Imposta il valore della proprietà statoRiconciliazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoRiconciliazione(String value) {
        this.statoRiconciliazione = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="PagamentoSingolo" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctRiconciliazionePagamentoSingolo" maxOccurs="unbounded" minOccurs="0"/>
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
        "pagamentoSingolo"
    })
    public static class PagamentiSingoli {

        @XmlElement(name = "PagamentoSingolo")
        protected List<CtRiconciliazionePagamentoSingolo> pagamentoSingolo;

        /**
         * Gets the value of the pagamentoSingolo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the pagamentoSingolo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPagamentoSingolo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CtRiconciliazionePagamentoSingolo }
         * 
         * 
         */
        public List<CtRiconciliazionePagamentoSingolo> getPagamentoSingolo() {
            if (pagamentoSingolo == null) {
                pagamentoSingolo = new ArrayList<CtRiconciliazionePagamentoSingolo>();
            }
            return this.pagamentoSingolo;
        }

    }

}
