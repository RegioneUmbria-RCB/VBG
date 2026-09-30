
package it.gruppoinit.pal.gp.pay.ws.client.pdfutils.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PrecompilaPDFResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrecompilaPDFResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="pdfList" type="{http://gruppoinit.it/schemas/messages/pdfutils}PDFFileType" maxOccurs="unbounded"/>
 *           &lt;element name="errori" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrecompilaPDFResponseType", propOrder = {
    "pdfList",
    "errori"
})
public class PrecompilaPDFResponseType {

    protected List<PDFFileType> pdfList;
    protected List<String> errori;

    /**
     * Gets the value of the pdfList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdfList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdfList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PDFFileType }
     * 
     * 
     */
    public List<PDFFileType> getPdfList() {
        if (pdfList == null) {
            pdfList = new ArrayList<PDFFileType>();
        }
        return this.pdfList;
    }

    /**
     * Gets the value of the errori property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errori property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrori().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getErrori() {
        if (errori == null) {
            errori = new ArrayList<String>();
        }
        return this.errori;
    }

}
