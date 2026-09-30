
package it.gruppoinit.pal.gp.pay.ws.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}PayRequestType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="riferimentoPosizione" type="{http://www.paevolution.com/ws/pagamenti_types/}RiferimentoPosizioneDebitoriaType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "riferimentoPosizione"
})
@XmlRootElement(name = "InviaAvvisiPagamentoType")
public class InviaAvvisiPagamentoType
    extends PayRequestType
{

    @XmlElement(required = true)
    protected List<RiferimentoPosizioneDebitoriaType> riferimentoPosizione;

    /**
     * Gets the value of the riferimentoPosizione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the riferimentoPosizione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRiferimentoPosizione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RiferimentoPosizioneDebitoriaType }
     * 
     * 
     */
    public List<RiferimentoPosizioneDebitoriaType> getRiferimentoPosizione() {
        if (riferimentoPosizione == null) {
            riferimentoPosizione = new ArrayList<RiferimentoPosizioneDebitoriaType>();
        }
        return this.riferimentoPosizione;
    }

}
