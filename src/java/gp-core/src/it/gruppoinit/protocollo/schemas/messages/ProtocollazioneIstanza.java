
package it.gruppoinit.protocollo.schemas.messages;

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
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceIstanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="mittenti" type="{http://it.gruppoinit/Protocollazione}DatiMittentiType" minOccurs="0"/>
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
    "codiceIstanza",
    "source",
    "mittenti"
})
@XmlRootElement(name = "ProtocollazioneIstanza")
public class ProtocollazioneIstanza {

    @XmlElement(nillable = true)
    protected String token;
    @XmlElement(nillable = true)
    protected String codiceIstanza;
    protected Integer source;
    @XmlElement(nillable = true)
    protected DatiMittentiType mittenti;

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
     * Gets the value of the codiceIstanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIstanza() {
        return codiceIstanza;
    }

    /**
     * Sets the value of the codiceIstanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIstanza(String value) {
        this.codiceIstanza = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSource(Integer value) {
        this.source = value;
    }

    /**
     * Gets the value of the mittenti property.
     * 
     * @return
     *     possible object is
     *     {@link DatiMittentiType }
     *     
     */
    public DatiMittentiType getMittenti() {
        return mittenti;
    }

    /**
     * Sets the value of the mittenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiMittentiType }
     *     
     */
    public void setMittenti(DatiMittentiType value) {
        this.mittenti = value;
    }

}
