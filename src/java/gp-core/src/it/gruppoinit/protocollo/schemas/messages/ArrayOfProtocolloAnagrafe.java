
package it.gruppoinit.protocollo.schemas.messages;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfProtocolloAnagrafe complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfProtocolloAnagrafe">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProtocolloAnagrafe" type="{http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data}ProtocolloAnagrafe" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProtocolloAnagrafe", namespace = "http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", propOrder = {
    "protocolloAnagrafe"
})
public class ArrayOfProtocolloAnagrafe {

    @XmlElement(name = "ProtocolloAnagrafe", nillable = true)
    protected List<ProtocolloAnagrafe> protocolloAnagrafe;

    /**
     * Gets the value of the protocolloAnagrafe property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the protocolloAnagrafe property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtocolloAnagrafe().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProtocolloAnagrafe }
     * 
     * 
     */
    public List<ProtocolloAnagrafe> getProtocolloAnagrafe() {
        if (protocolloAnagrafe == null) {
            protocolloAnagrafe = new ArrayList<ProtocolloAnagrafe>();
        }
        return this.protocolloAnagrafe;
    }

}
