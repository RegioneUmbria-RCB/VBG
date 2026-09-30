
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProspDisabSILP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProspDisabSILP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataRichiestaGradualita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataRichiestaEsonero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numProvvRichiestaGradualita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataRichiestaCompensaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataPrimaAssunzione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataUltAggiornam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataConcessioneEsonero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idTipoProspDisab" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numProvvConcessioneCompensaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numProvvConcessioneSospensione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgRichiestaEsonero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NConvenzioni" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgRichiestaSospensione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgStipulaConvenzione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numProvvConcessioneGradualita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioSospensione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataClassificazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataSecondaAssunzione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NLavoratoriGradualita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="provCompensaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgRichiestaGradualita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numProvvRichiestaEsonero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgConcessioneCompensaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="percConcessioneEsonero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgConcessioneSospensione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataRichiestaSospensione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoProspDisab" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInvio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFineSospensione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrMotivoSospensione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="organicoProspDisabSILP" type="{urn:AAEPCSI}ArrayOfOrganicoProspDisabSILP"/>
 *         &lt;element name="idProspDisab" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idMotivoSospensione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgRichiestaCompensaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="percConcessioneGradualita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="percRichiestaEsonero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataConcessioneSospensione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numClassificazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numProtocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataProtocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numProvvRichiestaCompensaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgConcessioneEsonero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataConcessioneCompensaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="controparte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numProvvConcessioneEsonero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataConcessioneGradualita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataUltAggiornamSILP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numProvvRichiestaSospensione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flgConcessioneGradualita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProspDisabSILP", propOrder = {
    "dataRichiestaGradualita",
    "dataRichiestaEsonero",
    "numProvvRichiestaGradualita",
    "dataRichiestaCompensaz",
    "dataPrimaAssunzione",
    "dataUltAggiornam",
    "dataConcessioneEsonero",
    "idTipoProspDisab",
    "numProvvConcessioneCompensaz",
    "numProvvConcessioneSospensione",
    "flgRichiestaEsonero",
    "nConvenzioni",
    "flgRichiestaSospensione",
    "flgStipulaConvenzione",
    "numProvvConcessioneGradualita",
    "dataInizioSospensione",
    "dataClassificazione",
    "dataSecondaAssunzione",
    "nLavoratoriGradualita",
    "provCompensaz",
    "idSede",
    "flgRichiestaGradualita",
    "numProvvRichiestaEsonero",
    "flgConcessioneCompensaz",
    "percConcessioneEsonero",
    "flgConcessioneSospensione",
    "dataRichiestaSospensione",
    "descrTipoProspDisab",
    "dataInvio",
    "dataFineSospensione",
    "descrMotivoSospensione",
    "organicoProspDisabSILP",
    "idProspDisab",
    "idMotivoSospensione",
    "flgRichiestaCompensaz",
    "percConcessioneGradualita",
    "percRichiestaEsonero",
    "dataConcessioneSospensione",
    "numClassificazione",
    "numProtocollo",
    "dataProtocollo",
    "annoRiferimento",
    "numProvvRichiestaCompensaz",
    "flgConcessioneEsonero",
    "siglaProv",
    "dataConcessioneCompensaz",
    "controparte",
    "numProvvConcessioneEsonero",
    "dataConcessioneGradualita",
    "dataUltAggiornamSILP",
    "numProvvRichiestaSospensione",
    "flgConcessioneGradualita"
})
public class ProspDisabSILP {

