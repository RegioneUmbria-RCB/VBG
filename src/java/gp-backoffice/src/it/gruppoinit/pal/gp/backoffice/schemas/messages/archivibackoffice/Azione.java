
package it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Azione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Azione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proprietaBase" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}ProprietaBase"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Azione", propOrder = {
    "proprietaBase"
})
public class Azione {

    @XmlElement(required = true)
    protected ProprietaBase proprietaBase;

    /**
     * Gets the value of the proprietaBase property.
     * 
     * @return
     *     possible object is
     *     {@link ProprietaBase }
     *     
     */
    public ProprietaBase getProprietaBase() {
        return proprietaBase;
    }

    /**
     * Sets the value of the proprietaBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProprietaBase }
     *     
     */
    public void setProprietaBase(ProprietaBase value) {
        this.proprietaBase = value;
    }

}
