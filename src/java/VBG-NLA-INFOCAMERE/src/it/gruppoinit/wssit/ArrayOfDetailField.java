
package it.gruppoinit.wssit;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDetailField complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDetailField">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DetailField" type="{http://init.sigepro.it}DetailField" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDetailField", propOrder = {
    "detailField"
})
public class ArrayOfDetailField {

    @XmlElement(name = "DetailField", nillable = true)
    protected List<DetailField> detailField;

    /**
     * Gets the value of the detailField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detailField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetailField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetailField }
     * 
     * 
     */
    public List<DetailField> getDetailField() {
        if (detailField == null) {
            detailField = new ArrayList<DetailField>();
        }
        return this.detailField;
    }

}