    @XmlElement(required = true, nillable = true)
    protected String dataRichiestaGradualita;
    @XmlElement(required = true, nillable = true)
    protected String dataRichiestaEsonero;
    @XmlElement(required = true, nillable = true)
    protected String numProvvRichiestaGradualita;
    @XmlElement(required = true, nillable = true)
    protected String dataRichiestaCompensaz;
    @XmlElement(required = true, nillable = true)
    protected String dataPrimaAssunzione;
    @XmlElement(required = true, nillable = true)
    protected String dataUltAggiornam;
    @XmlElement(required = true, nillable = true)
    protected String dataConcessioneEsonero;
    @XmlElement(required = true, nillable = true)
    protected String idTipoProspDisab;
    @XmlElement(required = true, nillable = true)
    protected String numProvvConcessioneCompensaz;
    @XmlElement(required = true, nillable = true)
    protected String numProvvConcessioneSospensione;
    @XmlElement(required = true, nillable = true)
    protected String flgRichiestaEsonero;
    @XmlElement(name = "NConvenzioni", required = true, nillable = true)
    protected String nConvenzioni;
    @XmlElement(required = true, nillable = true)
    protected String flgRichiestaSospensione;
    @XmlElement(required = true, nillable = true)
    protected String flgStipulaConvenzione;
    @XmlElement(required = true, nillable = true)
    protected String numProvvConcessioneGradualita;
    @XmlElement(required = true, nillable = true)
    protected String dataInizioSospensione;
    @XmlElement(required = true, nillable = true)
    protected String dataClassificazione;
    @XmlElement(required = true, nillable = true)
    protected String dataSecondaAssunzione;
    @XmlElement(name = "NLavoratoriGradualita", required = true, nillable = true)
    protected String nLavoratoriGradualita;
    @XmlElement(required = true, nillable = true)
    protected String provCompensaz;
    @XmlElement(required = true, nillable = true)
    protected String idSede;
    @XmlElement(required = true, nillable = true)
    protected String flgRichiestaGradualita;
    @XmlElement(required = true, nillable = true)
    protected String numProvvRichiestaEsonero;
    @XmlElement(required = true, nillable = true)
    protected String flgConcessioneCompensaz;
    @XmlElement(required = true, nillable = true)
    protected String percConcessioneEsonero;
    @XmlElement(required = true, nillable = true)
    protected String flgConcessioneSospensione;
    @XmlElement(required = true, nillable = true)
    protected String dataRichiestaSospensione;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoProspDisab;
    @XmlElement(required = true, nillable = true)
    protected String dataInvio;
    @XmlElement(required = true, nillable = true)
    protected String dataFineSospensione;
    @XmlElement(required = true, nillable = true)
    protected String descrMotivoSospensione;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfOrganicoProspDisabSILP organicoProspDisabSILP;
    @XmlElement(required = true, nillable = true)
    protected String idProspDisab;
    @XmlElement(required = true, nillable = true)
    protected String idMotivoSospensione;
    @XmlElement(required = true, nillable = true)
    protected String flgRichiestaCompensaz;
    @XmlElement(required = true, nillable = true)
    protected String percConcessioneGradualita;
    @XmlElement(required = true, nillable = true)
    protected String percRichiestaEsonero;
    @XmlElement(required = true, nillable = true)
    protected String dataConcessioneSospensione;
    @XmlElement(required = true, nillable = true)
    protected String numClassificazione;
    @XmlElement(required = true, nillable = true)
    protected String numProtocollo;
    @XmlElement(required = true, nillable = true)
    protected String dataProtocollo;
    @XmlElement(required = true, nillable = true)
    protected String annoRiferimento;
    @XmlElement(required = true, nillable = true)
    protected String numProvvRichiestaCompensaz;
    @XmlElement(required = true, nillable = true)
    protected String flgConcessioneEsonero;
    @XmlElement(required = true, nillable = true)
    protected String siglaProv;
    @XmlElement(required = true, nillable = true)
    protected String dataConcessioneCompensaz;
    @XmlElement(required = true, nillable = true)
    protected String controparte;
    @XmlElement(required = true, nillable = true)
    protected String numProvvConcessioneEsonero;
    @XmlElement(required = true, nillable = true)
    protected String dataConcessioneGradualita;
    @XmlElement(required = true, nillable = true)
    protected String dataUltAggiornamSILP;
    @XmlElement(required = true, nillable = true)
    protected String numProvvRichiestaSospensione;
    @XmlElement(required = true, nillable = true)
    protected String flgConcessioneGradualita;

