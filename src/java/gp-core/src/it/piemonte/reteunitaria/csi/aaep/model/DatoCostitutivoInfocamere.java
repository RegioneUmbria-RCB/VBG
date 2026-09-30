
package it.piemonte.reteunitaria.csi.aaep.model;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DatoCostitutivoInfocamere complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatoCostitutivoInfocamere">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrProvUffReg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataScadenzaPrimoEsercizio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="scadenzaEsSucc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valAzioniCapitSoc" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dataRegAtto" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="numAzioniCapitSociale" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descrSiglaProvNotaio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codFormaAmministr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoConferim" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFineEsAmmt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="notaio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="capitSocSottoscr" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="totFondoConsortile" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="descrTipoAtto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoAtto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numAnniEsAmmt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="capitSocVersato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="valutaCapitale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codDurataCS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFondazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="flagDurataIllimitata" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoConferim" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numMaxMembriCS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numMembriCSCarica" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dataIniEsAmmt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="totFondoConsortE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valutaCapitSociale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="localitaNotaio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numRegAtto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numSociCarica" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="totQuoteCapitSocE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dataTermineSocieta" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="numSoci" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="siglaProvNotaio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="totQuoteCapitSoc" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dataCostituzione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="capitalesocVers" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="descrFormaAmministr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valoreAzioniCapitSoc" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="numRepertorio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="capitSocDeliberato" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ufficioRegistro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numMinMembriCS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvUffRegistro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="capitaleSocDelib" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatoCostitutivoInfocamere", propOrder = {
    "descrProvUffReg",
    "dataScadenzaPrimoEsercizio",
    "scadenzaEsSucc",
    "valAzioniCapitSoc",
    "dataRegAtto",
    "numAzioniCapitSociale",
    "descrSiglaProvNotaio",
    "codFormaAmministr",
    "descrTipoConferim",
    "dataFineEsAmmt",
    "notaio",
    "capitSocSottoscr",
    "totFondoConsortile",
    "descrTipoAtto",
    "codTipoAtto",
    "numAnniEsAmmt",
    "capitSocVersato",
    "valutaCapitale",
    "codDurataCS",
    "dataFondazione",
    "flagDurataIllimitata",
    "codTipoConferim",
    "numMaxMembriCS",
    "numMembriCSCarica",
    "dataIniEsAmmt",
    "totFondoConsortE",
    "valutaCapitSociale",
    "localitaNotaio",
    "numRegAtto",
    "numSociCarica",
    "totQuoteCapitSocE",
    "dataTermineSocieta",
    "numSoci",
    "siglaProvNotaio",
    "totQuoteCapitSoc",
    "dataCostituzione",
    "capitalesocVers",
    "descrFormaAmministr",
    "valoreAzioniCapitSoc",
    "numRepertorio",
    "capitSocDeliberato",
    "ufficioRegistro",
    "numMinMembriCS",
    "siglaProvUffRegistro",
    "capitaleSocDelib"
})
public class DatoCostitutivoInfocamere {

    @XmlElement(required = true, nillable = true)
    protected String descrProvUffReg;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadenzaPrimoEsercizio;
    @XmlElement(required = true, nillable = true)
    protected String scadenzaEsSucc;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal valAzioniCapitSoc;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRegAtto;
    protected long numAzioniCapitSociale;
    @XmlElement(required = true, nillable = true)
    protected String descrSiglaProvNotaio;
    @XmlElement(required = true, nillable = true)
    protected String codFormaAmministr;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoConferim;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineEsAmmt;
    @XmlElement(required = true, nillable = true)
    protected String notaio;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal capitSocSottoscr;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal totFondoConsortile;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoAtto;
    @XmlElement(required = true, nillable = true)
    protected String codTipoAtto;
    @XmlElement(required = true, nillable = true)
    protected String numAnniEsAmmt;
    protected long capitSocVersato;
    @XmlElement(required = true, nillable = true)
    protected String valutaCapitale;
    @XmlElement(required = true, nillable = true)
    protected String codDurataCS;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFondazione;
    @XmlElement(required = true, nillable = true)
    protected String flagDurataIllimitata;
    @XmlElement(required = true, nillable = true)
    protected String codTipoConferim;
    @XmlElement(required = true, nillable = true)
    protected String numMaxMembriCS;
    protected long numMembriCSCarica;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataIniEsAmmt;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal totFondoConsortE;
    @XmlElement(required = true, nillable = true)
    protected String valutaCapitSociale;
    @XmlElement(required = true, nillable = true)
    protected String localitaNotaio;
    @XmlElement(required = true, nillable = true)
    protected String numRegAtto;
    protected long numSociCarica;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal totQuoteCapitSocE;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataTermineSocieta;
    protected long numSoci;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvNotaio;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal totQuoteCapitSoc;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCostituzione;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal capitalesocVers;
    @XmlElement(required = true, nillable = true)
    protected String descrFormaAmministr;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal valoreAzioniCapitSoc;
    @XmlElement(required = true, nillable = true)
    protected String numRepertorio;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal capitSocDeliberato;
    @XmlElement(required = true, nillable = true)
    protected String ufficioRegistro;
    @XmlElement(required = true, nillable = true)
    protected String numMinMembriCS;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvUffRegistro;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal capitaleSocDelib;

