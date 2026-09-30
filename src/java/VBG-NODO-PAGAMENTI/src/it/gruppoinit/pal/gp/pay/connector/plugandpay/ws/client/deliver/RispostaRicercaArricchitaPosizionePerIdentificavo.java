
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RispostaRicercaArricchitaPosizionePerIdentificavo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RispostaRicercaArricchitaPosizionePerIdentificavo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Posizione" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}PosizioneArricchita" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RispostaRicercaArricchitaPosizionePerIdentificavo", propOrder = {
    "posizione"
})
public class RispostaRicercaArricchitaPosizionePerIdentificavo {

    @XmlElementRef(name = "Posizione", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<PosizioneArricchita> posizione;

    /**
     * Recupera il valore della proprietà posizione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PosizioneArricchita }{@code >}
     *     
     */
    public JAXBElement<PosizioneArricchita> getPosizione() {
        return posizione;
    }

    /**
     * Imposta il valore della proprietà posizione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PosizioneArricchita }{@code >}
     *     
     */
    public void setPosizione(JAXBElement<PosizioneArricchita> value) {
        this.posizione = value;
    }

}
