
package it.toscana.regione.suap.sem.types.procedimento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Lista degli endoprocedimenti identificati dal loro codice (vedi 'codiceEndoprocedimento').
 * 			
 * 
 * <p>Java class for arrayEndoTipo1Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="arrayEndoTipo1Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="idEndoprocedimento" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}codiceEndoprocedimento"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "arrayEndoTipo1Type", propOrder = {
    "idEndoprocedimento"
})
public class ArrayEndoTipo1Type {

    @XmlElement(required = true)
    protected List<String> idEndoprocedimento;

    /**
     * Gets the value of the idEndoprocedimento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idEndoprocedimento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdEndoprocedimento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIdEndoprocedimento() {
        if (idEndoprocedimento == null) {
            idEndoprocedimento = new ArrayList<String>();
        }
        return this.idEndoprocedimento;
    }

}
