
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComuneSILP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComuneSILP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flgSedeLegale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NSedi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrRappLavoroPrevalente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codBelfioreStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idRappLavoroPrevalente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codISTATComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgMovimentazRappLavoro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComuneSILP", propOrder = {
    "flgSedeLegale",
    "descrStatoEstero",
    "nSedi",
    "descrRappLavoroPrevalente",
    "descrComune",
    "codBelfioreStatoEstero",
    "idRappLavoroPrevalente",
    "codISTATComune",
    "flgMovimentazRappLavoro"
})
public class ComuneSILP {

    @XmlElement(required = true, nillable = true)
    protected String flgSedeLegale;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoEstero;
    @XmlElement(name = "NSedi", required = true, nillable = true)
    protected String nSedi;
    @XmlElement(required = true, nillable = true)
    protected String descrRappLavoroPrevalente;
    @XmlElement(required = true, nillable = true)
    protected String descrComune;
    @XmlElement(required = true, nillable = true)
    protected String codBelfioreStatoEstero;
    @XmlElement(required = true, nillable = true)
    protected String idRappLavoroPrevalente;
    @XmlElement(required = true, nillable = true)
    protected String codISTATComune;
    @XmlElement(required = true, nillable = true)
    protected String flgMovimentazRappLavoro;

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
     * Gets the value of the descrRappLavoroPrevalente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrRappLavoroPrevalente() {
        return descrRappLavoroPrevalente;
    }

    /**
     * Sets the value of the descrRappLavoroPrevalente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrRappLavoroPrevalente(String value) {
        this.descrRappLavoroPrevalente = value;
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
     * Gets the value of the codBelfioreStatoEstero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodBelfioreStatoEstero() {
        return codBelfioreStatoEstero;
    }

    /**
     * Sets the value of the codBelfioreStatoEstero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodBelfioreStatoEstero(String value) {
        this.codBelfioreStatoEstero = value;
    }

    /**
     * Gets the value of the idRappLavoroPrevalente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRappLavoroPrevalente() {
        return idRappLavoroPrevalente;
    }

    /**
     * Sets the value of the idRappLavoroPrevalente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRappLavoroPrevalente(String value) {
        this.idRappLavoroPrevalente = value;
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
     * Gets the value of the flgMovimentazRappLavoro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgMovimentazRappLavoro() {
        return flgMovimentazRappLavoro;
    }

    /**
     * Sets the value of the flgMovimentazRappLavoro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgMovimentazRappLavoro(String value) {
        this.flgMovimentazRappLavoro = value;
    }

}
