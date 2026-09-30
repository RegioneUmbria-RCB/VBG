
package it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for soggettoSUAP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="soggettoSUAP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cognomeAddetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginAddetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomeAddetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="useridAddetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soggettoSUAP", propOrder = {
    "cognomeAddetto",
    "loginAddetto",
    "nomeAddetto",
    "useridAddetto"
})
public class SoggettoSUAP {

    protected String cognomeAddetto;
    protected String loginAddetto;
    protected String nomeAddetto;
    protected String useridAddetto;

    /**
     * Gets the value of the cognomeAddetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognomeAddetto() {
        return cognomeAddetto;
    }

    /**
     * Sets the value of the cognomeAddetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognomeAddetto(String value) {
        this.cognomeAddetto = value;
    }

    /**
     * Gets the value of the loginAddetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginAddetto() {
        return loginAddetto;
    }

    /**
     * Sets the value of the loginAddetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginAddetto(String value) {
        this.loginAddetto = value;
    }

    /**
     * Gets the value of the nomeAddetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeAddetto() {
        return nomeAddetto;
    }

    /**
     * Sets the value of the nomeAddetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeAddetto(String value) {
        this.nomeAddetto = value;
    }

    /**
     * Gets the value of the useridAddetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseridAddetto() {
        return useridAddetto;
    }

    /**
     * Sets the value of the useridAddetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseridAddetto(String value) {
        this.useridAddetto = value;
    }

}
