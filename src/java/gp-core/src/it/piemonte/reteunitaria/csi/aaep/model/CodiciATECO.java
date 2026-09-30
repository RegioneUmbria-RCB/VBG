
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CodiciATECO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CodiciATECO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizioneAteco" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoDiRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceAteco" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodiciATECO", propOrder = {
    "descrizioneAteco",
    "annoDiRiferimento",
    "codiceAteco"
})
public class CodiciATECO {

    @XmlElement(required = true, nillable = true)
    protected String descrizioneAteco;
    @XmlElement(required = true, nillable = true)
    protected String annoDiRiferimento;
    @XmlElement(required = true, nillable = true)
    protected String codiceAteco;

    /**
     * Gets the value of the descrizioneAteco property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneAteco() {
        return descrizioneAteco;
    }

    /**
     * Sets the value of the descrizioneAteco property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneAteco(String value) {
        this.descrizioneAteco = value;
    }

    /**
     * Gets the value of the annoDiRiferimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoDiRiferimento() {
        return annoDiRiferimento;
    }

    /**
     * Sets the value of the annoDiRiferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoDiRiferimento(String value) {
        this.annoDiRiferimento = value;
    }

    /**
     * Gets the value of the codiceAteco property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAteco() {
        return codiceAteco;
    }

    /**
     * Sets the value of the codiceAteco property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAteco(String value) {
        this.codiceAteco = value;
    }

}
