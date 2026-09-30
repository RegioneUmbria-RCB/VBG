
package it.gruppoinit.protocollo.schemas.messages;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfProtocolloMetadati complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfProtocolloMetadati">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProtocolloMetadati" type="{http://schemas.datacontract.org/2004/07/Init.SIGePro.Protocollo.Metadati}ProtocolloMetadati" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProtocolloMetadati", namespace = "http://schemas.datacontract.org/2004/07/Init.SIGePro.Protocollo.Metadati", propOrder = {
    "protocolloMetadati"
})
public class ArrayOfProtocolloMetadati {

    @XmlElement(name = "ProtocolloMetadati", nillable = true)
    protected List<ProtocolloMetadati> protocolloMetadati;

    /**
     * Gets the value of the protocolloMetadati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the protocolloMetadati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtocolloMetadati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProtocolloMetadati }
     * 
     * 
     */
    public List<ProtocolloMetadati> getProtocolloMetadati() {
        if (protocolloMetadati == null) {
            protocolloMetadati = new ArrayList<ProtocolloMetadati>();
        }
        return this.protocolloMetadati;
    }

}
