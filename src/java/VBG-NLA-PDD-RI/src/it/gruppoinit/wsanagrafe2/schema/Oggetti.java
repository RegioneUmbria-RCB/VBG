
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Oggetti complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Oggetti">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}BaseDataClass">
 *       &lt;sequence>
 *         &lt;element name="CODICEOGGETTO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDCOMUNE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OGGETTO" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="NOMEFILE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Oggetti", propOrder = {
    "codiceoggetto",
    "idcomune",
    "oggetto",
    "nomefile"
})
public class Oggetti
    extends BaseDataClass
{

    @XmlElement(name = "CODICEOGGETTO")
    protected String codiceoggetto;
    @XmlElement(name = "IDCOMUNE")
    protected String idcomune;
    @XmlElement(name = "OGGETTO")
    protected byte[] oggetto;
    @XmlElement(name = "NOMEFILE")
    protected String nomefile;

    /**
     * Gets the value of the codiceoggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEOGGETTO() {
        return codiceoggetto;
    }

    /**
     * Sets the value of the codiceoggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEOGGETTO(String value) {
        this.codiceoggetto = value;
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
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getOGGETTO() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setOGGETTO(byte[] value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the nomefile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMEFILE() {
        return nomefile;
    }

    /**
     * Sets the value of the nomefile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMEFILE(String value) {
        this.nomefile = value;
    }

}
