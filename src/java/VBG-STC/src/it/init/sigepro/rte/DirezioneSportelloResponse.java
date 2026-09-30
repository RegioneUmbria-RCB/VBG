
package it.init.sigepro.rte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.init.sigepro.rte.types.DirezioneType;


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
 *         &lt;element name="direzione" type="{http://sigepro.init.it/rte/types}DirezioneType"/>
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
    "direzione"
})
@XmlRootElement(name = "DirezioneSportelloResponse")
public class DirezioneSportelloResponse {

    @XmlElement(required = true)
    protected DirezioneType direzione;

    /**
     * Gets the value of the direzione property.
     * 
     * @return
     *     possible object is
     *     {@link DirezioneType }
     *     
     */
    public DirezioneType getDirezione() {
        return direzione;
    }

    /**
     * Sets the value of the direzione property.
     * 
     * @param value
     *     allowed object is
     *     {@link DirezioneType }
     *     
     */
    public void setDirezione(DirezioneType value) {
        this.direzione = value;
    }

}
