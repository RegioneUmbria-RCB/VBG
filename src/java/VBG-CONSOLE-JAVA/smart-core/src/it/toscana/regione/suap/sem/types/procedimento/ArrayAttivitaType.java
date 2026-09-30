
package it.toscana.regione.suap.sem.types.procedimento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Lista delle attivita' identificate dal loro codice (vedi 'codiceAttivita'').
 * 			
 * 
 * <p>Java class for arrayAttivitaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="arrayAttivitaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="idAttivitaBDR" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}codiceAttivita"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "arrayAttivitaType", propOrder = {
    "idAttivitaBDR"
})
public class ArrayAttivitaType {

    @XmlElement(required = true)
    protected List<String> idAttivitaBDR;

    /**
     * Gets the value of the idAttivitaBDR property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idAttivitaBDR property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdAttivitaBDR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIdAttivitaBDR() {
        if (idAttivitaBDR == null) {
            idAttivitaBDR = new ArrayList<String>();
        }
        return this.idAttivitaBDR;
    }

}
