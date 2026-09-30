
package it.ldp.suolopubblico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComplexTypeArea complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComplexTypeArea">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="metri_quadrati" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComplexTypeArea", propOrder = {
    "identificativo",
    "metriQuadrati",
    "descrizione"
})
public class ComplexTypeArea {

    @XmlElement(required = true, nillable = true)
    protected String identificativo;
    @XmlElement(name = "metri_quadrati", required = true, nillable = true)
    protected String metriQuadrati;
    @XmlElement(required = true, nillable = true)
    protected String descrizione;

    /**
     * Gets the value of the identificativo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativo() {
        return identificativo;
    }

    /**
     * Sets the value of the identificativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativo(String value) {
        this.identificativo = value;
    }

    /**
     * Gets the value of the metriQuadrati property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetriQuadrati() {
        return metriQuadrati;
    }

    /**
     * Sets the value of the metriQuadrati property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetriQuadrati(String value) {
        this.metriQuadrati = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

}
