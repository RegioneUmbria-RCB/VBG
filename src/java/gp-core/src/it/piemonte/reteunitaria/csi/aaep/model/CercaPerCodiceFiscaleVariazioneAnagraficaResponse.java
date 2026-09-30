
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
 *         &lt;element name="cercaPerCodiceFiscaleVariazioneAnagraficaReturn" type="{urn:AAEPCSI}AziendaVariazioneAnagrafica" minOccurs="0"/>
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
    "cercaPerCodiceFiscaleVariazioneAnagraficaReturn"
})
@XmlRootElement(name = "cercaPerCodiceFiscaleVariazioneAnagraficaResponse")
public class CercaPerCodiceFiscaleVariazioneAnagraficaResponse {

    protected AziendaVariazioneAnagrafica cercaPerCodiceFiscaleVariazioneAnagraficaReturn;

    /**
     * Gets the value of the cercaPerCodiceFiscaleVariazioneAnagraficaReturn property.
     * 
     * @return
     *     possible object is
     *     {@link AziendaVariazioneAnagrafica }
     *     
     */
    public AziendaVariazioneAnagrafica getCercaPerCodiceFiscaleVariazioneAnagraficaReturn() {
        return cercaPerCodiceFiscaleVariazioneAnagraficaReturn;
    }

    /**
     * Sets the value of the cercaPerCodiceFiscaleVariazioneAnagraficaReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link AziendaVariazioneAnagrafica }
     *     
     */
    public void setCercaPerCodiceFiscaleVariazioneAnagraficaReturn(AziendaVariazioneAnagrafica value) {
        this.cercaPerCodiceFiscaleVariazioneAnagraficaReturn = value;
    }

}
