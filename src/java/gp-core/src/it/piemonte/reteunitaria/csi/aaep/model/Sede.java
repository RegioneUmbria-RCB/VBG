
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Sede complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Sede">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nomeComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codCausaleCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneTipoSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="toponimo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indirizzoSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceStatoEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codSedeInailComp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneCausaleCessazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroCivico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneAttivitaSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idTipoSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denominazioneSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idSede" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFineAttivita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="siglaProvUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvincia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroDipendenti" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Sede", propOrder = {
    "nomeComune",
    "codCausaleCessazione",
    "descrizioneTipoSede",
    "descrStatoEstero",
    "toponimo",
    "indirizzoSede",
    "codiceStatoEstero",
    "codComune",
    "cap",
    "idAzienda",
    "codSedeInailComp",
    "descrizioneCausaleCessazione",
    "numeroCivico",
    "descrizioneAttivitaSede",
    "idTipoSede",
    "dataInizioAttivita",
    "telefono",
    "dataInizioValidita",
    "email",
    "denominazioneSede",
    "idSede",
    "dataFineAttivita",
    "siglaProvUL",
    "siglaProvincia",
    "fax",
    "indirizzo",
    "numeroDipendenti"
})
public class Sede {

    @XmlElement(required = true, nillable = true)
    protected String nomeComune;
    @XmlElement(required = true, nillable = true)
    protected String codCausaleCessazione;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneTipoSede;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoEstero;
    @XmlElement(required = true, nillable = true)
    protected String toponimo;
    @XmlElement(required = true, nillable = true)
    protected String indirizzoSede;
    @XmlElement(required = true, nillable = true)
    protected String codiceStatoEstero;
    @XmlElement(required = true, nillable = true)
    protected String codComune;
    @XmlElement(required = true, nillable = true)
    protected String cap;
    protected long idAzienda;
    @XmlElement(required = true, nillable = true)
    protected String codSedeInailComp;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneCausaleCessazione;
    @XmlElement(required = true, nillable = true)
    protected String numeroCivico;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneAttivitaSede;
    @XmlElement(required = true, nillable = true)
    protected String idTipoSede;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioAttivita;
    @XmlElement(required = true, nillable = true)
    protected String telefono;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(required = true, nillable = true)
    protected String email;
    @XmlElement(required = true, nillable = true)
    protected String denominazioneSede;
    @XmlElement(required = true, nillable = true)
    protected String idSede;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineAttivita;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvUL;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvincia;
    @XmlElement(required = true, nillable = true)
    protected String fax;
    @XmlElement(required = true, nillable = true)
    protected String indirizzo;
    @XmlElement(required = true, nillable = true)
    protected String numeroDipendenti;

    /**
     * Gets the value of the nomeComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeComune() {
        return nomeComune;
    }

    /**
     * Sets the value of the nomeComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeComune(String value) {
        this.nomeComune = value;
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
     * Gets the value of the descrizioneTipoSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneTipoSede() {
        return descrizioneTipoSede;
    }

    /**
     * Sets the value of the descrizioneTipoSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneTipoSede(String value) {
        this.descrizioneTipoSede = value;
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
     * Gets the value of the toponimo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToponimo() {
        return toponimo;
    }

    /**
     * Sets the value of the toponimo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToponimo(String value) {
        this.toponimo = value;
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
     * Gets the value of the codiceStatoEstero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceStatoEstero() {
        return codiceStatoEstero;
    }

    /**
     * Sets the value of the codiceStatoEstero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceStatoEstero(String value) {
        this.codiceStatoEstero = value;
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
     * Gets the value of the codSedeInailComp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodSedeInailComp() {
        return codSedeInailComp;
    }

    /**
     * Sets the value of the codSedeInailComp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodSedeInailComp(String value) {
        this.codSedeInailComp = value;
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
     * Gets the value of the descrizioneAttivitaSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneAttivitaSede() {
        return descrizioneAttivitaSede;
    }

    /**
     * Sets the value of the descrizioneAttivitaSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneAttivitaSede(String value) {
        this.descrizioneAttivitaSede = value;
    }

    /**
     * Gets the value of the idTipoSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTipoSede() {
        return idTipoSede;
    }

    /**
     * Sets the value of the idTipoSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTipoSede(String value) {
        this.idTipoSede = value;
    }

    /**
     * Gets the value of the dataInizioAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioAttivita() {
        return dataInizioAttivita;
    }

    /**
     * Sets the value of the dataInizioAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioAttivita(XMLGregorianCalendar value) {
        this.dataInizioAttivita = value;
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
     * Gets the value of the dataFineAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineAttivita() {
        return dataFineAttivita;
    }

    /**
     * Sets the value of the dataFineAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineAttivita(XMLGregorianCalendar value) {
        this.dataFineAttivita = value;
    }

    /**
     * Gets the value of the siglaProvUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvUL() {
        return siglaProvUL;
    }

    /**
     * Sets the value of the siglaProvUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvUL(String value) {
        this.siglaProvUL = value;
    }

    /**
     * Gets the value of the siglaProvincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvincia() {
        return siglaProvincia;
    }

    /**
     * Sets the value of the siglaProvincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvincia(String value) {
        this.siglaProvincia = value;
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

}
