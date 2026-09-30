
package it.gruppoinit.protocollo.schemas.messages;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfFirmatario complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFirmatario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Firmatario" type="{http://it.gruppoinit/Protocollazione}Firmatario" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFirmatario", propOrder = {
    "firmatario"
})
public class ArrayOfFirmatario {

    @XmlElement(name = "Firmatario", nillable = true)
    protected List<Firmatario> firmatario;

    /**
     * Gets the value of the firmatario property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the firmatario property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFirmatario().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Firmatario }
     * 
     * 
     */
    public List<Firmatario> getFirmatario() {
        if (firmatario == null) {
            firmatario = new ArrayList<Firmatario>();
        }
        return this.firmatario;
    }

}
