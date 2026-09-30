
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Titoli complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Titoli">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}BaseDataClass">
 *       &lt;sequence>
 *         &lt;element name="CODICETITOLO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDCOMUNE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TITOLO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Titoli", propOrder = {
    "codicetitolo",
    "idcomune",
    "titolo"
})
public class Titoli
    extends BaseDataClass
{

    @XmlElement(name = "CODICETITOLO")
    protected String codicetitolo;
    @XmlElement(name = "IDCOMUNE")
    protected String idcomune;
    @XmlElement(name = "TITOLO")
    protected String titolo;

    /**
     * Gets the value of the codicetitolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICETITOLO() {
        return codicetitolo;
    }

    /**
     * Sets the value of the codicetitolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICETITOLO(String value) {
        this.codicetitolo = value;
    }

    /**
     * Gets the value of the idcomune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCOMUNE() {
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
    public void setIDCOMUNE(String value) {
        this.idcomune = value;
    }

    /**
     * Gets the value of the titolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTITOLO() {
        return titolo;
    }

    /**
     * Sets the value of the titolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTITOLO(String value) {
        this.titolo = value;
    }

}
