
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ElenchiProfessionaliBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ElenchiProfessionaliBase">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}BaseDataClass">
 *       &lt;sequence>
 *         &lt;element name="EpId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EpDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElenchiProfessionaliBase", propOrder = {
    "epId",
    "epDescrizione"
})
public class ElenchiProfessionaliBase
    extends BaseDataClass
{

    @XmlElement(name = "EpId", required = true, type = Integer.class, nillable = true)
    protected Integer epId;
    @XmlElement(name = "EpDescrizione")
    protected String epDescrizione;

    /**
     * Gets the value of the epId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEpId() {
        return epId;
    }

    /**
     * Sets the value of the epId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEpId(Integer value) {
        this.epId = value;
    }

    /**
     * Gets the value of the epDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEpDescrizione() {
        return epDescrizione;
    }

    /**
     * Sets the value of the epDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEpDescrizione(String value) {
        this.epDescrizione = value;
    }

}
