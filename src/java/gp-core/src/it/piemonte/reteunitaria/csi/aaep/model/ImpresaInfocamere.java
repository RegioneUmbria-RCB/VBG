
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImpresaInfocamere complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImpresaInfocamere">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indicTrasfSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numIscrizRea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicStatoAttiv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="partitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataUltimoAggiorn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrCausCessaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrIterIscrAlboArt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataDenunciaCessaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrIndicTrasfSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataCessazFunSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numAddettiFam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ragioneSociale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrCausCessazFunSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataCessaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="datoCostitutivoInfocamere" type="{urn:AAEPCSI}DatoCostitutivoInfocamere"/>
 *         &lt;element name="dataInizioAtt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaPersoneRIInfoc" type="{urn:AAEPCSI}ArrayOfListaPersoneRIInfoc"/>
 *         &lt;element name="annoDenunciaAddetti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaProcConcorsInfoc" type="{urn:AAEPCSI}ArrayOfProcConcorsInfoc"/>
 *         &lt;element name="codFonte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvReaSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvRea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codCausCessaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgAggiornamento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataIscrizioneRea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="localizzazPiemonte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataCancellazRea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataIscrRegistroImpr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="provinciaIscrAlboArtigiano" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataDlbIscrAlboArtigiano" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numIscrizReaSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numAddettiSubord" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="impresaCessata" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrIndicStatoAttiv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrFonte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroIscrAlboArtigiano" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaSezSpecInfoc" type="{urn:AAEPCSI}ArrayOfSezSpecInfoc"/>
 *         &lt;element name="idAAEPFonteDatoSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numRegistroImpr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codCausCessazFunSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaOggSocialeInfoc" type="{urn:AAEPCSI}ArrayOfOggSocialeInfoc"/>
 *         &lt;element name="flgIterIscrAlboArt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="progrSedeSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataUltimoAggRi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAziendaSL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaSediInfoc" type="{urn:AAEPCSI}ArrayOfListaSediInfoc"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImpresaInfocamere", propOrder = {
    "indicTrasfSede",
    "numIscrizRea",
    "indicStatoAttiv",
    "partitaIva",
    "codNaturaGiuridica",
    "dataUltimoAggiorn",
    "descrCausCessaz",
    "descrIterIscrAlboArt",
    "dataDenunciaCessaz",
    "descrIndicTrasfSede",
    "dataCessazFunSede",
    "numAddettiFam",
    "descrNaturaGiuridica",
    "ragioneSociale",
    "descrCausCessazFunSede",
    "dataCessaz",
    "datoCostitutivoInfocamere",
    "dataInizioAtt",
    "listaPersoneRIInfoc",
    "annoDenunciaAddetti",
    "listaProcConcorsInfoc",
    "codFonte",
    "siglaProvReaSede",
    "siglaProvRea",
    "codCausCessaz",
    "flgAggiornamento",
    "dataIscrizioneRea",
    "localizzazPiemonte",
    "dataCancellazRea",
    "dataIscrRegistroImpr",
    "provinciaIscrAlboArtigiano",
    "dataDlbIscrAlboArtigiano",
    "numIscrizReaSede",
    "numAddettiSubord",
    "impresaCessata",
    "descrIndicStatoAttiv",
    "descrFonte",
    "numeroIscrAlboArtigiano",
    "listaSezSpecInfoc",
    "idAAEPFonteDatoSL",
    "codiceFiscale",
    "numRegistroImpr",
    "codCausCessazFunSede",
    "listaOggSocialeInfoc",
    "flgIterIscrAlboArt",
    "progrSedeSL",
    "dataUltimoAggRi",
    "idAAEPAziendaSL",
    "listaSediInfoc"
})
public class ImpresaInfocamere {

