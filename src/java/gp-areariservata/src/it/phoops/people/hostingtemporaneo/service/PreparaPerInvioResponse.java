
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for preparaPerInvioResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="preparaPerInvioResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rispostaPreparazionePerInvio" type="{http://service.hostingtemporaneo.people.phoops.it/}rispostaPreparazionePerInvio" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "preparaPerInvioResponse", propOrder = {
    "rispostaPreparazionePerInvio"
})
public class PreparaPerInvioResponse {

    protected RispostaPreparazionePerInvio rispostaPreparazionePerInvio;

    /**
     * Gets the value of the rispostaPreparazionePerInvio property.
     * 
     * @return
     *     possible object is
     *     {@link RispostaPreparazionePerInvio }
     *     
     */
    public RispostaPreparazionePerInvio getRispostaPreparazionePerInvio() {
        return rispostaPreparazionePerInvio;
    }

    /**
     * Sets the value of the rispostaPreparazionePerInvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link RispostaPreparazionePerInvio }
     *     
     */
    public void setRispostaPreparazionePerInvio(RispostaPreparazionePerInvio value) {
        this.rispostaPreparazionePerInvio = value;
    }

}
