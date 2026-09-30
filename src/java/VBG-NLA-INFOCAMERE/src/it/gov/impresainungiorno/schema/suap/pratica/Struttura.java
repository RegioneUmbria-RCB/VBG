
package it.gov.impresainungiorno.schema.suap.pratica;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             Struttura e composizione della pratica
 *         
 * 
 * <p>Classe Java per Struttura complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Struttura"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="modulo" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}AdempimentoSUAP" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Struttura", propOrder = {
    "modulo"
})
public class Struttura {

    @XmlElement(required = true)
    protected List<AdempimentoSUAP> modulo;

    /**
     * Gets the value of the modulo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modulo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModulo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdempimentoSUAP }
     * 
     * 
     */
    public List<AdempimentoSUAP> getModulo() {
        if (modulo == null) {
            modulo = new ArrayList<AdempimentoSUAP>();
        }
        return this.modulo;
    }

}
