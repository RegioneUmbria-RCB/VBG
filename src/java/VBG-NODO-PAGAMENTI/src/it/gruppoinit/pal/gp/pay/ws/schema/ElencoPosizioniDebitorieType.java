
package it.gruppoinit.pal.gp.pay.ws.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ElencoPosizioniDebitorieType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ElencoPosizioniDebitorieType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence maxOccurs="unbounded"&gt;
 *         &lt;element name="posizione" type="{http://www.paevolution.com/ws/pagamenti_types/}RiferimentoPosizioneDebitoriaType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElencoPosizioniDebitorieType", propOrder = {
    "posizione"
})
public class ElencoPosizioniDebitorieType {

    @XmlElement(required = true)
    protected List<RiferimentoPosizioneDebitoriaType> posizione;

    /**
     * Gets the value of the posizione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the posizione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosizione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RiferimentoPosizioneDebitoriaType }
     * 
     * 
     */
    public List<RiferimentoPosizioneDebitoriaType> getPosizione() {
        if (posizione == null) {
            posizione = new ArrayList<RiferimentoPosizioneDebitoriaType>();
        }
        return this.posizione;
    }

}
