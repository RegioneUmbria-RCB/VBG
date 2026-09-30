
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaSediInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaSediInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrTipoLocalizzazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numIscrizREA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComuneUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denominazioneSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="progrSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaSediInfoc", propOrder = {
    "descrTipoLocalizzazione",
    "indirizzo",
    "idAAEPAzienda",
    "siglaProvUL",
    "numIscrizREA",
    "descrComuneUL",
    "idAAEPFonteDato",
    "denominazioneSede",
    "siglaProvCCIAA",
    "progrSede"
})
public class ListaSediInfoc {

    @XmlElement(required = true, nillable = true)
    protected String descrTipoLocalizzazione;
    @XmlElement(required = true, nillable = true)
    protected String indirizzo;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvUL;
    @XmlElement(required = true, nillable = true)
    protected String numIscrizREA;
    @XmlElement(required = true, nillable = true)
    protected String descrComuneUL;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String denominazioneSede;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String progrSede;

    /**
     * Gets the value of the descrTipoLocalizzazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoLocalizzazione() {
        return descrTipoLocalizzazione;
    }

    /**
     * Sets the value of the descrTipoLocalizzazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoLocalizzazione(String value) {
        this.descrTipoLocalizzazione = value;
    }

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
     * Gets the value of the numIscrizREA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumIscrizREA() {
        return numIscrizREA;
    }

    /**
     * Sets the value of the numIscrizREA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumIscrizREA(String value) {
        this.numIscrizREA = value;
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
     * Gets the value of the idAAEPFonteDato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAAEPFonteDato() {
        return idAAEPFonteDato;
    }

    /**
     * Sets the value of the idAAEPFonteDato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAAEPFonteDato(String value) {
        this.idAAEPFonteDato = value;
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

    /**
     * Gets the value of the siglaProvCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvCCIAA() {
        return siglaProvCCIAA;
    }

    /**
     * Sets the value of the siglaProvCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvCCIAA(String value) {
        this.siglaProvCCIAA = value;
    }

    /**
     * Gets the value of the progrSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgrSede() {
        return progrSede;
    }

    /**
     * Sets the value of the progrSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgrSede(String value) {
        this.progrSede = value;
    }

}
