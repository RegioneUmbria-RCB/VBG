
package it.ldp.suolopubblico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComplexTypePeriodo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComplexTypePeriodo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inizio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fine" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="a_aree" type="{https://ws.ldpgis.it/}ArrayOfComplexTypeArea"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComplexTypePeriodo", propOrder = {
    "inizio",
    "fine",
    "aAree"
})
public class ComplexTypePeriodo {

    @XmlElement(required = true, nillable = true)
    protected String inizio;
    @XmlElement(required = true, nillable = true)
    protected String fine;
    @XmlElement(name = "a_aree", required = true, nillable = true)
    protected ArrayOfComplexTypeArea aAree;

    /**
     * Gets the value of the inizio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInizio() {
        return inizio;
    }

    /**
     * Sets the value of the inizio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInizio(String value) {
        this.inizio = value;
    }

    /**
     * Gets the value of the fine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFine() {
        return fine;
    }

    /**
     * Sets the value of the fine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFine(String value) {
        this.fine = value;
    }

    /**
     * Gets the value of the aAree property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfComplexTypeArea }
     *     
     */
    public ArrayOfComplexTypeArea getAAree() {
        return aAree;
    }

    /**
     * Sets the value of the aAree property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfComplexTypeArea }
     *     
     */
    public void setAAree(ArrayOfComplexTypeArea value) {
        this.aAree = value;
    }

}
