
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrganicoProspDisabSILP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrganicoProspDisabSILP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataUltAggiornamSILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idOrganicoProspDisab" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valoreOrganico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataUltAggiornam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrOrganicoProspDisab" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganicoProspDisabSILP", propOrder = {
    "dataUltAggiornamSILP",
    "idOrganicoProspDisab",
    "valoreOrganico",
    "dataUltAggiornam",
    "descrOrganicoProspDisab"
})
public class OrganicoProspDisabSILP {

    @XmlElement(required = true, nillable = true)
    protected String dataUltAggiornamSILP;
    @XmlElement(required = true, nillable = true)
    protected String idOrganicoProspDisab;
    @XmlElement(required = true, nillable = true)
    protected String valoreOrganico;
    @XmlElement(required = true, nillable = true)
    protected String dataUltAggiornam;
    @XmlElement(required = true, nillable = true)
    protected String descrOrganicoProspDisab;

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
     * Gets the value of the idOrganicoProspDisab property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdOrganicoProspDisab() {
        return idOrganicoProspDisab;
    }

    /**
     * Sets the value of the idOrganicoProspDisab property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdOrganicoProspDisab(String value) {
        this.idOrganicoProspDisab = value;
    }

    /**
     * Gets the value of the valoreOrganico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValoreOrganico() {
        return valoreOrganico;
    }

    /**
     * Sets the value of the valoreOrganico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValoreOrganico(String value) {
        this.valoreOrganico = value;
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
     * Gets the value of the descrOrganicoProspDisab property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrOrganicoProspDisab() {
        return descrOrganicoProspDisab;
    }

    /**
     * Sets the value of the descrOrganicoProspDisab property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrOrganicoProspDisab(String value) {
        this.descrOrganicoProspDisab = value;
    }

}
