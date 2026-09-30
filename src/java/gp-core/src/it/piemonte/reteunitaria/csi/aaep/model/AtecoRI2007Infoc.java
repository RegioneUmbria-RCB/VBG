
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AtecoRI2007Infoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AtecoRI2007Infoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codImportanzaRI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descrImportanzaRI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codImportanzaAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioAteco2007" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="descrAteco2007" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrImportanzaAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codAteco2007" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "AtecoRI2007Infoc", propOrder = {
    "codImportanzaRI",
    "idAAEPAzienda",
    "descrImportanzaRI",
    "codImportanzaAA",
    "dataInizioAteco2007",
    "descrAteco2007",
    "descrImportanzaAA",
    "idAAEPFonteDato",
    "codAteco2007",
    "progrSede"
})
public class AtecoRI2007Infoc {

    @XmlElement(required = true, nillable = true)
    protected String codImportanzaRI;
    protected long idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String descrImportanzaRI;
    @XmlElement(required = true, nillable = true)
    protected String codImportanzaAA;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioAteco2007;
    @XmlElement(required = true, nillable = true)
    protected String descrAteco2007;
    @XmlElement(required = true, nillable = true)
    protected String descrImportanzaAA;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String codAteco2007;
    protected long progrSede;

    /**
     * Gets the value of the codImportanzaRI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodImportanzaRI() {
        return codImportanzaRI;
    }

    /**
     * Sets the value of the codImportanzaRI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodImportanzaRI(String value) {
        this.codImportanzaRI = value;
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
     * Gets the value of the descrImportanzaRI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrImportanzaRI() {
        return descrImportanzaRI;
    }

    /**
     * Sets the value of the descrImportanzaRI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrImportanzaRI(String value) {
        this.descrImportanzaRI = value;
    }

    /**
     * Gets the value of the codImportanzaAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodImportanzaAA() {
        return codImportanzaAA;
    }

    /**
     * Sets the value of the codImportanzaAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodImportanzaAA(String value) {
        this.codImportanzaAA = value;
    }

    /**
     * Gets the value of the dataInizioAteco2007 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioAteco2007() {
        return dataInizioAteco2007;
    }

    /**
     * Sets the value of the dataInizioAteco2007 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioAteco2007(XMLGregorianCalendar value) {
        this.dataInizioAteco2007 = value;
    }

    /**
     * Gets the value of the descrAteco2007 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrAteco2007() {
        return descrAteco2007;
    }

    /**
     * Sets the value of the descrAteco2007 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrAteco2007(String value) {
        this.descrAteco2007 = value;
    }

    /**
     * Gets the value of the descrImportanzaAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrImportanzaAA() {
        return descrImportanzaAA;
    }

    /**
     * Sets the value of the descrImportanzaAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrImportanzaAA(String value) {
        this.descrImportanzaAA = value;
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
     * Gets the value of the codAteco2007 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAteco2007() {
        return codAteco2007;
    }

    /**
     * Sets the value of the codAteco2007 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAteco2007(String value) {
        this.codAteco2007 = value;
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
