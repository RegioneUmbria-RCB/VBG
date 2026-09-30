
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ricercaPerUtenteRegistratoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ricercaPerUtenteRegistratoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rispostaRicerca" type="{http://service.hostingtemporaneo.people.phoops.it/}rispostaRicerca" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaPerUtenteRegistratoResponse", propOrder = {
    "rispostaRicerca"
})
public class RicercaPerUtenteRegistratoResponse {

    protected RispostaRicerca rispostaRicerca;

    /**
     * Gets the value of the rispostaRicerca property.
     * 
     * @return
     *     possible object is
     *     {@link RispostaRicerca }
     *     
     */
    public RispostaRicerca getRispostaRicerca() {
        return rispostaRicerca;
    }

    /**
     * Sets the value of the rispostaRicerca property.
     * 
     * @param value
     *     allowed object is
     *     {@link RispostaRicerca }
     *     
     */
    public void setRispostaRicerca(RispostaRicerca value) {
        this.rispostaRicerca = value;
    }

}
