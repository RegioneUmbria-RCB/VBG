
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Contenuto del messaggio 'comunicazioneResponsabile': contiene il nominativo del responsabile assegnato con
 * 				allegata una comunicazione formale.
 * 			
 * 
 * <p>Java class for comunicazioneResponsabileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="comunicazioneResponsabileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responsabile" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}stringaNonVuota"/>
 *         &lt;element name="comunicazione-formale" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}comunicazioneFormaleType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comunicazioneResponsabileType", propOrder = {
    "responsabile",
    "comunicazioneFormale"
})
public class ComunicazioneResponsabileType {

    @XmlElement(required = true)
    protected String responsabile;
    @XmlElement(name = "comunicazione-formale", required = true)
    protected ComunicazioneFormaleType comunicazioneFormale;

    /**
     * Gets the value of the responsabile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsabile() {
        return responsabile;
    }

    /**
     * Sets the value of the responsabile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsabile(String value) {
        this.responsabile = value;
    }

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
