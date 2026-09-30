
package it.gruppoinit.pal.gp.pay.ws.client.pdfutils.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicaTextLayerPDFRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicaTextLayerPDFRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="layer" type="{http://gruppoinit.it/schemas/messages/pdfutils}Layer"/>
 *         &lt;element name="pdfText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="font" type="{http://gruppoinit.it/schemas/messages/pdfutils}Font" minOccurs="0"/>
 *         &lt;element name="pdf" type="{http://gruppoinit.it/schemas/messages/pdfutils}PDFFileType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicaTextLayerPDFRequest", propOrder = {
    "layer",
    "pdfText",
    "font",
    "pdf"
})
public class ApplicaTextLayerPDFRequest {

    @XmlElement(required = true)
    protected Layer layer;
    @XmlElement(required = true)
    protected List<String> pdfText;
    protected Font font;
    @XmlElement(required = true)
    protected PDFFileType pdf;

    /**
     * Gets the value of the layer property.
     * 
     * @return
     *     possible object is
     *     {@link Layer }
     *     
     */
    public Layer getLayer() {
        return layer;
    }

    /**
     * Sets the value of the layer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Layer }
     *     
     */
    public void setLayer(Layer value) {
        this.layer = value;
    }

    /**
     * Gets the value of the pdfText property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdfText property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdfText().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPdfText() {
        if (pdfText == null) {
            pdfText = new ArrayList<String>();
        }
        return this.pdfText;
    }

    /**
     * Gets the value of the font property.
     * 
     * @return
     *     possible object is
     *     {@link Font }
     *     
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the value of the font property.
     * 
     * @param value
     *     allowed object is
     *     {@link Font }
     *     
     */
    public void setFont(Font value) {
        this.font = value;
    }

    /**
     * Gets the value of the pdf property.
     * 
     * @return
     *     possible object is
     *     {@link PDFFileType }
     *     
     */
    public PDFFileType getPdf() {
        return pdf;
    }

    /**
     * Sets the value of the pdf property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDFFileType }
     *     
     */
    public void setPdf(PDFFileType value) {
        this.pdf = value;
    }

}
