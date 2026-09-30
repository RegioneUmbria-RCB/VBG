
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
 *         &lt;element name="CaricaPosizioniResult" type="{http://e-fil.eu/PnP/PlugAndPayFeed}RispostaCaricaPosizioni" minOccurs="0"/&gt;
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
    "caricaPosizioniResult"
})
@XmlRootElement(name = "CaricaPosizioniResponse")
public class CaricaPosizioniResponse {

    @XmlElementRef(name = "CaricaPosizioniResult", namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", type = JAXBElement.class, required = false)
    protected JAXBElement<RispostaCaricaPosizioni> caricaPosizioniResult;

    /**
     * Recupera il valore della proprietà caricaPosizioniResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RispostaCaricaPosizioni }{@code >}
     *     
     */
    public JAXBElement<RispostaCaricaPosizioni> getCaricaPosizioniResult() {
        return caricaPosizioniResult;
    }

    /**
     * Imposta il valore della proprietà caricaPosizioniResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RispostaCaricaPosizioni }{@code >}
     *     
     */
    public void setCaricaPosizioniResult(JAXBElement<RispostaCaricaPosizioni> value) {
        this.caricaPosizioniResult = value;
    }

}
