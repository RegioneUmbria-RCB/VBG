
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComponenteFormazioneProfessionale complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComponenteFormazioneProfessionale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indirizzoAlternativo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeCittaEstera" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codCausaleCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrCausaleCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoComponente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sitoWeb" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceQuartiere" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="riferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiciAteco" type="{urn:AAEPCSI}ArrayOfCodiciATECO"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroVerde" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrSettore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="corsoFP" type="{urn:AAEPCSI}ArrayOfCorsoFP"/>
 *         &lt;element name="codSettore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoComponente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaSindacato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="unitaOperativa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoSettore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComponenteFormazioneProfessionale", propOrder = {
    "indirizzoAlternativo",
    "idAAEPSede",
    "nomeCittaEstera",
    "codCausaleCessazione",
    "descrCausaleCessazione",
    "nomeStatoEstero",
    "descrComune",
    "codComune",
    "descrTipoComponente",
    "cap",
    "sitoWeb",
    "idAAEPFonteDato",
    "codiceQuartiere",
    "riferimento",
    "codiciAteco",
    "telefono",
    "numeroVerde",
    "email",
    "descrSettore",
    "corsoFP",
    "codSettore",
    "codTipoComponente",
    "siglaSindacato",
    "denominazione",
    "unitaOperativa",
    "fax",
    "idAAEPAzienda",
    "dataCessazione",
    "indirizzo",
    "annoSettore"
})
public class ComponenteFormazioneProfessionale {

    @XmlElement(required = true, nillable = true)
    protected String indirizzoAlternativo;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPSede;
    @XmlElement(required = true, nillable = true)
    protected String nomeCittaEstera;
    @XmlElement(required = true, nillable = true)
    protected String codCausaleCessazione;
    @XmlElement(required = true, nillable = true)
    protected String descrCausaleCessazione;
    @XmlElement(required = true, nillable = true)
    protected String nomeStatoEstero;
    @XmlElement(required = true, nillable = true)
    protected String descrComune;
    @XmlElement(required = true, nillable = true)
    protected String codComune;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoComponente;
    @XmlElement(required = true, nillable = true)
    protected String cap;
    @XmlElement(required = true, nillable = true)
    protected String sitoWeb;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String codiceQuartiere;
    @XmlElement(required = true, nillable = true)
    protected String riferimento;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfCodiciATECO codiciAteco;
    @XmlElement(required = true, nillable = true)
    protected String telefono;
    @XmlElement(required = true, nillable = true)
    protected String numeroVerde;
    @XmlElement(required = true, nillable = true)
    protected String email;
    @XmlElement(required = true, nillable = true)
    protected String descrSettore;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfCorsoFP corsoFP;
    @XmlElement(required = true, nillable = true)
    protected String codSettore;
    @XmlElement(required = true, nillable = true)
    protected String codTipoComponente;
    @XmlElement(required = true, nillable = true)
    protected String siglaSindacato;
    @XmlElement(required = true, nillable = true)
    protected String denominazione;
    @XmlElement(required = true, nillable = true)
    protected String unitaOperativa;
    @XmlElement(required = true, nillable = true)
    protected String fax;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String dataCessazione;
    @XmlElement(required = true, nillable = true)
    protected String indirizzo;
    @XmlElement(required = true, nillable = true)
    protected String annoSettore;

    /**
     * Gets the value of the indirizzoAlternativo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoAlternativo() {
        return indirizzoAlternativo;
    }

    /**
     * Sets the value of the indirizzoAlternativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoAlternativo(String value) {
        this.indirizzoAlternativo = value;
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
     * Gets the value of the nomeCittaEstera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeCittaEstera() {
        return nomeCittaEstera;
    }

    /**
     * Sets the value of the nomeCittaEstera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeCittaEstera(String value) {
        this.nomeCittaEstera = value;
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
     * Gets the value of the nomeStatoEstero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeStatoEstero() {
        return nomeStatoEstero;
    }

    /**
     * Sets the value of the nomeStatoEstero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeStatoEstero(String value) {
        this.nomeStatoEstero = value;
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
     * Gets the value of the codComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodComune() {
        return codComune;
    }

    /**
     * Sets the value of the codComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodComune(String value) {
        this.codComune = value;
    }

    /**
     * Gets the value of the descrTipoComponente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoComponente() {
        return descrTipoComponente;
    }

    /**
     * Sets the value of the descrTipoComponente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoComponente(String value) {
        this.descrTipoComponente = value;
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
     * Gets the value of the sitoWeb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitoWeb() {
        return sitoWeb;
    }

    /**
     * Sets the value of the sitoWeb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitoWeb(String value) {
        this.sitoWeb = value;
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
     * Gets the value of the codiceQuartiere property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceQuartiere() {
        return codiceQuartiere;
    }

    /**
     * Sets the value of the codiceQuartiere property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceQuartiere(String value) {
        this.codiceQuartiere = value;
    }

    /**
     * Gets the value of the riferimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimento() {
        return riferimento;
    }

    /**
     * Sets the value of the riferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimento(String value) {
        this.riferimento = value;
    }

    /**
     * Gets the value of the codiciAteco property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCodiciATECO }
     *     
     */
    public ArrayOfCodiciATECO getCodiciAteco() {
        return codiciAteco;
    }

    /**
     * Sets the value of the codiciAteco property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCodiciATECO }
     *     
     */
    public void setCodiciAteco(ArrayOfCodiciATECO value) {
        this.codiciAteco = value;
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
     * Gets the value of the numeroVerde property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroVerde() {
        return numeroVerde;
    }

    /**
     * Sets the value of the numeroVerde property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroVerde(String value) {
        this.numeroVerde = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the descrSettore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrSettore() {
        return descrSettore;
    }

    /**
     * Sets the value of the descrSettore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrSettore(String value) {
        this.descrSettore = value;
    }

    /**
     * Gets the value of the corsoFP property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCorsoFP }
     *     
     */
    public ArrayOfCorsoFP getCorsoFP() {
        return corsoFP;
    }

    /**
     * Sets the value of the corsoFP property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCorsoFP }
     *     
     */
    public void setCorsoFP(ArrayOfCorsoFP value) {
        this.corsoFP = value;
    }

    /**
     * Gets the value of the codSettore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodSettore() {
        return codSettore;
    }

    /**
     * Sets the value of the codSettore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodSettore(String value) {
        this.codSettore = value;
    }

    /**
     * Gets the value of the codTipoComponente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoComponente() {
        return codTipoComponente;
    }

    /**
     * Sets the value of the codTipoComponente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoComponente(String value) {
        this.codTipoComponente = value;
    }

    /**
     * Gets the value of the siglaSindacato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaSindacato() {
        return siglaSindacato;
    }

    /**
     * Sets the value of the siglaSindacato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaSindacato(String value) {
        this.siglaSindacato = value;
    }

    /**
     * Gets the value of the denominazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Sets the value of the denominazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Gets the value of the unitaOperativa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitaOperativa() {
        return unitaOperativa;
    }

    /**
     * Sets the value of the unitaOperativa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitaOperativa(String value) {
        this.unitaOperativa = value;
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
     * Gets the value of the annoSettore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoSettore() {
        return annoSettore;
    }

    /**
     * Sets the value of the annoSettore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoSettore(String value) {
        this.annoSettore = value;
    }

}