    /**
     * Gets the value of the descrProvUffReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrProvUffReg() {
        return descrProvUffReg;
    }

    /**
     * Sets the value of the descrProvUffReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrProvUffReg(String value) {
        this.descrProvUffReg = value;
    }

    /**
     * Gets the value of the dataScadenzaPrimoEsercizio property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenzaPrimoEsercizio() {
        return dataScadenzaPrimoEsercizio;
    }

    /**
     * Sets the value of the dataScadenzaPrimoEsercizio property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenzaPrimoEsercizio(XMLGregorianCalendar value) {
        this.dataScadenzaPrimoEsercizio = value;
    }

    /**
     * Gets the value of the scadenzaEsSucc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScadenzaEsSucc() {
        return scadenzaEsSucc;
    }

    /**
     * Sets the value of the scadenzaEsSucc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScadenzaEsSucc(String value) {
        this.scadenzaEsSucc = value;
    }

    /**
     * Gets the value of the valAzioniCapitSoc property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValAzioniCapitSoc() {
        return valAzioniCapitSoc;
    }

    /**
     * Sets the value of the valAzioniCapitSoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValAzioniCapitSoc(BigDecimal value) {
        this.valAzioniCapitSoc = value;
    }

    /**
     * Gets the value of the dataRegAtto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegAtto() {
        return dataRegAtto;
    }

    /**
     * Sets the value of the dataRegAtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegAtto(XMLGregorianCalendar value) {
        this.dataRegAtto = value;
    }

    /**
     * Gets the value of the numAzioniCapitSociale property.
     * 
     */
    public long getNumAzioniCapitSociale() {
        return numAzioniCapitSociale;
    }

    /**
     * Sets the value of the numAzioniCapitSociale property.
     * 
     */
    public void setNumAzioniCapitSociale(long value) {
        this.numAzioniCapitSociale = value;
    }

    /**
     * Gets the value of the descrSiglaProvNotaio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrSiglaProvNotaio() {
        return descrSiglaProvNotaio;
    }

    /**
     * Sets the value of the descrSiglaProvNotaio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrSiglaProvNotaio(String value) {
        this.descrSiglaProvNotaio = value;
    }

    /**
     * Gets the value of the codFormaAmministr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodFormaAmministr() {
        return codFormaAmministr;
    }

    /**
     * Sets the value of the codFormaAmministr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodFormaAmministr(String value) {
        this.codFormaAmministr = value;
    }

    /**
     * Gets the value of the descrTipoConferim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoConferim() {
        return descrTipoConferim;
    }

    /**
     * Sets the value of the descrTipoConferim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoConferim(String value) {
        this.descrTipoConferim = value;
    }

    /**
     * Gets the value of the dataFineEsAmmt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineEsAmmt() {
        return dataFineEsAmmt;
    }

    /**
     * Sets the value of the dataFineEsAmmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineEsAmmt(XMLGregorianCalendar value) {
        this.dataFineEsAmmt = value;
    }

    /**
     * Gets the value of the notaio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotaio() {
        return notaio;
    }

    /**
     * Sets the value of the notaio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotaio(String value) {
        this.notaio = value;
    }

    /**
     * Gets the value of the capitSocSottoscr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCapitSocSottoscr() {
        return capitSocSottoscr;
    }

    /**
     * Sets the value of the capitSocSottoscr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCapitSocSottoscr(BigDecimal value) {
        this.capitSocSottoscr = value;
    }

    /**
     * Gets the value of the totFondoConsortile property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotFondoConsortile() {
        return totFondoConsortile;
    }

    /**
     * Sets the value of the totFondoConsortile property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotFondoConsortile(BigDecimal value) {
        this.totFondoConsortile = value;
    }

    /**
     * Gets the value of the descrTipoAtto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoAtto() {
        return descrTipoAtto;
    }

    /**
     * Sets the value of the descrTipoAtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoAtto(String value) {
        this.descrTipoAtto = value;
    }

    /**
     * Gets the value of the codTipoAtto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoAtto() {
        return codTipoAtto;
    }

    /**
     * Sets the value of the codTipoAtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoAtto(String value) {
        this.codTipoAtto = value;
    }

    /**
     * Gets the value of the numAnniEsAmmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumAnniEsAmmt() {
        return numAnniEsAmmt;
    }

    /**
     * Sets the value of the numAnniEsAmmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumAnniEsAmmt(String value) {
        this.numAnniEsAmmt = value;
    }

    /**
     * Gets the value of the capitSocVersato property.
     * 
     */
    public long getCapitSocVersato() {
        return capitSocVersato;
    }

