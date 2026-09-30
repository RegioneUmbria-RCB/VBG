
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for copia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="copia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="status" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="uriOriginale" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="uriCopia" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="urlCopia" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "copia")
public class Copia {

    @XmlAttribute(name = "status", required = true)
    protected int status;
    @XmlAttribute(name = "uriOriginale", required = true)
    protected String uriOriginale;
    @XmlAttribute(name = "uriCopia")
    protected String uriCopia;
    @XmlAttribute(name = "urlCopia")
    protected String urlCopia;

    /**
     * Gets the value of the status property.
     * 
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     */
    public void setStatus(int value) {
        this.status = value;
    }

    /**
     * Gets the value of the uriOriginale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUriOriginale() {
        return uriOriginale;
    }

    /**
     * Sets the value of the uriOriginale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUriOriginale(String value) {
        this.uriOriginale = value;
    }

    /**
     * Gets the value of the uriCopia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUriCopia() {
        return uriCopia;
    }

    /**
     * Sets the value of the uriCopia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUriCopia(String value) {
        this.uriCopia = value;
    }

    /**
     * Gets the value of the urlCopia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlCopia() {
        return urlCopia;
    }

    /**
     * Sets the value of the urlCopia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlCopia(String value) {
        this.urlCopia = value;
    }

}
