
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AziendaAAEP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AziendaAAEP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="partitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NRegistroImpreseCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ragioneSociale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idFonteDato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaSediAAEP" type="{urn:AAEPCSI}ArrayOfListaSediAAEP"/>
 *         &lt;element name="descrizioneFonteDato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneStatoAttiv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneCausaleCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="provinciaCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="provinciaIscrAlboArtigiano" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataDlbIscrAlboArtigiano" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO91" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceCausaleCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroIscrAlboArtigiano" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneIterIscrAlboArt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2007" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataCostituzione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPSedeSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO2007" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2002" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO2002" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgIterIscrAlboArt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tribunaleCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO91" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rappresentanteLegaleAAEP" type="{urn:AAEPCSI}RappresentanteLegaleAAEP"/>
 *         &lt;element name="aziendaCessata" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioValSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oggettoSociale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAziendaSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idStatoAttiv" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "AziendaAAEP", propOrder = {
    "idNaturaGiuridica",
    "numeroCCIAA",
    "partitaIva",
    "nRegistroImpreseCCIAA",
    "ragioneSociale",
    "idFonteDato",
    "annoCCIAA",
    "listaSediAAEP",
    "descrizioneFonteDato",
    "descrizioneStatoAttiv",
    "descrizioneCausaleCessazione",
    "provinciaCCIAA",
    "provinciaIscrAlboArtigiano",
    "dataDlbIscrAlboArtigiano",
    "codATECO91",
    "codiceCausaleCessazione",
    "numeroIscrAlboArtigiano",
    "descrizioneIterIscrAlboArt",
    "codATECO2007",
    "dataCostituzione",
    "idAAEPSedeSL",
    "descrATECO2007",
    "codiceFiscale",
    "codATECO2002",
    "descrATECO2002",
    "descrizioneNaturaGiuridica",
    "flgIterIscrAlboArt",
    "tribunaleCCIAA",
    "descrATECO91",
    "rappresentanteLegaleAAEP",
    "aziendaCessata",
    "dataInizioValSL",
    "oggettoSociale",
    "idAAEPAziendaSL",
    "idStatoAttiv",
    "dataCessazione"
})
public class AziendaAAEP {

