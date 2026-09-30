
package it.ldp.suolopubblico;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfComplexTypeArea complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfComplexTypeArea">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ComplexTypeArea" type="{https://ws.ldpgis.it/}ComplexTypeArea" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfComplexTypeArea", propOrder = {
    "complexTypeArea"
})
public class ArrayOfComplexTypeArea {

    @XmlElement(name = "ComplexTypeArea")
    protected List<ComplexTypeArea> complexTypeArea;

    /**
     * Gets the value of the complexTypeArea property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the complexTypeArea property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComplexTypeArea().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypeArea }
     * 
     * 
     */
    public List<ComplexTypeArea> getComplexTypeArea() {
        if (complexTypeArea == null) {
            complexTypeArea = new ArrayList<ComplexTypeArea>();
        }
        return this.complexTypeArea;
    }

}
