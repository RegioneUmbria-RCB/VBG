
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfPosizioneDebitoriaOSAP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPosizioneDebitoriaOSAP"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioneDebitoriaOSAP" type="{http://entranext.it/}PosizioneDebitoriaOSAP" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPosizioneDebitoriaOSAP", propOrder = {
    "posizioneDebitoriaOSAP"
})
public class ArrayOfPosizioneDebitoriaOSAP {

    @XmlElement(name = "PosizioneDebitoriaOSAP", nillable = true)
    protected List<PosizioneDebitoriaOSAP> posizioneDebitoriaOSAP;

    /**
     * Gets the value of the posizioneDebitoriaOSAP property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the posizioneDebitoriaOSAP property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosizioneDebitoriaOSAP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioneDebitoriaOSAP }
     * 
     * 
     */
    public List<PosizioneDebitoriaOSAP> getPosizioneDebitoriaOSAP() {
        if (posizioneDebitoriaOSAP == null) {
            posizioneDebitoriaOSAP = new ArrayList<PosizioneDebitoriaOSAP>();
        }
        return this.posizioneDebitoriaOSAP;
    }

}
