
package it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EstremiAut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EstremiAut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="autoriznumero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="autorizdata" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceAutorizcomune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceAutorizregistro" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiAut", propOrder = {

})
public class EstremiAut {

    @XmlElement(required = true)
    protected String autoriznumero;
    @XmlElement(required = true)
    protected String autorizdata;
    @XmlElement(required = true)
    protected String codiceAutorizcomune;
    protected int codiceAutorizregistro;

    /**
     * Gets the value of the autoriznumero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoriznumero() {
        return autoriznumero;
    }

    /**
     * Sets the value of the autoriznumero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoriznumero(String value) {
        this.autoriznumero = value;
    }

    /**
     * Gets the value of the autorizdata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutorizdata() {
        return autorizdata;
    }

    /**
     * Sets the value of the autorizdata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutorizdata(String value) {
        this.autorizdata = value;
    }

    /**
     * Gets the value of the codiceAutorizcomune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAutorizcomune() {
        return codiceAutorizcomune;
    }

    /**
     * Sets the value of the codiceAutorizcomune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAutorizcomune(String value) {
        this.codiceAutorizcomune = value;
    }

    /**
     * Gets the value of the codiceAutorizregistro property.
     * 
     */
    public int getCodiceAutorizregistro() {
        return codiceAutorizregistro;
    }

    /**
     * Sets the value of the codiceAutorizregistro property.
     * 
     */
    public void setCodiceAutorizregistro(int value) {
        this.codiceAutorizregistro = value;
    }

}
