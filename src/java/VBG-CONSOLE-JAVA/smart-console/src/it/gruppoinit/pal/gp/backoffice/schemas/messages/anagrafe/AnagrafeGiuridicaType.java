
package it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AnagrafeGiuridicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AnagrafeGiuridicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="riferimentiAnagrafe" type="{http://gruppoinit.it/sigepro/schemas/messages/anagrafe}RiferimentiAnagrafeType" minOccurs="0"/>
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="formaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="partitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataCostituzione" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="sedeLegale" type="{http://gruppoinit.it/sigepro/schemas/messages/anagrafe}LocalizzazioneType" minOccurs="0"/>
 *         &lt;element name="corrispondenza" type="{http://gruppoinit.it/sigepro/schemas/messages/anagrafe}LocalizzazioneType" minOccurs="0"/>
 *         &lt;element name="nrCCIAA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comuneCCIAA" type="{http://gruppoinit.it/sigepro/schemas/messages/anagrafe}ComuneType" minOccurs="0"/>
 *         &lt;element name="dataCCIAA" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="nrTRIB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comuneTRIB" type="{http://gruppoinit.it/sigepro/schemas/messages/anagrafe}ComuneType" minOccurs="0"/>
 *         &lt;element name="dataTRIB" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="nrREA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provinciaREA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataREA" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cellulare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strongAuthId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="disabilitato" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="dataDisabilitato" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagrafeGiuridicaType", propOrder = {
    "riferimentiAnagrafe",
    "denominazione",
    "formaGiuridica",
    "codiceFiscale",
    "partitaIva",
    "dataCostituzione",
    "sedeLegale",
    "corrispondenza",
    "nrCCIAA",
    "comuneCCIAA",
    "dataCCIAA",
    "nrTRIB",
    "comuneTRIB",
    "dataTRIB",
    "nrREA",
    "provinciaREA",
    "dataREA",
    "telefono",
    "cellulare",
    "fax",
    "email",
    "pec",
    "referente",
    "strongAuthId",
    "password",
    "disabilitato",
    "dataDisabilitato",
    "note"
})
public class AnagrafeGiuridicaType {

    protected RiferimentiAnagrafeType riferimentiAnagrafe;
    @XmlElement(required = true)
    protected String denominazione;
    protected String formaGiuridica;
    @XmlElement(required = true)
    protected String codiceFiscale;
    @XmlElement(required = true)
    protected String partitaIva;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataCostituzione;
    protected LocalizzazioneType sedeLegale;
    protected LocalizzazioneType corrispondenza;
    protected String nrCCIAA;
    protected ComuneType comuneCCIAA;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataCCIAA;
    protected String nrTRIB;
    protected ComuneType comuneTRIB;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataTRIB;
    protected String nrREA;
    protected String provinciaREA;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataREA;
    protected String telefono;
    protected String cellulare;
    protected String fax;
    protected String email;
    protected String pec;
    protected String referente;
    protected String strongAuthId;
    protected String password;
    protected Boolean disabilitato;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataDisabilitato;
    protected String note;

    /**
     * Gets the value of the riferimentiAnagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentiAnagrafeType }
     *     
     */
    public RiferimentiAnagrafeType getRiferimentiAnagrafe() {
        return riferimentiAnagrafe;
    }

