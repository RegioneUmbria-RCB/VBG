
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
 *         &lt;element name="CreatePdfRTResult" type="{http://e-fil.eu/GeneratorPdf}RispostaCreaRTPdf" minOccurs="0"/&gt;
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
    "createPdfRTResult"
})
@XmlRootElement(name = "CreatePdfRTResponse")
public class CreatePdfRTResponse {

    @XmlElementRef(name = "CreatePdfRTResult", namespace = "http://e-fil.eu/GeneratorPdf", type = JAXBElement.class, required = false)
    protected JAXBElement<RispostaCreaRTPdf> createPdfRTResult;

    /**
     * Recupera il valore della proprietà createPdfRTResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RispostaCreaRTPdf }{@code >}
     *     
     */
    public JAXBElement<RispostaCreaRTPdf> getCreatePdfRTResult() {
        return createPdfRTResult;
    }

    /**
     * Imposta il valore della proprietà createPdfRTResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RispostaCreaRTPdf }{@code >}
     *     
     */
    public void setCreatePdfRTResult(JAXBElement<RispostaCreaRTPdf> value) {
        this.createPdfRTResult = value;
    }

}
