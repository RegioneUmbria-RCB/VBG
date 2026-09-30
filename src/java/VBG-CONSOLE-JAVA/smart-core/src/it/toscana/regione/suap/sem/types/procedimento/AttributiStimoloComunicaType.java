
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attributiStimoloComunicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attributiStimoloComunicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="presentazionePratica" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}presentazionePraticaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attributiStimoloComunicaType", propOrder = {
    "presentazionePratica"
})
public class AttributiStimoloComunicaType {

    @XmlElement(required = true)
    protected PresentazionePraticaType presentazionePratica;

    /**
     * Gets the value of the presentazionePratica property.
     * 
     * @return
     *     possible object is
     *     {@link PresentazionePraticaType }
     *     
     */
    public PresentazionePraticaType getPresentazionePratica() {
        return presentazionePratica;
    }

    /**
     * Sets the value of the presentazionePratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link PresentazionePraticaType }
     *     
     */
    public void setPresentazionePratica(PresentazionePraticaType value) {
        this.presentazionePratica = value;
    }

}
