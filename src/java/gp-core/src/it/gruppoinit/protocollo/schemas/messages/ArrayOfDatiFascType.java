
package it.gruppoinit.protocollo.schemas.messages;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDatiFascType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDatiFascType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DatiFascType" type="{http://it.gruppoinit/Protocollazione}DatiFascType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDatiFascType", propOrder = {
    "datiFascType"
})
public class ArrayOfDatiFascType {

    @XmlElement(name = "DatiFascType", nillable = true)
    protected List<DatiFascType> datiFascType;

    /**
     * Gets the value of the datiFascType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiFascType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiFascType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiFascType }
     * 
     * 
     */
    public List<DatiFascType> getDatiFascType() {
        if (datiFascType == null) {
            datiFascType = new ArrayList<DatiFascType>();
        }
        return this.datiFascType;
    }

}
