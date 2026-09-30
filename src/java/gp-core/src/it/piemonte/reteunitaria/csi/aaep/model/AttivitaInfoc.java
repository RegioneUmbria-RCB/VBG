
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttivitaInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttivitaInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="progrSede" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="rigaTesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="progrRiga" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttivitaInfoc", propOrder = {
    "progrSede",
    "idAAEPFonteDato",
    "rigaTesto",
    "idAAEPAzienda",
    "progrRiga"
})
public class AttivitaInfoc {

    protected long progrSede;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String rigaTesto;
    protected long idAAEPAzienda;
    protected long progrRiga;

    /**
     * Gets the value of the progrSede property.
     * 
     */
    public long getProgrSede() {
        return progrSede;
    }

    /**
     * Sets the value of the progrSede property.
     * 
     */
    public void setProgrSede(long value) {
        this.progrSede = value;
    }

    /**
     * Gets the value of the idAAEPFonteDato property.
     * 
     */
    public long getIdAAEPFonteDato() {
        return idAAEPFonteDato;
    }

    /**
     * Sets the value of the idAAEPFonteDato property.
     * 
     */
    public void setIdAAEPFonteDato(long value) {
        this.idAAEPFonteDato = value;
    }

    /**
     * Gets the value of the rigaTesto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRigaTesto() {
        return rigaTesto;
    }

    /**
     * Sets the value of the rigaTesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRigaTesto(String value) {
        this.rigaTesto = value;
    }

    /**
     * Gets the value of the idAAEPAzienda property.
     * 
     */
    public long getIdAAEPAzienda() {
        return idAAEPAzienda;
    }

    /**
     * Sets the value of the idAAEPAzienda property.
     * 
     */
    public void setIdAAEPAzienda(long value) {
        this.idAAEPAzienda = value;
    }

    /**
     * Gets the value of the progrRiga property.
     * 
     */
    public long getProgrRiga() {
        return progrRiga;
    }

    /**
     * Sets the value of the progrRiga property.
     * 
     */
    public void setProgrRiga(long value) {
        this.progrRiga = value;
    }

}
