
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfRipartizioneDiImportoInAccertamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRipartizioneDiImportoInAccertamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RipartizioneDiImportoInAccertamento" type="{http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts}RipartizioneDiImportoInAccertamento" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRipartizioneDiImportoInAccertamento", namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", propOrder = {
    "ripartizioneDiImportoInAccertamento"
})
public class ArrayOfRipartizioneDiImportoInAccertamento {

    @XmlElement(name = "RipartizioneDiImportoInAccertamento", nillable = true)
    protected List<RipartizioneDiImportoInAccertamento> ripartizioneDiImportoInAccertamento;

    /**
     * Gets the value of the ripartizioneDiImportoInAccertamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ripartizioneDiImportoInAccertamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRipartizioneDiImportoInAccertamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RipartizioneDiImportoInAccertamento }
     * 
     * 
     */
    public List<RipartizioneDiImportoInAccertamento> getRipartizioneDiImportoInAccertamento() {
        if (ripartizioneDiImportoInAccertamento == null) {
            ripartizioneDiImportoInAccertamento = new ArrayList<RipartizioneDiImportoInAccertamento>();
        }
        return this.ripartizioneDiImportoInAccertamento;
    }

}
