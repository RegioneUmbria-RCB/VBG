
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RiferimentoCatastaleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RiferimentoCatastaleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoCatasto">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Terreni"/>
 *               &lt;enumeration value="Edilizio Urbano"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="foglio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="particella" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sub" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sezione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitaImobiliare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiferimentoCatastaleType", propOrder = {
    "tipoCatasto",
    "foglio",
    "particella",
    "sub",
    "sezione",
    "unitaImobiliare"
})
public class RiferimentoCatastaleType {

    @XmlElement(required = true)
    protected String tipoCatasto;
    @XmlElement(required = true)
    protected String foglio;
    @XmlElement(required = true)
    protected String particella;
    protected String sub;
    protected String sezione;
    protected String unitaImobiliare;

    /**
     * Gets the value of the tipoCatasto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCatasto() {
        return tipoCatasto;
    }

    /**
     * Sets the value of the tipoCatasto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCatasto(String value) {
        this.tipoCatasto = value;
    }

    /**
     * Gets the value of the foglio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFoglio() {
        return foglio;
    }

    /**
     * Sets the value of the foglio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFoglio(String value) {
        this.foglio = value;
    }

    /**
     * Gets the value of the particella property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticella() {
        return particella;
    }

    /**
     * Sets the value of the particella property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticella(String value) {
        this.particella = value;
    }

    /**
     * Gets the value of the sub property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSub() {
        return sub;
    }

    /**
     * Sets the value of the sub property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSub(String value) {
        this.sub = value;
    }

    /**
     * Gets the value of the sezione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSezione() {
        return sezione;
    }

    /**
     * Sets the value of the sezione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSezione(String value) {
        this.sezione = value;
    }

    /**
     * Gets the value of the unitaImobiliare property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitaImobiliare() {
        return unitaImobiliare;
    }

    /**
     * Sets the value of the unitaImobiliare property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitaImobiliare(String value) {
        this.unitaImobiliare = value;
    }

}
