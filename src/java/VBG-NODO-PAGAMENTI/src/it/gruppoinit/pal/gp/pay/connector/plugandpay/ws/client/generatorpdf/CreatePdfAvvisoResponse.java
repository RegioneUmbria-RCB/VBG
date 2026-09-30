
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
 *         &lt;element name="CreatePdfAvvisoResult" type="{http://e-fil.eu/GeneratorPdf}RispostaCreaAvvisoPdf" minOccurs="0"/&gt;
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
    "createPdfAvvisoResult"
})
@XmlRootElement(name = "CreatePdfAvvisoResponse")
public class CreatePdfAvvisoResponse {

    @XmlElementRef(name = "CreatePdfAvvisoResult", namespace = "http://e-fil.eu/GeneratorPdf", type = JAXBElement.class, required = false)
    protected JAXBElement<RispostaCreaAvvisoPdf> createPdfAvvisoResult;

    /**
     * Recupera il valore della proprietà createPdfAvvisoResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RispostaCreaAvvisoPdf }{@code >}
     *     
     */
    public JAXBElement<RispostaCreaAvvisoPdf> getCreatePdfAvvisoResult() {
        return createPdfAvvisoResult;
    }

    /**
     * Imposta il valore della proprietà createPdfAvvisoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RispostaCreaAvvisoPdf }{@code >}
     *     
     */
    public void setCreatePdfAvvisoResult(JAXBElement<RispostaCreaAvvisoPdf> value) {
        this.createPdfAvvisoResult = value;
    }

}
