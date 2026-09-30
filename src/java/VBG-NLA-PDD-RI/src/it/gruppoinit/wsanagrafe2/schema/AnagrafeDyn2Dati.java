
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AnagrafeDyn2Dati complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AnagrafeDyn2Dati">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}BaseDataClass">
 *       &lt;sequence>
 *         &lt;element name="Idcomune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Codiceanagrafe" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FkD2cId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Indice" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IndiceMolteplicita" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Valore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Valoredecodificato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagrafeDyn2Dati", propOrder = {
    "idcomune",
    "codiceanagrafe",
    "fkD2CId",
    "indice",
    "indiceMolteplicita",
    "valore",
    "valoredecodificato"
})
public class AnagrafeDyn2Dati
    extends BaseDataClass
{

    @XmlElement(name = "Idcomune")
    protected String idcomune;
    @XmlElement(name = "Codiceanagrafe", required = true, type = Integer.class, nillable = true)
    protected Integer codiceanagrafe;
    @XmlElement(name = "FkD2cId", required = true, type = Integer.class, nillable = true)
    protected Integer fkD2CId;
    @XmlElement(name = "Indice", required = true, type = Integer.class, nillable = true)
    protected Integer indice;
    @XmlElement(name = "IndiceMolteplicita", required = true, type = Integer.class, nillable = true)
    protected Integer indiceMolteplicita;
    @XmlElement(name = "Valore")
    protected String valore;
    @XmlElement(name = "Valoredecodificato")
    protected String valoredecodificato;

    /**
     * Gets the value of the idcomune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdcomune() {
        return idcomune;
    }

    /**
     * Sets the value of the idcomune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdcomune(String value) {
        this.idcomune = value;
    }

    /**
     * Gets the value of the codiceanagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiceanagrafe() {
        return codiceanagrafe;
    }

    /**
     * Sets the value of the codiceanagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiceanagrafe(Integer value) {
        this.codiceanagrafe = value;
    }

    /**
     * Gets the value of the fkD2CId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFkD2CId() {
        return fkD2CId;
    }

    /**
     * Sets the value of the fkD2CId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFkD2CId(Integer value) {
        this.fkD2CId = value;
    }

    /**
     * Gets the value of the indice property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndice() {
        return indice;
    }

    /**
     * Sets the value of the indice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndice(Integer value) {
        this.indice = value;
    }

    /**
     * Gets the value of the indiceMolteplicita property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndiceMolteplicita() {
        return indiceMolteplicita;
    }

    /**
     * Sets the value of the indiceMolteplicita property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndiceMolteplicita(Integer value) {
        this.indiceMolteplicita = value;
    }

    /**
     * Gets the value of the valore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValore() {
        return valore;
    }

    /**
     * Sets the value of the valore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValore(String value) {
        this.valore = value;
    }

    /**
     * Gets the value of the valoredecodificato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValoredecodificato() {
        return valoredecodificato;
    }

    /**
     * Sets the value of the valoredecodificato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValoredecodificato(String value) {
        this.valoredecodificato = value;
    }

}