    /**
     * Gets the value of the dataRichiestaGradualita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataRichiestaGradualita() {
        return dataRichiestaGradualita;
    }

    /**
     * Sets the value of the dataRichiestaGradualita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRichiestaGradualita(String value) {
        this.dataRichiestaGradualita = value;
    }

    /**
     * Gets the value of the dataRichiestaEsonero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataRichiestaEsonero() {
        return dataRichiestaEsonero;
    }

    /**
     * Sets the value of the dataRichiestaEsonero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRichiestaEsonero(String value) {
        this.dataRichiestaEsonero = value;
    }

    /**
     * Gets the value of the numProvvRichiestaGradualita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumProvvRichiestaGradualita() {
        return numProvvRichiestaGradualita;
    }

    /**
     * Sets the value of the numProvvRichiestaGradualita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumProvvRichiestaGradualita(String value) {
        this.numProvvRichiestaGradualita = value;
    }

    /**
     * Gets the value of the dataRichiestaCompensaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataRichiestaCompensaz() {
        return dataRichiestaCompensaz;
    }

    /**
     * Sets the value of the dataRichiestaCompensaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRichiestaCompensaz(String value) {
        this.dataRichiestaCompensaz = value;
    }

    /**
     * Gets the value of the dataPrimaAssunzione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataPrimaAssunzione() {
        return dataPrimaAssunzione;
    }

    /**
     * Sets the value of the dataPrimaAssunzione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataPrimaAssunzione(String value) {
        this.dataPrimaAssunzione = value;
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
     * Gets the value of the dataConcessioneEsonero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataConcessioneEsonero() {
        return dataConcessioneEsonero;
    }

    /**
     * Sets the value of the dataConcessioneEsonero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataConcessioneEsonero(String value) {
        this.dataConcessioneEsonero = value;
    }

    /**
     * Gets the value of the idTipoProspDisab property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTipoProspDisab() {
        return idTipoProspDisab;
    }

    /**
     * Sets the value of the idTipoProspDisab property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTipoProspDisab(String value) {
        this.idTipoProspDisab = value;
    }

    /**
     * Gets the value of the numProvvConcessioneCompensaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumProvvConcessioneCompensaz() {
        return numProvvConcessioneCompensaz;
    }

    /**
     * Sets the value of the numProvvConcessioneCompensaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumProvvConcessioneCompensaz(String value) {
        this.numProvvConcessioneCompensaz = value;
    }

    /**
     * Gets the value of the numProvvConcessioneSospensione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumProvvConcessioneSospensione() {
        return numProvvConcessioneSospensione;
    }

    /**
     * Sets the value of the numProvvConcessioneSospensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumProvvConcessioneSospensione(String value) {
        this.numProvvConcessioneSospensione = value;
    }

    /**
     * Gets the value of the flgRichiestaEsonero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgRichiestaEsonero() {
        return flgRichiestaEsonero;
    }

    /**
     * Sets the value of the flgRichiestaEsonero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgRichiestaEsonero(String value) {
        this.flgRichiestaEsonero = value;
    }

    /**
     * Gets the value of the nConvenzioni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNConvenzioni() {
        return nConvenzioni;
    }

    /**
     * Sets the value of the nConvenzioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNConvenzioni(String value) {
        this.nConvenzioni = value;
    }

    /**
     * Gets the value of the flgRichiestaSospensione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgRichiestaSospensione() {
        return flgRichiestaSospensione;
    }

    /**
     * Sets the value of the flgRichiestaSospensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgRichiestaSospensione(String value) {
        this.flgRichiestaSospensione = value;
    }

    /**
     * Gets the value of the flgStipulaConvenzione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgStipulaConvenzione() {
        return flgStipulaConvenzione;
    }

    /**
     * Sets the value of the flgStipulaConvenzione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgStipulaConvenzione(String value) {
        this.flgStipulaConvenzione = value;
    }

    /**
     * Gets the value of the numProvvConcessioneGradualita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumProvvConcessioneGradualita() {
        return numProvvConcessioneGradualita;
    }

    /**
     * Sets the value of the numProvvConcessioneGradualita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumProvvConcessioneGradualita(String value) {
        this.numProvvConcessioneGradualita = value;
    }

    /**
     * Gets the value of the dataInizioSospensione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInizioSospensione() {
        return dataInizioSospensione;
    }

    /**
     * Sets the value of the dataInizioSospensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInizioSospensione(String value) {
        this.dataInizioSospensione = value;
    }

    /**
     * Gets the value of the dataClassificazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataClassificazione() {
        return dataClassificazione;
    }

    /**
     * Sets the value of the dataClassificazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataClassificazione(String value) {
        this.dataClassificazione = value;
    }

    /**
     * Gets the value of the dataSecondaAssunzione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSecondaAssunzione() {
        return dataSecondaAssunzione;
    }

    /**
     * Sets the value of the dataSecondaAssunzione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSecondaAssunzione(String value) {
        this.dataSecondaAssunzione = value;
    }

    /**
     * Gets the value of the nLavoratoriGradualita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNLavoratoriGradualita() {
        return nLavoratoriGradualita;
    }

    /**
     * Sets the value of the nLavoratoriGradualita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNLavoratoriGradualita(String value) {
        this.nLavoratoriGradualita = value;
    }

    /**
     * Gets the value of the provCompensaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvCompensaz() {
        return provCompensaz;
    }

    /**
     * Sets the value of the provCompensaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvCompensaz(String value) {
        this.provCompensaz = value;
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
     * Gets the value of the flgRichiestaGradualita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgRichiestaGradualita() {
        return flgRichiestaGradualita;
    }

    /**
     * Sets the value of the flgRichiestaGradualita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgRichiestaGradualita(String value) {
        this.flgRichiestaGradualita = value;
    }

    /**
     * Gets the value of the numProvvRichiestaEsonero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumProvvRichiestaEsonero() {
        return numProvvRichiestaEsonero;
    }

    /**
     * Sets the value of the numProvvRichiestaEsonero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumProvvRichiestaEsonero(String value) {
        this.numProvvRichiestaEsonero = value;
    }

    /**
     * Gets the value of the flgConcessioneCompensaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgConcessioneCompensaz() {
        return flgConcessioneCompensaz;
    }

    /**
     * Sets the value of the flgConcessioneCompensaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgConcessioneCompensaz(String value) {
        this.flgConcessioneCompensaz = value;
    }

    /**
     * Gets the value of the percConcessioneEsonero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPercConcessioneEsonero() {
        return percConcessioneEsonero;
    }

    /**
     * Sets the value of the percConcessioneEsonero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPercConcessioneEsonero(String value) {
        this.percConcessioneEsonero = value;
    }

    /**
     * Gets the value of the flgConcessioneSospensione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgConcessioneSospensione() {
        return flgConcessioneSospensione;
    }

    /**
     * Sets the value of the flgConcessioneSospensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgConcessioneSospensione(String value) {
        this.flgConcessioneSospensione = value;
    }

    /**
     * Gets the value of the dataRichiestaSospensione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataRichiestaSospensione() {
        return dataRichiestaSospensione;
    }

    /**
     * Sets the value of the dataRichiestaSospensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRichiestaSospensione(String value) {
        this.dataRichiestaSospensione = value;
    }

    /**
     * Gets the value of the descrTipoProspDisab property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoProspDisab() {
        return descrTipoProspDisab;
    }

    /**
     * Sets the value of the descrTipoProspDisab property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoProspDisab(String value) {
        this.descrTipoProspDisab = value;
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
     * Gets the value of the dataFineSospensione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFineSospensione() {
        return dataFineSospensione;
    }

    /**
     * Sets the value of the dataFineSospensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFineSospensione(String value) {
        this.dataFineSospensione = value;
    }

    /**
     * Gets the value of the descrMotivoSospensione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrMotivoSospensione() {
        return descrMotivoSospensione;
    }

    /**
     * Sets the value of the descrMotivoSospensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrMotivoSospensione(String value) {
        this.descrMotivoSospensione = value;
    }

    /**
     * Gets the value of the organicoProspDisabSILP property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrganicoProspDisabSILP }
     *     
     */
    public ArrayOfOrganicoProspDisabSILP getOrganicoProspDisabSILP() {
        return organicoProspDisabSILP;
    }

