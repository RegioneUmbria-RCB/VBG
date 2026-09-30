
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfEsitoDiValidazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfEsitoDiValidazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EsitoDiValidazione" type="{http://e-fil.eu/PnP/PlugAndPayFeed}EsitoDiValidazione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfEsitoDiValidazione", propOrder = {
    "esitoDiValidazione"
})
public class ArrayOfEsitoDiValidazione {

    @XmlElement(name = "EsitoDiValidazione", nillable = true)
    protected List<EsitoDiValidazione> esitoDiValidazione;

    /**
     * Gets the value of the esitoDiValidazione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esitoDiValidazione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsitoDiValidazione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsitoDiValidazione }
     * 
     * 
     */
    public List<EsitoDiValidazione> getEsitoDiValidazione() {
        if (esitoDiValidazione == null) {
            esitoDiValidazione = new ArrayList<EsitoDiValidazione>();
        }
        return this.esitoDiValidazione;
    }

}
