
package it.gruppoinit.pal.gp.backoffice.schemas.messages.regole;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParametroRegolaRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParametroRegolaRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeRegola" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeParametro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceComune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParametroRegolaRequest", propOrder = {
    "token",
    "software",
    "nomeRegola",
    "nomeParametro",
    "codiceComune"
})
public class ParametroRegolaRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String software;
    @XmlElement(required = true)
    protected String nomeRegola;
    @XmlElement(required = true)
    protected String nomeParametro;
    protected String codiceComune;

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
     * Gets the value of the software property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftware() {
        return software;
    }

    /**
     * Sets the value of the software property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftware(String value) {
        this.software = value;
    }

    /**
     * Gets the value of the nomeRegola property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeRegola() {
        return nomeRegola;
    }

    /**
     * Sets the value of the nomeRegola property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeRegola(String value) {
        this.nomeRegola = value;
    }

    /**
     * Gets the value of the nomeParametro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeParametro() {
        return nomeParametro;
    }

    /**
     * Sets the value of the nomeParametro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeParametro(String value) {
        this.nomeParametro = value;
    }

    /**
     * Gets the value of the codiceComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceComune() {
        return codiceComune;
    }

    /**
     * Sets the value of the codiceComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceComune(String value) {
        this.codiceComune = value;
    }

}
