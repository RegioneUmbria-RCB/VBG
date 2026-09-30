
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CorsoFP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CorsoFP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="progrAccorpamento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrSettore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sedeOccasionale" type="{urn:AAEPCSI}SedeOccasionale"/>
 *         &lt;element name="oreStage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroAllievi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaIndirizzi" type="{urn:AAEPCSI}ArrayOfListaIndirizzi"/>
 *         &lt;element name="annoGestione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orePratica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoComparto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oreTeoria" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numTotPartiIter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioCorso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoInizioCorso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrCausaleSoppressione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrLineaInterventoPOR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrDocRegionale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parteIterAttuale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComparto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="statoAvanzam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="totaleOreIter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codComparto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codCausaleSoppressione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codSettore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaDestinatari" type="{urn:AAEPCSI}ArrayOfListaDestinatari"/>
 *         &lt;element name="codDocRegionale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoAttivita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoSettore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codAzionePOR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrAzionePOR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFineCorso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codLineaInterventoPOR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoEntita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="progrCorso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codMisuraPOR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrMisuraPOR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CorsoFP", propOrder = {
    "progrAccorpamento",
    "descrSettore",
    "sedeOccasionale",
    "oreStage",
    "numeroAllievi",
    "listaIndirizzi",
    "annoGestione",
    "orePratica",
    "annoComparto",
    "oreTeoria",
    "numTotPartiIter",
    "dataInizioCorso",
    "annoInizioCorso",
    "descrCausaleSoppressione",
    "descrLineaInterventoPOR",
    "denominazione",
    "descrDocRegionale",
    "parteIterAttuale",
    "descrComparto",
    "statoAvanzam",
    "totaleOreIter",
    "codComparto",
    "codCausaleSoppressione",
    "codSettore",
    "listaDestinatari",
    "codDocRegionale",
    "tipoAttivita",
    "annoSettore",
    "codAzionePOR",
    "descrAzionePOR",
    "dataFineCorso",
    "codLineaInterventoPOR",
    "tipoEntita",
    "progrCorso",
    "codMisuraPOR",
    "descrMisuraPOR"
})
public class CorsoFP {

    @XmlElement(required = true, nillable = true)
    protected String progrAccorpamento;
    @XmlElement(required = true, nillable = true)
    protected String descrSettore;
    @XmlElement(required = true, nillable = true)
    protected SedeOccasionale sedeOccasionale;
    @XmlElement(required = true, nillable = true)
    protected String oreStage;
    @XmlElement(required = true, nillable = true)
    protected String numeroAllievi;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfListaIndirizzi listaIndirizzi;
    @XmlElement(required = true, nillable = true)
    protected String annoGestione;
    @XmlElement(required = true, nillable = true)
    protected String orePratica;
    @XmlElement(required = true, nillable = true)
    protected String annoComparto;
    @XmlElement(required = true, nillable = true)
    protected String oreTeoria;
    @XmlElement(required = true, nillable = true)
    protected String numTotPartiIter;
    @XmlElement(required = true, nillable = true)
    protected String dataInizioCorso;
    @XmlElement(required = true, nillable = true)
    protected String annoInizioCorso;
    @XmlElement(required = true, nillable = true)
    protected String descrCausaleSoppressione;
    @XmlElement(required = true, nillable = true)
    protected String descrLineaInterventoPOR;
    @XmlElement(required = true, nillable = true)
    protected String denominazione;
    @XmlElement(required = true, nillable = true)
    protected String descrDocRegionale;
    @XmlElement(required = true, nillable = true)
    protected String parteIterAttuale;
    @XmlElement(required = true, nillable = true)
    protected String descrComparto;
    @XmlElement(required = true, nillable = true)
    protected String statoAvanzam;
    @XmlElement(required = true, nillable = true)
    protected String totaleOreIter;
    @XmlElement(required = true, nillable = true)
    protected String codComparto;
    @XmlElement(required = true, nillable = true)
    protected String codCausaleSoppressione;
    @XmlElement(required = true, nillable = true)
    protected String codSettore;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfListaDestinatari listaDestinatari;
    @XmlElement(required = true, nillable = true)
    protected String codDocRegionale;
    @XmlElement(required = true, nillable = true)
    protected String tipoAttivita;
    @XmlElement(required = true, nillable = true)
    protected String annoSettore;
    @XmlElement(required = true, nillable = true)
    protected String codAzionePOR;
    @XmlElement(required = true, nillable = true)
    protected String descrAzionePOR;
    @XmlElement(required = true, nillable = true)
    protected String dataFineCorso;
    @XmlElement(required = true, nillable = true)
    protected String codLineaInterventoPOR;
    @XmlElement(required = true, nillable = true)
    protected String tipoEntita;
    @XmlElement(required = true, nillable = true)
    protected String progrCorso;
    @XmlElement(required = true, nillable = true)
    protected String codMisuraPOR;
    @XmlElement(required = true, nillable = true)
    protected String descrMisuraPOR;

