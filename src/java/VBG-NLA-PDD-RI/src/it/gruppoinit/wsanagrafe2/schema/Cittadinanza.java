
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Cittadinanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Cittadinanza">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}BaseDataClass">
 *       &lt;sequence>
 *         &lt;element name="Codice" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Disabilitato" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlgPaeseComunitario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cittadinanza", propOrder = {
    "codice",
    "descrizione",
    "cf",
    "disabilitato",
    "flgPaeseComunitario"
})
public class Cittadinanza
    extends BaseDataClass
{

    @XmlElement(name = "Codice", required = true, type = Integer.class, nillable = true)
    protected Integer codice;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "Cf")
    protected String cf;
    @XmlElement(name = "Disabilitato", required = true, type = Integer.class, nillable = true)
    protected Integer disabilitato;
    @XmlElement(name = "FlgPaeseComunitario", required = true, type = Integer.class, nillable = true)
    protected Integer flgPaeseComunitario;

    /**
     * Gets the value of the codice property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodice() {
        return codice;
    }

    /**
     * Sets the value of the codice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodice(Integer value) {
        this.codice = value;
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

    /**
     * Gets the value of the cf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCf() {
        return cf;
    }

    /**
     * Sets the value of the cf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCf(String value) {
        this.cf = value;
    }

    /**
     * Gets the value of the disabilitato property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDisabilitato() {
        return disabilitato;
    }

    /**
     * Sets the value of the disabilitato property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDisabilitato(Integer value) {
        this.disabilitato = value;
    }

    /**
     * Gets the value of the flgPaeseComunitario property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFlgPaeseComunitario() {
        return flgPaeseComunitario;
    }

    /**
     * Sets the value of the flgPaeseComunitario property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFlgPaeseComunitario(Integer value) {
        this.flgPaeseComunitario = value;
    }

}
