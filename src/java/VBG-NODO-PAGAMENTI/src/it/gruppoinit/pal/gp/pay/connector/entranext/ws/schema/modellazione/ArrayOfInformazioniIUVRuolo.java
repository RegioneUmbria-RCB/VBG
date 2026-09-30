
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfInformazioniIUVRuolo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfInformazioniIUVRuolo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InformazioniIUVRuolo" type="{http://entranext.it/}InformazioniIUVRuolo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInformazioniIUVRuolo", propOrder = {
    "informazioniIUVRuolo"
})
public class ArrayOfInformazioniIUVRuolo {

    @XmlElement(name = "InformazioniIUVRuolo", nillable = true)
    protected List<InformazioniIUVRuolo> informazioniIUVRuolo;

    /**
     * Gets the value of the informazioniIUVRuolo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the informazioniIUVRuolo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInformazioniIUVRuolo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InformazioniIUVRuolo }
     * 
     * 
     */
    public List<InformazioniIUVRuolo> getInformazioniIUVRuolo() {
        if (informazioniIUVRuolo == null) {
            informazioniIUVRuolo = new ArrayList<InformazioniIUVRuolo>();
        }
        return this.informazioniIUVRuolo;
    }

}
