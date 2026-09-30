
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfPosizioneDebitoriaICP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPosizioneDebitoriaICP"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioneDebitoriaICP" type="{http://entranext.it/}PosizioneDebitoriaICP" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPosizioneDebitoriaICP", propOrder = {
    "posizioneDebitoriaICP"
})
public class ArrayOfPosizioneDebitoriaICP {

    @XmlElement(name = "PosizioneDebitoriaICP", nillable = true)
    protected List<PosizioneDebitoriaICP> posizioneDebitoriaICP;

    /**
     * Gets the value of the posizioneDebitoriaICP property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the posizioneDebitoriaICP property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosizioneDebitoriaICP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioneDebitoriaICP }
     * 
     * 
     */
    public List<PosizioneDebitoriaICP> getPosizioneDebitoriaICP() {
        if (posizioneDebitoriaICP == null) {
            posizioneDebitoriaICP = new ArrayList<PosizioneDebitoriaICP>();
        }
        return this.posizioneDebitoriaICP;
    }

}
