//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ctCriteriRicerca complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctCriteriRicerca">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodiceTipoDebito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DaDataInserimento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ADataInserimento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DaDataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ADataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DaDataFineValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ADataFineValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DaDataLimitePagabilita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ADataLimitePagabilita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Contribuente" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctIdentificativoUnivocoPersonaFG"/>
 *         &lt;element name="Notificati" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Rendicontati" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Riconciliati" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctCriteriRicerca", propOrder = {
    "codiceTipoDebito",
    "daDataInserimento",
    "aDataInserimento",
    "daDataInizioValidita",
    "aDataInizioValidita",
    "daDataFineValidita",
    "aDataFineValidita",
    "daDataLimitePagabilita",
    "aDataLimitePagabilita",
    "contribuente",
    "notificati",
    "rendicontati",
    "riconciliati"
})
public class CtCriteriRicerca {

    @XmlElement(name = "CodiceTipoDebito", required = true)
    protected String codiceTipoDebito;
    @XmlElement(name = "DaDataInserimento")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar daDataInserimento;
    @XmlElement(name = "ADataInserimento")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar aDataInserimento;
    @XmlElement(name = "DaDataInizioValidita")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar daDataInizioValidita;
    @XmlElement(name = "ADataInizioValidita")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar aDataInizioValidita;
    @XmlElement(name = "DaDataFineValidita")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar daDataFineValidita;
    @XmlElement(name = "ADataFineValidita")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar aDataFineValidita;
    @XmlElement(name = "DaDataLimitePagabilita")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar daDataLimitePagabilita;
    @XmlElement(name = "ADataLimitePagabilita")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar aDataLimitePagabilita;
    @XmlElement(name = "Contribuente", required = true)
    protected CtIdentificativoUnivocoPersonaFG contribuente;
    @XmlElement(name = "Notificati")
    protected Boolean notificati;
    @XmlElement(name = "Rendicontati")
    protected Boolean rendicontati;
    @XmlElement(name = "Riconciliati")
    protected Boolean riconciliati;

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
     * Recupera il valore della proprietà daDataInserimento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDaDataInserimento() {
        return daDataInserimento;
    }

    /**
     * Imposta il valore della proprietà daDataInserimento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDaDataInserimento(XMLGregorianCalendar value) {
        this.daDataInserimento = value;
    }

    /**
     * Recupera il valore della proprietà aDataInserimento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getADataInserimento() {
        return aDataInserimento;
    }

    /**
     * Imposta il valore della proprietà aDataInserimento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setADataInserimento(XMLGregorianCalendar value) {
        this.aDataInserimento = value;
    }

    /**
     * Recupera il valore della proprietà daDataInizioValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDaDataInizioValidita() {
        return daDataInizioValidita;
    }

    /**
     * Imposta il valore della proprietà daDataInizioValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDaDataInizioValidita(XMLGregorianCalendar value) {
        this.daDataInizioValidita = value;
    }

    /**
     * Recupera il valore della proprietà aDataInizioValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getADataInizioValidita() {
        return aDataInizioValidita;
    }

    /**
     * Imposta il valore della proprietà aDataInizioValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setADataInizioValidita(XMLGregorianCalendar value) {
        this.aDataInizioValidita = value;
    }

    /**
     * Recupera il valore della proprietà daDataFineValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDaDataFineValidita() {
        return daDataFineValidita;
    }

    /**
     * Imposta il valore della proprietà daDataFineValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDaDataFineValidita(XMLGregorianCalendar value) {
        this.daDataFineValidita = value;
    }

    /**
     * Recupera il valore della proprietà aDataFineValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getADataFineValidita() {
        return aDataFineValidita;
    }

    /**
     * Imposta il valore della proprietà aDataFineValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setADataFineValidita(XMLGregorianCalendar value) {
        this.aDataFineValidita = value;
    }

    /**
     * Recupera il valore della proprietà daDataLimitePagabilita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDaDataLimitePagabilita() {
        return daDataLimitePagabilita;
    }

    /**
     * Imposta il valore della proprietà daDataLimitePagabilita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDaDataLimitePagabilita(XMLGregorianCalendar value) {
        this.daDataLimitePagabilita = value;
    }

    /**
     * Recupera il valore della proprietà aDataLimitePagabilita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getADataLimitePagabilita() {
        return aDataLimitePagabilita;
    }

    /**
     * Imposta il valore della proprietà aDataLimitePagabilita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setADataLimitePagabilita(XMLGregorianCalendar value) {
        this.aDataLimitePagabilita = value;
    }

    /**
     * Recupera il valore della proprietà contribuente.
     * 
     * @return
     *     possible object is
     *     {@link CtIdentificativoUnivocoPersonaFG }
     *     
     */
    public CtIdentificativoUnivocoPersonaFG getContribuente() {
        return contribuente;
    }

    /**
     * Imposta il valore della proprietà contribuente.
     * 
     * @param value
     *     allowed object is
     *     {@link CtIdentificativoUnivocoPersonaFG }
     *     
     */
    public void setContribuente(CtIdentificativoUnivocoPersonaFG value) {
        this.contribuente = value;
    }

    /**
     * Recupera il valore della proprietà notificati.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNotificati() {
        return notificati;
    }

    /**
     * Imposta il valore della proprietà notificati.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNotificati(Boolean value) {
        this.notificati = value;
    }

    /**
     * Recupera il valore della proprietà rendicontati.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRendicontati() {
        return rendicontati;
    }

    /**
     * Imposta il valore della proprietà rendicontati.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRendicontati(Boolean value) {
        this.rendicontati = value;
    }

    /**
     * Recupera il valore della proprietà riconciliati.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRiconciliati() {
        return riconciliati;
    }

    /**
     * Imposta il valore della proprietà riconciliati.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRiconciliati(Boolean value) {
        this.riconciliati = value;
    }

}
