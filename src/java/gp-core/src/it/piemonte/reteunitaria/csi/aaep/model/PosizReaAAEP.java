
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PosizReaAAEP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PosizReaAAEP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrFontePosizRea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NSediPosizRea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idFontePosizRea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgSedeLegalePosizRea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizReaAAEP", propOrder = {
    "descrFontePosizRea",
    "nSediPosizRea",
    "siglaProvCCIAA",
    "idFontePosizRea",
    "numeroCCIAA",
    "flgSedeLegalePosizRea"
})
public class PosizReaAAEP {

    @XmlElement(required = true, nillable = true)
    protected String descrFontePosizRea;
    @XmlElement(name = "NSediPosizRea", required = true, nillable = true)
    protected String nSediPosizRea;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String idFontePosizRea;
    @XmlElement(required = true, nillable = true)
    protected String numeroCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String flgSedeLegalePosizRea;

    /**
     * Gets the value of the descrFontePosizRea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrFontePosizRea() {
        return descrFontePosizRea;
    }

    /**
     * Sets the value of the descrFontePosizRea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrFontePosizRea(String value) {
        this.descrFontePosizRea = value;
    }

    /**
     * Gets the value of the nSediPosizRea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNSediPosizRea() {
        return nSediPosizRea;
    }

    /**
     * Sets the value of the nSediPosizRea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNSediPosizRea(String value) {
        this.nSediPosizRea = value;
    }

    /**
     * Gets the value of the siglaProvCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvCCIAA() {
        return siglaProvCCIAA;
    }

    /**
     * Sets the value of the siglaProvCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvCCIAA(String value) {
        this.siglaProvCCIAA = value;
    }

    /**
     * Gets the value of the idFontePosizRea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFontePosizRea() {
        return idFontePosizRea;
    }

    /**
     * Sets the value of the idFontePosizRea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFontePosizRea(String value) {
        this.idFontePosizRea = value;
    }

    /**
     * Gets the value of the numeroCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCCIAA() {
        return numeroCCIAA;
    }

    /**
     * Sets the value of the numeroCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCCIAA(String value) {
        this.numeroCCIAA = value;
    }

    /**
     * Gets the value of the flgSedeLegalePosizRea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgSedeLegalePosizRea() {
        return flgSedeLegalePosizRea;
    }

    /**
     * Sets the value of the flgSedeLegalePosizRea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgSedeLegalePosizRea(String value) {
        this.flgSedeLegalePosizRea = value;
    }

}
