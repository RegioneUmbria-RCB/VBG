
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment;

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
 *         &lt;element name="InviaCarrelloPosizioniResult" type="{http://e-fil.eu/PnP/PlugAndPayPayment}RispostaInviaCarrelloPosizioni" minOccurs="0"/&gt;
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
    "inviaCarrelloPosizioniResult"
})
@XmlRootElement(name = "InviaCarrelloPosizioniResponse")
public class InviaCarrelloPosizioniResponse {

    @XmlElementRef(name = "InviaCarrelloPosizioniResult", namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", type = JAXBElement.class, required = false)
    protected JAXBElement<RispostaInviaCarrelloPosizioni> inviaCarrelloPosizioniResult;

    /**
     * Recupera il valore della proprietà inviaCarrelloPosizioniResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RispostaInviaCarrelloPosizioni }{@code >}
     *     
     */
    public JAXBElement<RispostaInviaCarrelloPosizioni> getInviaCarrelloPosizioniResult() {
        return inviaCarrelloPosizioniResult;
    }

    /**
     * Imposta il valore della proprietà inviaCarrelloPosizioniResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RispostaInviaCarrelloPosizioni }{@code >}
     *     
     */
    public void setInviaCarrelloPosizioniResult(JAXBElement<RispostaInviaCarrelloPosizioni> value) {
        this.inviaCarrelloPosizioniResult = value;
    }

}
