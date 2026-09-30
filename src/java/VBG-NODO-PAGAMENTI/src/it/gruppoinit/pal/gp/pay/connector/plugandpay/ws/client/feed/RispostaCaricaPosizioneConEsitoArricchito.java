
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RispostaCaricaPosizioneConEsitoArricchito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RispostaCaricaPosizioneConEsitoArricchito"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EsitoDiCaricamentoArricchito" type="{http://e-fil.eu/PnP/PlugAndPayFeed}EsitoDiCaricamentoArricchito" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RispostaCaricaPosizioneConEsitoArricchito", propOrder = {
    "esitoDiCaricamentoArricchito"
})
public class RispostaCaricaPosizioneConEsitoArricchito {

    @XmlElementRef(name = "EsitoDiCaricamentoArricchito", namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", type = JAXBElement.class, required = false)
    protected JAXBElement<EsitoDiCaricamentoArricchito> esitoDiCaricamentoArricchito;

    /**
     * Recupera il valore della proprietà esitoDiCaricamentoArricchito.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link EsitoDiCaricamentoArricchito }{@code >}
     *     
     */
    public JAXBElement<EsitoDiCaricamentoArricchito> getEsitoDiCaricamentoArricchito() {
        return esitoDiCaricamentoArricchito;
    }

    /**
     * Imposta il valore della proprietà esitoDiCaricamentoArricchito.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link EsitoDiCaricamentoArricchito }{@code >}
     *     
     */
    public void setEsitoDiCaricamentoArricchito(JAXBElement<EsitoDiCaricamentoArricchito> value) {
        this.esitoDiCaricamentoArricchito = value;
    }

}
