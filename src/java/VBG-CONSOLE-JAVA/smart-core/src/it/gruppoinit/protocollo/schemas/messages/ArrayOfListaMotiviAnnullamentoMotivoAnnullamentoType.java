
package it.gruppoinit.protocollo.schemas.messages;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ListaMotiviAnnullamentoMotivoAnnullamentoType" type="{http://it.gruppoinit/Protocollazione}ListaMotiviAnnullamentoMotivoAnnullamentoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType", propOrder = {
    "listaMotiviAnnullamentoMotivoAnnullamentoType"
})
public class ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType {

    @XmlElement(name = "ListaMotiviAnnullamentoMotivoAnnullamentoType", nillable = true)
    protected List<ListaMotiviAnnullamentoMotivoAnnullamentoType> listaMotiviAnnullamentoMotivoAnnullamentoType;

    /**
     * Gets the value of the listaMotiviAnnullamentoMotivoAnnullamentoType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaMotiviAnnullamentoMotivoAnnullamentoType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaMotiviAnnullamentoMotivoAnnullamentoType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ListaMotiviAnnullamentoMotivoAnnullamentoType }
     * 
     * 
     */
    public List<ListaMotiviAnnullamentoMotivoAnnullamentoType> getListaMotiviAnnullamentoMotivoAnnullamentoType() {
        if (listaMotiviAnnullamentoMotivoAnnullamentoType == null) {
            listaMotiviAnnullamentoMotivoAnnullamentoType = new ArrayList<ListaMotiviAnnullamentoMotivoAnnullamentoType>();
        }
        return this.listaMotiviAnnullamentoMotivoAnnullamentoType;
    }

}
