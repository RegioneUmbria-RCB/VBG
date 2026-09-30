
package it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche;

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
 *         &lt;element name="dyn2ModellitType" type="{http://gruppoinit.it/sigepro/schemas/messages/schedeDinamiche}Dyn2ModellitType"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceScheda" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "dyn2ModellitType",
    "software",
    "codiceScheda"
})
@XmlRootElement(name = "StrutturaModelloTFindResponse")
public class StrutturaModelloTFindResponse {

    @XmlElement(required = true)
    protected Dyn2ModellitType dyn2ModellitType;
    @XmlElement(required = true)
    protected String software;
    @XmlElement(required = true)
    protected String codiceScheda;

    /**
     * Gets the value of the dyn2ModellitType property.
     * 
     * @return
     *     possible object is
     *     {@link Dyn2ModellitType }
     *     
     */
    public Dyn2ModellitType getDyn2ModellitType() {
        return dyn2ModellitType;
    }

    /**
     * Sets the value of the dyn2ModellitType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dyn2ModellitType }
     *     
     */
    public void setDyn2ModellitType(Dyn2ModellitType value) {
        this.dyn2ModellitType = value;
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
     * Gets the value of the codiceScheda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceScheda() {
        return codiceScheda;
    }

    /**
     * Sets the value of the codiceScheda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceScheda(String value) {
        this.codiceScheda = value;
    }

}
