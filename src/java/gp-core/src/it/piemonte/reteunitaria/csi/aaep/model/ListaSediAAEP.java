
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaSediAAEP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaSediAAEP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioVal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComuneUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denominazioneSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaSediAAEP", propOrder = {
    "indirizzo",
    "dataInizioVal",
    "idAAEPSede",
    "idAAEPAzienda",
    "siglaProvUL",
    "descrComuneUL",
    "descrTipoSede",
    "denominazioneSede"
})
public class ListaSediAAEP {

    @XmlElement(required = true, nillable = true)
    protected String indirizzo;
    @XmlElement(required = true, nillable = true)
    protected String dataInizioVal;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPSede;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvUL;
    @XmlElement(required = true, nillable = true)
    protected String descrComuneUL;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoSede;
    @XmlElement(required = true, nillable = true)
    protected String denominazioneSede;

    /**
     * Gets the value of the indirizzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Sets the value of the indirizzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Gets the value of the dataInizioVal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInizioVal() {
        return dataInizioVal;
    }

    /**
     * Sets the value of the dataInizioVal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInizioVal(String value) {
        this.dataInizioVal = value;
    }

    /**
     * Gets the value of the idAAEPSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAAEPSede() {
        return idAAEPSede;
    }

    /**
     * Sets the value of the idAAEPSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAAEPSede(String value) {
        this.idAAEPSede = value;
    }

    /**
     * Gets the value of the idAAEPAzienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAAEPAzienda() {
        return idAAEPAzienda;
    }

    /**
     * Sets the value of the idAAEPAzienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAAEPAzienda(String value) {
        this.idAAEPAzienda = value;
    }

    /**
     * Gets the value of the siglaProvUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvUL() {
        return siglaProvUL;
    }

    /**
     * Sets the value of the siglaProvUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvUL(String value) {
        this.siglaProvUL = value;
    }

    /**
     * Gets the value of the descrComuneUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrComuneUL() {
        return descrComuneUL;
    }

    /**
     * Sets the value of the descrComuneUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrComuneUL(String value) {
        this.descrComuneUL = value;
    }

    /**
     * Gets the value of the descrTipoSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoSede() {
        return descrTipoSede;
    }

    /**
     * Sets the value of the descrTipoSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoSede(String value) {
        this.descrTipoSede = value;
    }

    /**
     * Gets the value of the denominazioneSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneSede() {
        return denominazioneSede;
    }

    /**
     * Sets the value of the denominazioneSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneSede(String value) {
        this.denominazioneSede = value;
    }

}