    @XmlElement(required = true, nillable = true)
    protected String indicTrasfSede;
    @XmlElement(required = true, nillable = true)
    protected String numIscrizRea;
    @XmlElement(required = true, nillable = true)
    protected String indicStatoAttiv;
    @XmlElement(required = true, nillable = true)
    protected String partitaIva;
    @XmlElement(required = true, nillable = true)
    protected String codNaturaGiuridica;
    @XmlElement(required = true, nillable = true)
    protected String dataUltimoAggiorn;
    @XmlElement(required = true, nillable = true)
    protected String descrCausCessaz;
    @XmlElement(required = true, nillable = true)
    protected String descrIterIscrAlboArt;
    @XmlElement(required = true, nillable = true)
    protected String dataDenunciaCessaz;
    @XmlElement(required = true, nillable = true)
    protected String descrIndicTrasfSede;
    @XmlElement(required = true, nillable = true)
    protected String dataCessazFunSede;
    @XmlElement(required = true, nillable = true)
    protected String numAddettiFam;
    @XmlElement(required = true, nillable = true)
    protected String descrNaturaGiuridica;
    @XmlElement(required = true, nillable = true)
    protected String ragioneSociale;
    @XmlElement(required = true, nillable = true)
    protected String descrCausCessazFunSede;
    @XmlElement(required = true, nillable = true)
    protected String dataCessaz;
    @XmlElement(required = true, nillable = true)
    protected DatoCostitutivoInfocamere datoCostitutivoInfocamere;
    @XmlElement(required = true, nillable = true)
    protected String dataInizioAtt;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfListaPersoneRIInfoc listaPersoneRIInfoc;
    @XmlElement(required = true, nillable = true)
    protected String annoDenunciaAddetti;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfProcConcorsInfoc listaProcConcorsInfoc;
    @XmlElement(required = true, nillable = true)
    protected String codFonte;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvReaSede;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvRea;
    @XmlElement(required = true, nillable = true)
    protected String codCausCessaz;
    @XmlElement(required = true, nillable = true)
    protected String flgAggiornamento;
    @XmlElement(required = true, nillable = true)
    protected String dataIscrizioneRea;
    @XmlElement(required = true, nillable = true)
    protected String localizzazPiemonte;
    @XmlElement(required = true, nillable = true)
    protected String dataCancellazRea;
    @XmlElement(required = true, nillable = true)
    protected String dataIscrRegistroImpr;
    @XmlElement(required = true, nillable = true)
    protected String provinciaIscrAlboArtigiano;
    @XmlElement(required = true, nillable = true)
    protected String dataDlbIscrAlboArtigiano;
    @XmlElement(required = true, nillable = true)
    protected String numIscrizReaSede;
    @XmlElement(required = true, nillable = true)
    protected String numAddettiSubord;
    @XmlElement(required = true, nillable = true)
    protected String impresaCessata;
    @XmlElement(required = true, nillable = true)
    protected String descrIndicStatoAttiv;
    @XmlElement(required = true, nillable = true)
    protected String descrFonte;
    @XmlElement(required = true, nillable = true)
    protected String numeroIscrAlboArtigiano;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfSezSpecInfoc listaSezSpecInfoc;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPFonteDatoSL;
    @XmlElement(required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(required = true, nillable = true)
    protected String numRegistroImpr;
    @XmlElement(required = true, nillable = true)
    protected String codCausCessazFunSede;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfOggSocialeInfoc listaOggSocialeInfoc;
    @XmlElement(required = true, nillable = true)
    protected String flgIterIscrAlboArt;
    @XmlElement(required = true, nillable = true)
    protected String progrSedeSL;
    @XmlElement(required = true, nillable = true)
    protected String dataUltimoAggRi;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPAziendaSL;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfListaSediInfoc listaSediInfoc;

    /**
     * Gets the value of the indicTrasfSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicTrasfSede() {
        return indicTrasfSede;
    }

    /**
     * Sets the value of the indicTrasfSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicTrasfSede(String value) {
        this.indicTrasfSede = value;
    }

    /**
     * Gets the value of the numIscrizRea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumIscrizRea() {
        return numIscrizRea;
    }

    /**
     * Sets the value of the numIscrizRea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumIscrizRea(String value) {
        this.numIscrizRea = value;
    }

    /**
     * Gets the value of the indicStatoAttiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicStatoAttiv() {
        return indicStatoAttiv;
    }

    /**
     * Sets the value of the indicStatoAttiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicStatoAttiv(String value) {
        this.indicStatoAttiv = value;
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
     * Gets the value of the codNaturaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodNaturaGiuridica() {
        return codNaturaGiuridica;
    }

    /**
     * Sets the value of the codNaturaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodNaturaGiuridica(String value) {
        this.codNaturaGiuridica = value;
    }

    /**
     * Gets the value of the dataUltimoAggiorn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUltimoAggiorn() {
        return dataUltimoAggiorn;
    }

    /**
     * Sets the value of the dataUltimoAggiorn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUltimoAggiorn(String value) {
        this.dataUltimoAggiorn = value;
    }

    /**
     * Gets the value of the descrCausCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCausCessaz() {
        return descrCausCessaz;
    }

    /**
     * Sets the value of the descrCausCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCausCessaz(String value) {
        this.descrCausCessaz = value;
    }

    /**
     * Gets the value of the descrIterIscrAlboArt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrIterIscrAlboArt() {
        return descrIterIscrAlboArt;
    }

    /**
     * Sets the value of the descrIterIscrAlboArt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrIterIscrAlboArt(String value) {
        this.descrIterIscrAlboArt = value;
    }

    /**
     * Gets the value of the dataDenunciaCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataDenunciaCessaz() {
        return dataDenunciaCessaz;
    }

    /**
     * Sets the value of the dataDenunciaCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataDenunciaCessaz(String value) {
        this.dataDenunciaCessaz = value;
    }

    /**
     * Gets the value of the descrIndicTrasfSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrIndicTrasfSede() {
        return descrIndicTrasfSede;
    }

    /**
     * Sets the value of the descrIndicTrasfSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrIndicTrasfSede(String value) {
        this.descrIndicTrasfSede = value;
    }

    /**
     * Gets the value of the dataCessazFunSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCessazFunSede() {
        return dataCessazFunSede;
    }

    /**
     * Sets the value of the dataCessazFunSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCessazFunSede(String value) {
        this.dataCessazFunSede = value;
    }

    /**
     * Gets the value of the numAddettiFam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumAddettiFam() {
        return numAddettiFam;
    }

    /**
     * Sets the value of the numAddettiFam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumAddettiFam(String value) {
        this.numAddettiFam = value;
    }

    /**
     * Gets the value of the descrNaturaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrNaturaGiuridica() {
        return descrNaturaGiuridica;
    }

    /**
     * Sets the value of the descrNaturaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrNaturaGiuridica(String value) {
        this.descrNaturaGiuridica = value;
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
     * Gets the value of the descrCausCessazFunSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCausCessazFunSede() {
        return descrCausCessazFunSede;
    }

    /**
     * Sets the value of the descrCausCessazFunSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCausCessazFunSede(String value) {
        this.descrCausCessazFunSede = value;
    }

    /**
     * Gets the value of the dataCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCessaz() {
        return dataCessaz;
    }

    /**
     * Sets the value of the dataCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCessaz(String value) {
        this.dataCessaz = value;
    }

    /**
     * Gets the value of the datoCostitutivoInfocamere property.
     * 
     * @return
     *     possible object is
     *     {@link DatoCostitutivoInfocamere }
     *     
     */
    public DatoCostitutivoInfocamere getDatoCostitutivoInfocamere() {
        return datoCostitutivoInfocamere;
    }

    /**
     * Sets the value of the datoCostitutivoInfocamere property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatoCostitutivoInfocamere }
     *     
     */
    public void setDatoCostitutivoInfocamere(DatoCostitutivoInfocamere value) {
        this.datoCostitutivoInfocamere = value;
    }

    /**
     * Gets the value of the dataInizioAtt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInizioAtt() {
        return dataInizioAtt;
    }

    /**
     * Sets the value of the dataInizioAtt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInizioAtt(String value) {
        this.dataInizioAtt = value;
    }

    /**
     * Gets the value of the listaPersoneRIInfoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfListaPersoneRIInfoc }
     *     
     */
    public ArrayOfListaPersoneRIInfoc getListaPersoneRIInfoc() {
        return listaPersoneRIInfoc;
    }

    /**
     * Sets the value of the listaPersoneRIInfoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfListaPersoneRIInfoc }
     *     
     */
    public void setListaPersoneRIInfoc(ArrayOfListaPersoneRIInfoc value) {
        this.listaPersoneRIInfoc = value;
    }

    /**
     * Gets the value of the annoDenunciaAddetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoDenunciaAddetti() {
        return annoDenunciaAddetti;
    }

    /**
     * Sets the value of the annoDenunciaAddetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoDenunciaAddetti(String value) {
        this.annoDenunciaAddetti = value;
    }

    /**
     * Gets the value of the listaProcConcorsInfoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProcConcorsInfoc }
     *     
     */
    public ArrayOfProcConcorsInfoc getListaProcConcorsInfoc() {
        return listaProcConcorsInfoc;
    }

    /**
     * Sets the value of the listaProcConcorsInfoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProcConcorsInfoc }
     *     
     */
    public void setListaProcConcorsInfoc(ArrayOfProcConcorsInfoc value) {
        this.listaProcConcorsInfoc = value;
    }

    /**
     * Gets the value of the codFonte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodFonte() {
        return codFonte;
    }

    /**
     * Sets the value of the codFonte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodFonte(String value) {
        this.codFonte = value;
    }

    /**
     * Gets the value of the siglaProvReaSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvReaSede() {
        return siglaProvReaSede;
    }

    /**
     * Sets the value of the siglaProvReaSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvReaSede(String value) {
        this.siglaProvReaSede = value;
    }

    /**
     * Gets the value of the siglaProvRea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvRea() {
        return siglaProvRea;
    }

    /**
     * Sets the value of the siglaProvRea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvRea(String value) {
        this.siglaProvRea = value;
    }

    /**
     * Gets the value of the codCausCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCausCessaz() {
        return codCausCessaz;
    }

    /**
     * Sets the value of the codCausCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCausCessaz(String value) {
        this.codCausCessaz = value;
    }

    /**
     * Gets the value of the flgAggiornamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgAggiornamento() {
        return flgAggiornamento;
    }

    /**
     * Sets the value of the flgAggiornamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgAggiornamento(String value) {
        this.flgAggiornamento = value;
    }

    /**
     * Gets the value of the dataIscrizioneRea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataIscrizioneRea() {
        return dataIscrizioneRea;
    }

    /**
     * Sets the value of the dataIscrizioneRea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataIscrizioneRea(String value) {
        this.dataIscrizioneRea = value;
    }

    /**
     * Gets the value of the localizzazPiemonte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalizzazPiemonte() {
        return localizzazPiemonte;
    }

    /**
     * Sets the value of the localizzazPiemonte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalizzazPiemonte(String value) {
        this.localizzazPiemonte = value;
    }

    /**
     * Gets the value of the dataCancellazRea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCancellazRea() {
        return dataCancellazRea;
    }

    /**
     * Sets the value of the dataCancellazRea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCancellazRea(String value) {
        this.dataCancellazRea = value;
    }

    /**
     * Gets the value of the dataIscrRegistroImpr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataIscrRegistroImpr() {
        return dataIscrRegistroImpr;
    }

    /**
     * Sets the value of the dataIscrRegistroImpr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataIscrRegistroImpr(String value) {
        this.dataIscrRegistroImpr = value;
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
     * Gets the value of the numIscrizReaSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumIscrizReaSede() {
        return numIscrizReaSede;
    }

    /**
     * Sets the value of the numIscrizReaSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumIscrizReaSede(String value) {
        this.numIscrizReaSede = value;
    }

    /**
     * Gets the value of the numAddettiSubord property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumAddettiSubord() {
        return numAddettiSubord;
    }

    /**
     * Sets the value of the numAddettiSubord property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumAddettiSubord(String value) {
        this.numAddettiSubord = value;
    }

    /**
     * Gets the value of the impresaCessata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImpresaCessata() {
        return impresaCessata;
    }

    /**
     * Sets the value of the impresaCessata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImpresaCessata(String value) {
        this.impresaCessata = value;
    }

    /**
     * Gets the value of the descrIndicStatoAttiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrIndicStatoAttiv() {
        return descrIndicStatoAttiv;
    }

    /**
     * Sets the value of the descrIndicStatoAttiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrIndicStatoAttiv(String value) {
        this.descrIndicStatoAttiv = value;
    }

    /**
     * Gets the value of the descrFonte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrFonte() {
        return descrFonte;
    }

    /**
     * Sets the value of the descrFonte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrFonte(String value) {
        this.descrFonte = value;
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
     * Gets the value of the listaSezSpecInfoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSezSpecInfoc }
     *     
     */
    public ArrayOfSezSpecInfoc getListaSezSpecInfoc() {
        return listaSezSpecInfoc;
    }

    /**
     * Sets the value of the listaSezSpecInfoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSezSpecInfoc }
     *     
     */
    public void setListaSezSpecInfoc(ArrayOfSezSpecInfoc value) {
        this.listaSezSpecInfoc = value;
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
     * Gets the value of the numRegistroImpr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumRegistroImpr() {
        return numRegistroImpr;
    }

    /**
     * Sets the value of the numRegistroImpr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumRegistroImpr(String value) {
        this.numRegistroImpr = value;
    }

    /**
     * Gets the value of the codCausCessazFunSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCausCessazFunSede() {
        return codCausCessazFunSede;
    }

    /**
     * Sets the value of the codCausCessazFunSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCausCessazFunSede(String value) {
        this.codCausCessazFunSede = value;
    }

    /**
     * Gets the value of the listaOggSocialeInfoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOggSocialeInfoc }
     *     
     */
    public ArrayOfOggSocialeInfoc getListaOggSocialeInfoc() {
        return listaOggSocialeInfoc;
    }

    /**
     * Sets the value of the listaOggSocialeInfoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOggSocialeInfoc }
     *     
     */
    public void setListaOggSocialeInfoc(ArrayOfOggSocialeInfoc value) {
        this.listaOggSocialeInfoc = value;
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
     * Gets the value of the progrSedeSL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgrSedeSL() {
        return progrSedeSL;
    }

    /**
     * Sets the value of the progrSedeSL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgrSedeSL(String value) {
        this.progrSedeSL = value;
    }

    /**
     * Gets the value of the dataUltimoAggRi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUltimoAggRi() {
        return dataUltimoAggRi;
    }

    /**
     * Sets the value of the dataUltimoAggRi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUltimoAggRi(String value) {
        this.dataUltimoAggRi = value;
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
     * Gets the value of the listaSediInfoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfListaSediInfoc }
     *     
     */
    public ArrayOfListaSediInfoc getListaSediInfoc() {
        return listaSediInfoc;
    }

    /**
     * Sets the value of the listaSediInfoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfListaSediInfoc }
     *     
     */
    public void setListaSediInfoc(ArrayOfListaSediInfoc value) {
        this.listaSediInfoc = value;
    }

}
