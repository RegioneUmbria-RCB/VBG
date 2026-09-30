
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf;

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
 *         &lt;element name="request" type="{http://schemas.datacontract.org/2004/07/GeneratorPdfBytes.Service.Contracts}RichiestaCreaRTPdf" minOccurs="0"/&gt;
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
@XmlRootElement(name = "CreatePdfRT")
public class CreatePdfRT {

    @XmlElementRef(name = "request", namespace = "http://e-fil.eu/GeneratorPdf", type = JAXBElement.class, required = false)
    protected JAXBElement<RichiestaCreaRTPdf> request;

    /**
     * Recupera il valore della proprietà request.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RichiestaCreaRTPdf }{@code >}
     *     
     */
    public JAXBElement<RichiestaCreaRTPdf> getRequest() {
        return request;
    }

    /**
     * Imposta il valore della proprietà request.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RichiestaCreaRTPdf }{@code >}
     *     
     */
    public void setRequest(JAXBElement<RichiestaCreaRTPdf> value) {
        this.request = value;
    }

}
