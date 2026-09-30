
package it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listaPubblicazioniTot" type="{http://gruppoinit.it/sigepro/schemas/messages/albopretorio}ListaPubblicazioniTot" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "listaPubblicazioniTot"
})
@XmlRootElement(name = "ListaPubblicazioniTotResponse")
public class ListaPubblicazioniTotResponse {

    protected List<ListaPubblicazioniTot> listaPubblicazioniTot;

    /**
     * Gets the value of the listaPubblicazioniTot property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaPubblicazioniTot property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaPubblicazioniTot().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ListaPubblicazioniTot }
     * 
     * 
     */
    public List<ListaPubblicazioniTot> getListaPubblicazioniTot() {
        if (listaPubblicazioniTot == null) {
            listaPubblicazioniTot = new ArrayList<ListaPubblicazioniTot>();
        }
        return this.listaPubblicazioniTot;
    }

}
