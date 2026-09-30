
package it.gruppoinit.pdfutils.schemas.messages;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecuperaDatiDaPDFResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RecuperaDatiDaPDFResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="dati" type="{http://gruppoinit.it/schemas/messages/pdfutils}DatiPDFType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="errori" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecuperaDatiDaPDFResponseType", propOrder = {
    "dati",
    "errori"
})
public class RecuperaDatiDaPDFResponseType {

    protected List<DatiPDFType> dati;
    protected List<String> errori;

    /**
     * Gets the value of the dati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiPDFType }
     * 
     * 
     */
    public List<DatiPDFType> getDati() {
        if (dati == null) {
            dati = new ArrayList<DatiPDFType>();
        }
        return this.dati;
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
