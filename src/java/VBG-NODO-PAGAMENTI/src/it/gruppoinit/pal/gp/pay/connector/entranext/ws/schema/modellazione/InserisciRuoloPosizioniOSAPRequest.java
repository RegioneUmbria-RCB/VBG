
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciRuoloPosizioniOSAPRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciRuoloPosizioniOSAPRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}InserisciRuoloPosizioniRequestBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioniDebitorie" type="{http://entranext.it/}PosizioneDebitoriaOSAP" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciRuoloPosizioniOSAPRequest", propOrder = {
    "posizioniDebitorie"
})
public class InserisciRuoloPosizioniOSAPRequest
    extends InserisciRuoloPosizioniRequestBase
{

    @XmlElement(name = "PosizioniDebitorie")
    protected List<PosizioneDebitoriaOSAP> posizioniDebitorie;

    /**
     * Gets the value of the posizioniDebitorie property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the posizioniDebitorie property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosizioniDebitorie().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioneDebitoriaOSAP }
     * 
     * 
     */
    public List<PosizioneDebitoriaOSAP> getPosizioniDebitorie() {
        if (posizioniDebitorie == null) {
            posizioniDebitorie = new ArrayList<PosizioneDebitoriaOSAP>();
        }
        return this.posizioniDebitorie;
    }

}
