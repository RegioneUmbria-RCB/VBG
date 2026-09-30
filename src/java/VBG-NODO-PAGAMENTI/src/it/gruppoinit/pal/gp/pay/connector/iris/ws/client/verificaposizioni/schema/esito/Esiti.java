
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.esito;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Esiti complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Esiti"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Esito" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}Esito" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Esiti", propOrder = {
    "esito"
})
public class Esiti {

    @XmlElement(name = "Esito", required = true)
    protected List<Esito> esito;

    /**
     * Gets the value of the esito property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esito property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsito().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Esito }
     * 
     * 
     */
    public List<Esito> getEsito() {
        if (esito == null) {
            esito = new ArrayList<Esito>();
        }
        return this.esito;
    }

}
