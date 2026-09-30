
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for preparaPerInvio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="preparaPerInvio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="richiestaPreparazionePerInvio" type="{http://service.hostingtemporaneo.people.phoops.it/}richiestaPreparazionePerInvio" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "preparaPerInvio", propOrder = {
    "richiestaPreparazionePerInvio"
})
public class PreparaPerInvio {

    protected RichiestaPreparazionePerInvio richiestaPreparazionePerInvio;

    /**
     * Gets the value of the richiestaPreparazionePerInvio property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaPreparazionePerInvio }
     *     
     */
    public RichiestaPreparazionePerInvio getRichiestaPreparazionePerInvio() {
        return richiestaPreparazionePerInvio;
    }

    /**
     * Sets the value of the richiestaPreparazionePerInvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaPreparazionePerInvio }
     *     
     */
    public void setRichiestaPreparazionePerInvio(RichiestaPreparazionePerInvio value) {
        this.richiestaPreparazionePerInvio = value;
    }

}
