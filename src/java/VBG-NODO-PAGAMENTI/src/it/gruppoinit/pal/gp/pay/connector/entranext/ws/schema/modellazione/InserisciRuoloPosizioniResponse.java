
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciRuoloPosizioniResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciRuoloPosizioniResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}InserisciRuoloPosizioniResponseBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioniDebitorieResult" type="{http://entranext.it/}PosizioneDebitoriaResult" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciRuoloPosizioniResponse", propOrder = {
    "posizioniDebitorieResult"
})
public class InserisciRuoloPosizioniResponse
    extends InserisciRuoloPosizioniResponseBase
{

    @XmlElement(name = "PosizioniDebitorieResult")
    protected List<PosizioneDebitoriaResult> posizioniDebitorieResult;

    /**
     * Gets the value of the posizioniDebitorieResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the posizioniDebitorieResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosizioniDebitorieResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioneDebitoriaResult }
     * 
     * 
     */
    public List<PosizioneDebitoriaResult> getPosizioniDebitorieResult() {
        if (posizioniDebitorieResult == null) {
            posizioniDebitorieResult = new ArrayList<PosizioneDebitoriaResult>();
        }
        return this.posizioniDebitorieResult;
    }

}
