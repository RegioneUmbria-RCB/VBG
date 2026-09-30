
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AlboArtigInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlboArtigInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataDomCessazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataCessazRuolo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="codTipoRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codFormaRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="numRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataDeliberaRuolo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataDelibCessazRuolo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataDomIscrRuolo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiceCessaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrCessaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="descrTipoRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="progrSede" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlboArtigInfoc", propOrder = {
    "dataDomCessazione",
    "dataCessazRuolo",
    "codTipoRuolo",
    "codFormaRuolo",
    "idAAEPAzienda",
    "numRuolo",
    "dataDeliberaRuolo",
    "dataDelibCessazRuolo",
    "dataDomIscrRuolo",
    "idAAEPFonteDato",
    "codiceCessaz",
    "descrCessaz",
    "dataInizioAttivita",
    "descrTipoRuolo",
    "progrSede"
})
public class AlboArtigInfoc {

    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDomCessazione;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCessazRuolo;
    @XmlElement(required = true, nillable = true)
    protected String codTipoRuolo;
    @XmlElement(required = true, nillable = true)
    protected String codFormaRuolo;
    protected long idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String numRuolo;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDeliberaRuolo;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDelibCessazRuolo;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDomIscrRuolo;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String codiceCessaz;
    @XmlElement(required = true, nillable = true)
    protected String descrCessaz;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioAttivita;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoRuolo;
    protected long progrSede;

    /**
     * Gets the value of the dataDomCessazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDomCessazione() {
        return dataDomCessazione;
    }

    /**
     * Sets the value of the dataDomCessazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDomCessazione(XMLGregorianCalendar value) {
        this.dataDomCessazione = value;
    }

    /**
     * Gets the value of the dataCessazRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCessazRuolo() {
        return dataCessazRuolo;
    }

    /**
     * Sets the value of the dataCessazRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCessazRuolo(XMLGregorianCalendar value) {
        this.dataCessazRuolo = value;
    }

    /**
     * Gets the value of the codTipoRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoRuolo() {
        return codTipoRuolo;
    }

    /**
     * Sets the value of the codTipoRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoRuolo(String value) {
        this.codTipoRuolo = value;
    }

    /**
     * Gets the value of the codFormaRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodFormaRuolo() {
        return codFormaRuolo;
    }

    /**
     * Sets the value of the codFormaRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodFormaRuolo(String value) {
        this.codFormaRuolo = value;
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

    /**
     * Gets the value of the numRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumRuolo() {
        return numRuolo;
    }

    /**
     * Sets the value of the numRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumRuolo(String value) {
        this.numRuolo = value;
    }

    /**
     * Gets the value of the dataDeliberaRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDeliberaRuolo() {
        return dataDeliberaRuolo;
    }

    /**
     * Sets the value of the dataDeliberaRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDeliberaRuolo(XMLGregorianCalendar value) {
        this.dataDeliberaRuolo = value;
    }

    /**
     * Gets the value of the dataDelibCessazRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDelibCessazRuolo() {
        return dataDelibCessazRuolo;
    }

    /**
     * Sets the value of the dataDelibCessazRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDelibCessazRuolo(XMLGregorianCalendar value) {
        this.dataDelibCessazRuolo = value;
    }

    /**
     * Gets the value of the dataDomIscrRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDomIscrRuolo() {
        return dataDomIscrRuolo;
    }

    /**
     * Sets the value of the dataDomIscrRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDomIscrRuolo(XMLGregorianCalendar value) {
        this.dataDomIscrRuolo = value;
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
     * Gets the value of the codiceCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCessaz() {
        return codiceCessaz;
    }

    /**
     * Sets the value of the codiceCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCessaz(String value) {
        this.codiceCessaz = value;
    }

    /**
     * Gets the value of the descrCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCessaz() {
        return descrCessaz;
    }

    /**
     * Sets the value of the descrCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCessaz(String value) {
        this.descrCessaz = value;
    }

    /**
     * Gets the value of the dataInizioAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioAttivita() {
        return dataInizioAttivita;
    }

    /**
     * Sets the value of the dataInizioAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioAttivita(XMLGregorianCalendar value) {
        this.dataInizioAttivita = value;
    }

    /**
     * Gets the value of the descrTipoRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoRuolo() {
        return descrTipoRuolo;
    }

    /**
     * Sets the value of the descrTipoRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoRuolo(String value) {
        this.descrTipoRuolo = value;
    }

    /**
     * Gets the value of the progrSede property.
     * 
     */
    public long getProgrSede() {
        return progrSede;
    }

    /**
     * Sets the value of the progrSede property.
     * 
     */
    public void setProgrSede(long value) {
        this.progrSede = value;
    }

}
