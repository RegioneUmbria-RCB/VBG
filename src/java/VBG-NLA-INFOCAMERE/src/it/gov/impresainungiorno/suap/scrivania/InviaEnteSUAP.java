
package it.gov.impresainungiorno.suap.scrivania;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP;


/**
 * <p>Java class for inviaEnteSUAP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="inviaEnteSUAP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CooperazioneEnteSUAP" type="{http://www.impresainungiorno.gov.it/schema/suap/ente}CooperazioneEnteSUAP"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inviaEnteSUAP", propOrder = {
    "cooperazioneEnteSUAP"
})
public class InviaEnteSUAP {

    @XmlElement(name = "CooperazioneEnteSUAP", required = true)
    protected CooperazioneEnteSUAP cooperazioneEnteSUAP;

    /**
     * Gets the value of the cooperazioneEnteSUAP property.
     * 
     * @return
     *     possible object is
     *     {@link CooperazioneEnteSUAP }
     *     
     */
    public CooperazioneEnteSUAP getCooperazioneEnteSUAP() {
        return cooperazioneEnteSUAP;
    }

    /**
     * Sets the value of the cooperazioneEnteSUAP property.
     * 
     * @param value
     *     allowed object is
     *     {@link CooperazioneEnteSUAP }
     *     
     */
    public void setCooperazioneEnteSUAP(CooperazioneEnteSUAP value) {
        this.cooperazioneEnteSUAP = value;
    }

}
