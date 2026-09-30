
package it.ldp.suolopubblico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComplexTypePraticaIdentificativi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComplexTypePraticaIdentificativi">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativo_temporaneo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numero_pratica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComplexTypePraticaIdentificativi", propOrder = {
    "identificativoTemporaneo",
    "numeroPratica"
})
public class ComplexTypePraticaIdentificativi {

    @XmlElement(name = "identificativo_temporaneo", required = true, nillable = true)
    protected String identificativoTemporaneo;
    @XmlElement(name = "numero_pratica", required = true, nillable = true)
    protected String numeroPratica;

    /**
     * Gets the value of the identificativoTemporaneo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoTemporaneo() {
        return identificativoTemporaneo;
    }

    /**
     * Sets the value of the identificativoTemporaneo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoTemporaneo(String value) {
        this.identificativoTemporaneo = value;
    }

    /**
     * Gets the value of the numeroPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroPratica() {
        return numeroPratica;
    }

    /**
     * Sets the value of the numeroPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroPratica(String value) {
        this.numeroPratica = value;
    }

}
