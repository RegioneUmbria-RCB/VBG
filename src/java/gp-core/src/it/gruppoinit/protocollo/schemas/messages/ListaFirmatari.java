
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaFirmatari complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaFirmatari">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Firmatari" type="{http://it.gruppoinit/Protocollazione}ArrayOfFirmatario" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaFirmatari", propOrder = {
    "firmatari"
})
public class ListaFirmatari {

    @XmlElement(name = "Firmatari", nillable = true)
    protected ArrayOfFirmatario firmatari;

    /**
     * Gets the value of the firmatari property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFirmatario }
     *     
     */
    public ArrayOfFirmatario getFirmatari() {
        return firmatari;
    }

    /**
     * Sets the value of the firmatari property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFirmatario }
     *     
     */
    public void setFirmatari(ArrayOfFirmatario value) {
        this.firmatari = value;
    }

}
