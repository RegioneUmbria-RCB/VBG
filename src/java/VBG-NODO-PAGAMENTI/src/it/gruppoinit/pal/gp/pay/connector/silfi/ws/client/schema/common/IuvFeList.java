
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Lista di Codici Iuv
 * 				corrispondenti ad altrettanti Pagamenti Attesi
 * 			
 * 
 * <p>Classe Java per IuvFeList complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IuvFeList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IuvFe" type="{it/lineacomune/pagopa/be/ws/endpoint/shared}IuvFe" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IuvFeList", propOrder = {
    "iuvFe"
})
public class IuvFeList {

    @XmlElement(name = "IuvFe", required = true)
    protected List<IuvFe> iuvFe;

    /**
     * Gets the value of the iuvFe property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the iuvFe property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIuvFe().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IuvFe }
     * 
     * 
     */
    public List<IuvFe> getIuvFe() {
        if (iuvFe == null) {
            iuvFe = new ArrayList<IuvFe>();
        }
        return this.iuvFe;
    }

}
