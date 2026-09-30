
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MercatiPresenzeStorico complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MercatiPresenzeStorico">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}BaseDataClass">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Anagrafe" type="{http://init.sigepro.it}Anagrafe" minOccurs="0"/>
 *         &lt;element name="Idcomune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fkcodicemercato" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Fkidmercatiuso" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Codiceanagrafe" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Numeropresenze" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IdentAut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MercatiPresenzeStorico", propOrder = {
    "id",
    "anagrafe",
    "idcomune",
    "fkcodicemercato",
    "fkidmercatiuso",
    "codiceanagrafe",
    "anno",
    "numeropresenze",
    "identAut"
})
public class MercatiPresenzeStorico
    extends BaseDataClass
{

    @XmlElement(name = "Id", required = true, type = Integer.class, nillable = true)
    protected Integer id;
    @XmlElement(name = "Anagrafe")
    protected Anagrafe anagrafe;
    @XmlElement(name = "Idcomune")
    protected String idcomune;
    @XmlElement(name = "Fkcodicemercato", required = true, type = Integer.class, nillable = true)
    protected Integer fkcodicemercato;
    @XmlElement(name = "Fkidmercatiuso", required = true, type = Integer.class, nillable = true)
    protected Integer fkidmercatiuso;
    @XmlElement(name = "Codiceanagrafe", required = true, type = Integer.class, nillable = true)
    protected Integer codiceanagrafe;
    @XmlElement(name = "Anno", required = true, type = Integer.class, nillable = true)
    protected Integer anno;
    @XmlElement(name = "Numeropresenze", required = true, type = Integer.class, nillable = true)
    protected Integer numeropresenze;
    @XmlElement(name = "IdentAut")
    protected String identAut;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the anagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link Anagrafe }
     *     
     */
    public Anagrafe getAnagrafe() {
        return anagrafe;
    }

    /**
     * Sets the value of the anagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Anagrafe }
     *     
     */
    public void setAnagrafe(Anagrafe value) {
        this.anagrafe = value;
    }

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
     * Gets the value of the fkcodicemercato property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFkcodicemercato() {
        return fkcodicemercato;
    }

    /**
     * Sets the value of the fkcodicemercato property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFkcodicemercato(Integer value) {
        this.fkcodicemercato = value;
    }

    /**
     * Gets the value of the fkidmercatiuso property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFkidmercatiuso() {
        return fkidmercatiuso;
    }

    /**
     * Sets the value of the fkidmercatiuso property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFkidmercatiuso(Integer value) {
        this.fkidmercatiuso = value;
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
     * Gets the value of the anno property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnno() {
        return anno;
    }

    /**
     * Sets the value of the anno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnno(Integer value) {
        this.anno = value;
    }

    /**
     * Gets the value of the numeropresenze property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeropresenze() {
        return numeropresenze;
    }

    /**
     * Sets the value of the numeropresenze property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeropresenze(Integer value) {
        this.numeropresenze = value;
    }

    /**
     * Gets the value of the identAut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentAut() {
        return identAut;
    }

    /**
     * Sets the value of the identAut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentAut(String value) {
        this.identAut = value;
    }

}
