
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PersonaRIInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonaRIInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrTipoPersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicPoteriFirma" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EMail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numRISocParif" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codComuneSedeSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codComuneRes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codFiscaleSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComuneRes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrStatoSedeSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="siglaProvResidenza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codCittadinanza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaCCIAASocParif" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrToponimoResid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denominazSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvNascita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrToponimoSedeSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrStatoNascita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="altreIndicazResid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="viaSedeSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="viaResidenza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoPersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvSedeSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numCivicoResid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComuneNascita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataCostSP" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="quotaPartecipaz" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codStatoSedeSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codLimitazCapacitaAgire" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPazienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="progrPersona" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="progrOrdineVisura" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codStatoNascita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codToponimoResid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numTelefono" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="capSedeSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flagElettore" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codToponimoSedeSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codComuneNascita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrIndicatoriPotereF" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="capResidenza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrFlagElettore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="altreInfoSedeSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrStatoRes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numIscrReaSocParif" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComuneSedeSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numCivicoSedeSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="progrUnitaLocale" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codStatoRes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="quotaPartecipazEuro" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="valutaPartecip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrFrazioneRes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaCaricaPersInfoc" type="{urn:AAEPCSI}ArrayOfCaricaPersonaInfoc"/>
 *         &lt;element name="listaFallPersInfoc" type="{urn:AAEPCSI}ArrayOfFallimentoPersonaInfoc"/>
 *         &lt;element name="percentPartecip" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="sesso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrCittadinanza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="frazioneSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaRIInfoc", propOrder = {
    "descrTipoPersona",
    "indicPoteriFirma",
    "cognome",
    "eMail",
    "numRISocParif",
    "codComuneSedeSP",
    "codComuneRes",
    "idAAEPFonteDato",
    "codFiscaleSP",
    "descrComuneRes",
    "descrStatoSedeSP",
    "dataNascita",
    "siglaProvResidenza",
    "codCittadinanza",
    "fax",
    "siglaCCIAASocParif",
    "descrToponimoResid",
    "denominazSP",
    "siglaProvNascita",
    "descrToponimoSedeSP",
    "descrStatoNascita",
    "altreIndicazResid",
    "viaSedeSP",
    "viaResidenza",
    "tipoPersona",
    "siglaProvSedeSP",
    "numCivicoResid",
    "descrComuneNascita",
    "dataCostSP",
    "quotaPartecipaz",
    "codStatoSedeSP",
    "codLimitazCapacitaAgire",
    "idAAEPazienda",
    "progrPersona",
    "progrOrdineVisura",
    "codStatoNascita",
    "codToponimoResid",
    "numTelefono",
    "capSedeSP",
    "nome",
    "flagElettore",
    "codiceFiscale",
    "codToponimoSedeSP",
    "codComuneNascita",
    "descrIndicatoriPotereF",
    "capResidenza",
    "descrFlagElettore",
    "altreInfoSedeSP",
    "descrStatoRes",
    "numIscrReaSocParif",
    "descrComuneSedeSP",
    "numCivicoSedeSP",
    "progrUnitaLocale",
    "codStatoRes",
    "quotaPartecipazEuro",
    "valutaPartecip",
    "descrFrazioneRes",
    "listaCaricaPersInfoc",
    "listaFallPersInfoc",
    "percentPartecip",
    "sesso",
    "descrCittadinanza",
    "frazioneSP"
})
public class PersonaRIInfoc {

