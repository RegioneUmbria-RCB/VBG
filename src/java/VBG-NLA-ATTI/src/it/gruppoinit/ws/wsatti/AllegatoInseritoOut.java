
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AllegatoInseritoOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AllegatoInseritoOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Serial" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDBase" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Versione" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllegatoInseritoOut", propOrder = {
    "serial",
    "idBase",
    "versione"
})
public class AllegatoInseritoOut {

    @XmlElement(name = "Serial")
    protected long serial;
    @XmlElement(name = "IDBase")
    protected int idBase;
    @XmlElement(name = "Versione")
    protected short versione;

    /**
     * Gets the value of the serial property.
     * 
     */
    public long getSerial() {
        return serial;
    }

    /**
     * Sets the value of the serial property.
     * 
     */
    public void setSerial(long value) {
        this.serial = value;
    }

    /**
     * Gets the value of the idBase property.
     * 
     */
    public int getIDBase() {
        return idBase;
    }

    /**
     * Sets the value of the idBase property.
     * 
     */
    public void setIDBase(int value) {
        this.idBase = value;
    }

    /**
     * Gets the value of the versione property.
     * 
     */
    public short getVersione() {
        return versione;
    }

    /**
     * Sets the value of the versione property.
     * 
     */
    public void setVersione(short value) {
        this.versione = value;
    }

}
