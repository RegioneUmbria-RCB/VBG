
package it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dyn2ModellidType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dyn2ModellidType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idModelloD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="posverticale" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="posorizzontale" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="flgMultiplo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="flgObbligatorio" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="scriptcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dyn2CampiType" type="{http://gruppoinit.it/sigepro/schemas/messages/schedeDinamiche}Dyn2CampiType" minOccurs="0"/>
 *         &lt;element name="dyn2ModellidtestiType" type="{http://gruppoinit.it/sigepro/schemas/messages/schedeDinamiche}Dyn2ModellidtestiType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dyn2ModellidType", propOrder = {
    "idModelloD",
    "posverticale",
    "posorizzontale",
    "flgMultiplo",
    "flgObbligatorio",
    "scriptcode",
    "dyn2CampiType",
    "dyn2ModellidtestiType"
})
public class Dyn2ModellidType {

    @XmlElement(required = true)
    protected String idModelloD;
    protected BigInteger posverticale;
    protected BigInteger posorizzontale;
    protected Boolean flgMultiplo;
    protected Boolean flgObbligatorio;
    protected String scriptcode;
    protected Dyn2CampiType dyn2CampiType;
    protected Dyn2ModellidtestiType dyn2ModellidtestiType;

    /**
     * Gets the value of the idModelloD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdModelloD() {
        return idModelloD;
    }

    /**
     * Sets the value of the idModelloD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdModelloD(String value) {
        this.idModelloD = value;
    }

    /**
     * Gets the value of the posverticale property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPosverticale() {
        return posverticale;
    }

    /**
     * Sets the value of the posverticale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPosverticale(BigInteger value) {
        this.posverticale = value;
    }

    /**
     * Gets the value of the posorizzontale property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPosorizzontale() {
        return posorizzontale;
    }

    /**
     * Sets the value of the posorizzontale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPosorizzontale(BigInteger value) {
        this.posorizzontale = value;
    }

    /**
     * Gets the value of the flgMultiplo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlgMultiplo() {
        return flgMultiplo;
    }

    /**
     * Sets the value of the flgMultiplo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlgMultiplo(Boolean value) {
        this.flgMultiplo = value;
    }

    /**
     * Gets the value of the flgObbligatorio property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlgObbligatorio() {
        return flgObbligatorio;
    }

    /**
     * Sets the value of the flgObbligatorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlgObbligatorio(Boolean value) {
        this.flgObbligatorio = value;
    }

    /**
     * Gets the value of the scriptcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScriptcode() {
        return scriptcode;
    }

    /**
     * Sets the value of the scriptcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScriptcode(String value) {
        this.scriptcode = value;
    }

    /**
     * Gets the value of the dyn2CampiType property.
     * 
     * @return
     *     possible object is
     *     {@link Dyn2CampiType }
     *     
     */
    public Dyn2CampiType getDyn2CampiType() {
        return dyn2CampiType;
    }

    /**
     * Sets the value of the dyn2CampiType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dyn2CampiType }
     *     
     */
    public void setDyn2CampiType(Dyn2CampiType value) {
        this.dyn2CampiType = value;
    }

    /**
     * Gets the value of the dyn2ModellidtestiType property.
     * 
     * @return
     *     possible object is
     *     {@link Dyn2ModellidtestiType }
     *     
     */
    public Dyn2ModellidtestiType getDyn2ModellidtestiType() {
        return dyn2ModellidtestiType;
    }

    /**
     * Sets the value of the dyn2ModellidtestiType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dyn2ModellidtestiType }
     *     
     */
    public void setDyn2ModellidtestiType(Dyn2ModellidtestiType value) {
        this.dyn2ModellidtestiType = value;
    }

}
