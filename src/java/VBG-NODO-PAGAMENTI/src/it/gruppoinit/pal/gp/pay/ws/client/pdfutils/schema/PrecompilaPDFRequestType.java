
package it.gruppoinit.pal.gp.pay.ws.client.pdfutils.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PrecompilaPDFRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrecompilaPDFRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="xmlFileIn" type="{http://gruppoinit.it/schemas/messages/pdfutils}XmlFileType"/>
 *         &lt;element name="pdfList" type="{http://gruppoinit.it/schemas/messages/pdfutils}PDFFileType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrecompilaPDFRequestType", propOrder = {
    "token",
    "xmlFileIn",
    "pdfList"
})
public class PrecompilaPDFRequestType {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected XmlFileType xmlFileIn;
    @XmlElement(required = true)
    protected List<PDFFileType> pdfList;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the xmlFileIn property.
     * 
     * @return
     *     possible object is
     *     {@link XmlFileType }
     *     
     */
    public XmlFileType getXmlFileIn() {
        return xmlFileIn;
    }

    /**
     * Sets the value of the xmlFileIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlFileType }
     *     
     */
    public void setXmlFileIn(XmlFileType value) {
        this.xmlFileIn = value;
    }

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

}