    /**
     * Gets the value of the progrAccorpamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgrAccorpamento() {
        return progrAccorpamento;
    }

    /**
     * Sets the value of the progrAccorpamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgrAccorpamento(String value) {
        this.progrAccorpamento = value;
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
     * Gets the value of the sedeOccasionale property.
     * 
     * @return
     *     possible object is
     *     {@link SedeOccasionale }
     *     
     */
    public SedeOccasionale getSedeOccasionale() {
        return sedeOccasionale;
    }

    /**
     * Sets the value of the sedeOccasionale property.
     * 
     * @param value
     *     allowed object is
     *     {@link SedeOccasionale }
     *     
     */
    public void setSedeOccasionale(SedeOccasionale value) {
        this.sedeOccasionale = value;
    }

    /**
     * Gets the value of the oreStage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOreStage() {
        return oreStage;
    }

    /**
     * Sets the value of the oreStage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOreStage(String value) {
        this.oreStage = value;
    }

    /**
     * Gets the value of the numeroAllievi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroAllievi() {
        return numeroAllievi;
    }

    /**
     * Sets the value of the numeroAllievi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroAllievi(String value) {
        this.numeroAllievi = value;
    }

    /**
     * Gets the value of the listaIndirizzi property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfListaIndirizzi }
     *     
     */
    public ArrayOfListaIndirizzi getListaIndirizzi() {
        return listaIndirizzi;
    }

    /**
     * Sets the value of the listaIndirizzi property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfListaIndirizzi }
     *     
     */
    public void setListaIndirizzi(ArrayOfListaIndirizzi value) {
        this.listaIndirizzi = value;
    }

    /**
     * Gets the value of the annoGestione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoGestione() {
        return annoGestione;
    }

    /**
     * Sets the value of the annoGestione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoGestione(String value) {
        this.annoGestione = value;
    }

    /**
     * Gets the value of the orePratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrePratica() {
        return orePratica;
    }

    /**
     * Sets the value of the orePratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrePratica(String value) {
        this.orePratica = value;
    }

    /**
     * Gets the value of the annoComparto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoComparto() {
        return annoComparto;
    }

    /**
     * Sets the value of the annoComparto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoComparto(String value) {
        this.annoComparto = value;
    }

    /**
     * Gets the value of the oreTeoria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOreTeoria() {
        return oreTeoria;
    }

    /**
     * Sets the value of the oreTeoria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOreTeoria(String value) {
        this.oreTeoria = value;
    }

    /**
     * Gets the value of the numTotPartiIter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTotPartiIter() {
        return numTotPartiIter;
    }

    /**
     * Sets the value of the numTotPartiIter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTotPartiIter(String value) {
        this.numTotPartiIter = value;
    }

    /**
     * Gets the value of the dataInizioCorso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInizioCorso() {
        return dataInizioCorso;
    }

    /**
     * Sets the value of the dataInizioCorso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInizioCorso(String value) {
        this.dataInizioCorso = value;
    }

    /**
     * Gets the value of the annoInizioCorso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoInizioCorso() {
        return annoInizioCorso;
    }

    /**
     * Sets the value of the annoInizioCorso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoInizioCorso(String value) {
        this.annoInizioCorso = value;
    }

    /**
     * Gets the value of the descrCausaleSoppressione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCausaleSoppressione() {
        return descrCausaleSoppressione;
    }

    /**
     * Sets the value of the descrCausaleSoppressione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCausaleSoppressione(String value) {
        this.descrCausaleSoppressione = value;
    }

    /**
     * Gets the value of the descrLineaInterventoPOR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrLineaInterventoPOR() {
        return descrLineaInterventoPOR;
    }

    /**
     * Sets the value of the descrLineaInterventoPOR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrLineaInterventoPOR(String value) {
        this.descrLineaInterventoPOR = value;
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
     * Gets the value of the descrDocRegionale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrDocRegionale() {
        return descrDocRegionale;
    }

    /**
     * Sets the value of the descrDocRegionale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrDocRegionale(String value) {
        this.descrDocRegionale = value;
    }

    /**
     * Gets the value of the parteIterAttuale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParteIterAttuale() {
        return parteIterAttuale;
    }

    /**
     * Sets the value of the parteIterAttuale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParteIterAttuale(String value) {
        this.parteIterAttuale = value;
    }

    /**
     * Gets the value of the descrComparto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrComparto() {
        return descrComparto;
    }

    /**
     * Sets the value of the descrComparto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrComparto(String value) {
        this.descrComparto = value;
    }

    /**
     * Gets the value of the statoAvanzam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoAvanzam() {
        return statoAvanzam;
    }

    /**
     * Sets the value of the statoAvanzam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoAvanzam(String value) {
        this.statoAvanzam = value;
    }

    /**
     * Gets the value of the totaleOreIter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotaleOreIter() {
        return totaleOreIter;
    }

    /**
     * Sets the value of the totaleOreIter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotaleOreIter(String value) {
        this.totaleOreIter = value;
    }

    /**
     * Gets the value of the codComparto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodComparto() {
        return codComparto;
    }

    /**
     * Sets the value of the codComparto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodComparto(String value) {
        this.codComparto = value;
    }

    /**
     * Gets the value of the codCausaleSoppressione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCausaleSoppressione() {
        return codCausaleSoppressione;
    }

    /**
     * Sets the value of the codCausaleSoppressione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCausaleSoppressione(String value) {
        this.codCausaleSoppressione = value;
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
     * Gets the value of the listaDestinatari property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfListaDestinatari }
     *     
     */
    public ArrayOfListaDestinatari getListaDestinatari() {
        return listaDestinatari;
    }

