
package it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio;

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
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="maxResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="da" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="a" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlRootElement(name = "ListaPubblicazioniMaxRequest")
public class ListaPubblicazioniMaxRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String software;
    protected int maxResult;
    protected int da;
    protected int a;

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
     * Gets the value of the maxResult property.
     * 
     */
    public int getMaxResult() {
        return maxResult;
    }

    /**
     * Sets the value of the maxResult property.
     * 
     */
    public void setMaxResult(int value) {
        this.maxResult = value;
    }

    /**
     * Gets the value of the da property.
     * 
     */
    public int getDa() {
        return da;
    }

    /**
     * Sets the value of the da property.
     * 
     */
    public void setDa(int value) {
        this.da = value;
    }

    /**
     * Gets the value of the a property.
     * 
     */
    public int getA() {
        return a;
    }

    /**
     * Sets the value of the a property.
     * 
     */
    public void setA(int value) {
        this.a = value;
    }

}
