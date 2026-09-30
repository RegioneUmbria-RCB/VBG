
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProvinciaAAEP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProvinciaAAEP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="siglaProv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrProv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgSedeLegale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NSedi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaComuniAAEP" type="{urn:AAEPCSI}ArrayOfComuneAAEP"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProvinciaAAEP", propOrder = {
    "siglaProv",
    "siglaStatoEstero",
    "descrProv",
    "flgSedeLegale",
    "nSedi",
    "descrStatoEstero",
    "listaComuniAAEP"
})
public class ProvinciaAAEP {

    @XmlElement(required = true, nillable = true)
    protected String siglaProv;
    @XmlElement(required = true, nillable = true)
    protected String siglaStatoEstero;
    @XmlElement(required = true, nillable = true)
    protected String descrProv;
    @XmlElement(required = true, nillable = true)
    protected String flgSedeLegale;
    @XmlElement(name = "NSedi", required = true, nillable = true)
    protected String nSedi;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoEstero;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfComuneAAEP listaComuniAAEP;

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
     * Gets the value of the siglaStatoEstero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaStatoEstero() {
        return siglaStatoEstero;
    }

    /**
     * Sets the value of the siglaStatoEstero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaStatoEstero(String value) {
        this.siglaStatoEstero = value;
    }

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
     * Gets the value of the listaComuniAAEP property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfComuneAAEP }
     *     
     */
    public ArrayOfComuneAAEP getListaComuniAAEP() {
        return listaComuniAAEP;
    }

    /**
     * Sets the value of the listaComuniAAEP property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfComuneAAEP }
     *     
     */
    public void setListaComuniAAEP(ArrayOfComuneAAEP value) {
        this.listaComuniAAEP = value;
    }

}
