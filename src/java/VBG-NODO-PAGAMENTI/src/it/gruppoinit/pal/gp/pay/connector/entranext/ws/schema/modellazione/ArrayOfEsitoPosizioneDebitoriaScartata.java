
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfEsitoPosizioneDebitoriaScartata complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfEsitoPosizioneDebitoriaScartata"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EsitoPosizioneDebitoriaScartata" type="{http://entranext.it/}EsitoPosizioneDebitoriaScartata" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfEsitoPosizioneDebitoriaScartata", propOrder = {
    "esitoPosizioneDebitoriaScartata"
})
public class ArrayOfEsitoPosizioneDebitoriaScartata {

    @XmlElement(name = "EsitoPosizioneDebitoriaScartata", nillable = true)
    protected List<EsitoPosizioneDebitoriaScartata> esitoPosizioneDebitoriaScartata;

    /**
     * Gets the value of the esitoPosizioneDebitoriaScartata property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esitoPosizioneDebitoriaScartata property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsitoPosizioneDebitoriaScartata().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsitoPosizioneDebitoriaScartata }
     * 
     * 
     */
    public List<EsitoPosizioneDebitoriaScartata> getEsitoPosizioneDebitoriaScartata() {
        if (esitoPosizioneDebitoriaScartata == null) {
            esitoPosizioneDebitoriaScartata = new ArrayList<EsitoPosizioneDebitoriaScartata>();
        }
        return this.esitoPosizioneDebitoriaScartata;
    }

}
