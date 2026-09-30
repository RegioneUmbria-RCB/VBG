
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfEsitoDiCaricamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfEsitoDiCaricamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EsitoDiCaricamento" type="{http://e-fil.eu/PnP/PlugAndPayFeed}EsitoDiCaricamento" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfEsitoDiCaricamento", propOrder = {
    "esitoDiCaricamento"
})
public class ArrayOfEsitoDiCaricamento {

    @XmlElement(name = "EsitoDiCaricamento", nillable = true)
    protected List<EsitoDiCaricamento> esitoDiCaricamento;

    /**
     * Gets the value of the esitoDiCaricamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esitoDiCaricamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsitoDiCaricamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsitoDiCaricamento }
     * 
     * 
     */
    public List<EsitoDiCaricamento> getEsitoDiCaricamento() {
        if (esitoDiCaricamento == null) {
            esitoDiCaricamento = new ArrayList<EsitoDiCaricamento>();
        }
        return this.esitoDiCaricamento;
    }

}
