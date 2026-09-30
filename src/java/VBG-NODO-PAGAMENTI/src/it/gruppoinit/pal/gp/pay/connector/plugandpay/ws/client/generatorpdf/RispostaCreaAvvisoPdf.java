package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per RispostaCreaAvvisoPdf complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RispostaCreaAvvisoPdf"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AvvisoPdf" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RispostaCreaAvvisoPdf", propOrder = { "avvisoPdf" })
public class RispostaCreaAvvisoPdf {

    @XmlElement(name = "AvvisoPdf", required = false)
    protected byte[] avvisoPdf;

    /**
     * Recupera il valore della proprietà avvisoPdf.
     * 
     * @return possible object is {@link byte[]}
     * 
     */
    public byte[] getAvvisoPdf() {

	return avvisoPdf;
    }

    /**
     * Imposta il valore della proprietà avvisoPdf.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     */
    public void setAvvisoPdf(byte[] value) {

	this.avvisoPdf = value;
    }
}
