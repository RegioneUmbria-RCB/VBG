
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SedeSILP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SedeSILP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrTipoVia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codBelfioreStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codINPS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgUbicazioneSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codCPI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataUltAggiornam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2007SILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO2002SILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgObbligoProspDisab" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codBelfioreComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioAttivita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrFonteDato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgMovimentazRappLavoro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idFonteDato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrClasseAmpiezza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrUbicazioneSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFineAttivita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codStatoSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrStatoSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrCPI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoSedeAAEP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroDipendenti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codINAIL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codISTATComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaReferentiSILP" type="{urn:AAEPCSI}ArrayOfReferenteSILP"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2002SILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denominazioneSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2007" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indirizzoSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO2007" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codATECO2002" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoVia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codClasseAmpiezza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoSedeAAEP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO2002" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idRappLavoroPrevalente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataNumeroDipendenti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrATECO2007SILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroCivico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrRappLavoroPrevalente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataUltAggiornamSILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SedeSILP", propOrder = {
    "descrTipoVia",
    "codBelfioreStatoEstero",
    "descrComune",
    "codINPS",
    "flgUbicazioneSede",
    "codCPI",
    "dataUltAggiornam",
    "codATECO2007SILP",
    "descrATECO2002SILP",
    "flgObbligoProspDisab",
    "codBelfioreComune",
    "descrStatoEstero",
    "fax",
    "dataInizioAttivita",
    "descrFonteDato",
    "flgMovimentazRappLavoro",
    "idFonteDato",
    "descrClasseAmpiezza",
    "descrUbicazioneSede",
    "dataFineAttivita",
    "telefono",
    "codStatoSede",
    "descrStatoSede",
    "descrCPI",
    "idSede",
    "descrTipoSedeAAEP",
    "numeroDipendenti",
    "codINAIL",
    "codISTATComune",
    "listaReferentiSILP",
    "localita",
    "codATECO2002SILP",
    "denominazioneSede",
    "indirizzo",
    "codATECO2007",
    "indirizzoSede",
    "cap",
    "descrATECO2007",
    "codATECO2002",
    "codTipoVia",
    "codClasseAmpiezza",
    "codTipoSedeAAEP",
    "descrATECO2002",
    "descrTipoSede",
    "codTipoSede",
    "siglaProv",
    "idRappLavoroPrevalente",
    "dataNumeroDipendenti",
    "descrATECO2007SILP",
    "numeroCivico",
    "descrRappLavoroPrevalente",
    "dataUltAggiornamSILP"
})
public class SedeSILP {

    @XmlElement(required = true, nillable = true)
    protected String descrTipoVia;
    @XmlElement(required = true, nillable = true)
    protected String codBelfioreStatoEstero;
    @XmlElement(required = true, nillable = true)
    protected String descrComune;
    @XmlElement(required = true, nillable = true)
    protected String codINPS;
    @XmlElement(required = true, nillable = true)
    protected String flgUbicazioneSede;
    @XmlElement(required = true, nillable = true)
    protected String codCPI;
    @XmlElement(required = true, nillable = true)
    protected String dataUltAggiornam;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2007SILP;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO2002SILP;
    @XmlElement(required = true, nillable = true)
    protected String flgObbligoProspDisab;
    @XmlElement(required = true, nillable = true)
    protected String codBelfioreComune;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoEstero;
    @XmlElement(required = true, nillable = true)
    protected String fax;
    @XmlElement(required = true, nillable = true)
    protected String dataInizioAttivita;
    @XmlElement(required = true, nillable = true)
    protected String descrFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String flgMovimentazRappLavoro;
    @XmlElement(required = true, nillable = true)
    protected String idFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String descrClasseAmpiezza;
    @XmlElement(required = true, nillable = true)
    protected String descrUbicazioneSede;
    @XmlElement(required = true, nillable = true)
    protected String dataFineAttivita;
    @XmlElement(required = true, nillable = true)
    protected String telefono;
    @XmlElement(required = true, nillable = true)
    protected String codStatoSede;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoSede;
    @XmlElement(required = true, nillable = true)
    protected String descrCPI;
    @XmlElement(required = true, nillable = true)
    protected String idSede;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoSedeAAEP;
    @XmlElement(required = true, nillable = true)
    protected String numeroDipendenti;
    @XmlElement(required = true, nillable = true)
    protected String codINAIL;
    @XmlElement(required = true, nillable = true)
    protected String codISTATComune;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfReferenteSILP listaReferentiSILP;
    @XmlElement(required = true, nillable = true)
    protected String localita;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2002SILP;
    @XmlElement(required = true, nillable = true)
    protected String denominazioneSede;
    @XmlElement(required = true, nillable = true)
    protected String indirizzo;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2007;
    @XmlElement(required = true, nillable = true)
    protected String indirizzoSede;
    @XmlElement(required = true, nillable = true)
    protected String cap;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO2007;
    @XmlElement(required = true, nillable = true)
    protected String codATECO2002;
    @XmlElement(required = true, nillable = true)
    protected String codTipoVia;
    @XmlElement(required = true, nillable = true)
    protected String codClasseAmpiezza;
    @XmlElement(required = true, nillable = true)
    protected String codTipoSedeAAEP;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO2002;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoSede;
    @XmlElement(required = true, nillable = true)
    protected String codTipoSede;
    @XmlElement(required = true, nillable = true)
    protected String siglaProv;
    @XmlElement(required = true, nillable = true)
    protected String idRappLavoroPrevalente;
    @XmlElement(required = true, nillable = true)
    protected String dataNumeroDipendenti;
    @XmlElement(required = true, nillable = true)
    protected String descrATECO2007SILP;
    @XmlElement(required = true, nillable = true)
    protected String numeroCivico;
    @XmlElement(required = true, nillable = true)
    protected String descrRappLavoroPrevalente;
    @XmlElement(required = true, nillable = true)
    protected String dataUltAggiornamSILP;

