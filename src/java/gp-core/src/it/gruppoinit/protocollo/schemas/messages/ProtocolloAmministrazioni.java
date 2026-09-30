
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtocolloAmministrazioni complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtocolloAmministrazioni">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CODICEAMMINISTRAZIONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mezzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModalitaTrasmissione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PEC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PARTITAIVA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AMMINISTRAZIONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INDIRIZZO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EMAIL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ComuneResidenza" type="{http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data}ProtocolloComune" minOccurs="0"/>
 *         &lt;element name="CITTA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROVINCIA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CAP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TELEFONO1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROT_UO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROT_RUOLO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TELEFONO2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UFFICIO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodiceIPA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STC_IDSPORTELLO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtocolloAmministrazioni", namespace = "http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", propOrder = {
    "codiceamministrazione",
    "mezzo",
    "modalitaTrasmissione",
    "pec",
    "partitaiva",
    "amministrazione",
    "indirizzo",
    "email",
    "comuneResidenza",
    "citta",
    "provincia",
    "cap",
    "telefono1",
    "fax",
    "protuo",
    "protruolo",
    "telefono2",
    "ufficio",
    "codiceIPA",
    "stcidsportello"
})
public class ProtocolloAmministrazioni {

    @XmlElement(name = "CODICEAMMINISTRAZIONE", nillable = true)
    protected String codiceamministrazione;
    @XmlElement(name = "Mezzo", nillable = true)
    protected String mezzo;
    @XmlElement(name = "ModalitaTrasmissione", nillable = true)
    protected String modalitaTrasmissione;
    @XmlElement(name = "PEC", nillable = true)
    protected String pec;
    @XmlElement(name = "PARTITAIVA", nillable = true)
    protected String partitaiva;
    @XmlElement(name = "AMMINISTRAZIONE", nillable = true)
    protected String amministrazione;
    @XmlElement(name = "INDIRIZZO", nillable = true)
    protected String indirizzo;
    @XmlElement(name = "EMAIL", nillable = true)
    protected String email;
    @XmlElement(name = "ComuneResidenza", nillable = true)
    protected ProtocolloComune comuneResidenza;
    @XmlElement(name = "CITTA", nillable = true)
    protected String citta;
    @XmlElement(name = "PROVINCIA", nillable = true)
    protected String provincia;
    @XmlElement(name = "CAP", nillable = true)
    protected String cap;
    @XmlElement(name = "TELEFONO1", nillable = true)
    protected String telefono1;
    @XmlElement(name = "FAX", nillable = true)
    protected String fax;
    @XmlElement(name = "PROT_UO", nillable = true)
    protected String protuo;
    @XmlElement(name = "PROT_RUOLO", nillable = true)
    protected String protruolo;
    @XmlElement(name = "TELEFONO2", nillable = true)
    protected String telefono2;
    @XmlElement(name = "UFFICIO", nillable = true)
    protected String ufficio;
    @XmlElement(name = "CodiceIPA", nillable = true)
    protected String codiceIPA;
    @XmlElement(name = "STC_IDSPORTELLO", nillable = true)
    protected String stcidsportello;

    /**
     * Gets the value of the codiceamministrazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEAMMINISTRAZIONE() {
        return codiceamministrazione;
    }

    /**
     * Sets the value of the codiceamministrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEAMMINISTRAZIONE(String value) {
        this.codiceamministrazione = value;
    }

    /**
     * Gets the value of the mezzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMezzo() {
        return mezzo;
    }

    /**
     * Sets the value of the mezzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMezzo(String value) {
        this.mezzo = value;
    }

    /**
     * Gets the value of the modalitaTrasmissione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModalitaTrasmissione() {
        return modalitaTrasmissione;
    }

    /**
     * Sets the value of the modalitaTrasmissione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModalitaTrasmissione(String value) {
        this.modalitaTrasmissione = value;
    }

    /**
     * Gets the value of the pec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPEC() {
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
    public void setPEC(String value) {
        this.pec = value;
    }

    /**
     * Gets the value of the partitaiva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARTITAIVA() {
        return partitaiva;
    }

    /**
     * Sets the value of the partitaiva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARTITAIVA(String value) {
        this.partitaiva = value;
    }

    /**
     * Gets the value of the amministrazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAMMINISTRAZIONE() {
        return amministrazione;
    }

    /**
     * Sets the value of the amministrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMMINISTRAZIONE(String value) {
        this.amministrazione = value;
    }

    /**
     * Gets the value of the indirizzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDIRIZZO() {
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
    public void setINDIRIZZO(String value) {
        this.indirizzo = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMAIL() {
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
    public void setEMAIL(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the comuneResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloComune }
     *     
     */
    public ProtocolloComune getComuneResidenza() {
        return comuneResidenza;
    }

    /**
     * Sets the value of the comuneResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloComune }
     *     
     */
    public void setComuneResidenza(ProtocolloComune value) {
        this.comuneResidenza = value;
    }

    /**
     * Gets the value of the citta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCITTA() {
        return citta;
    }

    /**
     * Sets the value of the citta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCITTA(String value) {
        this.citta = value;
    }

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIA() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIA(String value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAP() {
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
    public void setCAP(String value) {
        this.cap = value;
    }

    /**
     * Gets the value of the telefono1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONO1() {
        return telefono1;
    }

    /**
     * Sets the value of the telefono1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONO1(String value) {
        this.telefono1 = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAX() {
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
    public void setFAX(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the protuo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROTUO() {
        return protuo;
    }

    /**
     * Sets the value of the protuo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROTUO(String value) {
        this.protuo = value;
    }

    /**
     * Gets the value of the protruolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROTRUOLO() {
        return protruolo;
    }

    /**
     * Sets the value of the protruolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROTRUOLO(String value) {
        this.protruolo = value;
    }

    /**
     * Gets the value of the telefono2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONO2() {
        return telefono2;
    }

    /**
     * Sets the value of the telefono2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONO2(String value) {
        this.telefono2 = value;
    }

    /**
     * Gets the value of the ufficio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFFICIO() {
        return ufficio;
    }

    /**
     * Sets the value of the ufficio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFFICIO(String value) {
        this.ufficio = value;
    }

    /**
     * Gets the value of the codiceIPA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIPA() {
        return codiceIPA;
    }

    /**
     * Sets the value of the codiceIPA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIPA(String value) {
        this.codiceIPA = value;
    }

    /**
     * Gets the value of the stcidsportello property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTCIDSPORTELLO() {
        return stcidsportello;
    }

    /**
     * Sets the value of the stcidsportello property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTCIDSPORTELLO(String value) {
        this.stcidsportello = value;
    }

}
