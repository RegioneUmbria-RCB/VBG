
package it.ldp.suolopubblico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComplexTypeAreeUsoPubblico complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComplexTypeAreeUsoPubblico">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipologia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="giorni_settimana" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ripetizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="a_periodi" type="{https://ws.ldpgis.it/}ArrayOfComplexTypePeriodo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComplexTypeAreeUsoPubblico", propOrder = {
    "tipologia",
    "giorniSettimana",
    "ripetizione",
    "aPeriodi"
})
@XmlRootElement(name = "ComplexTypeAreeUsoPubblico", namespace="https://ws.ldpgis.it/")
public class ComplexTypeAreeUsoPubblico {

    @XmlElement(required = true, nillable = true)
    protected String tipologia;
    @XmlElement(name = "giorni_settimana", required = true, nillable = true)
    protected String giorniSettimana;
    @XmlElement(required = true, nillable = true)
    protected String ripetizione;
    @XmlElement(name = "a_periodi", required = true, nillable = true)
    protected ArrayOfComplexTypePeriodo aPeriodi;

    /**
     * Gets the value of the tipologia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologia() {
        return tipologia;
    }

    /**
     * Sets the value of the tipologia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologia(String value) {
        this.tipologia = value;
    }

    /**
     * Gets the value of the giorniSettimana property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiorniSettimana() {
        return giorniSettimana;
    }

    /**
     * Sets the value of the giorniSettimana property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiorniSettimana(String value) {
        this.giorniSettimana = value;
    }

    /**
     * Gets the value of the ripetizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRipetizione() {
        return ripetizione;
    }

    /**
     * Sets the value of the ripetizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRipetizione(String value) {
        this.ripetizione = value;
    }

    /**
     * Gets the value of the aPeriodi property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfComplexTypePeriodo }
     *     
     */
    public ArrayOfComplexTypePeriodo getAPeriodi() {
        return aPeriodi;
    }

    /**
     * Sets the value of the aPeriodi property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfComplexTypePeriodo }
     *     
     */
    public void setAPeriodi(ArrayOfComplexTypePeriodo value) {
        this.aPeriodi = value;
    }

}
