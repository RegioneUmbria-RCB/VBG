
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfErroreDiValidazionePosizione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfErroreDiValidazionePosizione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ErroreDiValidazionePosizione" type="{http://e-fil.eu/PnP/PlugAndPayPayment}ErroreDiValidazionePosizione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfErroreDiValidazionePosizione", propOrder = {
    "erroreDiValidazionePosizione"
})
public class ArrayOfErroreDiValidazionePosizione {

    @XmlElement(name = "ErroreDiValidazionePosizione", nillable = true)
    protected List<ErroreDiValidazionePosizione> erroreDiValidazionePosizione;

    /**
     * Gets the value of the erroreDiValidazionePosizione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the erroreDiValidazionePosizione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErroreDiValidazionePosizione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErroreDiValidazionePosizione }
     * 
     * 
     */
    public List<ErroreDiValidazionePosizione> getErroreDiValidazionePosizione() {
        if (erroreDiValidazionePosizione == null) {
            erroreDiValidazionePosizione = new ArrayList<ErroreDiValidazionePosizione>();
        }
        return this.erroreDiValidazionePosizione;
    }

}
