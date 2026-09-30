
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CaricaPosizioneResult" type="{http://e-fil.eu/PnP/PlugAndPayFeed}RispostaCaricaPosizione" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "caricaPosizioneResult"
})
@XmlRootElement(name = "CaricaPosizioneResponse")
public class CaricaPosizioneResponse {

    @XmlElementRef(name = "CaricaPosizioneResult", namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", type = JAXBElement.class, required = false)
    protected JAXBElement<RispostaCaricaPosizione> caricaPosizioneResult;

    /**
     * Recupera il valore della proprietà caricaPosizioneResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RispostaCaricaPosizione }{@code >}
     *     
     */
    public JAXBElement<RispostaCaricaPosizione> getCaricaPosizioneResult() {
        return caricaPosizioneResult;
    }

    /**
     * Imposta il valore della proprietà caricaPosizioneResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RispostaCaricaPosizione }{@code >}
     *     
     */
    public void setCaricaPosizioneResult(JAXBElement<RispostaCaricaPosizione> value) {
        this.caricaPosizioneResult = value;
    }

}