    @XmlElement(required = true, nillable = true)
    protected String descrTipoPersona;
    protected long indicPoteriFirma;
    @XmlElement(required = true, nillable = true)
    protected String cognome;
    @XmlElement(name = "EMail", required = true, nillable = true)
    protected String eMail;
    @XmlElement(required = true, nillable = true)
    protected String numRISocParif;
    @XmlElement(required = true, nillable = true)
    protected String codComuneSedeSP;
    @XmlElement(required = true, nillable = true)
    protected String codComuneRes;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String codFiscaleSP;
    @XmlElement(required = true, nillable = true)
    protected String descrComuneRes;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoSedeSP;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNascita;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvResidenza;
    @XmlElement(required = true, nillable = true)
    protected String codCittadinanza;
    @XmlElement(required = true, nillable = true)
    protected String fax;
    @XmlElement(required = true, nillable = true)
    protected String siglaCCIAASocParif;
    @XmlElement(required = true, nillable = true)
    protected String descrToponimoResid;
    @XmlElement(required = true, nillable = true)
    protected String denominazSP;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvNascita;
    @XmlElement(required = true, nillable = true)
    protected String descrToponimoSedeSP;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoNascita;
    @XmlElement(required = true, nillable = true)
    protected String altreIndicazResid;
    @XmlElement(required = true, nillable = true)
    protected String viaSedeSP;
    @XmlElement(required = true, nillable = true)
    protected String viaResidenza;
    @XmlElement(required = true, nillable = true)
    protected String tipoPersona;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvSedeSP;
    @XmlElement(required = true, nillable = true)
    protected String numCivicoResid;
    @XmlElement(required = true, nillable = true)
    protected String descrComuneNascita;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCostSP;
    protected long quotaPartecipaz;
    @XmlElement(required = true, nillable = true)
    protected String codStatoSedeSP;
    @XmlElement(required = true, nillable = true)
    protected String codLimitazCapacitaAgire;
    protected long idAAEPazienda;
    protected long progrPersona;
    protected long progrOrdineVisura;
    @XmlElement(required = true, nillable = true)
    protected String codStatoNascita;
    @XmlElement(required = true, nillable = true)
    protected String codToponimoResid;
    @XmlElement(required = true, nillable = true)
    protected String numTelefono;
    @XmlElement(required = true, nillable = true)
    protected String capSedeSP;
    @XmlElement(required = true, nillable = true)
    protected String nome;
    protected long flagElettore;
    @XmlElement(required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(required = true, nillable = true)
    protected String codToponimoSedeSP;
    @XmlElement(required = true, nillable = true)
    protected String codComuneNascita;
    @XmlElement(required = true, nillable = true)
    protected String descrIndicatoriPotereF;
    @XmlElement(required = true, nillable = true)
    protected String capResidenza;
    @XmlElement(required = true, nillable = true)
    protected String descrFlagElettore;
    @XmlElement(required = true, nillable = true)
    protected String altreInfoSedeSP;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoRes;
    @XmlElement(required = true, nillable = true)
    protected String numIscrReaSocParif;
    @XmlElement(required = true, nillable = true)
    protected String descrComuneSedeSP;
    @XmlElement(required = true, nillable = true)
    protected String numCivicoSedeSP;
    protected long progrUnitaLocale;
    @XmlElement(required = true, nillable = true)
    protected String codStatoRes;
    protected long quotaPartecipazEuro;
    @XmlElement(required = true, nillable = true)
    protected String valutaPartecip;
    @XmlElement(required = true, nillable = true)
    protected String descrFrazioneRes;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfCaricaPersonaInfoc listaCaricaPersInfoc;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfFallimentoPersonaInfoc listaFallPersInfoc;
    protected long percentPartecip;
    @XmlElement(required = true, nillable = true)
    protected String sesso;
    @XmlElement(required = true, nillable = true)
    protected String descrCittadinanza;
    @XmlElement(required = true, nillable = true)
    protected String frazioneSP;

    /**
     * Gets the value of the descrTipoPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoPersona() {
        return descrTipoPersona;
    }

    /**
     * Sets the value of the descrTipoPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoPersona(String value) {
        this.descrTipoPersona = value;
    }

    /**
     * Gets the value of the indicPoteriFirma property.
     * 
     */
    public long getIndicPoteriFirma() {
        return indicPoteriFirma;
    }

    /**
     * Sets the value of the indicPoteriFirma property.
     * 
     */
    public void setIndicPoteriFirma(long value) {
        this.indicPoteriFirma = value;
    }

    /**
     * Gets the value of the cognome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets the value of the cognome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Gets the value of the eMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMail() {
        return eMail;
    }

    /**
     * Sets the value of the eMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMail(String value) {
        this.eMail = value;
    }

    /**
     * Gets the value of the numRISocParif property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumRISocParif() {
        return numRISocParif;
    }

    /**
     * Sets the value of the numRISocParif property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumRISocParif(String value) {
        this.numRISocParif = value;
    }

    /**
     * Gets the value of the codComuneSedeSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodComuneSedeSP() {
        return codComuneSedeSP;
    }

    /**
     * Sets the value of the codComuneSedeSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodComuneSedeSP(String value) {
        this.codComuneSedeSP = value;
    }

    /**
     * Gets the value of the codComuneRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodComuneRes() {
        return codComuneRes;
    }

    /**
     * Sets the value of the codComuneRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodComuneRes(String value) {
        this.codComuneRes = value;
    }

    /**
     * Gets the value of the idAAEPFonteDato property.
     * 
     */
    public long getIdAAEPFonteDato() {
        return idAAEPFonteDato;
    }

    /**
     * Sets the value of the idAAEPFonteDato property.
     * 
     */
    public void setIdAAEPFonteDato(long value) {
        this.idAAEPFonteDato = value;
    }

    /**
     * Gets the value of the codFiscaleSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodFiscaleSP() {
        return codFiscaleSP;
    }

    /**
     * Sets the value of the codFiscaleSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodFiscaleSP(String value) {
        this.codFiscaleSP = value;
    }

    /**
     * Gets the value of the descrComuneRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrComuneRes() {
        return descrComuneRes;
    }

    /**
     * Sets the value of the descrComuneRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrComuneRes(String value) {
        this.descrComuneRes = value;
    }

    /**
     * Gets the value of the descrStatoSedeSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrStatoSedeSP() {
        return descrStatoSedeSP;
    }

    /**
     * Sets the value of the descrStatoSedeSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrStatoSedeSP(String value) {
        this.descrStatoSedeSP = value;
    }

    /**
     * Gets the value of the dataNascita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNascita() {
        return dataNascita;
    }

    /**
     * Sets the value of the dataNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNascita(XMLGregorianCalendar value) {
        this.dataNascita = value;
    }

    /**
     * Gets the value of the siglaProvResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvResidenza() {
        return siglaProvResidenza;
    }

    /**
     * Sets the value of the siglaProvResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvResidenza(String value) {
        this.siglaProvResidenza = value;
    }

    /**
     * Gets the value of the codCittadinanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCittadinanza() {
        return codCittadinanza;
    }

    /**
     * Sets the value of the codCittadinanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCittadinanza(String value) {
        this.codCittadinanza = value;
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
     * Gets the value of the siglaCCIAASocParif property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaCCIAASocParif() {
        return siglaCCIAASocParif;
    }

    /**
     * Sets the value of the siglaCCIAASocParif property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaCCIAASocParif(String value) {
        this.siglaCCIAASocParif = value;
    }

    /**
     * Gets the value of the descrToponimoResid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrToponimoResid() {
        return descrToponimoResid;
    }

    /**
     * Sets the value of the descrToponimoResid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrToponimoResid(String value) {
        this.descrToponimoResid = value;
    }

    /**
     * Gets the value of the denominazSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazSP() {
        return denominazSP;
    }

    /**
     * Sets the value of the denominazSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazSP(String value) {
        this.denominazSP = value;
    }

    /**
     * Gets the value of the siglaProvNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvNascita() {
        return siglaProvNascita;
    }

    /**
     * Sets the value of the siglaProvNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvNascita(String value) {
        this.siglaProvNascita = value;
    }

    /**
     * Gets the value of the descrToponimoSedeSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrToponimoSedeSP() {
        return descrToponimoSedeSP;
    }

    /**
     * Sets the value of the descrToponimoSedeSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrToponimoSedeSP(String value) {
        this.descrToponimoSedeSP = value;
    }

    /**
     * Gets the value of the descrStatoNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrStatoNascita() {
        return descrStatoNascita;
    }

    /**
     * Sets the value of the descrStatoNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrStatoNascita(String value) {
        this.descrStatoNascita = value;
    }

    /**
     * Gets the value of the altreIndicazResid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltreIndicazResid() {
        return altreIndicazResid;
    }

    /**
     * Sets the value of the altreIndicazResid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltreIndicazResid(String value) {
        this.altreIndicazResid = value;
    }

    /**
     * Gets the value of the viaSedeSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViaSedeSP() {
        return viaSedeSP;
    }

    /**
     * Sets the value of the viaSedeSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViaSedeSP(String value) {
        this.viaSedeSP = value;
    }

    /**
     * Gets the value of the viaResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViaResidenza() {
        return viaResidenza;
    }

    /**
     * Sets the value of the viaResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViaResidenza(String value) {
        this.viaResidenza = value;
    }

    /**
     * Gets the value of the tipoPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Sets the value of the tipoPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPersona(String value) {
        this.tipoPersona = value;
    }

    /**
     * Gets the value of the siglaProvSedeSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvSedeSP() {
        return siglaProvSedeSP;
    }

    /**
     * Sets the value of the siglaProvSedeSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvSedeSP(String value) {
        this.siglaProvSedeSP = value;
    }

    /**
     * Gets the value of the numCivicoResid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumCivicoResid() {
        return numCivicoResid;
    }

    /**
     * Sets the value of the numCivicoResid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumCivicoResid(String value) {
        this.numCivicoResid = value;
    }

    /**
     * Gets the value of the descrComuneNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrComuneNascita() {
        return descrComuneNascita;
    }

    /**
     * Sets the value of the descrComuneNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrComuneNascita(String value) {
        this.descrComuneNascita = value;
    }

    /**
     * Gets the value of the dataCostSP property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCostSP() {
        return dataCostSP;
    }

    /**
     * Sets the value of the dataCostSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCostSP(XMLGregorianCalendar value) {
        this.dataCostSP = value;
    }

    /**
     * Gets the value of the quotaPartecipaz property.
     * 
     */
    public long getQuotaPartecipaz() {
        return quotaPartecipaz;
    }

    /**
     * Sets the value of the quotaPartecipaz property.
     * 
     */
    public void setQuotaPartecipaz(long value) {
        this.quotaPartecipaz = value;
    }

    /**
     * Gets the value of the codStatoSedeSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodStatoSedeSP() {
        return codStatoSedeSP;
    }

    /**
     * Sets the value of the codStatoSedeSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodStatoSedeSP(String value) {
        this.codStatoSedeSP = value;
    }

    /**
     * Gets the value of the codLimitazCapacitaAgire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodLimitazCapacitaAgire() {
        return codLimitazCapacitaAgire;
    }

    /**
     * Sets the value of the codLimitazCapacitaAgire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodLimitazCapacitaAgire(String value) {
        this.codLimitazCapacitaAgire = value;
    }

    /**
     * Gets the value of the idAAEPazienda property.
     * 
     */
    public long getIdAAEPazienda() {
        return idAAEPazienda;
    }

    /**
     * Sets the value of the idAAEPazienda property.
     * 
     */
    public void setIdAAEPazienda(long value) {
        this.idAAEPazienda = value;
    }

    /**
     * Gets the value of the progrPersona property.
     * 
     */
    public long getProgrPersona() {
        return progrPersona;
    }

    /**
     * Sets the value of the progrPersona property.
     * 
     */
    public void setProgrPersona(long value) {
        this.progrPersona = value;
    }

    /**
     * Gets the value of the progrOrdineVisura property.
     * 
     */
    public long getProgrOrdineVisura() {
        return progrOrdineVisura;
    }

    /**
     * Sets the value of the progrOrdineVisura property.
     * 
     */
    public void setProgrOrdineVisura(long value) {
        this.progrOrdineVisura = value;
    }

    /**
     * Gets the value of the codStatoNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodStatoNascita() {
        return codStatoNascita;
    }

    /**
     * Sets the value of the codStatoNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodStatoNascita(String value) {
        this.codStatoNascita = value;
    }

    /**
     * Gets the value of the codToponimoResid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodToponimoResid() {
        return codToponimoResid;
    }

    /**
     * Sets the value of the codToponimoResid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodToponimoResid(String value) {
        this.codToponimoResid = value;
    }

    /**
     * Gets the value of the numTelefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTelefono() {
        return numTelefono;
    }

    /**
     * Sets the value of the numTelefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTelefono(String value) {
        this.numTelefono = value;
    }

    /**
     * Gets the value of the capSedeSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapSedeSP() {
        return capSedeSP;
    }

    /**
     * Sets the value of the capSedeSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapSedeSP(String value) {
        this.capSedeSP = value;
    }

    /**
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Gets the value of the flagElettore property.
     * 
     */
    public long getFlagElettore() {
        return flagElettore;
    }

    /**
     * Sets the value of the flagElettore property.
     * 
     */
    public void setFlagElettore(long value) {
        this.flagElettore = value;
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
     * Gets the value of the codToponimoSedeSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodToponimoSedeSP() {
        return codToponimoSedeSP;
    }

    /**
     * Sets the value of the codToponimoSedeSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodToponimoSedeSP(String value) {
        this.codToponimoSedeSP = value;
    }

    /**
     * Gets the value of the codComuneNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodComuneNascita() {
        return codComuneNascita;
    }

    /**
     * Sets the value of the codComuneNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodComuneNascita(String value) {
        this.codComuneNascita = value;
    }

    /**
     * Gets the value of the descrIndicatoriPotereF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrIndicatoriPotereF() {
        return descrIndicatoriPotereF;
    }

    /**
     * Sets the value of the descrIndicatoriPotereF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrIndicatoriPotereF(String value) {
        this.descrIndicatoriPotereF = value;
    }

    /**
     * Gets the value of the capResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapResidenza() {
        return capResidenza;
    }

    /**
     * Sets the value of the capResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapResidenza(String value) {
        this.capResidenza = value;
    }

    /**
     * Gets the value of the descrFlagElettore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrFlagElettore() {
        return descrFlagElettore;
    }

    /**
     * Sets the value of the descrFlagElettore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrFlagElettore(String value) {
        this.descrFlagElettore = value;
    }

    /**
     * Gets the value of the altreInfoSedeSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltreInfoSedeSP() {
        return altreInfoSedeSP;
    }

    /**
     * Sets the value of the altreInfoSedeSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltreInfoSedeSP(String value) {
        this.altreInfoSedeSP = value;
    }

    /**
     * Gets the value of the descrStatoRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrStatoRes() {
        return descrStatoRes;
    }

    /**
     * Sets the value of the descrStatoRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrStatoRes(String value) {
        this.descrStatoRes = value;
    }

    /**
     * Gets the value of the numIscrReaSocParif property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumIscrReaSocParif() {
        return numIscrReaSocParif;
    }

    /**
     * Sets the value of the numIscrReaSocParif property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumIscrReaSocParif(String value) {
        this.numIscrReaSocParif = value;
    }

    /**
     * Gets the value of the descrComuneSedeSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrComuneSedeSP() {
        return descrComuneSedeSP;
    }

    /**
     * Sets the value of the descrComuneSedeSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrComuneSedeSP(String value) {
        this.descrComuneSedeSP = value;
    }

    /**
     * Gets the value of the numCivicoSedeSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumCivicoSedeSP() {
        return numCivicoSedeSP;
    }

    /**
     * Sets the value of the numCivicoSedeSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumCivicoSedeSP(String value) {
        this.numCivicoSedeSP = value;
    }

    /**
     * Gets the value of the progrUnitaLocale property.
     * 
     */
    public long getProgrUnitaLocale() {
        return progrUnitaLocale;
    }

    /**
     * Sets the value of the progrUnitaLocale property.
     * 
     */
    public void setProgrUnitaLocale(long value) {
        this.progrUnitaLocale = value;
    }

    /**
     * Gets the value of the codStatoRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodStatoRes() {
        return codStatoRes;
    }

    /**
     * Sets the value of the codStatoRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodStatoRes(String value) {
        this.codStatoRes = value;
    }

    /**
     * Gets the value of the quotaPartecipazEuro property.
     * 
     */
    public long getQuotaPartecipazEuro() {
        return quotaPartecipazEuro;
    }

    /**
     * Sets the value of the quotaPartecipazEuro property.
     * 
     */
    public void setQuotaPartecipazEuro(long value) {
        this.quotaPartecipazEuro = value;
    }

    /**
     * Gets the value of the valutaPartecip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValutaPartecip() {
        return valutaPartecip;
    }

    /**
     * Sets the value of the valutaPartecip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValutaPartecip(String value) {
        this.valutaPartecip = value;
    }

    /**
     * Gets the value of the descrFrazioneRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrFrazioneRes() {
        return descrFrazioneRes;
    }

    /**
     * Sets the value of the descrFrazioneRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrFrazioneRes(String value) {
        this.descrFrazioneRes = value;
    }

    /**
     * Gets the value of the listaCaricaPersInfoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCaricaPersonaInfoc }
     *     
     */
    public ArrayOfCaricaPersonaInfoc getListaCaricaPersInfoc() {
        return listaCaricaPersInfoc;
    }

    /**
     * Sets the value of the listaCaricaPersInfoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCaricaPersonaInfoc }
     *     
     */
    public void setListaCaricaPersInfoc(ArrayOfCaricaPersonaInfoc value) {
        this.listaCaricaPersInfoc = value;
    }

    /**
     * Gets the value of the listaFallPersInfoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFallimentoPersonaInfoc }
     *     
     */
    public ArrayOfFallimentoPersonaInfoc getListaFallPersInfoc() {
        return listaFallPersInfoc;
    }

    /**
     * Sets the value of the listaFallPersInfoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFallimentoPersonaInfoc }
     *     
     */
    public void setListaFallPersInfoc(ArrayOfFallimentoPersonaInfoc value) {
        this.listaFallPersInfoc = value;
    }

    /**
     * Gets the value of the percentPartecip property.
     * 
     */
    public long getPercentPartecip() {
        return percentPartecip;
    }

    /**
     * Sets the value of the percentPartecip property.
     * 
     */
    public void setPercentPartecip(long value) {
        this.percentPartecip = value;
    }

    /**
     * Gets the value of the sesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * Sets the value of the sesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSesso(String value) {
        this.sesso = value;
    }

    /**
     * Gets the value of the descrCittadinanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCittadinanza() {
        return descrCittadinanza;
    }

    /**
     * Sets the value of the descrCittadinanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCittadinanza(String value) {
        this.descrCittadinanza = value;
    }

    /**
     * Gets the value of the frazioneSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrazioneSP() {
        return frazioneSP;
    }

    /**
     * Sets the value of the frazioneSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrazioneSP(String value) {
        this.frazioneSP = value;
    }

}
