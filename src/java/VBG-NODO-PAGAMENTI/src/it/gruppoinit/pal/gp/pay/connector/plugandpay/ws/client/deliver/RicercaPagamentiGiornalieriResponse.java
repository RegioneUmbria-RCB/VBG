
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
 *         &lt;element name="RicercaPagamentiGiornalieriResult" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}RispostaRicercaPagamentiGiornalieri" minOccurs="0"/&gt;
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
    "ricercaPagamentiGiornalieriResult"
})
@XmlRootElement(name = "RicercaPagamentiGiornalieriResponse")
public class RicercaPagamentiGiornalieriResponse {

    @XmlElementRef(name = "RicercaPagamentiGiornalieriResult", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<RispostaRicercaPagamentiGiornalieri> ricercaPagamentiGiornalieriResult;

    /**
     * Recupera il valore della proprietà ricercaPagamentiGiornalieriResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RispostaRicercaPagamentiGiornalieri }{@code >}
     *     
     */
    public JAXBElement<RispostaRicercaPagamentiGiornalieri> getRicercaPagamentiGiornalieriResult() {
        return ricercaPagamentiGiornalieriResult;
    }

    /**
     * Imposta il valore della proprietà ricercaPagamentiGiornalieriResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RispostaRicercaPagamentiGiornalieri }{@code >}
     *     
     */
    public void setRicercaPagamentiGiornalieriResult(JAXBElement<RispostaRicercaPagamentiGiornalieri> value) {
        this.ricercaPagamentiGiornalieriResult = value;
    }

}
