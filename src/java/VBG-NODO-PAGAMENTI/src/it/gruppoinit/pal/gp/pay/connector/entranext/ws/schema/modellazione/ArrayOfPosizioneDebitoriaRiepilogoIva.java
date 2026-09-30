
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfPosizioneDebitoria_RiepilogoIva complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPosizioneDebitoria_RiepilogoIva"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioneDebitoria_RiepilogoIva" type="{http://entranext.it/}PosizioneDebitoria_RiepilogoIva" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPosizioneDebitoria_RiepilogoIva", propOrder = {
    "posizioneDebitoriaRiepilogoIva"
})
public class ArrayOfPosizioneDebitoriaRiepilogoIva {

    @XmlElement(name = "PosizioneDebitoria_RiepilogoIva", nillable = true)
    protected List<PosizioneDebitoriaRiepilogoIva> posizioneDebitoriaRiepilogoIva;

    /**
     * Gets the value of the posizioneDebitoriaRiepilogoIva property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the posizioneDebitoriaRiepilogoIva property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosizioneDebitoriaRiepilogoIva().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioneDebitoriaRiepilogoIva }
     * 
     * 
     */
    public List<PosizioneDebitoriaRiepilogoIva> getPosizioneDebitoriaRiepilogoIva() {
        if (posizioneDebitoriaRiepilogoIva == null) {
            posizioneDebitoriaRiepilogoIva = new ArrayList<PosizioneDebitoriaRiepilogoIva>();
        }
        return this.posizioneDebitoriaRiepilogoIva;
    }

}
