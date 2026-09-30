
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="cercaPuntualeSedeInfocReturn" type="{urn:AAEPCSI}SedeInfocamere" minOccurs="0"/>
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
    "cercaPuntualeSedeInfocReturn"
})
@XmlRootElement(name = "cercaPuntualeSedeInfocResponse")
public class CercaPuntualeSedeInfocResponse {

    protected SedeInfocamere cercaPuntualeSedeInfocReturn;

    /**
     * Gets the value of the cercaPuntualeSedeInfocReturn property.
     * 
     * @return
     *     possible object is
     *     {@link SedeInfocamere }
     *     
     */
    public SedeInfocamere getCercaPuntualeSedeInfocReturn() {
        return cercaPuntualeSedeInfocReturn;
    }

    /**
     * Sets the value of the cercaPuntualeSedeInfocReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link SedeInfocamere }
     *     
     */
    public void setCercaPuntualeSedeInfocReturn(SedeInfocamere value) {
        this.cercaPuntualeSedeInfocReturn = value;
    }

}
