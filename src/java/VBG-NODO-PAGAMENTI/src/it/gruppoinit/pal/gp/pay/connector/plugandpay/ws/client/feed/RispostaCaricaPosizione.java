
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RispostaCaricaPosizione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RispostaCaricaPosizione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EsitoDiCaricamento" type="{http://e-fil.eu/PnP/PlugAndPayFeed}EsitoDiCaricamento" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RispostaCaricaPosizione", propOrder = {
    "esitoDiCaricamento"
})
public class RispostaCaricaPosizione {

    @XmlElementRef(name = "EsitoDiCaricamento", namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", type = JAXBElement.class, required = false)
    protected JAXBElement<EsitoDiCaricamento> esitoDiCaricamento;

    /**
     * Recupera il valore della proprietà esitoDiCaricamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link EsitoDiCaricamento }{@code >}
     *     
     */
    public JAXBElement<EsitoDiCaricamento> getEsitoDiCaricamento() {
        return esitoDiCaricamento;
    }

    /**
     * Imposta il valore della proprietà esitoDiCaricamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link EsitoDiCaricamento }{@code >}
     *     
     */
    public void setEsitoDiCaricamento(JAXBElement<EsitoDiCaricamento> value) {
        this.esitoDiCaricamento = value;
    }

}
