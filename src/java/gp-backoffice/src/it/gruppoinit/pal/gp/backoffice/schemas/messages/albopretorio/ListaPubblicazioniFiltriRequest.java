
package it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="categoria" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="valido_dal" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="valido_al" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
@XmlRootElement(name = "ListaPubblicazioniFiltriRequest")
public class ListaPubblicazioniFiltriRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String software;
    @XmlElement(required = true, nillable = true)
    protected String oggetto;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer categoria;
    @XmlElement(name = "valido_dal", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validoDal;
    @XmlElement(name = "valido_al", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validoAl;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer da;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer a;

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
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the categoria property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCategoria() {
        return categoria;
    }

    /**
     * Sets the value of the categoria property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCategoria(Integer value) {
        this.categoria = value;
    }

    /**
     * Gets the value of the validoDal property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidoDal() {
        return validoDal;
    }

    /**
     * Sets the value of the validoDal property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidoDal(XMLGregorianCalendar value) {
        this.validoDal = value;
    }

    /**
     * Gets the value of the validoAl property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidoAl() {
        return validoAl;
    }

    /**
     * Sets the value of the validoAl property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidoAl(XMLGregorianCalendar value) {
        this.validoAl = value;
    }

    /**
     * Gets the value of the da property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDa() {
        return da;
    }

    /**
     * Sets the value of the da property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDa(Integer value) {
        this.da = value;
    }

    /**
     * Gets the value of the a property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getA() {
        return a;
    }

    /**
     * Sets the value of the a property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setA(Integer value) {
        this.a = value;
    }

}
