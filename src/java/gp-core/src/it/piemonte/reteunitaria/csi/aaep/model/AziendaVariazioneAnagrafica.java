
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AziendaVariazioneAnagrafica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AziendaVariazioneAnagrafica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codici" type="{urn:AAEPCSI}ArrayOfCodiciATECO"/>
 *         &lt;element name="numeroCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceCausaleCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="partitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneTipoAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneStatoAttiv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="provinciaCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idTipoAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="idStatoAttiv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FIscrSezArtigiani" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneCausaleCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrIscrSezArtigiani" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneFontedato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oggettoSociale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="idNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataFineValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="NRegistroImpreseCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataCostituzione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataCessazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="tribunaleCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaVariazAnagrafica" type="{urn:AAEPCSI}ArrayOfVariazioneAnagrafica"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AziendaVariazioneAnagrafica", propOrder = {
    "codici",
    "numeroCCIAA",
    "descrizioneNaturaGiuridica",
    "codiceCausaleCessazione",
    "codiceFiscale",
    "partitaIva",
    "descrizioneTipoAzienda",
    "descrizioneStatoAttiv",
    "provinciaCCIAA",
    "idTipoAzienda",
    "idAzienda",
    "idStatoAttiv",
    "fIscrSezArtigiani",
    "descrizioneCausaleCessazione",
    "descrIscrSezArtigiani",
    "descrizioneFontedato",
    "annoCCIAA",
    "oggettoSociale",
    "idFonteDato",
    "idNaturaGiuridica",
    "dataInizioValidita",
    "dataFineValidita",
    "nRegistroImpreseCCIAA",
    "denominazione",
    "dataCostituzione",
    "dataCessazione",
    "tribunaleCCIAA",
    "listaVariazAnagrafica"
})
public class AziendaVariazioneAnagrafica {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfCodiciATECO codici;
    @XmlElement(required = true, nillable = true)
    protected String numeroCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneNaturaGiuridica;
    @XmlElement(required = true, nillable = true)
    protected String codiceCausaleCessazione;
    @XmlElement(required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(required = true, nillable = true)
    protected String partitaIva;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneTipoAzienda;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneStatoAttiv;
    @XmlElement(required = true, nillable = true)
    protected String provinciaCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String idTipoAzienda;
    protected long idAzienda;
    @XmlElement(required = true, nillable = true)
    protected String idStatoAttiv;
    @XmlElement(name = "FIscrSezArtigiani", required = true, nillable = true)
    protected String fIscrSezArtigiani;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneCausaleCessazione;
    @XmlElement(required = true, nillable = true)
    protected String descrIscrSezArtigiani;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneFontedato;
    @XmlElement(required = true, nillable = true)
    protected String annoCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String oggettoSociale;
    protected long idFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String idNaturaGiuridica;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineValidita;
    @XmlElement(name = "NRegistroImpreseCCIAA", required = true, nillable = true)
    protected String nRegistroImpreseCCIAA;
    @XmlElement(required = true, nillable = true)
    protected String denominazione;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCostituzione;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCessazione;
    @XmlElement(required = true, nillable = true)
    protected String tribunaleCCIAA;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfVariazioneAnagrafica listaVariazAnagrafica;

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
     * Gets the value of the descrizioneTipoAzienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneTipoAzienda() {
        return descrizioneTipoAzienda;
    }

    /**
     * Sets the value of the descrizioneTipoAzienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneTipoAzienda(String value) {
        this.descrizioneTipoAzienda = value;
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
     * Gets the value of the idTipoAzienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTipoAzienda() {
        return idTipoAzienda;
    }

    /**
     * Sets the value of the idTipoAzienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTipoAzienda(String value) {
        this.idTipoAzienda = value;
    }

    /**
     * Gets the value of the idAzienda property.
     * 
     */
    public long getIdAzienda() {
        return idAzienda;
    }

    /**
     * Sets the value of the idAzienda property.
     * 
     */
    public void setIdAzienda(long value) {
        this.idAzienda = value;
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
     * Gets the value of the fIscrSezArtigiani property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIscrSezArtigiani() {
        return fIscrSezArtigiani;
    }

    /**
     * Sets the value of the fIscrSezArtigiani property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIscrSezArtigiani(String value) {
        this.fIscrSezArtigiani = value;
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
     * Gets the value of the descrIscrSezArtigiani property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrIscrSezArtigiani() {
        return descrIscrSezArtigiani;
    }

    /**
     * Sets the value of the descrIscrSezArtigiani property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrIscrSezArtigiani(String value) {
        this.descrIscrSezArtigiani = value;
    }

    /**
     * Gets the value of the descrizioneFontedato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneFontedato() {
        return descrizioneFontedato;
    }

    /**
     * Sets the value of the descrizioneFontedato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneFontedato(String value) {
        this.descrizioneFontedato = value;
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
     * Gets the value of the idFonteDato property.
     * 
     */
    public long getIdFonteDato() {
        return idFonteDato;
    }

    /**
     * Sets the value of the idFonteDato property.
     * 
     */
    public void setIdFonteDato(long value) {
        this.idFonteDato = value;
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
     * Gets the value of the dataInizioValidita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets the value of the dataInizioValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioValidita(XMLGregorianCalendar value) {
        this.dataInizioValidita = value;
    }

    /**
     * Gets the value of the dataFineValidita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets the value of the dataFineValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineValidita(XMLGregorianCalendar value) {
        this.dataFineValidita = value;
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
     * Gets the value of the dataCostituzione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCostituzione() {
        return dataCostituzione;
    }

    /**
     * Sets the value of the dataCostituzione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCostituzione(XMLGregorianCalendar value) {
        this.dataCostituzione = value;
    }

    /**
     * Gets the value of the dataCessazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCessazione() {
        return dataCessazione;
    }

    /**
     * Sets the value of the dataCessazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCessazione(XMLGregorianCalendar value) {
        this.dataCessazione = value;
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
     * Gets the value of the listaVariazAnagrafica property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVariazioneAnagrafica }
     *     
     */
    public ArrayOfVariazioneAnagrafica getListaVariazAnagrafica() {
        return listaVariazAnagrafica;
    }

    /**
     * Sets the value of the listaVariazAnagrafica property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVariazioneAnagrafica }
     *     
     */
    public void setListaVariazAnagrafica(ArrayOfVariazioneAnagrafica value) {
        this.listaVariazAnagrafica = value;
    }

}
