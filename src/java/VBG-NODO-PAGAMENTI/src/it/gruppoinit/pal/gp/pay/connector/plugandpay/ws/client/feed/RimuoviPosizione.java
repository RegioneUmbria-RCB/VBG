
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
 *         &lt;element name="request" type="{http://e-fil.eu/PnP/PlugAndPayFeed}RichiestaRimuoviPosizione" minOccurs="0"/&gt;
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
    "request"
})
@XmlRootElement(name = "RimuoviPosizione")
public class RimuoviPosizione {

    @XmlElementRef(name = "request", namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", type = JAXBElement.class, required = false)
    protected JAXBElement<RichiestaRimuoviPosizione> request;

    /**
     * Recupera il valore della proprietà request.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RichiestaRimuoviPosizione }{@code >}
     *     
     */
    public JAXBElement<RichiestaRimuoviPosizione> getRequest() {
        return request;
    }

    /**
     * Imposta il valore della proprietà request.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RichiestaRimuoviPosizione }{@code >}
     *     
     */
    public void setRequest(JAXBElement<RichiestaRimuoviPosizione> value) {
        this.request = value;
    }

}
