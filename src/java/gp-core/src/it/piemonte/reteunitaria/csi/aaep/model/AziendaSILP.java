
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AziendaSILP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AziendaSILP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrATECO2002SILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2002" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaProspDisabSILP" type="{urn:AAEPCSI}ArrayOfListaProspDisabSILP"/>
 *         &lt;element name="aziendaCessata" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO2007SILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgAziendaArtigiana" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataAcquisizioneAAEP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaSediSILP" type="{urn:AAEPCSI}ArrayOfListaSediSILP"/>
 *         &lt;element name="dataUltAggiornamSILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codNaturaGiuridicaAAEP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="partitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idSedeSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NDipendenti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInvio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrNaturaGiuridicaAAEP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataUltAggiornam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idProspDisabRiferim" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codCCNLMinistero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataUltAggiornamGenerale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codNaturaGiuridicaSILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO2007" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2002SILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ragioneSociale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrCCNLSILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO2002" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2007SILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrCCNLMinistero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2007" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrNaturaGiuridicaSILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codCCNLSILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AziendaSILP", propOrder = {
    "descrATECO2002SILP",
    "codATECO2002",
    "listaProspDisabSILP",
    "aziendaCessata",
    "descrATECO2007SILP",
    "flgAziendaArtigiana",
    "dataAcquisizioneAAEP",
    "listaSediSILP",
    "dataUltAggiornamSILP",
    "codNaturaGiuridicaAAEP",
    "codiceFiscale",
    "partitaIva",
    "idSedeSL",
    "nDipendenti",
    "dataInvio",
    "descrNaturaGiuridicaAAEP",
    "dataUltAggiornam",
    "idProspDisabRiferim",
    "annoRiferimento",
    "codCCNLMinistero",
    "dataUltAggiornamGenerale",
    "codNaturaGiuridicaSILP",
    "descrATECO2007",
    "codATECO2002SILP",
    "ragioneSociale",
    "descrCCNLSILP",
    "descrATECO2002",
    "codATECO2007SILP",
    "descrCCNLMinistero",
    "codATECO2007",
    "descrNaturaGiuridicaSILP",
    "codCCNLSILP"
})
public class AziendaSILP {

