
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Contenuto del messaggio 'chiusuraIntegrazioni': puo' contenere una comunicazione formale.
 * 			
 * 
 * <p>Java class for chiusuraIntegrazioniType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="chiusuraIntegrazioniType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="comunicazione-formale" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}comunicazioneFormaleType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chiusuraIntegrazioniType", propOrder = {
    "comunicazioneFormale"
})
public class ChiusuraIntegrazioniType {

    @XmlElement(name = "comunicazione-formale")
    protected ComunicazioneFormaleType comunicazioneFormale;

    /**
     * Gets the value of the comunicazioneFormale property.
     * 
     * @return
     *     possible object is
     *     {@link ComunicazioneFormaleType }
     *     
     */
    public ComunicazioneFormaleType getComunicazioneFormale() {
        return comunicazioneFormale;
    }

    /**
     * Sets the value of the comunicazioneFormale property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComunicazioneFormaleType }
     *     
     */
    public void setComunicazioneFormale(ComunicazioneFormaleType value) {
        this.comunicazioneFormale = value;
    }

}
