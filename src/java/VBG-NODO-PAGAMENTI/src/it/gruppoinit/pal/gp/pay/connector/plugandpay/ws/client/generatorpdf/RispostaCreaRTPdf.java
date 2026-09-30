package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per RispostaCreaRTPdf complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RispostaCreaRTPdf"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RTPdf" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RispostaCreaRTPdf", propOrder = { "rtPdf" })
public class RispostaCreaRTPdf {

    @XmlElement(name = "RTPdf", required = false)
    protected byte[] rtPdf;

    /**
     * Recupera il valore della proprietà rtPdf.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     */
    public byte[] getRTPdf() {

	return rtPdf;
    }

    /**
     * Imposta il valore della proprietà rtPdf.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     */
    public void setRTPdf(byte[] value) {

	this.rtPdf = value;
    }
}