    /**
     * Sets the value of the organicoProspDisabSILP property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrganicoProspDisabSILP }
     *     
     */
    public void setOrganicoProspDisabSILP(ArrayOfOrganicoProspDisabSILP value) {
        this.organicoProspDisabSILP = value;
    }

    /**
     * Gets the value of the idProspDisab property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdProspDisab() {
        return idProspDisab;
    }

    /**
     * Sets the value of the idProspDisab property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdProspDisab(String value) {
        this.idProspDisab = value;
    }

    /**
     * Gets the value of the idMotivoSospensione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdMotivoSospensione() {
        return idMotivoSospensione;
    }

    /**
     * Sets the value of the idMotivoSospensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdMotivoSospensione(String value) {
        this.idMotivoSospensione = value;
    }

    /**
     * Gets the value of the flgRichiestaCompensaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgRichiestaCompensaz() {
        return flgRichiestaCompensaz;
    }

    /**
     * Sets the value of the flgRichiestaCompensaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgRichiestaCompensaz(String value) {
        this.flgRichiestaCompensaz = value;
    }

    /**
     * Gets the value of the percConcessioneGradualita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPercConcessioneGradualita() {
        return percConcessioneGradualita;
    }

    /**
     * Sets the value of the percConcessioneGradualita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPercConcessioneGradualita(String value) {
        this.percConcessioneGradualita = value;
    }

    /**
     * Gets the value of the percRichiestaEsonero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPercRichiestaEsonero() {
        return percRichiestaEsonero;
    }

    /**
     * Sets the value of the percRichiestaEsonero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPercRichiestaEsonero(String value) {
        this.percRichiestaEsonero = value;
    }

    /**
     * Gets the value of the dataConcessioneSospensione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataConcessioneSospensione() {
        return dataConcessioneSospensione;
    }

    /**
     * Sets the value of the dataConcessioneSospensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataConcessioneSospensione(String value) {
        this.dataConcessioneSospensione = value;
    }

    /**
     * Gets the value of the numClassificazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumClassificazione() {
        return numClassificazione;
    }

    /**
     * Sets the value of the numClassificazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumClassificazione(String value) {
        this.numClassificazione = value;
    }

    /**
     * Gets the value of the numProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumProtocollo() {
        return numProtocollo;
    }

    /**
     * Sets the value of the numProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumProtocollo(String value) {
        this.numProtocollo = value;
    }

    /**
     * Gets the value of the dataProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProtocollo() {
        return dataProtocollo;
    }

    /**
     * Sets the value of the dataProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProtocollo(String value) {
        this.dataProtocollo = value;
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
     * Gets the value of the numProvvRichiestaCompensaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumProvvRichiestaCompensaz() {
        return numProvvRichiestaCompensaz;
    }

    /**
     * Sets the value of the numProvvRichiestaCompensaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumProvvRichiestaCompensaz(String value) {
        this.numProvvRichiestaCompensaz = value;
    }

    /**
     * Gets the value of the flgConcessioneEsonero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgConcessioneEsonero() {
        return flgConcessioneEsonero;
    }

    /**
     * Sets the value of the flgConcessioneEsonero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgConcessioneEsonero(String value) {
        this.flgConcessioneEsonero = value;
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
     * Gets the value of the dataConcessioneCompensaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataConcessioneCompensaz() {
        return dataConcessioneCompensaz;
    }

    /**
     * Sets the value of the dataConcessioneCompensaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataConcessioneCompensaz(String value) {
        this.dataConcessioneCompensaz = value;
    }

    /**
     * Gets the value of the controparte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControparte() {
        return controparte;
    }

    /**
     * Sets the value of the controparte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControparte(String value) {
        this.controparte = value;
    }

    /**
     * Gets the value of the numProvvConcessioneEsonero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumProvvConcessioneEsonero() {
        return numProvvConcessioneEsonero;
    }

    /**
     * Sets the value of the numProvvConcessioneEsonero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumProvvConcessioneEsonero(String value) {
        this.numProvvConcessioneEsonero = value;
    }

    /**
     * Gets the value of the dataConcessioneGradualita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataConcessioneGradualita() {
        return dataConcessioneGradualita;
    }

    /**
     * Sets the value of the dataConcessioneGradualita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataConcessioneGradualita(String value) {
        this.dataConcessioneGradualita = value;
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
     * Gets the value of the numProvvRichiestaSospensione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumProvvRichiestaSospensione() {
        return numProvvRichiestaSospensione;
    }

    /**
     * Sets the value of the numProvvRichiestaSospensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumProvvRichiestaSospensione(String value) {
        this.numProvvRichiestaSospensione = value;
    }

    /**
     * Gets the value of the flgConcessioneGradualita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgConcessioneGradualita() {
        return flgConcessioneGradualita;
    }

    /**
     * Sets the value of the flgConcessioneGradualita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgConcessioneGradualita(String value) {
        this.flgConcessioneGradualita = value;
    }

}
