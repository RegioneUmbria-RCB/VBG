
package it.gruppoinit.impresainungiorno.schema.base.v_1_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Identificazione di indirizzo italiano, per i casi in cui un indirizzo straniero non e' valido 
 * 
 * <p>Java class for IndirizzoItaliano complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndirizzoItaliano">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}CittaItaliana"/>
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}DatiIndirizzoItaliano"/>
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}DatiIndirizzo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndirizzoItaliano", propOrder = {
    "provincia",
    "comune",
    "cap",
    "toponimo",
    "denominazioneStradale",
    "numeroCivico",
    "frazione"
})
@XmlSeeAlso({
    IndirizzoItalianoConRecapiti.class
})
public class IndirizzoItaliano {

    @XmlElement(required = true)
    protected Provincia provincia;
    @XmlElement(required = true)
    protected Comune comune;
    @XmlElement(required = true)
    protected String cap;
    @XmlElement(required = true)
    protected String toponimo;
    @XmlElement(name = "denominazione-stradale", required = true)
    protected String denominazioneStradale;
    @XmlElement(name = "numero-civico", required = true)
    protected String numeroCivico;
    protected String frazione;

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link Provincia }
     *     
     */
    public Provincia getProvincia() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Provincia }
     *     
     */
    public void setProvincia(Provincia value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the comune property.
     * 
     * @return
     *     possible object is
     *     {@link Comune }
     *     
     */
    public Comune getComune() {
        return comune;
    }

    /**
     * Sets the value of the comune property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comune }
     *     
     */
    public void setComune(Comune value) {
        this.comune = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCap() {
        return cap;
    }

    /**
     * Sets the value of the cap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCap(String value) {
        this.cap = value;
    }

    /**
     * Gets the value of the toponimo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToponimo() {
        return toponimo;
    }

    /**
     * Sets the value of the toponimo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToponimo(String value) {
        this.toponimo = value;
    }

    /**
     * Gets the value of the denominazioneStradale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneStradale() {
        return denominazioneStradale;
    }

    /**
     * Sets the value of the denominazioneStradale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneStradale(String value) {
        this.denominazioneStradale = value;
    }

    /**
     * Gets the value of the numeroCivico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCivico() {
        return numeroCivico;
    }

    /**
     * Sets the value of the numeroCivico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCivico(String value) {
        this.numeroCivico = value;
    }

    /**
     * Gets the value of the frazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrazione() {
        return frazione;
    }

    /**
     * Sets the value of the frazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrazione(String value) {
        this.frazione = value;
    }

}
