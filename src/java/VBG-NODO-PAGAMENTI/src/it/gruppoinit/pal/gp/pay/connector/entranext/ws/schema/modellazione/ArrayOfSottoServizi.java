
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfSottoServizi complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSottoServizi"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SottoServizi" type="{http://entranext.it/}SottoServizi" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSottoServizi", propOrder = {
    "sottoServizi"
})
public class ArrayOfSottoServizi {

    @XmlElement(name = "SottoServizi", nillable = true)
    protected List<SottoServizi> sottoServizi;

    /**
     * Gets the value of the sottoServizi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sottoServizi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSottoServizi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SottoServizi }
     * 
     * 
     */
    public List<SottoServizi> getSottoServizi() {
        if (sottoServizi == null) {
            sottoServizi = new ArrayList<SottoServizi>();
        }
        return this.sottoServizi;
    }

}
