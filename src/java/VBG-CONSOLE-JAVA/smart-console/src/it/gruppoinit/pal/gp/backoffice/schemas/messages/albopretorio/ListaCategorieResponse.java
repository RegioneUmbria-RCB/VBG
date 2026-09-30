
package it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ListaCategorie" type="{http://gruppoinit.it/sigepro/schemas/messages/albopretorio}ListaCategorie" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "listaCategorie"
})
@XmlRootElement(name = "ListaCategorieResponse")
public class ListaCategorieResponse {

    @XmlElement(name = "ListaCategorie")
    protected List<ListaCategorie> listaCategorie;

    /**
     * Gets the value of the listaCategorie property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaCategorie property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaCategorie().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ListaCategorie }
     * 
     * 
     */
    public List<ListaCategorie> getListaCategorie() {
        if (listaCategorie == null) {
            listaCategorie = new ArrayList<ListaCategorie>();
        }
        return this.listaCategorie;
    }

}
