
package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanze;

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
 *         &lt;element name="codiceRuolo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiceIstanza" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "codiceRuolo",
    "codiceIstanza"
})
@XmlRootElement(name = "IstanzeRuoliRequest", namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze")
public class IstanzeRuoliRequest {

    @XmlElement(namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze", required = true)
    protected String token;
    @XmlElement(namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze")
    protected int codiceRuolo;
    @XmlElement(namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze")
    protected int codiceIstanza;

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
     * Gets the value of the codiceRuolo property.
     * 
     */
    public int getCodiceRuolo() {
        return codiceRuolo;
    }

    /**
     * Sets the value of the codiceRuolo property.
     * 
     */
    public void setCodiceRuolo(int value) {
        this.codiceRuolo = value;
    }

    /**
     * Gets the value of the codiceIstanza property.
     * 
     */
    public int getCodiceIstanza() {
        return codiceIstanza;
    }

    /**
     * Sets the value of the codiceIstanza property.
     * 
     */
    public void setCodiceIstanza(int value) {
        this.codiceIstanza = value;
    }

}
