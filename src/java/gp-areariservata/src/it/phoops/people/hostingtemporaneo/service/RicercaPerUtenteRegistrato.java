
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ricercaPerUtenteRegistrato complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ricercaPerUtenteRegistrato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="richiestaRicerca" type="{http://service.hostingtemporaneo.people.phoops.it/}richiestaRicerca" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaPerUtenteRegistrato", propOrder = {
    "richiestaRicerca"
})
public class RicercaPerUtenteRegistrato {

    protected RichiestaRicerca richiestaRicerca;

    /**
     * Gets the value of the richiestaRicerca property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaRicerca }
     *     
     */
    public RichiestaRicerca getRichiestaRicerca() {
        return richiestaRicerca;
    }

    /**
     * Sets the value of the richiestaRicerca property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaRicerca }
     *     
     */
    public void setRichiestaRicerca(RichiestaRicerca value) {
        this.richiestaRicerca = value;
    }

}
