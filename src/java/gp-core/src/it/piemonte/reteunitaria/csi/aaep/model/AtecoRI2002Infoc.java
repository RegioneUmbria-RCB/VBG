
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AtecoRI2002Infoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AtecoRI2002Infoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codImportanzaRI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descrImportanzaRI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codImportanzaAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrImportanzaAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioAteco2002" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descrAteco2002" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="progrSede" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codAteco2002" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtecoRI2002Infoc", propOrder = {
    "codImportanzaRI",
    "idAAEPAzienda",
    "descrImportanzaRI",
    "codImportanzaAA",
    "descrImportanzaAA",
    "dataInizioAteco2002",
    "idAAEPFonteDato",
    "descrAteco2002",
    "progrSede",
    "codAteco2002"
})
public class AtecoRI2002Infoc {

    @XmlElement(required = true, nillable = true)
    protected String codImportanzaRI;
    protected long idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String descrImportanzaRI;
    @XmlElement(required = true, nillable = true)
    protected String codImportanzaAA;
    @XmlElement(required = true, nillable = true)
    protected String descrImportanzaAA;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioAteco2002;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String descrAteco2002;
    protected long progrSede;
    @XmlElement(required = true, nillable = true)
    protected String codAteco2002;

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
     * Gets the value of the dataInizioAteco2002 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioAteco2002() {
        return dataInizioAteco2002;
    }

    /**
     * Sets the value of the dataInizioAteco2002 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioAteco2002(XMLGregorianCalendar value) {
        this.dataInizioAteco2002 = value;
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
     * Gets the value of the descrAteco2002 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrAteco2002() {
        return descrAteco2002;
    }

    /**
     * Sets the value of the descrAteco2002 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrAteco2002(String value) {
        this.descrAteco2002 = value;
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
     * Gets the value of the codAteco2002 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAteco2002() {
        return codAteco2002;
    }

    /**
     * Sets the value of the codAteco2002 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAteco2002(String value) {
        this.codAteco2002 = value;
    }

}
