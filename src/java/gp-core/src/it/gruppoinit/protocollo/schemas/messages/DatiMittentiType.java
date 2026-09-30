
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiMittentiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiMittentiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Anagrafe" type="{http://it.gruppoinit/Protocollazione}ArrayOfDatiAnagraficiType" minOccurs="0"/>
 *         &lt;element name="Amministrazione" type="{http://it.gruppoinit/Protocollazione}ArrayOfDatiAnagraficiType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiMittentiType", propOrder = {
    "anagrafe",
    "amministrazione"
})
public class DatiMittentiType {

    @XmlElement(name = "Anagrafe", nillable = true)
    protected ArrayOfDatiAnagraficiType anagrafe;
    @XmlElement(name = "Amministrazione", nillable = true)
    protected ArrayOfDatiAnagraficiType amministrazione;

    /**
     * Gets the value of the anagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDatiAnagraficiType }
     *     
     */
    public ArrayOfDatiAnagraficiType getAnagrafe() {
        return anagrafe;
    }

    /**
     * Sets the value of the anagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDatiAnagraficiType }
     *     
     */
    public void setAnagrafe(ArrayOfDatiAnagraficiType value) {
        this.anagrafe = value;
    }

    /**
     * Gets the value of the amministrazione property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDatiAnagraficiType }
     *     
     */
    public ArrayOfDatiAnagraficiType getAmministrazione() {
        return amministrazione;
    }

    /**
     * Sets the value of the amministrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDatiAnagraficiType }
     *     
     */
    public void setAmministrazione(ArrayOfDatiAnagraficiType value) {
        this.amministrazione = value;
    }

}
