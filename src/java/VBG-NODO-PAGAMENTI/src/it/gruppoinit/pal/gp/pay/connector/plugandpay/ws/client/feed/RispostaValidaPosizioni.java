
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RispostaValidaPosizioni complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RispostaValidaPosizioni"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EsitiDiCaricamento" type="{http://e-fil.eu/PnP/PlugAndPayFeed}ArrayOfEsitoDiValidazione" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RispostaValidaPosizioni", propOrder = {
    "esitiDiCaricamento"
})
public class RispostaValidaPosizioni {

    @XmlElementRef(name = "EsitiDiCaricamento", namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfEsitoDiValidazione> esitiDiCaricamento;

    /**
     * Recupera il valore della proprietà esitiDiCaricamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfEsitoDiValidazione }{@code >}
     *     
     */
    public JAXBElement<ArrayOfEsitoDiValidazione> getEsitiDiCaricamento() {
        return esitiDiCaricamento;
    }

    /**
     * Imposta il valore della proprietà esitiDiCaricamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfEsitoDiValidazione }{@code >}
     *     
     */
    public void setEsitiDiCaricamento(JAXBElement<ArrayOfEsitoDiValidazione> value) {
        this.esitiDiCaricamento = value;
    }

}