    /**
     * Sets the value of the capitSocVersato property.
     * 
     */
    public void setCapitSocVersato(long value) {
        this.capitSocVersato = value;
    }

    /**
     * Gets the value of the valutaCapitale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValutaCapitale() {
        return valutaCapitale;
    }

    /**
     * Sets the value of the valutaCapitale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValutaCapitale(String value) {
        this.valutaCapitale = value;
    }

    /**
     * Gets the value of the codDurataCS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDurataCS() {
        return codDurataCS;
    }

    /**
     * Sets the value of the codDurataCS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDurataCS(String value) {
        this.codDurataCS = value;
    }

    /**
     * Gets the value of the dataFondazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFondazione() {
        return dataFondazione;
    }

    /**
     * Sets the value of the dataFondazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFondazione(XMLGregorianCalendar value) {
        this.dataFondazione = value;
    }

    /**
     * Gets the value of the flagDurataIllimitata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagDurataIllimitata() {
        return flagDurataIllimitata;
    }

    /**
     * Sets the value of the flagDurataIllimitata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagDurataIllimitata(String value) {
        this.flagDurataIllimitata = value;
    }

    /**
     * Gets the value of the codTipoConferim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoConferim() {
        return codTipoConferim;
    }

    /**
     * Sets the value of the codTipoConferim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoConferim(String value) {
        this.codTipoConferim = value;
    }

    /**
     * Gets the value of the numMaxMembriCS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumMaxMembriCS() {
        return numMaxMembriCS;
    }

    /**
     * Sets the value of the numMaxMembriCS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumMaxMembriCS(String value) {
        this.numMaxMembriCS = value;
    }

    /**
     * Gets the value of the numMembriCSCarica property.
     * 
     */
    public long getNumMembriCSCarica() {
        return numMembriCSCarica;
    }

    /**
     * Sets the value of the numMembriCSCarica property.
     * 
     */
    public void setNumMembriCSCarica(long value) {
        this.numMembriCSCarica = value;
    }

    /**
     * Gets the value of the dataIniEsAmmt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataIniEsAmmt() {
        return dataIniEsAmmt;
    }

    /**
     * Sets the value of the dataIniEsAmmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataIniEsAmmt(XMLGregorianCalendar value) {
        this.dataIniEsAmmt = value;
    }

    /**
     * Gets the value of the totFondoConsortE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotFondoConsortE() {
        return totFondoConsortE;
    }

    /**
     * Sets the value of the totFondoConsortE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotFondoConsortE(BigDecimal value) {
        this.totFondoConsortE = value;
    }

    /**
     * Gets the value of the valutaCapitSociale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValutaCapitSociale() {
        return valutaCapitSociale;
    }

    /**
     * Sets the value of the valutaCapitSociale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValutaCapitSociale(String value) {
        this.valutaCapitSociale = value;
    }

    /**
     * Gets the value of the localitaNotaio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalitaNotaio() {
        return localitaNotaio;
    }

    /**
     * Sets the value of the localitaNotaio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalitaNotaio(String value) {
        this.localitaNotaio = value;
    }

    /**
     * Gets the value of the numRegAtto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumRegAtto() {
        return numRegAtto;
    }

    /**
     * Sets the value of the numRegAtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumRegAtto(String value) {
        this.numRegAtto = value;
    }

    /**
     * Gets the value of the numSociCarica property.
     * 
     */
    public long getNumSociCarica() {
        return numSociCarica;
    }