    @XmlElement(required = true, nillable = true)
    protected String descrATECO2002SILP;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2002;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfListaProspDisabSILP listaProspDisabSILP;
    @XmlElement(required = true, nillable = true)
    protected String aziendaCessata;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO2007SILP;
    @XmlElement(required = true, nillable = true)
    protected String flgAziendaArtigiana;
    @XmlElement(required = true, nillable = true)
    protected String dataAcquisizioneAAEP;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfListaSediSILP listaSediSILP;
    @XmlElement(required = true, nillable = true)
    protected String dataUltAggiornamSILP;
    @XmlElement(required = true, nillable = true)
    protected String codNaturaGiuridicaAAEP;
    @XmlElement(required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(required = true, nillable = true)
    protected String partitaIva;
    @XmlElement(required = true, nillable = true)
    protected String idSedeSL;
    @XmlElement(name = "NDipendenti", required = true, nillable = true)
    protected String nDipendenti;
    @XmlElement(required = true, nillable = true)
    protected String dataInvio;
    @XmlElement(required = true, nillable = true)
    protected String descrNaturaGiuridicaAAEP;
    @XmlElement(required = true, nillable = true)
    protected String dataUltAggiornam;
    @XmlElement(required = true, nillable = true)
    protected String idProspDisabRiferim;
    @XmlElement(required = true, nillable = true)
    protected String annoRiferimento;
    @XmlElement(required = true, nillable = true)
    protected String codCCNLMinistero;
    @XmlElement(required = true, nillable = true)
    protected String dataUltAggiornamGenerale;
    @XmlElement(required = true, nillable = true)
    protected String codNaturaGiuridicaSILP;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO2007;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2002SILP;
    @XmlElement(required = true, nillable = true)
    protected String ragioneSociale;
    @XmlElement(required = true, nillable = true)
    protected String descrCCNLSILP;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO2002;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2007SILP;
    @XmlElement(required = true, nillable = true)
    protected String descrCCNLMinistero;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2007;
    @XmlElement(required = true, nillable = true)
    protected String descrNaturaGiuridicaSILP;
    @XmlElement(required = true, nillable = true)
    protected String codCCNLSILP;

    /**
     * Gets the value of the descrATECO2002SILP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrATECO2002SILP() {
        return descrATECO2002SILP;
    }

    /**
     * Sets the value of the descrATECO2002SILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrATECO2002SILP(String value) {
        this.descrATECO2002SILP = value;
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
     * Gets the value of the listaProspDisabSILP property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfListaProspDisabSILP }
     *     
     */
    public ArrayOfListaProspDisabSILP getListaProspDisabSILP() {
        return listaProspDisabSILP;
    }

    /**
     * Sets the value of the listaProspDisabSILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfListaProspDisabSILP }
     *     
     */
    public void setListaProspDisabSILP(ArrayOfListaProspDisabSILP value) {
        this.listaProspDisabSILP = value;
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
     * Gets the value of the descrATECO2007SILP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrATECO2007SILP() {
        return descrATECO2007SILP;
    }

    /**
     * Sets the value of the descrATECO2007SILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrATECO2007SILP(String value) {
        this.descrATECO2007SILP = value;
    }

    /**
     * Gets the value of the flgAziendaArtigiana property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgAziendaArtigiana() {
        return flgAziendaArtigiana;
    }

    /**
     * Sets the value of the flgAziendaArtigiana property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgAziendaArtigiana(String value) {
        this.flgAziendaArtigiana = value;
    }

    /**
     * Gets the value of the dataAcquisizioneAAEP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataAcquisizioneAAEP() {
        return dataAcquisizioneAAEP;
    }

    /**
     * Sets the value of the dataAcquisizioneAAEP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataAcquisizioneAAEP(String value) {
        this.dataAcquisizioneAAEP = value;
    }

    /**
     * Gets the value of the listaSediSILP property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfListaSediSILP }
     *     
     */
    public ArrayOfListaSediSILP getListaSediSILP() {
        return listaSediSILP;
    }

    /**
     * Sets the value of the listaSediSILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfListaSediSILP }
     *     
     */
    public void setListaSediSILP(ArrayOfListaSediSILP value) {
        this.listaSediSILP = value;
    }

    /**
     * Gets the value of the dataUltAggiornamSILP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUltAggiornamSILP() {
        return dataUltAggiornamSILP;
    }

    /**
     * Sets the value of the dataUltAggiornamSILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUltAggiornamSILP(String value) {
        this.dataUltAggiornamSILP = value;
    }

    /**
     * Gets the value of the codNaturaGiuridicaAAEP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodNaturaGiuridicaAAEP() {
        return codNaturaGiuridicaAAEP;
    }

    /**
     * Sets the value of the codNaturaGiuridicaAAEP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodNaturaGiuridicaAAEP(String value) {
        this.codNaturaGiuridicaAAEP = value;
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
     * Gets the value of the idSedeSL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSedeSL() {
        return idSedeSL;
    }

    /**
     * Sets the value of the idSedeSL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSedeSL(String value) {
        this.idSedeSL = value;
    }

    /**
     * Gets the value of the nDipendenti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNDipendenti() {
        return nDipendenti;
    }

    /**
     * Sets the value of the nDipendenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNDipendenti(String value) {
        this.nDipendenti = value;
    }

    /**
     * Gets the value of the dataInvio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInvio() {
        return dataInvio;
    }

    /**
     * Sets the value of the dataInvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInvio(String value) {
        this.dataInvio = value;
    }

    /**
     * Gets the value of the descrNaturaGiuridicaAAEP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrNaturaGiuridicaAAEP() {
        return descrNaturaGiuridicaAAEP;
    }

    /**
     * Sets the value of the descrNaturaGiuridicaAAEP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrNaturaGiuridicaAAEP(String value) {
        this.descrNaturaGiuridicaAAEP = value;
    }

    /**
     * Gets the value of the dataUltAggiornam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUltAggiornam() {
        return dataUltAggiornam;
    }

    /**
     * Sets the value of the dataUltAggiornam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUltAggiornam(String value) {
        this.dataUltAggiornam = value;
    }

    /**
     * Gets the value of the idProspDisabRiferim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdProspDisabRiferim() {
        return idProspDisabRiferim;
    }

    /**
     * Sets the value of the idProspDisabRiferim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdProspDisabRiferim(String value) {
        this.idProspDisabRiferim = value;
    }

    /**
     * Gets the value of the annoRiferimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoRiferimento() {
        return annoRiferimento;
    }

    /**
     * Sets the value of the annoRiferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoRiferimento(String value) {
        this.annoRiferimento = value;
    }

    /**
     * Gets the value of the codCCNLMinistero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCCNLMinistero() {
        return codCCNLMinistero;
    }

    /**
     * Sets the value of the codCCNLMinistero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCCNLMinistero(String value) {
        this.codCCNLMinistero = value;
    }

    /**
     * Gets the value of the dataUltAggiornamGenerale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUltAggiornamGenerale() {
        return dataUltAggiornamGenerale;
    }

    /**
     * Sets the value of the dataUltAggiornamGenerale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUltAggiornamGenerale(String value) {
        this.dataUltAggiornamGenerale = value;
    }

    /**
     * Gets the value of the codNaturaGiuridicaSILP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodNaturaGiuridicaSILP() {
        return codNaturaGiuridicaSILP;
    }

    /**
     * Sets the value of the codNaturaGiuridicaSILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodNaturaGiuridicaSILP(String value) {
        this.codNaturaGiuridicaSILP = value;
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
     * Gets the value of the codATECO2002SILP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodATECO2002SILP() {
        return codATECO2002SILP;
    }

    /**
     * Sets the value of the codATECO2002SILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodATECO2002SILP(String value) {
        this.codATECO2002SILP = value;
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
     * Gets the value of the descrCCNLSILP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCCNLSILP() {
        return descrCCNLSILP;
    }

    /**
     * Sets the value of the descrCCNLSILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCCNLSILP(String value) {
        this.descrCCNLSILP = value;
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
     * Gets the value of the codATECO2007SILP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodATECO2007SILP() {
        return codATECO2007SILP;
    }

    /**
     * Sets the value of the codATECO2007SILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodATECO2007SILP(String value) {
        this.codATECO2007SILP = value;
    }

    /**
     * Gets the value of the descrCCNLMinistero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCCNLMinistero() {
        return descrCCNLMinistero;
    }

    /**
     * Sets the value of the descrCCNLMinistero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCCNLMinistero(String value) {
        this.descrCCNLMinistero = value;
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
     * Gets the value of the descrNaturaGiuridicaSILP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrNaturaGiuridicaSILP() {
        return descrNaturaGiuridicaSILP;
    }

    /**
     * Sets the value of the descrNaturaGiuridicaSILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrNaturaGiuridicaSILP(String value) {
        this.descrNaturaGiuridicaSILP = value;
    }

    /**
     * Gets the value of the codCCNLSILP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCCNLSILP() {
        return codCCNLSILP;
    }

    /**
     * Sets the value of the codCCNLSILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCCNLSILP(String value) {
        this.codCCNLSILP = value;
    }

}
