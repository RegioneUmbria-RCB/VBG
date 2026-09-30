
package it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paInviaCarrelloPosizioniResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paInviaCarrelloPosizioniResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paInviaCarrelloPosizioniOutput" type="{http://services.sia.eu/}paInviaCarrelloPosizioniOutputType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paInviaCarrelloPosizioniResponse", propOrder = {
    "paInviaCarrelloPosizioniOutput"
})
public class PaInviaCarrelloPosizioniResponse {

    @XmlElement(required = true)
    protected PaInviaCarrelloPosizioniOutputType paInviaCarrelloPosizioniOutput;

    /**
     * Gets the value of the paInviaCarrelloPosizioniOutput property.
     * 
     * @return
     *     possible object is
     *     {@link PaInviaCarrelloPosizioniOutputType }
     *     
     */
    public PaInviaCarrelloPosizioniOutputType getPaInviaCarrelloPosizioniOutput() {
        return paInviaCarrelloPosizioniOutput;
    }

    /**
     * Sets the value of the paInviaCarrelloPosizioniOutput property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaInviaCarrelloPosizioniOutputType }
     *     
     */
    public void setPaInviaCarrelloPosizioniOutput(PaInviaCarrelloPosizioniOutputType value) {
        this.paInviaCarrelloPosizioniOutput = value;
    }

}
