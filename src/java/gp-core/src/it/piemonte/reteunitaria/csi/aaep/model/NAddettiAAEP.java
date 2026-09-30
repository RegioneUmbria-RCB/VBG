
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NAddettiAAEP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NAddettiAAEP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NAddetti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrFonteNAddetti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrFonteAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idFonteAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="formatoNAddetti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idFonteNAddetti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NAddettiAAEP", propOrder = {
    "nAddetti",
    "descrFonteNAddetti",
    "descrFonteAzienda",
    "idFonteAzienda",
    "formatoNAddetti",
    "idFonteNAddetti"
})
public class NAddettiAAEP {

    @XmlElement(name = "NAddetti", required = true, nillable = true)
    protected String nAddetti;
    @XmlElement(required = true, nillable = true)
    protected String descrFonteNAddetti;
    @XmlElement(required = true, nillable = true)
    protected String descrFonteAzienda;
    @XmlElement(required = true, nillable = true)
    protected String idFonteAzienda;
    @XmlElement(required = true, nillable = true)
    protected String formatoNAddetti;
    @XmlElement(required = true, nillable = true)
    protected String idFonteNAddetti;

    /**
     * Gets the value of the nAddetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAddetti() {
        return nAddetti;
    }

    /**
     * Sets the value of the nAddetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAddetti(String value) {
        this.nAddetti = value;
    }

    /**
     * Gets the value of the descrFonteNAddetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrFonteNAddetti() {
        return descrFonteNAddetti;
    }

    /**
     * Sets the value of the descrFonteNAddetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrFonteNAddetti(String value) {
        this.descrFonteNAddetti = value;
    }

    /**
     * Gets the value of the descrFonteAzienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrFonteAzienda() {
        return descrFonteAzienda;
    }

    /**
     * Sets the value of the descrFonteAzienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrFonteAzienda(String value) {
        this.descrFonteAzienda = value;
    }

    /**
     * Gets the value of the idFonteAzienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFonteAzienda() {
        return idFonteAzienda;
    }

    /**
     * Sets the value of the idFonteAzienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFonteAzienda(String value) {
        this.idFonteAzienda = value;
    }

    /**
     * Gets the value of the formatoNAddetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatoNAddetti() {
        return formatoNAddetti;
    }

    /**
     * Sets the value of the formatoNAddetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatoNAddetti(String value) {
        this.formatoNAddetti = value;
    }

    /**
     * Gets the value of the idFonteNAddetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFonteNAddetti() {
        return idFonteNAddetti;
    }

    /**
     * Sets the value of the idFonteNAddetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFonteNAddetti(String value) {
        this.idFonteNAddetti = value;
    }

}
