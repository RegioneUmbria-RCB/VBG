
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FiltroRicerca complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FiltroRicerca">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="null" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="chiave" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="obbligatorio" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FiltroRicerca", propOrder = {
    "valore",
    "_null",
    "chiave",
    "obbligatorio"
})
public class FiltroRicerca {

    @XmlElement(required = true, nillable = true)
    protected String valore;
    @XmlElement(name = "null")
    protected boolean _null;
    @XmlElement(required = true, nillable = true)
    protected String chiave;
    protected boolean obbligatorio;

    /**
     * Gets the value of the valore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValore() {
        return valore;
    }

    /**
     * Sets the value of the valore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValore(String value) {
        this.valore = value;
    }

    /**
     * Gets the value of the null property.
     * 
     */
    public boolean isNull() {
        return _null;
    }

    /**
     * Sets the value of the null property.
     * 
     */
    public void setNull(boolean value) {
        this._null = value;
    }

    /**
     * Gets the value of the chiave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChiave() {
        return chiave;
    }

    /**
     * Sets the value of the chiave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChiave(String value) {
        this.chiave = value;
    }

    /**
     * Gets the value of the obbligatorio property.
     * 
     */
    public boolean isObbligatorio() {
        return obbligatorio;
    }

    /**
     * Sets the value of the obbligatorio property.
     * 
     */
    public void setObbligatorio(boolean value) {
        this.obbligatorio = value;
    }

}
