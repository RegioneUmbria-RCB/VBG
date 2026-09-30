
package it.gruppoinit.pal.gp.backoffice.schemas.messages.stradario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="risultato" type="{http://gruppoinit.it/anagrafici/stradario/types}ListaStradarioType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "risultato"
})
@XmlRootElement(name = "CercaStradarioResponse")
public class CercaStradarioResponse {

    @XmlElement(required = true)
    protected ListaStradarioType risultato;

    /**
     * Gets the value of the risultato property.
     * 
     * @return
     *     possible object is
     *     {@link ListaStradarioType }
     *     
     */
    public ListaStradarioType getRisultato() {
        return risultato;
    }

    /**
     * Sets the value of the risultato property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaStradarioType }
     *     
     */
    public void setRisultato(ListaStradarioType value) {
        this.risultato = value;
    }

}
