
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProvinciaSILP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProvinciaSILP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrProv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idProspDisabRiferim" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgSedeLegale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NSedi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NDipendenti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codBelfioreStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInvio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaComuniSILP" type="{urn:AAEPCSI}ArrayOfComuneSILP"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProvinciaSILP", propOrder = {
    "descrProv",
    "idProspDisabRiferim",
    "flgSedeLegale",
    "descrStatoEstero",
    "nSedi",
    "nDipendenti",
    "codBelfioreStatoEstero",
    "annoRiferimento",
    "siglaProv",
    "dataInvio",
    "listaComuniSILP"
})
public class ProvinciaSILP {

    @XmlElement(required = true, nillable = true)
    protected String descrProv;
    @XmlElement(required = true, nillable = true)
    protected String idProspDisabRiferim;
    @XmlElement(required = true, nillable = true)
    protected String flgSedeLegale;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoEstero;
    @XmlElement(name = "NSedi", required = true, nillable = true)
    protected String nSedi;
    @XmlElement(name = "NDipendenti", required = true, nillable = true)
    protected String nDipendenti;
    @XmlElement(required = true, nillable = true)
    protected String codBelfioreStatoEstero;
    @XmlElement(required = true, nillable = true)
    protected String annoRiferimento;
    @XmlElement(required = true, nillable = true)
    protected String siglaProv;
    @XmlElement(required = true, nillable = true)
    protected String dataInvio;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfComuneSILP listaComuniSILP;

    /**
     * Gets the value of the descrProv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrProv() {
        return descrProv;
    }

    /**
     * Sets the value of the descrProv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrProv(String value) {
        this.descrProv = value;
    }

    /**
     * Gets the value of the idProspDisabRiferim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdProspDisabRiferim() {
        return idProspDisabRiferim;
    }

    /**
     * Sets the value of the idProspDisabRiferim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdProspDisabRiferim(String value) {
        this.idProspDisabRiferim = value;
    }

    /**
     * Gets the value of the flgSedeLegale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgSedeLegale() {
        return flgSedeLegale;
    }

    /**
     * Sets the value of the flgSedeLegale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgSedeLegale(String value) {
        this.flgSedeLegale = value;
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
     * Gets the value of the nSedi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNSedi() {
        return nSedi;
    }

    /**
     * Sets the value of the nSedi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNSedi(String value) {
        this.nSedi = value;
    }

    /**
     * Gets the value of the nDipendenti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNDipendenti() {
        return nDipendenti;
    }

    /**
     * Sets the value of the nDipendenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNDipendenti(String value) {
        this.nDipendenti = value;
    }

    /**
     * Gets the value of the codBelfioreStatoEstero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodBelfioreStatoEstero() {
        return codBelfioreStatoEstero;
    }

    /**
     * Sets the value of the codBelfioreStatoEstero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodBelfioreStatoEstero(String value) {
        this.codBelfioreStatoEstero = value;
    }

    /**
     * Gets the value of the annoRiferimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoRiferimento() {
        return annoRiferimento;
    }

    /**
     * Sets the value of the annoRiferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoRiferimento(String value) {
        this.annoRiferimento = value;
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
     * Gets the value of the dataInvio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInvio() {
        return dataInvio;
    }

    /**
     * Sets the value of the dataInvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInvio(String value) {
        this.dataInvio = value;
    }

    /**
     * Gets the value of the listaComuniSILP property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfComuneSILP }
     *     
     */
    public ArrayOfComuneSILP getListaComuniSILP() {
        return listaComuniSILP;
    }

    /**
     * Sets the value of the listaComuniSILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfComuneSILP }
     *     
     */
    public void setListaComuniSILP(ArrayOfComuneSILP value) {
        this.listaComuniSILP = value;
    }

}
