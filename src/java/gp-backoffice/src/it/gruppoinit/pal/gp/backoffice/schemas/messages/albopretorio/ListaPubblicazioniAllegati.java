
package it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaPubblicazioniAllegati complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaPubblicazioniAllegati">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="pubblicazioniAllegati" type="{http://gruppoinit.it/sigepro/schemas/messages/albopretorio}pubblicazioniAllegati"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaPubblicazioniAllegati", propOrder = {
    "pubblicazioniAllegati"
})
public class ListaPubblicazioniAllegati {

    protected List<PubblicazioniAllegati> pubblicazioniAllegati;

    /**
     * Gets the value of the pubblicazioniAllegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pubblicazioniAllegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPubblicazioniAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PubblicazioniAllegati }
     * 
     * 
     */
    public List<PubblicazioniAllegati> getPubblicazioniAllegati() {
        if (pubblicazioniAllegati == null) {
            pubblicazioniAllegati = new ArrayList<PubblicazioniAllegati>();
        }
        return this.pubblicazioniAllegati;
    }

}
