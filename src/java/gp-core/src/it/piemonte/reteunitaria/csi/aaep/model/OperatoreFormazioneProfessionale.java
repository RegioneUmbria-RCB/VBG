
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperatoreFormazioneProfessionale complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OperatoreFormazioneProfessionale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codici" type="{urn:AAEPCSI}ArrayOfCodiciATECO"/>
 *         &lt;element name="numeroCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="componenteFP" type="{urn:AAEPCSI}ArrayOfComponenteFormazioneProfessionale"/>
 *         &lt;element name="descrGruppoOperatore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDatoSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codCausaleCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrCausaleCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codGruppoOperatore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPSedeSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="partitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codOperatore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoOperatore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAziendaSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="provinciaCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoOperatore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ragioneSociale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operatoreCessato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperatoreFormazioneProfessionale", propOrder = {
    "codici",
    "numeroCCIAA",
    "componenteFP",
    "descrGruppoOperatore",
    "idAAEPFonteDatoSL",
    "codCausaleCessazione",
    "descrCausaleCessazione",
    "codGruppoOperatore",
    "codiceFiscale",
    "idAAEPSedeSL",
    "partitaIva",
    "codOperatore",
    "codTipoOperatore",
    "idAAEPAziendaSL",
    "provinciaCCIAA",
    "annoCCIAA",
    "descrTipoOperatore",
    "ragioneSociale",
    "operatoreCessato",
    "dataCessazione"
})
public class OperatoreFormazioneProfessionale {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfCodiciATECO codici;
    @XmlElement(required = true, nillable = true)
    protected String numeroCCIAA;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfComponenteFormazioneProfessionale componenteFP;
    @XmlElement(required = true, nillable = true)
    protected String descrGruppoOperatore;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPFonteDatoSL;
    @XmlElement(required = true, nillable = true)
    protected String codCausaleCessazione;
    @XmlElement(required = true, nillable = true)
    protected String descrCausaleCessazione;
    @XmlElement(required = true, nillable = true)
    protected String codGruppoOperatore;
    @XmlElement(required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPSedeSL;
    @XmlElement(required = true, nillable = true)
    protected String partitaIva;
    @XmlElement(required = true, nillable = true)
    protected String codOperatore;
    @XmlElement(required = true, nillable = true)
    protected String codTipoOperatore;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPAziendaSL;
    @XmlElement(required = true, nillable = true)
    protected String provinciaCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String annoCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoOperatore;
    @XmlElement(required = true, nillable = true)
    protected String ragioneSociale;
    @XmlElement(required = true, nillable = true)
    protected String operatoreCessato;
    @XmlElement(required = true, nillable = true)
    protected String dataCessazione;

    /**
     * Gets the value of the codici property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCodiciATECO }
     *     
     */
    public ArrayOfCodiciATECO getCodici() {
        return codici;
    }

    /**
     * Sets the value of the codici property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCodiciATECO }
     *     
     */
    public void setCodici(ArrayOfCodiciATECO value) {
        this.codici = value;
    }

    /**
     * Gets the value of the numeroCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCCIAA() {
        return numeroCCIAA;
    }

    /**
     * Sets the value of the numeroCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCCIAA(String value) {
        this.numeroCCIAA = value;
    }

    /**
     * Gets the value of the componenteFP property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfComponenteFormazioneProfessionale }
     *     
     */
    public ArrayOfComponenteFormazioneProfessionale getComponenteFP() {
        return componenteFP;
    }

    /**
     * Sets the value of the componenteFP property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfComponenteFormazioneProfessionale }
     *     
     */
    public void setComponenteFP(ArrayOfComponenteFormazioneProfessionale value) {
        this.componenteFP = value;
    }

    /**
     * Gets the value of the descrGruppoOperatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrGruppoOperatore() {
        return descrGruppoOperatore;
    }

    /**
     * Sets the value of the descrGruppoOperatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrGruppoOperatore(String value) {
        this.descrGruppoOperatore = value;
    }

    /**
     * Gets the value of the idAAEPFonteDatoSL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAAEPFonteDatoSL() {
        return idAAEPFonteDatoSL;
    }

    /**
     * Sets the value of the idAAEPFonteDatoSL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAAEPFonteDatoSL(String value) {
        this.idAAEPFonteDatoSL = value;
    }

    /**
     * Gets the value of the codCausaleCessazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCausaleCessazione() {
        return codCausaleCessazione;
    }

    /**
     * Sets the value of the codCausaleCessazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCausaleCessazione(String value) {
        this.codCausaleCessazione = value;
    }

    /**
     * Gets the value of the descrCausaleCessazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCausaleCessazione() {
        return descrCausaleCessazione;
    }

    /**
     * Sets the value of the descrCausaleCessazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCausaleCessazione(String value) {
        this.descrCausaleCessazione = value;
    }

    /**
     * Gets the value of the codGruppoOperatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodGruppoOperatore() {
        return codGruppoOperatore;
    }

    /**
     * Sets the value of the codGruppoOperatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodGruppoOperatore(String value) {
        this.codGruppoOperatore = value;
    }

    /**
     * Gets the value of the codiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets the value of the codiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Gets the value of the idAAEPSedeSL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAAEPSedeSL() {
        return idAAEPSedeSL;
    }

    /**
     * Sets the value of the idAAEPSedeSL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAAEPSedeSL(String value) {
        this.idAAEPSedeSL = value;
    }

    /**
     * Gets the value of the partitaIva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Sets the value of the partitaIva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitaIva(String value) {
        this.partitaIva = value;
    }

    /**
     * Gets the value of the codOperatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodOperatore() {
        return codOperatore;
    }

    /**
     * Sets the value of the codOperatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodOperatore(String value) {
        this.codOperatore = value;
    }

    /**
     * Gets the value of the codTipoOperatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoOperatore() {
        return codTipoOperatore;
    }

    /**
     * Sets the value of the codTipoOperatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoOperatore(String value) {
        this.codTipoOperatore = value;
    }

    /**
     * Gets the value of the idAAEPAziendaSL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAAEPAziendaSL() {
        return idAAEPAziendaSL;
    }

    /**
     * Sets the value of the idAAEPAziendaSL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAAEPAziendaSL(String value) {
        this.idAAEPAziendaSL = value;
    }

    /**
     * Gets the value of the provinciaCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaCCIAA() {
        return provinciaCCIAA;
    }

    /**
     * Sets the value of the provinciaCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaCCIAA(String value) {
        this.provinciaCCIAA = value;
    }

    /**
     * Gets the value of the annoCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoCCIAA() {
        return annoCCIAA;
    }

    /**
     * Sets the value of the annoCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoCCIAA(String value) {
        this.annoCCIAA = value;
    }

    /**
     * Gets the value of the descrTipoOperatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoOperatore() {
        return descrTipoOperatore;
    }

    /**
     * Sets the value of the descrTipoOperatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoOperatore(String value) {
        this.descrTipoOperatore = value;
    }

    /**
     * Gets the value of the ragioneSociale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    /**
     * Sets the value of the ragioneSociale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagioneSociale(String value) {
        this.ragioneSociale = value;
    }

    /**
     * Gets the value of the operatoreCessato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatoreCessato() {
        return operatoreCessato;
    }

    /**
     * Sets the value of the operatoreCessato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatoreCessato(String value) {
        this.operatoreCessato = value;
    }

    /**
     * Gets the value of the dataCessazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCessazione() {
        return dataCessazione;
    }

    /**
     * Sets the value of the dataCessazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCessazione(String value) {
        this.dataCessazione = value;
    }

}
