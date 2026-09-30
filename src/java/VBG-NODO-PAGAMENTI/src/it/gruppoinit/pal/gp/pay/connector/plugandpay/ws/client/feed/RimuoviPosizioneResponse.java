
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
 *         &lt;element name="RimuoviPosizioneResult" type="{http://e-fil.eu/PnP/PlugAndPayFeed}RispostaRimuoviPosizione" minOccurs="0"/&gt;
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
    "rimuoviPosizioneResult"
})
@XmlRootElement(name = "RimuoviPosizioneResponse")
public class RimuoviPosizioneResponse {

    @XmlElementRef(name = "RimuoviPosizioneResult", namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", type = JAXBElement.class, required = false)
    protected JAXBElement<RispostaRimuoviPosizione> rimuoviPosizioneResult;

    /**
     * Recupera il valore della proprietà rimuoviPosizioneResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RispostaRimuoviPosizione }{@code >}
     *     
     */
    public JAXBElement<RispostaRimuoviPosizione> getRimuoviPosizioneResult() {
        return rimuoviPosizioneResult;
    }

    /**
     * Imposta il valore della proprietà rimuoviPosizioneResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RispostaRimuoviPosizione }{@code >}
     *     
     */
    public void setRimuoviPosizioneResult(JAXBElement<RispostaRimuoviPosizione> value) {
        this.rimuoviPosizioneResult = value;
    }

}
