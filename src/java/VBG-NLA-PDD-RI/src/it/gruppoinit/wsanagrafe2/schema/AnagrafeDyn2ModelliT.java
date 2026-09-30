
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AnagrafeDyn2ModelliT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AnagrafeDyn2ModelliT">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}BaseDataClass">
 *       &lt;sequence>
 *         &lt;element name="Idcomune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Codiceanagrafe" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FkD2mtId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagrafeDyn2ModelliT", propOrder = {
    "idcomune",
    "codiceanagrafe",
    "fkD2MtId"
})
public class AnagrafeDyn2ModelliT
    extends BaseDataClass
{

    @XmlElement(name = "Idcomune")
    protected String idcomune;
    @XmlElement(name = "Codiceanagrafe", required = true, type = Integer.class, nillable = true)
    protected Integer codiceanagrafe;
    @XmlElement(name = "FkD2mtId", required = true, type = Integer.class, nillable = true)
    protected Integer fkD2MtId;

    /**
     * Gets the value of the idcomune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdcomune() {
        return idcomune;
    }

    /**
     * Sets the value of the idcomune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdcomune(String value) {
        this.idcomune = value;
    }

    /**
     * Gets the value of the codiceanagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiceanagrafe() {
        return codiceanagrafe;
    }

    /**
     * Sets the value of the codiceanagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiceanagrafe(Integer value) {
        this.codiceanagrafe = value;
    }

    /**
     * Gets the value of the fkD2MtId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFkD2MtId() {
        return fkD2MtId;
    }

    /**
     * Sets the value of the fkD2MtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFkD2MtId(Integer value) {
        this.fkD2MtId = value;
    }

}
