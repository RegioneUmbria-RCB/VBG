
package it.gruppoinit.sigepro.schemas.messages.regole;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ComuniESoftwarePerRegolaResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ComuniESoftwarePerRegolaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="configurazioni" type="{http://gruppoinit.it/sigepro/schemas/messages/regole}ComuniESoftwareType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComuniESoftwarePerRegolaResponse", propOrder = {
    "configurazioni"
})
public class ComuniESoftwarePerRegolaResponse {

    protected List<ComuniESoftwareType> configurazioni;

    /**
     * Gets the value of the configurazioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configurazioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfigurazioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComuniESoftwareType }
     * 
     * 
     */
    public List<ComuniESoftwareType> getConfigurazioni() {
        if (configurazioni == null) {
            configurazioni = new ArrayList<ComuniESoftwareType>();
        }
        return this.configurazioni;
    }

}
