
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
 *         &lt;element name="RicercaPosizioniPerCodiceFiscalePartitaIvaResult" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}RispostaRicercaPosizioniPerCodiceFiscalePartitaIva" minOccurs="0"/&gt;
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
    "ricercaPosizioniPerCodiceFiscalePartitaIvaResult"
})
@XmlRootElement(name = "RicercaPosizioniPerCodiceFiscalePartitaIvaResponse")
public class RicercaPosizioniPerCodiceFiscalePartitaIvaResponse {

    @XmlElementRef(name = "RicercaPosizioniPerCodiceFiscalePartitaIvaResult", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<RispostaRicercaPosizioniPerCodiceFiscalePartitaIva> ricercaPosizioniPerCodiceFiscalePartitaIvaResult;

    /**
     * Recupera il valore della proprietà ricercaPosizioniPerCodiceFiscalePartitaIvaResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RispostaRicercaPosizioniPerCodiceFiscalePartitaIva }{@code >}
     *     
     */
    public JAXBElement<RispostaRicercaPosizioniPerCodiceFiscalePartitaIva> getRicercaPosizioniPerCodiceFiscalePartitaIvaResult() {
        return ricercaPosizioniPerCodiceFiscalePartitaIvaResult;
    }

    /**
     * Imposta il valore della proprietà ricercaPosizioniPerCodiceFiscalePartitaIvaResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RispostaRicercaPosizioniPerCodiceFiscalePartitaIva }{@code >}
     *     
     */
    public void setRicercaPosizioniPerCodiceFiscalePartitaIvaResult(JAXBElement<RispostaRicercaPosizioniPerCodiceFiscalePartitaIva> value) {
        this.ricercaPosizioniPerCodiceFiscalePartitaIvaResult = value;
    }

}
