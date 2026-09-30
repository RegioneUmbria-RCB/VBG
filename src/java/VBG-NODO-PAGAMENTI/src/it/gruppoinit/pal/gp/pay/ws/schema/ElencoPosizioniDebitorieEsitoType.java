
package it.gruppoinit.pal.gp.pay.ws.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ElencoPosizioniDebitorieEsitoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ElencoPosizioniDebitorieEsitoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence maxOccurs="unbounded"&gt;
 *         &lt;element name="esitoPosizione" type="{http://www.paevolution.com/ws/pagamenti_types/}EsitoOperazionePosizioneDebitoriaType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElencoPosizioniDebitorieEsitoType", propOrder = {
    "esitoPosizione"
})
public class ElencoPosizioniDebitorieEsitoType {

    @XmlElement(required = true)
    protected List<EsitoOperazionePosizioneDebitoriaType> esitoPosizione;

    /**
     * Gets the value of the esitoPosizione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esitoPosizione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsitoPosizione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsitoOperazionePosizioneDebitoriaType }
     * 
     * 
     */
    public List<EsitoOperazionePosizioneDebitoriaType> getEsitoPosizione() {
        if (esitoPosizione == null) {
            esitoPosizione = new ArrayList<EsitoOperazionePosizioneDebitoriaType>();
        }
        return this.esitoPosizione;
    }

}
