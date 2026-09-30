
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for FallimentoPersonaInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FallimentoPersonaInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numSentenzaFallim" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denomSoggCausaFallim" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrProvTribunFallim" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="progrFallimento" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dataSentenza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="descrOrganoGiudiz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFallimento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="fkProPers" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="tribunaleFallim" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codOrganoGiudiz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomePersSoggCausaFallim" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataChiusuraFallim" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataNascPersCausaSF" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="numFallimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvTrinunFallim" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="curatoreFallim" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FallimentoPersonaInfoc", propOrder = {
    "numSentenzaFallim",
    "denomSoggCausaFallim",
    "descrProvTribunFallim",
    "progrFallimento",
    "dataSentenza",
    "descrOrganoGiudiz",
    "dataFallimento",
    "fkProPers",
    "tribunaleFallim",
    "idAAEPFonteDato",
    "codOrganoGiudiz",
    "nomePersSoggCausaFallim",
    "dataChiusuraFallim",
    "dataNascPersCausaSF",
    "numFallimento",
    "siglaProvTrinunFallim",
    "idAAEPAzienda",
    "curatoreFallim"
})
public class FallimentoPersonaInfoc {

    @XmlElement(required = true, nillable = true)
    protected String numSentenzaFallim;
    @XmlElement(required = true, nillable = true)
    protected String denomSoggCausaFallim;
    @XmlElement(required = true, nillable = true)
    protected String descrProvTribunFallim;
    protected long progrFallimento;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataSentenza;
    @XmlElement(required = true, nillable = true)
    protected String descrOrganoGiudiz;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFallimento;
    protected long fkProPers;
    @XmlElement(required = true, nillable = true)
    protected String tribunaleFallim;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String codOrganoGiudiz;
    @XmlElement(required = true, nillable = true)
    protected String nomePersSoggCausaFallim;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataChiusuraFallim;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNascPersCausaSF;
    @XmlElement(required = true, nillable = true)
    protected String numFallimento;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvTrinunFallim;
    protected long idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String curatoreFallim;

    /**
     * Gets the value of the numSentenzaFallim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumSentenzaFallim() {
        return numSentenzaFallim;
    }

    /**
     * Sets the value of the numSentenzaFallim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumSentenzaFallim(String value) {
        this.numSentenzaFallim = value;
    }

    /**
     * Gets the value of the denomSoggCausaFallim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenomSoggCausaFallim() {
        return denomSoggCausaFallim;
    }

    /**
     * Sets the value of the denomSoggCausaFallim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenomSoggCausaFallim(String value) {
        this.denomSoggCausaFallim = value;
    }

    /**
     * Gets the value of the descrProvTribunFallim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrProvTribunFallim() {
        return descrProvTribunFallim;
    }

    /**
     * Sets the value of the descrProvTribunFallim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrProvTribunFallim(String value) {
        this.descrProvTribunFallim = value;
    }

    /**
     * Gets the value of the progrFallimento property.
     * 
     */
    public long getProgrFallimento() {
        return progrFallimento;
    }

    /**
     * Sets the value of the progrFallimento property.
     * 
     */
    public void setProgrFallimento(long value) {
        this.progrFallimento = value;
    }

    /**
     * Gets the value of the dataSentenza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataSentenza() {
        return dataSentenza;
    }

    /**
     * Sets the value of the dataSentenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataSentenza(XMLGregorianCalendar value) {
        this.dataSentenza = value;
    }

    /**
     * Gets the value of the descrOrganoGiudiz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrOrganoGiudiz() {
        return descrOrganoGiudiz;
    }

    /**
     * Sets the value of the descrOrganoGiudiz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrOrganoGiudiz(String value) {
        this.descrOrganoGiudiz = value;
    }

    /**
     * Gets the value of the dataFallimento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFallimento() {
        return dataFallimento;
    }

    /**
     * Sets the value of the dataFallimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFallimento(XMLGregorianCalendar value) {
        this.dataFallimento = value;
    }

    /**
     * Gets the value of the fkProPers property.
     * 
     */
    public long getFkProPers() {
        return fkProPers;
    }

    /**
     * Sets the value of the fkProPers property.
     * 
     */
    public void setFkProPers(long value) {
        this.fkProPers = value;
    }

    /**
     * Gets the value of the tribunaleFallim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTribunaleFallim() {
        return tribunaleFallim;
    }

    /**
     * Sets the value of the tribunaleFallim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTribunaleFallim(String value) {
        this.tribunaleFallim = value;
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
     * Gets the value of the codOrganoGiudiz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodOrganoGiudiz() {
        return codOrganoGiudiz;
    }

    /**
     * Sets the value of the codOrganoGiudiz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodOrganoGiudiz(String value) {
        this.codOrganoGiudiz = value;
    }

    /**
     * Gets the value of the nomePersSoggCausaFallim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomePersSoggCausaFallim() {
        return nomePersSoggCausaFallim;
    }

    /**
     * Sets the value of the nomePersSoggCausaFallim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomePersSoggCausaFallim(String value) {
        this.nomePersSoggCausaFallim = value;
    }

    /**
     * Gets the value of the dataChiusuraFallim property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataChiusuraFallim() {
        return dataChiusuraFallim;
    }

    /**
     * Sets the value of the dataChiusuraFallim property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataChiusuraFallim(XMLGregorianCalendar value) {
        this.dataChiusuraFallim = value;
    }

    /**
     * Gets the value of the dataNascPersCausaSF property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNascPersCausaSF() {
        return dataNascPersCausaSF;
    }

    /**
     * Sets the value of the dataNascPersCausaSF property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNascPersCausaSF(XMLGregorianCalendar value) {
        this.dataNascPersCausaSF = value;
    }

    /**
     * Gets the value of the numFallimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumFallimento() {
        return numFallimento;
    }

    /**
     * Sets the value of the numFallimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumFallimento(String value) {
        this.numFallimento = value;
    }

    /**
     * Gets the value of the siglaProvTrinunFallim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvTrinunFallim() {
        return siglaProvTrinunFallim;
    }

    /**
     * Sets the value of the siglaProvTrinunFallim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvTrinunFallim(String value) {
        this.siglaProvTrinunFallim = value;
    }

    /**
     * Gets the value of the idAAEPAzienda property.
     * 
     */
    public long getIdAAEPAzienda() {
        return idAAEPAzienda;
    }

    /**
     * Sets the value of the idAAEPAzienda property.
     * 
     */
    public void setIdAAEPAzienda(long value) {
        this.idAAEPAzienda = value;
    }

    /**
     * Gets the value of the curatoreFallim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuratoreFallim() {
        return curatoreFallim;
    }

    /**
     * Sets the value of the curatoreFallim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuratoreFallim(String value) {
        this.curatoreFallim = value;
    }

}
