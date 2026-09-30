
package it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe;

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
 *       &lt;all>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="datiAnagrafici" type="{http://gruppoinit.it/sigepro/schemas/messages/anagrafe}AnagrafeType"/>
 *         &lt;element name="tipoInserimento" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="AUTH_TYPE_REG"/>
 *               &lt;enumeration value="AUTH_TYPE_REG_SC"/>
 *               &lt;enumeration value="AUTH_TYPE_REG_SSO"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="xmlDatiAnagrafici" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "InserimentoAnagrafeRequest")
public class InserimentoAnagrafeRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected AnagrafeType datiAnagrafici;
    protected String tipoInserimento;
    protected String xmlDatiAnagrafici;

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
     * Gets the value of the datiAnagrafici property.
     * 
     * @return
     *     possible object is
     *     {@link AnagrafeType }
     *     
     */
    public AnagrafeType getDatiAnagrafici() {
        return datiAnagrafici;
    }

    /**
     * Sets the value of the datiAnagrafici property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnagrafeType }
     *     
     */
    public void setDatiAnagrafici(AnagrafeType value) {
        this.datiAnagrafici = value;
    }

    /**
     * Gets the value of the tipoInserimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoInserimento() {
        return tipoInserimento;
    }

    /**
     * Sets the value of the tipoInserimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoInserimento(String value) {
        this.tipoInserimento = value;
    }

    /**
     * Gets the value of the xmlDatiAnagrafici property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlDatiAnagrafici() {
        return xmlDatiAnagrafici;
    }

    /**
     * Sets the value of the xmlDatiAnagrafici property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlDatiAnagrafici(String value) {
        this.xmlDatiAnagrafici = value;
    }

}