    /**
     * Sets the value of the riferimentiAnagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentiAnagrafeType }
     *     
     */
    public void setRiferimentiAnagrafe(RiferimentiAnagrafeType value) {
        this.riferimentiAnagrafe = value;
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
     * Gets the value of the formaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormaGiuridica() {
        return formaGiuridica;
    }

    /**
     * Sets the value of the formaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormaGiuridica(String value) {
        this.formaGiuridica = value;
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
     * Gets the value of the sedeLegale property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizzazioneType }
     *     
     */
    public LocalizzazioneType getSedeLegale() {
        return sedeLegale;
    }

    /**
     * Sets the value of the sedeLegale property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizzazioneType }
     *     
     */
    public void setSedeLegale(LocalizzazioneType value) {
        this.sedeLegale = value;
    }

    /**
     * Gets the value of the corrispondenza property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizzazioneType }
     *     
     */
    public LocalizzazioneType getCorrispondenza() {
        return corrispondenza;
    }

    /**
     * Sets the value of the corrispondenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizzazioneType }
     *     
     */
    public void setCorrispondenza(LocalizzazioneType value) {
        this.corrispondenza = value;
    }

    /**
     * Gets the value of the nrCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrCCIAA() {
        return nrCCIAA;
    }

    /**
     * Sets the value of the nrCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNrCCIAA(String value) {
        this.nrCCIAA = value;
    }

    /**
     * Gets the value of the comuneCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link ComuneType }
     *     
     */
    public ComuneType getComuneCCIAA() {
        return comuneCCIAA;
    }

    /**
     * Sets the value of the comuneCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComuneType }
     *     
     */
    public void setComuneCCIAA(ComuneType value) {
        this.comuneCCIAA = value;
    }

    /**
     * Gets the value of the dataCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCCIAA() {
        return dataCCIAA;
    }

    /**
     * Sets the value of the dataCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCCIAA(XMLGregorianCalendar value) {
        this.dataCCIAA = value;
    }

    /**
     * Gets the value of the nrTRIB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrTRIB() {
        return nrTRIB;
    }

    /**
     * Sets the value of the nrTRIB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNrTRIB(String value) {
        this.nrTRIB = value;
    }

    /**
     * Gets the value of the comuneTRIB property.
     * 
     * @return
     *     possible object is
     *     {@link ComuneType }
     *     
     */
    public ComuneType getComuneTRIB() {
        return comuneTRIB;
    }

    /**
     * Sets the value of the comuneTRIB property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComuneType }
     *     
     */
    public void setComuneTRIB(ComuneType value) {
        this.comuneTRIB = value;
    }

    /**
     * Gets the value of the dataTRIB property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataTRIB() {
        return dataTRIB;
    }

    /**
     * Sets the value of the dataTRIB property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataTRIB(XMLGregorianCalendar value) {
        this.dataTRIB = value;
    }

    /**
     * Gets the value of the nrREA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrREA() {
        return nrREA;
    }

    /**
     * Sets the value of the nrREA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNrREA(String value) {
        this.nrREA = value;
    }

    /**
     * Gets the value of the provinciaREA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaREA() {
        return provinciaREA;
    }

    /**
     * Sets the value of the provinciaREA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaREA(String value) {
        this.provinciaREA = value;
    }

    /**
     * Gets the value of the dataREA property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataREA() {
        return dataREA;
    }

    /**
     * Sets the value of the dataREA property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataREA(XMLGregorianCalendar value) {
        this.dataREA = value;
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
     * Gets the value of the cellulare property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellulare() {
        return cellulare;
    }

    /**
     * Sets the value of the cellulare property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellulare(String value) {
        this.cellulare = value;
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
     * Gets the value of the pec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPec() {
        return pec;
    }

    /**
     * Sets the value of the pec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPec(String value) {
        this.pec = value;
    }

    /**
     * Gets the value of the referente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferente() {
        return referente;
    }

    /**
     * Sets the value of the referente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferente(String value) {
        this.referente = value;
    }

    /**
     * Gets the value of the strongAuthId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrongAuthId() {
        return strongAuthId;
    }

    /**
     * Sets the value of the strongAuthId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrongAuthId(String value) {
        this.strongAuthId = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the disabilitato property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDisabilitato() {
        return disabilitato;
    }

    /**
     * Sets the value of the disabilitato property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDisabilitato(Boolean value) {
        this.disabilitato = value;
    }

    /**
     * Gets the value of the dataDisabilitato property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDisabilitato() {
        return dataDisabilitato;
    }

    /**
     * Sets the value of the dataDisabilitato property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDisabilitato(XMLGregorianCalendar value) {
        this.dataDisabilitato = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

}
