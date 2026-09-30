
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaAttEconProd complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaAttEconProd">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrATECO2007" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ragioneSociale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2002" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO2002" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="aziendaCessata" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO91" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO91" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="localitaUbicazSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneFonteDato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idFonteDato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2007" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvUbicazSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaAttEconProd", propOrder = {
    "descrATECO2007",
    "codiceFiscale",
    "ragioneSociale",
    "codATECO2002",
    "descrATECO2002",
    "aziendaCessata",
    "codATECO91",
    "idNaturaGiuridica",
    "descrATECO91",
    "localitaUbicazSL",
    "descrizioneFonteDato",
    "idFonteDato",
    "descrizioneNaturaGiuridica",
    "codATECO2007",
    "siglaProvUbicazSL"
})
public class ListaAttEconProd {

    @XmlElement(required = true, nillable = true)
    protected String descrATECO2007;
    @XmlElement(required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(required = true, nillable = true)
    protected String ragioneSociale;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2002;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO2002;
    @XmlElement(required = true, nillable = true)
    protected String aziendaCessata;
    @XmlElement(required = true, nillable = true)
    protected String codATECO91;
    @XmlElement(required = true, nillable = true)
    protected String idNaturaGiuridica;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO91;
    @XmlElement(required = true, nillable = true)
    protected String localitaUbicazSL;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String idFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneNaturaGiuridica;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2007;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvUbicazSL;

    /**
     * Gets the value of the descrATECO2007 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrATECO2007() {
        return descrATECO2007;
    }

    /**
     * Sets the value of the descrATECO2007 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrATECO2007(String value) {
        this.descrATECO2007 = value;
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
     * Gets the value of the codATECO2002 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodATECO2002() {
        return codATECO2002;
    }

    /**
     * Sets the value of the codATECO2002 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodATECO2002(String value) {
        this.codATECO2002 = value;
    }

    /**
     * Gets the value of the descrATECO2002 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrATECO2002() {
        return descrATECO2002;
    }

    /**
     * Sets the value of the descrATECO2002 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrATECO2002(String value) {
        this.descrATECO2002 = value;
    }

    /**
     * Gets the value of the aziendaCessata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAziendaCessata() {
        return aziendaCessata;
    }

    /**
     * Sets the value of the aziendaCessata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAziendaCessata(String value) {
        this.aziendaCessata = value;
    }

    /**
     * Gets the value of the codATECO91 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodATECO91() {
        return codATECO91;
    }

    /**
     * Sets the value of the codATECO91 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodATECO91(String value) {
        this.codATECO91 = value;
    }

    /**
     * Gets the value of the idNaturaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNaturaGiuridica() {
        return idNaturaGiuridica;
    }

    /**
     * Sets the value of the idNaturaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNaturaGiuridica(String value) {
        this.idNaturaGiuridica = value;
    }

    /**
     * Gets the value of the descrATECO91 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrATECO91() {
        return descrATECO91;
    }

    /**
     * Sets the value of the descrATECO91 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrATECO91(String value) {
        this.descrATECO91 = value;
    }

    /**
     * Gets the value of the localitaUbicazSL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalitaUbicazSL() {
        return localitaUbicazSL;
    }

    /**
     * Sets the value of the localitaUbicazSL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalitaUbicazSL(String value) {
        this.localitaUbicazSL = value;
    }

    /**
     * Gets the value of the descrizioneFonteDato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneFonteDato() {
        return descrizioneFonteDato;
    }

    /**
     * Sets the value of the descrizioneFonteDato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneFonteDato(String value) {
        this.descrizioneFonteDato = value;
    }

    /**
     * Gets the value of the idFonteDato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFonteDato() {
        return idFonteDato;
    }

    /**
     * Sets the value of the idFonteDato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFonteDato(String value) {
        this.idFonteDato = value;
    }

    /**
     * Gets the value of the descrizioneNaturaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneNaturaGiuridica() {
        return descrizioneNaturaGiuridica;
    }

    /**
     * Sets the value of the descrizioneNaturaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneNaturaGiuridica(String value) {
        this.descrizioneNaturaGiuridica = value;
    }

    /**
     * Gets the value of the codATECO2007 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodATECO2007() {
        return codATECO2007;
    }

    /**
     * Sets the value of the codATECO2007 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodATECO2007(String value) {
        this.codATECO2007 = value;
    }

    /**
     * Gets the value of the siglaProvUbicazSL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvUbicazSL() {
        return siglaProvUbicazSL;
    }

    /**
     * Sets the value of the siglaProvUbicazSL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvUbicazSL(String value) {
        this.siglaProvUbicazSL = value;
    }

}
