
package it.gruppoinit.protocollo.schemas.messages;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDatiAnagraficiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDatiAnagraficiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DatiAnagraficiType" type="{http://it.gruppoinit/Protocollazione}DatiAnagraficiType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDatiAnagraficiType", propOrder = {
    "datiAnagraficiType"
})
public class ArrayOfDatiAnagraficiType {

    @XmlElement(name = "DatiAnagraficiType", nillable = true)
    protected List<DatiAnagraficiType> datiAnagraficiType;

    /**
     * Gets the value of the datiAnagraficiType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiAnagraficiType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiAnagraficiType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiAnagraficiType }
     * 
     * 
     */
    public List<DatiAnagraficiType> getDatiAnagraficiType() {
        if (datiAnagraficiType == null) {
            datiAnagraficiType = new ArrayList<DatiAnagraficiType>();
        }
        return this.datiAnagraficiType;
    }

}
