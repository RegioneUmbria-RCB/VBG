
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfParametroPosizione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfParametroPosizione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ParametroPosizione" type="{http://e-fil.eu/PnP/PlugAndPayFeed}ParametroPosizione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfParametroPosizione", propOrder = {
    "parametroPosizione"
})
public class ArrayOfParametroPosizione {

    @XmlElement(name = "ParametroPosizione", nillable = true)
    protected List<ParametroPosizione> parametroPosizione;

    /**
     * Gets the value of the parametroPosizione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parametroPosizione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParametroPosizione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParametroPosizione }
     * 
     * 
     */
    public List<ParametroPosizione> getParametroPosizione() {
        if (parametroPosizione == null) {
            parametroPosizione = new ArrayList<ParametroPosizione>();
        }
        return this.parametroPosizione;
    }

}
