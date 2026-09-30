
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProcConcorsInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcConcorsInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numRestistrAtto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvRegAtto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codLiquidazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataRegistroAtto" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataRevocalLiquidaz" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="codAtto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTribunale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrAltreIndicazioni" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dataAperturaProc" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataEsecConcordPrevent" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="descrCodAtto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrNotaio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFineLiquidaz" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="progrLiquidazione" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dataChiusuraLiquidaz" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="descIndicatEsecutAtto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="localRegistroAtto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcConcorsInfoc", propOrder = {
    "numRestistrAtto",
    "siglaProvRegAtto",
    "codLiquidazione",
    "dataRegistroAtto",
    "dataRevocalLiquidaz",
    "codAtto",
    "descrTribunale",
    "descrAltreIndicazioni",
    "idAAEPFonteDato",
    "dataAperturaProc",
    "dataEsecConcordPrevent",
    "descrCodAtto",
    "descrNotaio",
    "dataFineLiquidaz",
    "progrLiquidazione",
    "dataChiusuraLiquidaz",
    "descIndicatEsecutAtto",
    "localRegistroAtto",
    "idAAEPAzienda"
})
public class ProcConcorsInfoc {

    @XmlElement(required = true, nillable = true)
    protected String numRestistrAtto;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvRegAtto;
    @XmlElement(required = true, nillable = true)
    protected String codLiquidazione;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRegistroAtto;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRevocalLiquidaz;
    @XmlElement(required = true, nillable = true)
    protected String codAtto;
    @XmlElement(required = true, nillable = true)
    protected String descrTribunale;
    @XmlElement(required = true, nillable = true)
    protected String descrAltreIndicazioni;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAperturaProc;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEsecConcordPrevent;
    @XmlElement(required = true, nillable = true)
    protected String descrCodAtto;
    @XmlElement(required = true, nillable = true)
    protected String descrNotaio;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineLiquidaz;
    protected long progrLiquidazione;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataChiusuraLiquidaz;
    @XmlElement(required = true, nillable = true)
    protected String descIndicatEsecutAtto;
    @XmlElement(required = true, nillable = true)
    protected String localRegistroAtto;
    protected long idAAEPAzienda;

    /**
     * Gets the value of the numRestistrAtto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumRestistrAtto() {
        return numRestistrAtto;
    }

    /**
     * Sets the value of the numRestistrAtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumRestistrAtto(String value) {
        this.numRestistrAtto = value;
    }

    /**
     * Gets the value of the siglaProvRegAtto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvRegAtto() {
        return siglaProvRegAtto;
    }

    /**
     * Sets the value of the siglaProvRegAtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvRegAtto(String value) {
        this.siglaProvRegAtto = value;
    }

    /**
     * Gets the value of the codLiquidazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodLiquidazione() {
        return codLiquidazione;
    }

    /**
     * Sets the value of the codLiquidazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodLiquidazione(String value) {
        this.codLiquidazione = value;
    }

    /**
     * Gets the value of the dataRegistroAtto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegistroAtto() {
        return dataRegistroAtto;
    }

    /**
     * Sets the value of the dataRegistroAtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegistroAtto(XMLGregorianCalendar value) {
        this.dataRegistroAtto = value;
    }

    /**
     * Gets the value of the dataRevocalLiquidaz property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRevocalLiquidaz() {
        return dataRevocalLiquidaz;
    }

    /**
     * Sets the value of the dataRevocalLiquidaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRevocalLiquidaz(XMLGregorianCalendar value) {
        this.dataRevocalLiquidaz = value;
    }

    /**
     * Gets the value of the codAtto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAtto() {
        return codAtto;
    }

    /**
     * Sets the value of the codAtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAtto(String value) {
        this.codAtto = value;
    }

    /**
     * Gets the value of the descrTribunale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTribunale() {
        return descrTribunale;
    }

    /**
     * Sets the value of the descrTribunale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTribunale(String value) {
        this.descrTribunale = value;
    }

    /**
     * Gets the value of the descrAltreIndicazioni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrAltreIndicazioni() {
        return descrAltreIndicazioni;
    }

    /**
     * Sets the value of the descrAltreIndicazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrAltreIndicazioni(String value) {
        this.descrAltreIndicazioni = value;
    }

    /**
     * Gets the value of the idAAEPFonteDato property.
     * 
     */
    public long getIdAAEPFonteDato() {
        return idAAEPFonteDato;
    }

    /**
     * Sets the value of the idAAEPFonteDato property.
     * 
     */
    public void setIdAAEPFonteDato(long value) {
        this.idAAEPFonteDato = value;
    }

    /**
     * Gets the value of the dataAperturaProc property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAperturaProc() {
        return dataAperturaProc;
    }

    /**
     * Sets the value of the dataAperturaProc property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAperturaProc(XMLGregorianCalendar value) {
        this.dataAperturaProc = value;
    }

    /**
     * Gets the value of the dataEsecConcordPrevent property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsecConcordPrevent() {
        return dataEsecConcordPrevent;
    }

    /**
     * Sets the value of the dataEsecConcordPrevent property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsecConcordPrevent(XMLGregorianCalendar value) {
        this.dataEsecConcordPrevent = value;
    }

    /**
     * Gets the value of the descrCodAtto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCodAtto() {
        return descrCodAtto;
    }

    /**
     * Sets the value of the descrCodAtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCodAtto(String value) {
        this.descrCodAtto = value;
    }

    /**
     * Gets the value of the descrNotaio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrNotaio() {
        return descrNotaio;
    }

    /**
     * Sets the value of the descrNotaio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrNotaio(String value) {
        this.descrNotaio = value;
    }

    /**
     * Gets the value of the dataFineLiquidaz property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineLiquidaz() {
        return dataFineLiquidaz;
    }

    /**
     * Sets the value of the dataFineLiquidaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineLiquidaz(XMLGregorianCalendar value) {
        this.dataFineLiquidaz = value;
    }

    /**
     * Gets the value of the progrLiquidazione property.
     * 
     */
    public long getProgrLiquidazione() {
        return progrLiquidazione;
    }

    /**
     * Sets the value of the progrLiquidazione property.
     * 
     */
    public void setProgrLiquidazione(long value) {
        this.progrLiquidazione = value;
    }

    /**
     * Gets the value of the dataChiusuraLiquidaz property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataChiusuraLiquidaz() {
        return dataChiusuraLiquidaz;
    }

    /**
     * Sets the value of the dataChiusuraLiquidaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataChiusuraLiquidaz(XMLGregorianCalendar value) {
        this.dataChiusuraLiquidaz = value;
    }

    /**
     * Gets the value of the descIndicatEsecutAtto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescIndicatEsecutAtto() {
        return descIndicatEsecutAtto;
    }

    /**
     * Sets the value of the descIndicatEsecutAtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescIndicatEsecutAtto(String value) {
        this.descIndicatEsecutAtto = value;
    }

    /**
     * Gets the value of the localRegistroAtto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalRegistroAtto() {
        return localRegistroAtto;
    }

    /**
     * Sets the value of the localRegistroAtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalRegistroAtto(String value) {
        this.localRegistroAtto = value;
    }

    /**
     * Gets the value of the idAAEPAzienda property.
     * 
     */
    public long getIdAAEPAzienda() {
        return idAAEPAzienda;
    }

    /**
     * Sets the value of the idAAEPAzienda property.
     * 
     */
    public void setIdAAEPAzienda(long value) {
        this.idAAEPAzienda = value;
    }

}
