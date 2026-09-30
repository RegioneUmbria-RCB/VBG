
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReferenteSILP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReferenteSILP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cognomeReferente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ruoloReferente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idReferente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataUltAggiornam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeReferente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataUltAggiornamSILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EMail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenteSILP", propOrder = {
    "cognomeReferente",
    "ruoloReferente",
    "idReferente",
    "fax",
    "dataUltAggiornam",
    "nomeReferente",
    "dataUltAggiornamSILP",
    "telefono",
    "eMail"
})
public class ReferenteSILP {

    @XmlElement(required = true, nillable = true)
    protected String cognomeReferente;
    @XmlElement(required = true, nillable = true)
    protected String ruoloReferente;
    @XmlElement(required = true, nillable = true)
    protected String idReferente;
    @XmlElement(required = true, nillable = true)
    protected String fax;
    @XmlElement(required = true, nillable = true)
    protected String dataUltAggiornam;
    @XmlElement(required = true, nillable = true)
    protected String nomeReferente;
    @XmlElement(required = true, nillable = true)
    protected String dataUltAggiornamSILP;
    @XmlElement(required = true, nillable = true)
    protected String telefono;
    @XmlElement(name = "EMail", required = true, nillable = true)
    protected String eMail;

    /**
     * Gets the value of the cognomeReferente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognomeReferente() {
        return cognomeReferente;
    }

    /**
     * Sets the value of the cognomeReferente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognomeReferente(String value) {
        this.cognomeReferente = value;
    }

    /**
     * Gets the value of the ruoloReferente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuoloReferente() {
        return ruoloReferente;
    }

    /**
     * Sets the value of the ruoloReferente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuoloReferente(String value) {
        this.ruoloReferente = value;
    }

    /**
     * Gets the value of the idReferente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdReferente() {
        return idReferente;
    }

    /**
     * Sets the value of the idReferente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdReferente(String value) {
        this.idReferente = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the dataUltAggiornam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUltAggiornam() {
        return dataUltAggiornam;
    }

    /**
     * Sets the value of the dataUltAggiornam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUltAggiornam(String value) {
        this.dataUltAggiornam = value;
    }

    /**
     * Gets the value of the nomeReferente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeReferente() {
        return nomeReferente;
    }

    /**
     * Sets the value of the nomeReferente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeReferente(String value) {
        this.nomeReferente = value;
    }

    /**
     * Gets the value of the dataUltAggiornamSILP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUltAggiornamSILP() {
        return dataUltAggiornamSILP;
    }

    /**
     * Sets the value of the dataUltAggiornamSILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUltAggiornamSILP(String value) {
        this.dataUltAggiornamSILP = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the eMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMail() {
        return eMail;
    }

    /**
     * Sets the value of the eMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMail(String value) {
        this.eMail = value;
    }

}
