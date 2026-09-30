
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AnagraficaRappresentante complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnagraficaRappresentante"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}Anagrafica"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="carica" type="{http://www.impresainungiorno.gov.it/schema/base}Carica" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagraficaRappresentante", propOrder = {
    "carica"
})
public class AnagraficaRappresentante
    extends Anagrafica
{

    @XmlElement(required = true)
    protected List<Carica> carica;

    /**
     * Gets the value of the carica property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the carica property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCarica().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Carica }
     * 
     * 
     */
    public List<Carica> getCarica() {
        if (carica == null) {
            carica = new ArrayList<Carica>();
        }
        return this.carica;
    }

}
