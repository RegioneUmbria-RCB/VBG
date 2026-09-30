
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DettaglioImportoTransato complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioImportoTransato"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Voce" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}Voce" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioImportoTransato", propOrder = {
    "voce"
})
public class DettaglioImportoTransato {

    @XmlElement(name = "Voce", required = true)
    protected List<Voce> voce;

    /**
     * Gets the value of the voce property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the voce property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVoce().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Voce }
     * 
     * 
     */
    public List<Voce> getVoce() {
        if (voce == null) {
            voce = new ArrayList<Voce>();
        }
        return this.voce;
    }

}