    /**
     * Sets the value of the listaDestinatari property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfListaDestinatari }
     *     
     */
    public void setListaDestinatari(ArrayOfListaDestinatari value) {
        this.listaDestinatari = value;
    }

    /**
     * Gets the value of the codDocRegionale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDocRegionale() {
        return codDocRegionale;
    }

    /**
     * Sets the value of the codDocRegionale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDocRegionale(String value) {
        this.codDocRegionale = value;
    }

    /**
     * Gets the value of the tipoAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAttivita() {
        return tipoAttivita;
    }

    /**
     * Sets the value of the tipoAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAttivita(String value) {
        this.tipoAttivita = value;
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

    /**
     * Gets the value of the codAzionePOR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAzionePOR() {
        return codAzionePOR;
    }

    /**
     * Sets the value of the codAzionePOR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAzionePOR(String value) {
        this.codAzionePOR = value;
    }

    /**
     * Gets the value of the descrAzionePOR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrAzionePOR() {
        return descrAzionePOR;
    }

    /**
     * Sets the value of the descrAzionePOR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrAzionePOR(String value) {
        this.descrAzionePOR = value;
    }

    /**
     * Gets the value of the dataFineCorso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFineCorso() {
        return dataFineCorso;
    }

    /**
     * Sets the value of the dataFineCorso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFineCorso(String value) {
        this.dataFineCorso = value;
    }

    /**
     * Gets the value of the codLineaInterventoPOR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodLineaInterventoPOR() {
        return codLineaInterventoPOR;
    }

    /**
     * Sets the value of the codLineaInterventoPOR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodLineaInterventoPOR(String value) {
        this.codLineaInterventoPOR = value;
    }

    /**
     * Gets the value of the tipoEntita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoEntita() {
        return tipoEntita;
    }

    /**
     * Sets the value of the tipoEntita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoEntita(String value) {
        this.tipoEntita = value;
    }

    /**
     * Gets the value of the progrCorso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgrCorso() {
        return progrCorso;
    }

    /**
     * Sets the value of the progrCorso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgrCorso(String value) {
        this.progrCorso = value;
    }

    /**
     * Gets the value of the codMisuraPOR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodMisuraPOR() {
        return codMisuraPOR;
    }

    /**
     * Sets the value of the codMisuraPOR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodMisuraPOR(String value) {
        this.codMisuraPOR = value;
    }

    /**
     * Gets the value of the descrMisuraPOR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrMisuraPOR() {
        return descrMisuraPOR;
    }

    /**
     * Sets the value of the descrMisuraPOR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrMisuraPOR(String value) {
        this.descrMisuraPOR = value;
    }

}
