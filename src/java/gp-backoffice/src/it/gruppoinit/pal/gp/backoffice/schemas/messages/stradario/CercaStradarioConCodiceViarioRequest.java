
package it.gruppoinit.pal.gp.backoffice.schemas.messages.stradario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceCatastaleComune" type="{http://gruppoinit.it/anagrafici/stradario/types}CodiceCatastaleType" minOccurs="0"/>
 *         &lt;element name="codiceviario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "token",
    "codiceCatastaleComune",
    "codiceviario"
})
@XmlRootElement(name = "CercaStradarioConCodiceViarioRequest")
public class CercaStradarioConCodiceViarioRequest {

    @XmlElement(required = true)
    protected String token;
    protected String codiceCatastaleComune;
    @XmlElement(required = true)
    protected String codiceviario;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
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

    /**
     * Gets the value of the codiceviario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceviario() {
        return codiceviario;
    }

    /**
     * Sets the value of the codiceviario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceviario(String value) {
        this.codiceviario = value;
    }

}
