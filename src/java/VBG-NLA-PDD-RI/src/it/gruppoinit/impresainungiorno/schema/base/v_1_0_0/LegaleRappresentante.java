
package it.gruppoinit.impresainungiorno.schema.base.v_1_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * @deprecated
 * 
 * <p>Java class for LegaleRappresentante complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LegaleRappresentante">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}DatiPersona">
 *       &lt;sequence>
 *         &lt;element name="carica" type="{http://www.impresainungiorno.gov.it/schema/base}Carica"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LegaleRappresentante", propOrder = {
    "carica"
})
public class LegaleRappresentante
    extends DatiPersona
{

    @XmlElement(required = true)
    protected Carica carica;

    /**
     * Gets the value of the carica property.
     * 
     * @return
     *     possible object is
     *     {@link Carica }
     *     
     */
    public Carica getCarica() {
        return carica;
    }

    /**
     * Sets the value of the carica property.
     * 
     * @param value
     *     allowed object is
     *     {@link Carica }
     *     
     */
    public void setCarica(Carica value) {
        this.carica = value;
    }

}
