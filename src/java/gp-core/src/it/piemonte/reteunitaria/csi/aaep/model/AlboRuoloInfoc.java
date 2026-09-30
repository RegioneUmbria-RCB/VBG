
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AlboRuoloInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlboRuoloInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrEnte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codCausaleCessaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataDelibCessz" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataDomandaIscriz" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataCessazRuolo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="progrRuolo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codTipoRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataDelibIscriz" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="descrCausaleCessaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="numRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="progrSede" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descrFormaRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codEnte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codFormaRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipologiaRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataDomandaCessaz" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlboRuoloInfoc", propOrder = {
    "descrEnte",
    "siglaProvRuolo",
    "codCausaleCessaz",
    "dataDelibCessz",
    "dataDomandaIscriz",
    "dataCessazRuolo",
    "progrRuolo",
    "codTipoRuolo",
    "dataDelibIscriz",
    "descrCausaleCessaz",
    "idAAEPFonteDato",
    "numRuolo",
    "descrTipoRuolo",
    "progrSede",
    "descrFormaRuolo",
    "codEnte",
    "idAAEPAzienda",
    "codFormaRuolo",
    "codTipologiaRuolo",
    "dataDomandaCessaz"
})
public class AlboRuoloInfoc {

    @XmlElement(required = true, nillable = true)
    protected String descrEnte;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvRuolo;
    @XmlElement(required = true, nillable = true)
    protected String codCausaleCessaz;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDelibCessz;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDomandaIscriz;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCessazRuolo;
    protected long progrRuolo;
    @XmlElement(required = true, nillable = true)
    protected String codTipoRuolo;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDelibIscriz;
    @XmlElement(required = true, nillable = true)
    protected String descrCausaleCessaz;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String numRuolo;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoRuolo;
    protected long progrSede;
    @XmlElement(required = true, nillable = true)
    protected String descrFormaRuolo;
    @XmlElement(required = true, nillable = true)
    protected String codEnte;
    protected long idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String codFormaRuolo;
    @XmlElement(required = true, nillable = true)
    protected String codTipologiaRuolo;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDomandaCessaz;

    /**
     * Gets the value of the descrEnte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrEnte() {
        return descrEnte;
    }

    /**
     * Sets the value of the descrEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrEnte(String value) {
        this.descrEnte = value;
    }

    /**
     * Gets the value of the siglaProvRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvRuolo() {
        return siglaProvRuolo;
    }

    /**
     * Sets the value of the siglaProvRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvRuolo(String value) {
        this.siglaProvRuolo = value;
    }

    /**
     * Gets the value of the codCausaleCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCausaleCessaz() {
        return codCausaleCessaz;
    }

    /**
     * Sets the value of the codCausaleCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCausaleCessaz(String value) {
        this.codCausaleCessaz = value;
    }

    /**
     * Gets the value of the dataDelibCessz property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDelibCessz() {
        return dataDelibCessz;
    }

    /**
     * Sets the value of the dataDelibCessz property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDelibCessz(XMLGregorianCalendar value) {
        this.dataDelibCessz = value;
    }

    /**
     * Gets the value of the dataDomandaIscriz property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDomandaIscriz() {
        return dataDomandaIscriz;
    }

    /**
     * Sets the value of the dataDomandaIscriz property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDomandaIscriz(XMLGregorianCalendar value) {
        this.dataDomandaIscriz = value;
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
     * Gets the value of the progrRuolo property.
     * 
     */
    public long getProgrRuolo() {
        return progrRuolo;
    }

    /**
     * Sets the value of the progrRuolo property.
     * 
     */
    public void setProgrRuolo(long value) {
        this.progrRuolo = value;
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
     * Gets the value of the dataDelibIscriz property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDelibIscriz() {
        return dataDelibIscriz;
    }

    /**
     * Sets the value of the dataDelibIscriz property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDelibIscriz(XMLGregorianCalendar value) {
        this.dataDelibIscriz = value;
    }

    /**
     * Gets the value of the descrCausaleCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCausaleCessaz() {
        return descrCausaleCessaz;
    }

    /**
     * Sets the value of the descrCausaleCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCausaleCessaz(String value) {
        this.descrCausaleCessaz = value;
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

    /**
     * Gets the value of the descrFormaRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrFormaRuolo() {
        return descrFormaRuolo;
    }

    /**
     * Sets the value of the descrFormaRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrFormaRuolo(String value) {
        this.descrFormaRuolo = value;
    }

    /**
     * Gets the value of the codEnte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEnte() {
        return codEnte;
    }

    /**
     * Sets the value of the codEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEnte(String value) {
        this.codEnte = value;
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
     * Gets the value of the codTipologiaRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipologiaRuolo() {
        return codTipologiaRuolo;
    }

    /**
     * Sets the value of the codTipologiaRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipologiaRuolo(String value) {
        this.codTipologiaRuolo = value;
    }

    /**
     * Gets the value of the dataDomandaCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDomandaCessaz() {
        return dataDomandaCessaz;
    }

    /**
     * Sets the value of the dataDomandaCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDomandaCessaz(XMLGregorianCalendar value) {
        this.dataDomandaCessaz = value;
    }

}
