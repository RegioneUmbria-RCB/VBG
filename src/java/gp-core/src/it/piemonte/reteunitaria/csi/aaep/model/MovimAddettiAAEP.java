
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MovimAddettiAAEP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MovimAddettiAAEP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="movimAddetti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="formatoMovimAddetti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrFonteMovimAddetti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codISTATComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idFonteMovimAddetti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MovimAddettiAAEP", propOrder = {
    "movimAddetti",
    "formatoMovimAddetti",
    "descrFonteMovimAddetti",
    "codISTATComune",
    "descrComune",
    "idFonteMovimAddetti"
})
public class MovimAddettiAAEP {

    @XmlElement(required = true, nillable = true)
    protected String movimAddetti;
    @XmlElement(required = true, nillable = true)
    protected String formatoMovimAddetti;
    @XmlElement(required = true, nillable = true)
    protected String descrFonteMovimAddetti;
    @XmlElement(required = true, nillable = true)
    protected String codISTATComune;
    @XmlElement(required = true, nillable = true)
    protected String descrComune;
    @XmlElement(required = true, nillable = true)
    protected String idFonteMovimAddetti;

    /**
     * Gets the value of the movimAddetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMovimAddetti() {
        return movimAddetti;
    }

    /**
     * Sets the value of the movimAddetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMovimAddetti(String value) {
        this.movimAddetti = value;
    }

    /**
     * Gets the value of the formatoMovimAddetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatoMovimAddetti() {
        return formatoMovimAddetti;
    }

    /**
     * Sets the value of the formatoMovimAddetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatoMovimAddetti(String value) {
        this.formatoMovimAddetti = value;
    }

    /**
     * Gets the value of the descrFonteMovimAddetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrFonteMovimAddetti() {
        return descrFonteMovimAddetti;
    }

    /**
     * Sets the value of the descrFonteMovimAddetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrFonteMovimAddetti(String value) {
        this.descrFonteMovimAddetti = value;
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
     * Gets the value of the idFonteMovimAddetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFonteMovimAddetti() {
        return idFonteMovimAddetti;
    }

    /**
     * Sets the value of the idFonteMovimAddetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFonteMovimAddetti(String value) {
        this.idFonteMovimAddetti = value;
    }

}
