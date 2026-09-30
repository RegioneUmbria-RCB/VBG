
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RichiedenteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RichiedenteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ruolo" type="{http://sigepro.init.it/rte/types}RuoloType" minOccurs="0"/>
 *         &lt;element name="anagrafica" type="{http://sigepro.init.it/rte/types}PersonaFisicaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiedenteType", propOrder = {
    "ruolo",
    "anagrafica"
})
public class RichiedenteType {

    protected RuoloType ruolo;
    @XmlElement(required = true)
    protected PersonaFisicaType anagrafica;

    /**
     * Gets the value of the ruolo property.
     * 
     * @return
     *     possible object is
     *     {@link RuoloType }
     *     
     */
    public RuoloType getRuolo() {
        return ruolo;
    }

    /**
     * Sets the value of the ruolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuoloType }
     *     
     */
    public void setRuolo(RuoloType value) {
        this.ruolo = value;
    }

    /**
     * Gets the value of the anagrafica property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaFisicaType }
     *     
     */
    public PersonaFisicaType getAnagrafica() {
        return anagrafica;
    }

    /**
     * Sets the value of the anagrafica property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaFisicaType }
     *     
     */
    public void setAnagrafica(PersonaFisicaType value) {
        this.anagrafica = value;
    }

}
