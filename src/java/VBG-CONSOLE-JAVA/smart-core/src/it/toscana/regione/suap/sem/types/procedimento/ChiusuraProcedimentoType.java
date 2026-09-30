
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Contenuto dello stimolo 'chiusuraProcedimento'.
 * 			
 * 
 * <p>Java class for chiusuraProcedimentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="chiusuraProcedimentoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esitoPositivoProcedimentoOrdinario" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "chiusuraProcedimentoType", propOrder = {
    "esitoPositivoProcedimentoOrdinario",
    "comunicazioneFormale"
})
public class ChiusuraProcedimentoType {

    protected boolean esitoPositivoProcedimentoOrdinario;
    @XmlElement(name = "comunicazione-formale", required = true)
    protected ComunicazioneFormaleType comunicazioneFormale;

    /**
     * Gets the value of the esitoPositivoProcedimentoOrdinario property.
     * 
     */
    public boolean isEsitoPositivoProcedimentoOrdinario() {
        return esitoPositivoProcedimentoOrdinario;
    }

    /**
     * Sets the value of the esitoPositivoProcedimentoOrdinario property.
     * 
     */
    public void setEsitoPositivoProcedimentoOrdinario(boolean value) {
        this.esitoPositivoProcedimentoOrdinario = value;
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
