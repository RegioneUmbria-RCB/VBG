
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfRuolo_CausaleImporto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRuolo_CausaleImporto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Ruolo_CausaleImporto" type="{http://entranext.it/}Ruolo_CausaleImporto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRuolo_CausaleImporto", propOrder = {
    "ruoloCausaleImporto"
})
public class ArrayOfRuoloCausaleImporto {

    @XmlElement(name = "Ruolo_CausaleImporto", nillable = true)
    protected List<RuoloCausaleImporto> ruoloCausaleImporto;

    /**
     * Gets the value of the ruoloCausaleImporto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ruoloCausaleImporto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRuoloCausaleImporto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RuoloCausaleImporto }
     * 
     * 
     */
    public List<RuoloCausaleImporto> getRuoloCausaleImporto() {
        if (ruoloCausaleImporto == null) {
            ruoloCausaleImporto = new ArrayList<RuoloCausaleImporto>();
        }
        return this.ruoloCausaleImporto;
    }

}
