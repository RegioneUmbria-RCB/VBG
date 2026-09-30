
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaSediSILP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaSediSILP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denominazioneSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaSediSILP", propOrder = {
    "indirizzo",
    "descrStatoEstero",
    "descrComune",
    "descrTipoSede",
    "idSede",
    "siglaProv",
    "localita",
    "denominazioneSede"
})
public class ListaSediSILP {

    @XmlElement(required = true, nillable = true)
    protected String indirizzo;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoEstero;
    @XmlElement(required = true, nillable = true)
    protected String descrComune;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoSede;
    @XmlElement(required = true, nillable = true)
    protected String idSede;
    @XmlElement(required = true, nillable = true)
    protected String siglaProv;
    @XmlElement(required = true, nillable = true)
    protected String localita;
    @XmlElement(required = true, nillable = true)
    protected String denominazioneSede;

    /**
     * Gets the value of the indirizzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Sets the value of the indirizzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Gets the value of the descrStatoEstero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrStatoEstero() {
        return descrStatoEstero;
    }

    /**
     * Sets the value of the descrStatoEstero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrStatoEstero(String value) {
        this.descrStatoEstero = value;
    }

    /**
     * Gets the value of the descrComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrComune() {
        return descrComune;
    }

    /**
     * Sets the value of the descrComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrComune(String value) {
        this.descrComune = value;
    }

    /**
     * Gets the value of the descrTipoSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoSede() {
        return descrTipoSede;
    }

    /**
     * Sets the value of the descrTipoSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoSede(String value) {
        this.descrTipoSede = value;
    }

    /**
     * Gets the value of the idSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSede() {
        return idSede;
    }

    /**
     * Sets the value of the idSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSede(String value) {
        this.idSede = value;
    }

    /**
     * Gets the value of the siglaProv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProv() {
        return siglaProv;
    }

    /**
     * Sets the value of the siglaProv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProv(String value) {
        this.siglaProv = value;
    }

    /**
     * Gets the value of the localita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalita() {
        return localita;
    }

    /**
     * Sets the value of the localita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalita(String value) {
        this.localita = value;
    }

    /**
     * Gets the value of the denominazioneSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneSede() {
        return denominazioneSede;
    }

    /**
     * Sets the value of the denominazioneSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneSede(String value) {
        this.denominazioneSede = value;
    }

}
