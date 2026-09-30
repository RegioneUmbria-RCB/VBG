
package it.gruppoinit.impresainungiorno.schema.base.v_1_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AnagraficaRappresentante complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AnagraficaRappresentante">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}Anagrafica">
 *       &lt;sequence>
 *         &lt;element name="carica" type="{http://www.impresainungiorno.gov.it/schema/base}Carica" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
