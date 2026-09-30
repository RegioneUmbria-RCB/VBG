
package it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Endoprocedimento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Endoprocedimento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proprietaBase" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}ProprietaBase"/>
 *         &lt;element name="tempistiche" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}Tempistiche"/>
 *         &lt;element name="natureEndo" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}NatureEndo"/>
 *         &lt;element name="amministrazione" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}Amministrazione"/>
 *         &lt;element name="prefixMapping" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Endoprocedimento", propOrder = {
    "proprietaBase",
    "tempistiche",
    "natureEndo",
    "amministrazione",
    "prefixMapping"
})
public class Endoprocedimento {

    @XmlElement(required = true)
    protected ProprietaBase proprietaBase;
    @XmlElement(required = true)
    protected Tempistiche tempistiche;
    @XmlElement(required = true)
    protected NatureEndo natureEndo;
    @XmlElement(required = true)
    protected Amministrazione amministrazione;
    @XmlElement(required = true)
    protected String prefixMapping;

    /**
     * Gets the value of the proprietaBase property.
     * 
     * @return
     *     possible object is
     *     {@link ProprietaBase }
     *     
     */
    public ProprietaBase getProprietaBase() {
        return proprietaBase;
    }

    /**
     * Sets the value of the proprietaBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProprietaBase }
     *     
     */
    public void setProprietaBase(ProprietaBase value) {
        this.proprietaBase = value;
    }

    /**
     * Gets the value of the tempistiche property.
     * 
     * @return
     *     possible object is
     *     {@link Tempistiche }
     *     
     */
    public Tempistiche getTempistiche() {
        return tempistiche;
    }

    /**
     * Sets the value of the tempistiche property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tempistiche }
     *     
     */
    public void setTempistiche(Tempistiche value) {
        this.tempistiche = value;
    }

    /**
     * Gets the value of the natureEndo property.
     * 
     * @return
     *     possible object is
     *     {@link NatureEndo }
     *     
     */
    public NatureEndo getNatureEndo() {
        return natureEndo;
    }

    /**
     * Sets the value of the natureEndo property.
     * 
     * @param value
     *     allowed object is
     *     {@link NatureEndo }
     *     
     */
    public void setNatureEndo(NatureEndo value) {
        this.natureEndo = value;
    }

    /**
     * Gets the value of the amministrazione property.
     * 
     * @return
     *     possible object is
     *     {@link Amministrazione }
     *     
     */
    public Amministrazione getAmministrazione() {
        return amministrazione;
    }

    /**
     * Sets the value of the amministrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link Amministrazione }
     *     
     */
    public void setAmministrazione(Amministrazione value) {
        this.amministrazione = value;
    }

    /**
     * Gets the value of the prefixMapping property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefixMapping() {
        return prefixMapping;
    }

    /**
     * Sets the value of the prefixMapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefixMapping(String value) {
        this.prefixMapping = value;
    }

}
