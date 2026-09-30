
package it.ldp.suolopubblico;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfComplexTypeStringa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfComplexTypeStringa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ComplexTypeStringa" type="{https://ws.ldpgis.it/}ComplexTypeStringa" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfComplexTypeStringa", propOrder = {
    "complexTypeStringa"
})
public class ArrayOfComplexTypeStringa {

    @XmlElement(name = "ComplexTypeStringa")
    protected List<ComplexTypeStringa> complexTypeStringa;

    /**
     * Gets the value of the complexTypeStringa property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the complexTypeStringa property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComplexTypeStringa().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypeStringa }
     * 
     * 
     */
    public List<ComplexTypeStringa> getComplexTypeStringa() {
        if (complexTypeStringa == null) {
            complexTypeStringa = new ArrayList<ComplexTypeStringa>();
        }
        return this.complexTypeStringa;
    }

}