    /**
     * Gets the value of the descrTipoVia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoVia() {
        return descrTipoVia;
    }

    /**
     * Sets the value of the descrTipoVia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoVia(String value) {
        this.descrTipoVia = value;
    }

    /**
     * Gets the value of the codBelfioreStatoEstero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodBelfioreStatoEstero() {
        return codBelfioreStatoEstero;
    }

    /**
     * Sets the value of the codBelfioreStatoEstero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodBelfioreStatoEstero(String value) {
        this.codBelfioreStatoEstero = value;
    }

    /**
     * Gets the value of the descrComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrComune() {
        return descrComune;
    }

    /**
     * Sets the value of the descrComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrComune(String value) {
        this.descrComune = value;
    }

    /**
     * Gets the value of the codINPS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodINPS() {
        return codINPS;
    }

    /**
     * Sets the value of the codINPS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodINPS(String value) {
        this.codINPS = value;
    }

    /**
     * Gets the value of the flgUbicazioneSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgUbicazioneSede() {
        return flgUbicazioneSede;
    }

    /**
     * Sets the value of the flgUbicazioneSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgUbicazioneSede(String value) {
        this.flgUbicazioneSede = value;
    }

    /**
     * Gets the value of the codCPI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCPI() {
        return codCPI;
    }

    /**
     * Sets the value of the codCPI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCPI(String value) {
        this.codCPI = value;
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
     * Gets the value of the flgObbligoProspDisab property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgObbligoProspDisab() {
        return flgObbligoProspDisab;
    }

    /**
     * Sets the value of the flgObbligoProspDisab property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgObbligoProspDisab(String value) {
        this.flgObbligoProspDisab = value;
    }

    /**
     * Gets the value of the codBelfioreComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodBelfioreComune() {
        return codBelfioreComune;
    }

    /**
     * Sets the value of the codBelfioreComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodBelfioreComune(String value) {
        this.codBelfioreComune = value;
    }

    /**
     * Gets the value of the descrStatoEstero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrStatoEstero() {
        return descrStatoEstero;
    }

    /**
     * Sets the value of the descrStatoEstero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrStatoEstero(String value) {
        this.descrStatoEstero = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the dataInizioAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInizioAttivita() {
        return dataInizioAttivita;
    }

    /**
     * Sets the value of the dataInizioAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInizioAttivita(String value) {
        this.dataInizioAttivita = value;
    }

    /**
     * Gets the value of the descrFonteDato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrFonteDato() {
        return descrFonteDato;
    }

    /**
     * Sets the value of the descrFonteDato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrFonteDato(String value) {
        this.descrFonteDato = value;
    }

    /**
     * Gets the value of the flgMovimentazRappLavoro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgMovimentazRappLavoro() {
        return flgMovimentazRappLavoro;
    }

    /**
     * Sets the value of the flgMovimentazRappLavoro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgMovimentazRappLavoro(String value) {
        this.flgMovimentazRappLavoro = value;
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
     * Gets the value of the descrClasseAmpiezza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrClasseAmpiezza() {
        return descrClasseAmpiezza;
    }

    /**
     * Sets the value of the descrClasseAmpiezza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrClasseAmpiezza(String value) {
        this.descrClasseAmpiezza = value;
    }

    /**
     * Gets the value of the descrUbicazioneSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrUbicazioneSede() {
        return descrUbicazioneSede;
    }

    /**
     * Sets the value of the descrUbicazioneSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrUbicazioneSede(String value) {
        this.descrUbicazioneSede = value;
    }

    /**
     * Gets the value of the dataFineAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFineAttivita() {
        return dataFineAttivita;
    }

    /**
     * Sets the value of the dataFineAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFineAttivita(String value) {
        this.dataFineAttivita = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the codStatoSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodStatoSede() {
        return codStatoSede;
    }

    /**
     * Sets the value of the codStatoSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodStatoSede(String value) {
        this.codStatoSede = value;
    }

    /**
     * Gets the value of the descrStatoSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrStatoSede() {
        return descrStatoSede;
    }

    /**
     * Sets the value of the descrStatoSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrStatoSede(String value) {
        this.descrStatoSede = value;
    }

    /**
     * Gets the value of the descrCPI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCPI() {
        return descrCPI;
    }

    /**
     * Sets the value of the descrCPI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCPI(String value) {
        this.descrCPI = value;
    }

    /**
     * Gets the value of the idSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSede() {
        return idSede;
    }

    /**
     * Sets the value of the idSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSede(String value) {
        this.idSede = value;
    }

    /**
     * Gets the value of the descrTipoSedeAAEP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoSedeAAEP() {
        return descrTipoSedeAAEP;
    }

    /**
     * Sets the value of the descrTipoSedeAAEP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoSedeAAEP(String value) {
        this.descrTipoSedeAAEP = value;
    }

    /**
     * Gets the value of the numeroDipendenti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroDipendenti() {
        return numeroDipendenti;
    }

    /**
     * Sets the value of the numeroDipendenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroDipendenti(String value) {
        this.numeroDipendenti = value;
    }

    /**
     * Gets the value of the codINAIL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodINAIL() {
        return codINAIL;
    }

    /**
     * Sets the value of the codINAIL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodINAIL(String value) {
        this.codINAIL = value;
    }

    /**
     * Gets the value of the codISTATComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodISTATComune() {
        return codISTATComune;
    }

    /**
     * Sets the value of the codISTATComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodISTATComune(String value) {
        this.codISTATComune = value;
    }

    /**
     * Gets the value of the listaReferentiSILP property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfReferenteSILP }
     *     
     */
    public ArrayOfReferenteSILP getListaReferentiSILP() {
        return listaReferentiSILP;
    }

