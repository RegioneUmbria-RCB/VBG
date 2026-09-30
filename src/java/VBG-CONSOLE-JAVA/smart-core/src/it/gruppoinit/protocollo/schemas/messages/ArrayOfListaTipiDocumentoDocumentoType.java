
package it.gruppoinit.protocollo.schemas.messages;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfListaTipiDocumentoDocumentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfListaTipiDocumentoDocumentoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ListaTipiDocumentoDocumentoType" type="{http://it.gruppoinit/Protocollazione}ListaTipiDocumentoDocumentoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfListaTipiDocumentoDocumentoType", propOrder = {
    "listaTipiDocumentoDocumentoType"
})
public class ArrayOfListaTipiDocumentoDocumentoType {

    @XmlElement(name = "ListaTipiDocumentoDocumentoType", nillable = true)
    protected List<ListaTipiDocumentoDocumentoType> listaTipiDocumentoDocumentoType;

    /**
     * Gets the value of the listaTipiDocumentoDocumentoType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaTipiDocumentoDocumentoType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaTipiDocumentoDocumentoType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ListaTipiDocumentoDocumentoType }
     * 
     * 
     */
    public List<ListaTipiDocumentoDocumentoType> getListaTipiDocumentoDocumentoType() {
        if (listaTipiDocumentoDocumentoType == null) {
            listaTipiDocumentoDocumentoType = new ArrayList<ListaTipiDocumentoDocumentoType>();
        }
        return this.listaTipiDocumentoDocumentoType;
    }

}
