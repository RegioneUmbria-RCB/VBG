
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfRendicontazionePagamenti complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRendicontazionePagamenti"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RendicontazionePagamenti" type="{http://entranext.it/}RendicontazionePagamenti" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRendicontazionePagamenti", propOrder = {
    "rendicontazionePagamenti"
})
public class ArrayOfRendicontazionePagamenti {

    @XmlElement(name = "RendicontazionePagamenti", nillable = true)
    protected List<RendicontazionePagamenti> rendicontazionePagamenti;

    /**
     * Gets the value of the rendicontazionePagamenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rendicontazionePagamenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRendicontazionePagamenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RendicontazionePagamenti }
     * 
     * 
     */
    public List<RendicontazionePagamenti> getRendicontazionePagamenti() {
        if (rendicontazionePagamenti == null) {
            rendicontazionePagamenti = new ArrayList<RendicontazionePagamenti>();
        }
        return this.rendicontazionePagamenti;
    }

}
