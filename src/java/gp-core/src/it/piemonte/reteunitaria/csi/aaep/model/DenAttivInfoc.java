
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DenAttivInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DenAttivInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="progrDenuncia" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="progrSede" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descrEnte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiceEnte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataDenInizioAttiv" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
@XmlType(name = "DenAttivInfoc", propOrder = {
    "progrDenuncia",
    "progrSede",
    "descrEnte",
    "idAAEPFonteDato",
    "codiceEnte",
    "dataDenInizioAttiv",
    "idAAEPAzienda"
})
public class DenAttivInfoc {

    protected long progrDenuncia;
    protected long progrSede;
    @XmlElement(required = true, nillable = true)
    protected String descrEnte;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String codiceEnte;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDenInizioAttiv;
    protected long idAAEPAzienda;

    /**
     * Gets the value of the progrDenuncia property.
     * 
     */
    public long getProgrDenuncia() {
        return progrDenuncia;
    }

    /**
     * Sets the value of the progrDenuncia property.
     * 
     */
    public void setProgrDenuncia(long value) {
        this.progrDenuncia = value;
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
     * Gets the value of the codiceEnte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEnte() {
        return codiceEnte;
    }

    /**
     * Sets the value of the codiceEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEnte(String value) {
        this.codiceEnte = value;
    }

    /**
     * Gets the value of the dataDenInizioAttiv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDenInizioAttiv() {
        return dataDenInizioAttiv;
    }

    /**
     * Sets the value of the dataDenInizioAttiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDenInizioAttiv(XMLGregorianCalendar value) {
        this.dataDenInizioAttiv = value;
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
