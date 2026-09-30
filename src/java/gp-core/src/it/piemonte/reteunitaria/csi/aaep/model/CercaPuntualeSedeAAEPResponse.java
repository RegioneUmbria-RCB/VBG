
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
 *         &lt;element name="cercaPuntualeSedeAAEPReturn" type="{urn:AAEPCSI}Sede" minOccurs="0"/>
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
    "cercaPuntualeSedeAAEPReturn"
})
@XmlRootElement(name = "cercaPuntualeSedeAAEPResponse")
public class CercaPuntualeSedeAAEPResponse {

    protected Sede cercaPuntualeSedeAAEPReturn;

    /**
     * Gets the value of the cercaPuntualeSedeAAEPReturn property.
     * 
     * @return
     *     possible object is
     *     {@link Sede }
     *     
     */
    public Sede getCercaPuntualeSedeAAEPReturn() {
        return cercaPuntualeSedeAAEPReturn;
    }

    /**
     * Sets the value of the cercaPuntualeSedeAAEPReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sede }
     *     
     */
    public void setCercaPuntualeSedeAAEPReturn(Sede value) {
        this.cercaPuntualeSedeAAEPReturn = value;
    }

}