    @XmlElement(required = true, nillable = true)
    protected String idNaturaGiuridica;
    @XmlElement(required = true, nillable = true)
    protected String numeroCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String partitaIva;
    @XmlElement(name = "NRegistroImpreseCCIAA", required = true, nillable = true)
    protected String nRegistroImpreseCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String ragioneSociale;
    @XmlElement(required = true, nillable = true)
    protected String idFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String annoCCIAA;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfListaSediAAEP listaSediAAEP;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneStatoAttiv;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneCausaleCessazione;
    @XmlElement(required = true, nillable = true)
    protected String provinciaCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String provinciaIscrAlboArtigiano;
    @XmlElement(required = true, nillable = true)
    protected String dataDlbIscrAlboArtigiano;
    @XmlElement(required = true, nillable = true)
    protected String codATECO91;
    @XmlElement(required = true, nillable = true)
    protected String codiceCausaleCessazione;
    @XmlElement(required = true, nillable = true)
    protected String numeroIscrAlboArtigiano;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneIterIscrAlboArt;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2007;
    @XmlElement(required = true, nillable = true)
    protected String dataCostituzione;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPSedeSL;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO2007;
    @XmlElement(required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2002;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO2002;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneNaturaGiuridica;
    @XmlElement(required = true, nillable = true)
    protected String flgIterIscrAlboArt;
    @XmlElement(required = true, nillable = true)
    protected String tribunaleCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO91;
    @XmlElement(required = true, nillable = true)
    protected RappresentanteLegaleAAEP rappresentanteLegaleAAEP;
    @XmlElement(required = true, nillable = true)
    protected String aziendaCessata;
    @XmlElement(required = true, nillable = true)
    protected String dataInizioValSL;
    @XmlElement(required = true, nillable = true)
    protected String oggettoSociale;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPAziendaSL;
    @XmlElement(required = true, nillable = true)
    protected String idStatoAttiv;
    @XmlElement(required = true, nillable = true)
    protected String dataCessazione;

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
     * Gets the value of the nRegistroImpreseCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNRegistroImpreseCCIAA() {
        return nRegistroImpreseCCIAA;
    }

    /**
     * Sets the value of the nRegistroImpreseCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNRegistroImpreseCCIAA(String value) {
        this.nRegistroImpreseCCIAA = value;
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
     * Gets the value of the listaSediAAEP property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfListaSediAAEP }
     *     
     */
    public ArrayOfListaSediAAEP getListaSediAAEP() {
        return listaSediAAEP;
    }

    /**
     * Sets the value of the listaSediAAEP property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfListaSediAAEP }
     *     
     */
    public void setListaSediAAEP(ArrayOfListaSediAAEP value) {
        this.listaSediAAEP = value;
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
     * Gets the value of the descrizioneStatoAttiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneStatoAttiv() {
        return descrizioneStatoAttiv;
    }

    /**
     * Sets the value of the descrizioneStatoAttiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneStatoAttiv(String value) {
        this.descrizioneStatoAttiv = value;
    }

    /**
     * Gets the value of the descrizioneCausaleCessazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneCausaleCessazione() {
        return descrizioneCausaleCessazione;
    }

    /**
     * Sets the value of the descrizioneCausaleCessazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneCausaleCessazione(String value) {
        this.descrizioneCausaleCessazione = value;
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
     * Gets the value of the provinciaIscrAlboArtigiano property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaIscrAlboArtigiano() {
        return provinciaIscrAlboArtigiano;
    }

    /**
     * Sets the value of the provinciaIscrAlboArtigiano property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaIscrAlboArtigiano(String value) {
        this.provinciaIscrAlboArtigiano = value;
    }

    /**
     * Gets the value of the dataDlbIscrAlboArtigiano property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataDlbIscrAlboArtigiano() {
        return dataDlbIscrAlboArtigiano;
    }

    /**
     * Sets the value of the dataDlbIscrAlboArtigiano property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataDlbIscrAlboArtigiano(String value) {
        this.dataDlbIscrAlboArtigiano = value;
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
     * Gets the value of the codiceCausaleCessazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCausaleCessazione() {
        return codiceCausaleCessazione;
    }

    /**
     * Sets the value of the codiceCausaleCessazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCausaleCessazione(String value) {
        this.codiceCausaleCessazione = value;
    }

    /**
     * Gets the value of the numeroIscrAlboArtigiano property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroIscrAlboArtigiano() {
        return numeroIscrAlboArtigiano;
    }

    /**
     * Sets the value of the numeroIscrAlboArtigiano property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroIscrAlboArtigiano(String value) {
        this.numeroIscrAlboArtigiano = value;
    }

    /**
     * Gets the value of the descrizioneIterIscrAlboArt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneIterIscrAlboArt() {
        return descrizioneIterIscrAlboArt;
    }

    /**
     * Sets the value of the descrizioneIterIscrAlboArt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneIterIscrAlboArt(String value) {
        this.descrizioneIterIscrAlboArt = value;
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
     * Gets the value of the dataCostituzione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCostituzione() {
        return dataCostituzione;
    }

    /**
     * Sets the value of the dataCostituzione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCostituzione(String value) {
        this.dataCostituzione = value;
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
     * Gets the value of the flgIterIscrAlboArt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgIterIscrAlboArt() {
        return flgIterIscrAlboArt;
    }

    /**
     * Sets the value of the flgIterIscrAlboArt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgIterIscrAlboArt(String value) {
        this.flgIterIscrAlboArt = value;
    }

    /**
     * Gets the value of the tribunaleCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTribunaleCCIAA() {
        return tribunaleCCIAA;
    }

    /**
     * Sets the value of the tribunaleCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTribunaleCCIAA(String value) {
        this.tribunaleCCIAA = value;
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
     * Gets the value of the rappresentanteLegaleAAEP property.
     * 
     * @return
     *     possible object is
     *     {@link RappresentanteLegaleAAEP }
     *     
     */
    public RappresentanteLegaleAAEP getRappresentanteLegaleAAEP() {
        return rappresentanteLegaleAAEP;
    }

    /**
     * Sets the value of the rappresentanteLegaleAAEP property.
     * 
     * @param value
     *     allowed object is
     *     {@link RappresentanteLegaleAAEP }
     *     
     */
    public void setRappresentanteLegaleAAEP(RappresentanteLegaleAAEP value) {
        this.rappresentanteLegaleAAEP = value;
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
     * Gets the value of the dataInizioValSL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInizioValSL() {
        return dataInizioValSL;
    }

    /**
     * Sets the value of the dataInizioValSL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInizioValSL(String value) {
        this.dataInizioValSL = value;
    }

    /**
     * Gets the value of the oggettoSociale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoSociale() {
        return oggettoSociale;
    }

    /**
     * Sets the value of the oggettoSociale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoSociale(String value) {
        this.oggettoSociale = value;
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
     * Gets the value of the idStatoAttiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdStatoAttiv() {
        return idStatoAttiv;
    }

    /**
     * Sets the value of the idStatoAttiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdStatoAttiv(String value) {
        this.idStatoAttiv = value;
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
