
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

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
 *         &lt;element name="RicercaPosizionePerIdentificavoResult" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}RispostaRicercaPosizionePerIdentificavo" minOccurs="0"/&gt;
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
    "ricercaPosizionePerIdentificavoResult"
})
@XmlRootElement(name = "RicercaPosizionePerIdentificavoResponse")
public class RicercaPosizionePerIdentificavoResponse {

    @XmlElementRef(name = "RicercaPosizionePerIdentificavoResult", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<RispostaRicercaPosizionePerIdentificavo> ricercaPosizionePerIdentificavoResult;

    /**
     * Recupera il valore della proprietà ricercaPosizionePerIdentificavoResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RispostaRicercaPosizionePerIdentificavo }{@code >}
     *     
     */
    public JAXBElement<RispostaRicercaPosizionePerIdentificavo> getRicercaPosizionePerIdentificavoResult() {
        return ricercaPosizionePerIdentificavoResult;
    }

    /**
     * Imposta il valore della proprietà ricercaPosizionePerIdentificavoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RispostaRicercaPosizionePerIdentificavo }{@code >}
     *     
     */
    public void setRicercaPosizionePerIdentificavoResult(JAXBElement<RispostaRicercaPosizionePerIdentificavo> value) {
        this.ricercaPosizionePerIdentificavoResult = value;
    }

}
