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
 * <p>Classe Java per ctDettaglioDebito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctDettaglioDebito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDDeb" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stText256"/>
 *         &lt;element name="Gruppo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Ordinamento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="DataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="DataFineValidita" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="DataLimitePagabilita" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ImportoDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stImporto"/>
 *         &lt;element name="CausaleDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stText140"/>
 *         &lt;element name="DettagliImporto" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctDettagliImporto" minOccurs="0"/>
 *         &lt;element name="ParametriDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctParametriDebito" minOccurs="0"/>
 *         &lt;element name="MarcaDaBollo" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctMarcaBollo" minOccurs="0"/>
 *         &lt;element name="CodiceLotto" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stCodiceLotto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctDettaglioDebito", propOrder = {
    "idDeb",
    "gruppo",
    "ordinamento",
    "dataInizioValidita",
    "dataFineValidita",
    "dataLimitePagabilita",
    "importoDebito",
    "causaleDebito",
    "dettagliImporto",
    "parametriDebito",
    "marcaDaBollo",
    "codiceLotto"
})
public class CtDettaglioDebito {

    @XmlElement(name = "IDDeb", required = true)
    protected String idDeb;
    @XmlElement(name = "Gruppo", required = true)
    protected String gruppo;
    @XmlElement(name = "Ordinamento")
    protected Integer ordinamento;
    @XmlElement(name = "DataInizioValidita", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(name = "DataFineValidita")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFineValidita;
    @XmlElement(name = "DataLimitePagabilita")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataLimitePagabilita;
    @XmlElement(name = "ImportoDebito", required = true)
    protected BigDecimal importoDebito;
    @XmlElement(name = "CausaleDebito", required = true)
    protected String causaleDebito;
    @XmlElement(name = "DettagliImporto")
    protected CtDettagliImporto dettagliImporto;
    @XmlElement(name = "ParametriDebito")
    protected CtParametriDebito parametriDebito;
    @XmlElement(name = "MarcaDaBollo")
    protected CtMarcaBollo marcaDaBollo;
    @XmlElement(name = "CodiceLotto")
    protected String codiceLotto;

    /**
     * Recupera il valore della proprietà idDeb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDDeb() {
        return idDeb;
    }

    /**
     * Imposta il valore della proprietà idDeb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDDeb(String value) {
        this.idDeb = value;
    }

    /**
     * Recupera il valore della proprietà gruppo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGruppo() {
        return gruppo;
    }

    /**
     * Imposta il valore della proprietà gruppo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGruppo(String value) {
        this.gruppo = value;
    }

    /**
     * Recupera il valore della proprietà ordinamento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrdinamento() {
        return ordinamento;
    }

    /**
     * Imposta il valore della proprietà ordinamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrdinamento(Integer value) {
        this.ordinamento = value;
    }

    /**
     * Recupera il valore della proprietà dataInizioValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Imposta il valore della proprietà dataInizioValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioValidita(XMLGregorianCalendar value) {
        this.dataInizioValidita = value;
    }

    /**
     * Recupera il valore della proprietà dataFineValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Imposta il valore della proprietà dataFineValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineValidita(XMLGregorianCalendar value) {
        this.dataFineValidita = value;
    }

    /**
     * Recupera il valore della proprietà dataLimitePagabilita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataLimitePagabilita() {
        return dataLimitePagabilita;
    }

    /**
     * Imposta il valore della proprietà dataLimitePagabilita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataLimitePagabilita(XMLGregorianCalendar value) {
        this.dataLimitePagabilita = value;
    }

    /**
     * Recupera il valore della proprietà importoDebito.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoDebito() {
        return importoDebito;
    }

    /**
     * Imposta il valore della proprietà importoDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoDebito(BigDecimal value) {
        this.importoDebito = value;
    }

    /**
     * Recupera il valore della proprietà causaleDebito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausaleDebito() {
        return causaleDebito;
    }

    /**
     * Imposta il valore della proprietà causaleDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausaleDebito(String value) {
        this.causaleDebito = value;
    }

    /**
     * Recupera il valore della proprietà dettagliImporto.
     * 
     * @return
     *     possible object is
     *     {@link CtDettagliImporto }
     *     
     */
    public CtDettagliImporto getDettagliImporto() {
        return dettagliImporto;
    }

    /**
     * Imposta il valore della proprietà dettagliImporto.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDettagliImporto }
     *     
     */
    public void setDettagliImporto(CtDettagliImporto value) {
        this.dettagliImporto = value;
    }

    /**
     * Recupera il valore della proprietà parametriDebito.
     * 
     * @return
     *     possible object is
     *     {@link CtParametriDebito }
     *     
     */
    public CtParametriDebito getParametriDebito() {
        return parametriDebito;
    }

    /**
     * Imposta il valore della proprietà parametriDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtParametriDebito }
     *     
     */
    public void setParametriDebito(CtParametriDebito value) {
        this.parametriDebito = value;
    }

    /**
     * Recupera il valore della proprietà marcaDaBollo.
     * 
     * @return
     *     possible object is
     *     {@link CtMarcaBollo }
     *     
     */
    public CtMarcaBollo getMarcaDaBollo() {
        return marcaDaBollo;
    }

    /**
     * Imposta il valore della proprietà marcaDaBollo.
     * 
     * @param value
     *     allowed object is
     *     {@link CtMarcaBollo }
     *     
     */
    public void setMarcaDaBollo(CtMarcaBollo value) {
        this.marcaDaBollo = value;
    }

    /**
     * Recupera il valore della proprietà codiceLotto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceLotto() {
        return codiceLotto;
    }

    /**
     * Imposta il valore della proprietà codiceLotto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceLotto(String value) {
        this.codiceLotto = value;
    }

}
