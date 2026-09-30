
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiInpsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiInpsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sedeIscrizione" type="{http://sigepro.init.it/rte/types}CodiceDescrizioneType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiInpsType", propOrder = {
    "numero",
    "sedeIscrizione"
})
public class DatiInpsType {

    @XmlElement(required = true)
    protected String numero;
    @XmlElement(required = true)
    protected CodiceDescrizioneType sedeIscrizione;

    /**
     * Gets the value of the numero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

    /**
     * Gets the value of the sedeIscrizione property.
     * 
     * @return
     *     possible object is
     *     {@link CodiceDescrizioneType }
     *     
     */
    public CodiceDescrizioneType getSedeIscrizione() {
        return sedeIscrizione;
    }

    /**
     * Sets the value of the sedeIscrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceDescrizioneType }
     *     
     */
    public void setSedeIscrizione(CodiceDescrizioneType value) {
        this.sedeIscrizione = value;
    }

}
