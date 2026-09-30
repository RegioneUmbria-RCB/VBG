
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Comuni complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Comuni">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}BaseDataClass">
 *       &lt;sequence>
 *         &lt;element name="CODICECOMUNE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COMUNE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SIGLAPROVINCIA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROVINCIA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="REGIONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CAP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODICEISTAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODICEISTATREGIONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODICESTATOESTERO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProvinciaClass" type="{http://init.sigepro.it}VwProvince" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Comuni", propOrder = {
    "codicecomune",
    "comune",
    "siglaprovincia",
    "provincia",
    "regione",
    "cap",
    "cf",
    "codiceistat",
    "codiceistatregione",
    "codicestatoestero",
    "provinciaClass"
})
public class Comuni
    extends BaseDataClass
{

    @XmlElement(name = "CODICECOMUNE")
    protected String codicecomune;
    @XmlElement(name = "COMUNE")
    protected String comune;
    @XmlElement(name = "SIGLAPROVINCIA")
    protected String siglaprovincia;
    @XmlElement(name = "PROVINCIA")
    protected String provincia;
    @XmlElement(name = "REGIONE")
    protected String regione;
    @XmlElement(name = "CAP")
    protected String cap;
    @XmlElement(name = "CF")
    protected String cf;
    @XmlElement(name = "CODICEISTAT")
    protected String codiceistat;
    @XmlElement(name = "CODICEISTATREGIONE")
    protected String codiceistatregione;
    @XmlElement(name = "CODICESTATOESTERO")
    protected String codicestatoestero;
    @XmlElement(name = "ProvinciaClass")
    protected VwProvince provinciaClass;

    /**
     * Gets the value of the codicecomune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICECOMUNE() {
        return codicecomune;
    }

    /**
     * Sets the value of the codicecomune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICECOMUNE(String value) {
        this.codicecomune = value;
    }

    /**
     * Gets the value of the comune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMUNE() {
        return comune;
    }

    /**
     * Sets the value of the comune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMUNE(String value) {
        this.comune = value;
    }

    /**
     * Gets the value of the siglaprovincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSIGLAPROVINCIA() {
        return siglaprovincia;
    }

    /**
     * Sets the value of the siglaprovincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSIGLAPROVINCIA(String value) {
        this.siglaprovincia = value;
    }

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIA() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIA(String value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the regione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGIONE() {
        return regione;
    }

    /**
     * Sets the value of the regione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGIONE(String value) {
        this.regione = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAP() {
        return cap;
    }

    /**
     * Sets the value of the cap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAP(String value) {
        this.cap = value;
    }

    /**
     * Gets the value of the cf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCF() {
        return cf;
    }

    /**
     * Sets the value of the cf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCF(String value) {
        this.cf = value;
    }

    /**
     * Gets the value of the codiceistat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEISTAT() {
        return codiceistat;
    }

    /**
     * Sets the value of the codiceistat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEISTAT(String value) {
        this.codiceistat = value;
    }

    /**
     * Gets the value of the codiceistatregione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEISTATREGIONE() {
        return codiceistatregione;
    }

    /**
     * Sets the value of the codiceistatregione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEISTATREGIONE(String value) {
        this.codiceistatregione = value;
    }

    /**
     * Gets the value of the codicestatoestero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICESTATOESTERO() {
        return codicestatoestero;
    }

    /**
     * Sets the value of the codicestatoestero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICESTATOESTERO(String value) {
        this.codicestatoestero = value;
    }

    /**
     * Gets the value of the provinciaClass property.
     * 
     * @return
     *     possible object is
     *     {@link VwProvince }
     *     
     */
    public VwProvince getProvinciaClass() {
        return provinciaClass;
    }

    /**
     * Sets the value of the provinciaClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link VwProvince }
     *     
     */
    public void setProvinciaClass(VwProvince value) {
        this.provinciaClass = value;
    }

}
