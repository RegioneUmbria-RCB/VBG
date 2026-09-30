
package it.gruppoinit.protocollo.schemas.messages;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDatiProtocolloLettoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDatiProtocolloLettoResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DatiProtocolloLettoResponseType" type="{http://it.gruppoinit/Protocollazione}DatiProtocolloLettoResponseType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDatiProtocolloLettoResponseType", propOrder = {
    "datiProtocolloLettoResponseType"
})
public class ArrayOfDatiProtocolloLettoResponseType {

    @XmlElement(name = "DatiProtocolloLettoResponseType", nillable = true)
    protected List<DatiProtocolloLettoResponseType> datiProtocolloLettoResponseType;

    /**
     * Gets the value of the datiProtocolloLettoResponseType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiProtocolloLettoResponseType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiProtocolloLettoResponseType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiProtocolloLettoResponseType }
     * 
     * 
     */
    public List<DatiProtocolloLettoResponseType> getDatiProtocolloLettoResponseType() {
        if (datiProtocolloLettoResponseType == null) {
            datiProtocolloLettoResponseType = new ArrayList<DatiProtocolloLettoResponseType>();
        }
        return this.datiProtocolloLettoResponseType;
    }

}
