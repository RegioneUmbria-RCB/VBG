
package it.toscana.regione.suap.sem.types.procedimento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stimoloComunicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="stimoloComunicaType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.suap.regione.toscana.it/sem/types/procedimento}abstractStimoloType">
 *       &lt;sequence>
 *         &lt;element name="attributiSpecifici" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}attributiStimoloComunicaType"/>
 *         &lt;element name="allegati" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}allegatoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stimoloComunicaType", propOrder = {
    "attributiSpecifici",
    "allegati"
})
public class StimoloComunicaType
    extends AbstractStimoloType
{

    @XmlElement(required = true)
    protected AttributiStimoloComunicaType attributiSpecifici;
    protected List<AllegatoType> allegati;

    /**
     * Gets the value of the attributiSpecifici property.
     * 
     * @return
     *     possible object is
     *     {@link AttributiStimoloComunicaType }
     *     
     */
    public AttributiStimoloComunicaType getAttributiSpecifici() {
        return attributiSpecifici;
    }

    /**
     * Sets the value of the attributiSpecifici property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributiStimoloComunicaType }
     *     
     */
    public void setAttributiSpecifici(AttributiStimoloComunicaType value) {
        this.attributiSpecifici = value;
    }

    /**
     * Gets the value of the allegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllegatoType }
     * 
     * 
     */
    public List<AllegatoType> getAllegati() {
        if (allegati == null) {
            allegati = new ArrayList<AllegatoType>();
        }
        return this.allegati;
    }

}