    /**
     * Sets the value of the numSociCarica property.
     * 
     */
    public void setNumSociCarica(long value) {
        this.numSociCarica = value;
    }

    /**
     * Gets the value of the totQuoteCapitSocE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotQuoteCapitSocE() {
        return totQuoteCapitSocE;
    }

    /**
     * Sets the value of the totQuoteCapitSocE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotQuoteCapitSocE(BigDecimal value) {
        this.totQuoteCapitSocE = value;
    }

    /**
     * Gets the value of the dataTermineSocieta property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataTermineSocieta() {
        return dataTermineSocieta;
    }

    /**
     * Sets the value of the dataTermineSocieta property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataTermineSocieta(XMLGregorianCalendar value) {
        this.dataTermineSocieta = value;
    }

    /**
     * Gets the value of the numSoci property.
     * 
     */
    public long getNumSoci() {
        return numSoci;
    }

    /**
     * Sets the value of the numSoci property.
     * 
     */
    public void setNumSoci(long value) {
        this.numSoci = value;
    }

    /**
     * Gets the value of the siglaProvNotaio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvNotaio() {
        return siglaProvNotaio;
    }

    /**
     * Sets the value of the siglaProvNotaio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvNotaio(String value) {
        this.siglaProvNotaio = value;
    }

    /**
     * Gets the value of the totQuoteCapitSoc property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotQuoteCapitSoc() {
        return totQuoteCapitSoc;
    }

    /**
     * Sets the value of the totQuoteCapitSoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotQuoteCapitSoc(BigDecimal value) {
        this.totQuoteCapitSoc = value;
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
     * Gets the value of the capitalesocVers property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCapitalesocVers() {
        return capitalesocVers;
    }

    /**
     * Sets the value of the capitalesocVers property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCapitalesocVers(BigDecimal value) {
        this.capitalesocVers = value;
    }

    /**
     * Gets the value of the descrFormaAmministr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrFormaAmministr() {
        return descrFormaAmministr;
    }

    /**
     * Sets the value of the descrFormaAmministr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrFormaAmministr(String value) {
        this.descrFormaAmministr = value;
    }

    /**
     * Gets the value of the valoreAzioniCapitSoc property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValoreAzioniCapitSoc() {
        return valoreAzioniCapitSoc;
    }

    /**
     * Sets the value of the valoreAzioniCapitSoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValoreAzioniCapitSoc(BigDecimal value) {
        this.valoreAzioniCapitSoc = value;
    }

    /**
     * Gets the value of the numRepertorio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumRepertorio() {
        return numRepertorio;
    }

    /**
     * Sets the value of the numRepertorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumRepertorio(String value) {
        this.numRepertorio = value;
    }

    /**
     * Gets the value of the capitSocDeliberato property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCapitSocDeliberato() {
        return capitSocDeliberato;
    }

    /**
     * Sets the value of the capitSocDeliberato property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCapitSocDeliberato(BigDecimal value) {
        this.capitSocDeliberato = value;
    }

    /**
     * Gets the value of the ufficioRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUfficioRegistro() {
        return ufficioRegistro;
    }

    /**
     * Sets the value of the ufficioRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUfficioRegistro(String value) {
        this.ufficioRegistro = value;
    }

    /**
     * Gets the value of the numMinMembriCS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumMinMembriCS() {
        return numMinMembriCS;
    }

    /**
     * Sets the value of the numMinMembriCS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumMinMembriCS(String value) {
        this.numMinMembriCS = value;
    }

    /**
     * Gets the value of the siglaProvUffRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvUffRegistro() {
        return siglaProvUffRegistro;
    }

    /**
     * Sets the value of the siglaProvUffRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvUffRegistro(String value) {
        this.siglaProvUffRegistro = value;
    }

    /**
     * Gets the value of the capitaleSocDelib property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCapitaleSocDelib() {
        return capitaleSocDelib;
    }

    /**
     * Sets the value of the capitaleSocDelib property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCapitaleSocDelib(BigDecimal value) {
        this.capitaleSocDelib = value;
    }

}
