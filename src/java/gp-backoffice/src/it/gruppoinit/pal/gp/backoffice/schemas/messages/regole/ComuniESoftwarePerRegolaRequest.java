
package it.gruppoinit.pal.gp.backoffice.schemas.messages.regole;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComuniESoftwarePerRegolaRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComuniESoftwarePerRegolaRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeRegola" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeParametro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComuniESoftwarePerRegolaRequest", propOrder = {
    "token",
    "nomeRegola",
    "nomeParametro"
})
public class ComuniESoftwarePerRegolaRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String nomeRegola;
    protected String nomeParametro;

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

}
