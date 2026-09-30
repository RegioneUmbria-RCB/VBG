
package it.gruppoinit.pal.gp.backoffice.schemas.messages.stradario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StradarioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StradarioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codicestradario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dug" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="toponimo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="128"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="cap" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="([0-9]{5})"/>
 *               &lt;length value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="localita" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="128"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="codviario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceCatastaleComune" type="{http://gruppoinit.it/anagrafici/stradario/types}CodiceCatastaleType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StradarioType", propOrder = {
    "codicestradario",
    "dug",
    "toponimo",
    "cap",
    "localita",
    "codviario",
    "codiceCatastaleComune"
})
public class StradarioType {

    protected String codicestradario;
    protected String dug;
    @XmlElement(required = true)
    protected String toponimo;
    protected String cap;
    protected String localita;
    protected String codviario;
    protected String codiceCatastaleComune;

    /**
     * Gets the value of the codicestradario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicestradario() {
        return codicestradario;
    }

    /**
     * Sets the value of the codicestradario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicestradario(String value) {
        this.codicestradario = value;
    }

    /**
     * Gets the value of the dug property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDug() {
        return dug;
    }

    /**
     * Sets the value of the dug property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDug(String value) {
        this.dug = value;
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
     * Gets the value of the localita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalita() {
        return localita;
    }

    /**
     * Sets the value of the localita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalita(String value) {
        this.localita = value;
    }

    /**
     * Gets the value of the codviario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodviario() {
        return codviario;
    }

    /**
     * Sets the value of the codviario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodviario(String value) {
        this.codviario = value;
    }

    /**
     * Gets the value of the codiceCatastaleComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCatastaleComune() {
        return codiceCatastaleComune;
    }

    /**
     * Sets the value of the codiceCatastaleComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCatastaleComune(String value) {
        this.codiceCatastaleComune = value;
    }

}
