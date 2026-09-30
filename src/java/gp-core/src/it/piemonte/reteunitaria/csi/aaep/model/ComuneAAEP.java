
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComuneAAEP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComuneAAEP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="siglaStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codISTATComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeCittaEstera" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgSedeLegale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NSedi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComuneAAEP", propOrder = {
    "siglaStatoEstero",
    "codISTATComune",
    "descrComune",
    "nomeCittaEstera",
    "flgSedeLegale",
    "nSedi",
    "descrStatoEstero"
})
public class ComuneAAEP {

    @XmlElement(required = true, nillable = true)
    protected String siglaStatoEstero;
    @XmlElement(required = true, nillable = true)
    protected String codISTATComune;
    @XmlElement(required = true, nillable = true)
    protected String descrComune;
    @XmlElement(required = true, nillable = true)
    protected String nomeCittaEstera;
    @XmlElement(required = true, nillable = true)
    protected String flgSedeLegale;
    @XmlElement(name = "NSedi", required = true, nillable = true)
    protected String nSedi;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoEstero;

    /**
     * Gets the value of the siglaStatoEstero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaStatoEstero() {
        return siglaStatoEstero;
    }

    /**
     * Sets the value of the siglaStatoEstero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaStatoEstero(String value) {
        this.siglaStatoEstero = value;
    }

    /**
     * Gets the value of the codISTATComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodISTATComune() {
        return codISTATComune;
    }

    /**
     * Sets the value of the codISTATComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodISTATComune(String value) {
        this.codISTATComune = value;
    }

    /**
     * Gets the value of the descrComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrComune() {
        return descrComune;
    }

    /**
     * Sets the value of the descrComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrComune(String value) {
        this.descrComune = value;
    }

    /**
     * Gets the value of the nomeCittaEstera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeCittaEstera() {
        return nomeCittaEstera;
    }

    /**
     * Sets the value of the nomeCittaEstera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeCittaEstera(String value) {
        this.nomeCittaEstera = value;
    }

    /**
     * Gets the value of the flgSedeLegale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgSedeLegale() {
        return flgSedeLegale;
    }

    /**
     * Sets the value of the flgSedeLegale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgSedeLegale(String value) {
        this.flgSedeLegale = value;
    }

    /**
     * Gets the value of the nSedi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNSedi() {
        return nSedi;
    }

    /**
     * Sets the value of the nSedi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNSedi(String value) {
        this.nSedi = value;
    }

    /**
     * Gets the value of the descrStatoEstero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrStatoEstero() {
        return descrStatoEstero;
    }

    /**
     * Sets the value of the descrStatoEstero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrStatoEstero(String value) {
        this.descrStatoEstero = value;
    }

}
