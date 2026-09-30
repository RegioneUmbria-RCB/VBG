
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfPosizioniNoteDiCredito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPosizioniNoteDiCredito"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PosizioniNoteDiCredito" type="{http://entranext.it/}PosizioniNoteDiCredito" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPosizioniNoteDiCredito", propOrder = {
    "posizioniNoteDiCredito"
})
public class ArrayOfPosizioniNoteDiCredito {

    @XmlElement(name = "PosizioniNoteDiCredito", nillable = true)
    protected List<PosizioniNoteDiCredito> posizioniNoteDiCredito;

    /**
     * Gets the value of the posizioniNoteDiCredito property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the posizioniNoteDiCredito property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosizioniNoteDiCredito().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioniNoteDiCredito }
     * 
     * 
     */
    public List<PosizioniNoteDiCredito> getPosizioniNoteDiCredito() {
        if (posizioniNoteDiCredito == null) {
            posizioniNoteDiCredito = new ArrayList<PosizioniNoteDiCredito>();
        }
        return this.posizioniNoteDiCredito;
    }

}