    /**
     * Sets the value of the listaReferentiSILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfReferenteSILP }
     *     
     */
    public void setListaReferentiSILP(ArrayOfReferenteSILP value) {
        this.listaReferentiSILP = value;
    }

    /**
     * Gets the value of the localita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalita() {
        return localita;
    }

    /**
     * Sets the value of the localita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalita(String value) {
        this.localita = value;
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
     * Gets the value of the indirizzoSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoSede() {
        return indirizzoSede;
    }

    /**
     * Sets the value of the indirizzoSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoSede(String value) {
        this.indirizzoSede = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCap() {
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
    public void setCap(String value) {
        this.cap = value;
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
     * Gets the value of the codTipoVia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoVia() {
        return codTipoVia;
    }

    /**
     * Sets the value of the codTipoVia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoVia(String value) {
        this.codTipoVia = value;
    }

    /**
     * Gets the value of the codClasseAmpiezza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodClasseAmpiezza() {
        return codClasseAmpiezza;
    }

    /**
     * Sets the value of the codClasseAmpiezza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodClasseAmpiezza(String value) {
        this.codClasseAmpiezza = value;
    }

    /**
     * Gets the value of the codTipoSedeAAEP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoSedeAAEP() {
        return codTipoSedeAAEP;
    }

    /**
     * Sets the value of the codTipoSedeAAEP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoSedeAAEP(String value) {
        this.codTipoSedeAAEP = value;
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
     * Gets the value of the codTipoSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoSede() {
        return codTipoSede;
    }

    /**
     * Sets the value of the codTipoSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoSede(String value) {
        this.codTipoSede = value;
    }

    /**
     * Gets the value of the siglaProv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProv() {
        return siglaProv;
    }

    /**
     * Sets the value of the siglaProv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProv(String value) {
        this.siglaProv = value;
    }

    /**
     * Gets the value of the idRappLavoroPrevalente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRappLavoroPrevalente() {
        return idRappLavoroPrevalente;
    }

    /**
     * Sets the value of the idRappLavoroPrevalente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRappLavoroPrevalente(String value) {
        this.idRappLavoroPrevalente = value;
    }

    /**
     * Gets the value of the dataNumeroDipendenti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataNumeroDipendenti() {
        return dataNumeroDipendenti;
    }

    /**
     * Sets the value of the dataNumeroDipendenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataNumeroDipendenti(String value) {
        this.dataNumeroDipendenti = value;
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
     * Gets the value of the numeroCivico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCivico() {
        return numeroCivico;
    }

    /**
     * Sets the value of the numeroCivico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCivico(String value) {
        this.numeroCivico = value;
    }

    /**
     * Gets the value of the descrRappLavoroPrevalente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrRappLavoroPrevalente() {
        return descrRappLavoroPrevalente;
    }

    /**
     * Sets the value of the descrRappLavoroPrevalente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrRappLavoroPrevalente(String value) {
        this.descrRappLavoroPrevalente = value;
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

}
