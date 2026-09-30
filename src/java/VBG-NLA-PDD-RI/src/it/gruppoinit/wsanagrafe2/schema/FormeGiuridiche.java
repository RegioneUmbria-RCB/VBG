
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FormeGiuridiche complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FormeGiuridiche">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}BaseDataClass">
 *       &lt;sequence>
 *         &lt;element name="CODICEFORMAGIURIDICA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDCOMUNE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FORMAGIURIDICA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODICECCIAA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormeGiuridiche", propOrder = {
    "codiceformagiuridica",
    "idcomune",
    "formagiuridica",
    "codicecciaa"
})
public class FormeGiuridiche
    extends BaseDataClass
{

    @XmlElement(name = "CODICEFORMAGIURIDICA")
    protected String codiceformagiuridica;
    @XmlElement(name = "IDCOMUNE")
    protected String idcomune;
    @XmlElement(name = "FORMAGIURIDICA")
    protected String formagiuridica;
    @XmlElement(name = "CODICECCIAA")
    protected String codicecciaa;

    /**
     * Gets the value of the codiceformagiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEFORMAGIURIDICA() {
        return codiceformagiuridica;
    }

    /**
     * Sets the value of the codiceformagiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEFORMAGIURIDICA(String value) {
        this.codiceformagiuridica = value;
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
     * Gets the value of the formagiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFORMAGIURIDICA() {
        return formagiuridica;
    }

    /**
     * Sets the value of the formagiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFORMAGIURIDICA(String value) {
        this.formagiuridica = value;
    }

    /**
     * Gets the value of the codicecciaa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICECCIAA() {
        return codicecciaa;
    }

    /**
     * Sets the value of the codicecciaa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICECCIAA(String value) {
        this.codicecciaa = value;
    }

}
