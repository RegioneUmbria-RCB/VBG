
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiCassaEdileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiCassaEdileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cassaEdile" type="{http://sigepro.init.it/rte/types}CodiceDescrizioneType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiCassaEdileType", propOrder = {
    "numero",
    "cassaEdile"
})
public class DatiCassaEdileType {

    @XmlElement(required = true)
    protected String numero;
    @XmlElement(required = true)
    protected CodiceDescrizioneType cassaEdile;

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
     * Gets the value of the cassaEdile property.
     * 
     * @return
     *     possible object is
     *     {@link CodiceDescrizioneType }
     *     
     */
    public CodiceDescrizioneType getCassaEdile() {
        return cassaEdile;
    }

    /**
     * Sets the value of the cassaEdile property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceDescrizioneType }
     *     
     */
    public void setCassaEdile(CodiceDescrizioneType value) {
        this.cassaEdile = value;
    }

}
