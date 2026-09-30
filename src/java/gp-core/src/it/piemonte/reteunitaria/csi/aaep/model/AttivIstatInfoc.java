
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AttivIstatInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttivIstatInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codImportanza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codAttivita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrImportanza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrAttivita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dataCessazAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataInizioAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
@XmlType(name = "AttivIstatInfoc", propOrder = {
    "codImportanza",
    "idAAEPAzienda",
    "codAttivita",
    "descrImportanza",
    "descrAttivita",
    "idAAEPFonteDato",
    "dataCessazAttivita",
    "dataInizioAttivita",
    "progrSede"
})
public class AttivIstatInfoc {

    @XmlElement(required = true, nillable = true)
    protected String codImportanza;
    protected long idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String codAttivita;
    @XmlElement(required = true, nillable = true)
    protected String descrImportanza;
    @XmlElement(required = true, nillable = true)
    protected String descrAttivita;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCessazAttivita;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioAttivita;
    protected long progrSede;

    /**
     * Gets the value of the codImportanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodImportanza() {
        return codImportanza;
    }

    /**
     * Sets the value of the codImportanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodImportanza(String value) {
        this.codImportanza = value;
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
     * Gets the value of the codAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAttivita() {
        return codAttivita;
    }

    /**
     * Sets the value of the codAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAttivita(String value) {
        this.codAttivita = value;
    }

    /**
     * Gets the value of the descrImportanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrImportanza() {
        return descrImportanza;
    }

    /**
     * Sets the value of the descrImportanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrImportanza(String value) {
        this.descrImportanza = value;
    }

    /**
     * Gets the value of the descrAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrAttivita() {
        return descrAttivita;
    }

    /**
     * Sets the value of the descrAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrAttivita(String value) {
        this.descrAttivita = value;
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
     * Gets the value of the dataCessazAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCessazAttivita() {
        return dataCessazAttivita;
    }

    /**
     * Sets the value of the dataCessazAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCessazAttivita(XMLGregorianCalendar value) {
        this.dataCessazAttivita = value;
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
