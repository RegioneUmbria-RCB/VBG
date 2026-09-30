
package it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dyn2ModellitType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dyn2ModellitType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idModelloT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modellomultiplo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="flgStoricizza" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="flgReadonlyWeb" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="modelloFrontoffice" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="codiceScheda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dyn2BasecontestiType" type="{http://gruppoinit.it/sigepro/schemas/messages/schedeDinamiche}Dyn2BasecontestiType" minOccurs="0"/>
 *         &lt;element name="dyn2ModellidType" type="{http://gruppoinit.it/sigepro/schemas/messages/schedeDinamiche}Dyn2ModellidType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="dyn2ModelliTScriptType" type="{http://gruppoinit.it/sigepro/schemas/messages/schedeDinamiche}Dyn2ModelliTScriptType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dyn2ModellitType", propOrder = {
    "idModelloT",
    "software",
    "descrizione",
    "modellomultiplo",
    "flgStoricizza",
    "flgReadonlyWeb",
    "modelloFrontoffice",
    "codiceScheda",
    "dyn2BasecontestiType",
    "dyn2ModellidType",
    "dyn2ModelliTScriptType"
})
public class Dyn2ModellitType {

    @XmlElement(required = true)
    protected String idModelloT;
    @XmlElement(required = true)
    protected String software;
    @XmlElement(required = true)
    protected String descrizione;
    protected Boolean modellomultiplo;
    protected Boolean flgStoricizza;
    protected Boolean flgReadonlyWeb;
    protected Boolean modelloFrontoffice;
    protected String codiceScheda;
    protected Dyn2BasecontestiType dyn2BasecontestiType;
    protected List<Dyn2ModellidType> dyn2ModellidType;
    protected List<Dyn2ModelliTScriptType> dyn2ModelliTScriptType;

    /**
     * Gets the value of the idModelloT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdModelloT() {
        return idModelloT;
    }

    /**
     * Sets the value of the idModelloT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdModelloT(String value) {
        this.idModelloT = value;
    }

    /**
     * Gets the value of the software property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftware() {
        return software;
    }

    /**
     * Sets the value of the software property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftware(String value) {
        this.software = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the modellomultiplo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isModellomultiplo() {
        return modellomultiplo;
    }

    /**
     * Sets the value of the modellomultiplo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setModellomultiplo(Boolean value) {
        this.modellomultiplo = value;
    }

    /**
     * Gets the value of the flgStoricizza property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlgStoricizza() {
        return flgStoricizza;
    }

    /**
     * Sets the value of the flgStoricizza property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlgStoricizza(Boolean value) {
        this.flgStoricizza = value;
    }

    /**
     * Gets the value of the flgReadonlyWeb property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlgReadonlyWeb() {
        return flgReadonlyWeb;
    }

    /**
     * Sets the value of the flgReadonlyWeb property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlgReadonlyWeb(Boolean value) {
        this.flgReadonlyWeb = value;
    }

    /**
     * Gets the value of the modelloFrontoffice property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isModelloFrontoffice() {
        return modelloFrontoffice;
    }

    /**
     * Sets the value of the modelloFrontoffice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setModelloFrontoffice(Boolean value) {
        this.modelloFrontoffice = value;
    }

    /**
     * Gets the value of the codiceScheda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceScheda() {
        return codiceScheda;
    }

    /**
     * Sets the value of the codiceScheda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceScheda(String value) {
        this.codiceScheda = value;
    }

    /**
     * Gets the value of the dyn2BasecontestiType property.
     * 
     * @return
     *     possible object is
     *     {@link Dyn2BasecontestiType }
     *     
     */
    public Dyn2BasecontestiType getDyn2BasecontestiType() {
        return dyn2BasecontestiType;
    }

    /**
     * Sets the value of the dyn2BasecontestiType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dyn2BasecontestiType }
     *     
     */
    public void setDyn2BasecontestiType(Dyn2BasecontestiType value) {
        this.dyn2BasecontestiType = value;
    }

    /**
     * Gets the value of the dyn2ModellidType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dyn2ModellidType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDyn2ModellidType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dyn2ModellidType }
     * 
     * 
     */
    public List<Dyn2ModellidType> getDyn2ModellidType() {
        if (dyn2ModellidType == null) {
            dyn2ModellidType = new ArrayList<Dyn2ModellidType>();
        }
        return this.dyn2ModellidType;
    }

    /**
     * Gets the value of the dyn2ModelliTScriptType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dyn2ModelliTScriptType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDyn2ModelliTScriptType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dyn2ModelliTScriptType }
     * 
     * 
     */
    public List<Dyn2ModelliTScriptType> getDyn2ModelliTScriptType() {
        if (dyn2ModelliTScriptType == null) {
            dyn2ModelliTScriptType = new ArrayList<Dyn2ModelliTScriptType>();
        }
        return this.dyn2ModelliTScriptType;
    }

}
