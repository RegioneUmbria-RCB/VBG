
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for inviaStimoloComunicaRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="inviaStimoloComunicaRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stimolo" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}stimoloComunicaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inviaStimoloComunicaRequestType", propOrder = {
    "stimolo"
})
public class InviaStimoloComunicaRequestType {

    @XmlElement(required = true)
    protected StimoloComunicaType stimolo;

    /**
     * Gets the value of the stimolo property.
     * 
     * @return
     *     possible object is
     *     {@link StimoloComunicaType }
     *     
     */
    public StimoloComunicaType getStimolo() {
        return stimolo;
    }

    /**
     * Sets the value of the stimolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link StimoloComunicaType }
     *     
     */
    public void setStimolo(StimoloComunicaType value) {
        this.stimolo = value;
    }

}
