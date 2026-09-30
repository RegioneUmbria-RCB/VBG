
package it.ldp.suolopubblico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComplexTypePeriodo2Wkt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComplexTypePeriodo2Wkt">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_temporaneo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="num_pratica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inizio_periodo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fine_periodo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nome_disegno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComplexTypePeriodo2Wkt", propOrder = {
    "idTemporaneo",
    "numPratica",
    "inizioPeriodo",
    "finePeriodo",
    "nomeDisegno"
})
public class ComplexTypePeriodo2Wkt {

    @XmlElement(name = "id_temporaneo", required = true, nillable = true)
    protected String idTemporaneo;
    @XmlElement(name = "num_pratica", required = true, nillable = true)
    protected String numPratica;
    @XmlElement(name = "inizio_periodo", required = true, nillable = true)
    protected String inizioPeriodo;
    @XmlElement(name = "fine_periodo", required = true, nillable = true)
    protected String finePeriodo;
    @XmlElement(name = "nome_disegno", required = true, nillable = true)
    protected String nomeDisegno;

    /**
     * Gets the value of the idTemporaneo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTemporaneo() {
        return idTemporaneo;
    }

    /**
     * Sets the value of the idTemporaneo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTemporaneo(String value) {
        this.idTemporaneo = value;
    }

    /**
     * Gets the value of the numPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumPratica() {
        return numPratica;
    }

    /**
     * Sets the value of the numPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumPratica(String value) {
        this.numPratica = value;
    }

    /**
     * Gets the value of the inizioPeriodo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInizioPeriodo() {
        return inizioPeriodo;
    }

    /**
     * Sets the value of the inizioPeriodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInizioPeriodo(String value) {
        this.inizioPeriodo = value;
    }

    /**
     * Gets the value of the finePeriodo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinePeriodo() {
        return finePeriodo;
    }

    /**
     * Sets the value of the finePeriodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinePeriodo(String value) {
        this.finePeriodo = value;
    }

    /**
     * Gets the value of the nomeDisegno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeDisegno() {
        return nomeDisegno;
    }

    /**
     * Sets the value of the nomeDisegno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeDisegno(String value) {
        this.nomeDisegno = value;
    }

}
