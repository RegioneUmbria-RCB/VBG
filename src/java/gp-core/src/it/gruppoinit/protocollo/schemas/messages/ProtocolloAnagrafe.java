
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProtocolloAnagrafe complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtocolloAnagrafe">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CAP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CITTA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODCOMNASCITA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODICEANAGRAFE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CODICEFISCALE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COMUNERESIDENZA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodiceIstatComNasc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodiceIstatComRes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodiceStatoEsteroNasc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodiceStatoEsteroRes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ComuneResidenza" type="{http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data}ProtocolloComune" minOccurs="0"/>
 *         &lt;element name="DATANASCITA" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DATANOMINATIVO" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="EMAIL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INDIRIZZO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mezzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModalitaTrasmissione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NOME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NOMINATIVO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PARTITAIVA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROVINCIA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PecAnagrafica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PecProtocollazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SESSO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TELEFONO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TELEFONOCELLULARE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TIPOANAGRAFE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TITOLO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtocolloAnagrafe", namespace = "http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", propOrder = {
    "cap",
    "citta",
    "codcomnascita",
    "codiceanagrafe",
    "codicefiscale",
    "comuneresidenza",
    "codiceIstatComNasc",
    "codiceIstatComRes",
    "codiceStatoEsteroNasc",
    "codiceStatoEsteroRes",
    "comuneResidenza",
    "datanascita",
    "datanominativo",
    "email",
    "fax",
    "indirizzo",
    "mezzo",
    "modalitaTrasmissione",
    "nome",
    "nominativo",
    "partitaiva",
    "provincia",
    "pecAnagrafica",
    "pecProtocollazione",
    "sesso",
    "telefono",
    "telefonocellulare",
    "tipoanagrafe",
    "titolo"
})
public class ProtocolloAnagrafe {

    @XmlElement(name = "CAP", nillable = true)
    protected String cap;
    @XmlElement(name = "CITTA", nillable = true)
    protected String citta;
    @XmlElement(name = "CODCOMNASCITA", nillable = true)
    protected String codcomnascita;
    @XmlElement(name = "CODICEANAGRAFE", nillable = true)
    protected String codiceanagrafe;
    @XmlElement(name = "CODICEFISCALE", nillable = true)
    protected String codicefiscale;
    @XmlElement(name = "COMUNERESIDENZA", nillable = true)
    protected String comuneresidenza;
    @XmlElement(name = "CodiceIstatComNasc", nillable = true)
    protected String codiceIstatComNasc;
    @XmlElement(name = "CodiceIstatComRes", nillable = true)
    protected String codiceIstatComRes;
    @XmlElement(name = "CodiceStatoEsteroNasc", nillable = true)
    protected String codiceStatoEsteroNasc;
    @XmlElement(name = "CodiceStatoEsteroRes", nillable = true)
    protected String codiceStatoEsteroRes;
    @XmlElement(name = "ComuneResidenza", nillable = true)
    protected ProtocolloComune comuneResidenza;
    @XmlElement(name = "DATANASCITA", nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datanascita;
    @XmlElement(name = "DATANOMINATIVO", nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datanominativo;
    @XmlElement(name = "EMAIL", nillable = true)
    protected String email;
    @XmlElement(name = "FAX", nillable = true)
    protected String fax;
    @XmlElement(name = "INDIRIZZO", nillable = true)
    protected String indirizzo;
    @XmlElement(name = "Mezzo", nillable = true)
    protected String mezzo;
    @XmlElement(name = "ModalitaTrasmissione", nillable = true)
    protected String modalitaTrasmissione;
    @XmlElement(name = "NOME", nillable = true)
    protected String nome;
    @XmlElement(name = "NOMINATIVO", nillable = true)
    protected String nominativo;
    @XmlElement(name = "PARTITAIVA", nillable = true)
    protected String partitaiva;
    @XmlElement(name = "PROVINCIA", nillable = true)
    protected String provincia;
    @XmlElement(name = "PecAnagrafica", nillable = true)
    protected String pecAnagrafica;
    @XmlElement(name = "PecProtocollazione", nillable = true)
    protected String pecProtocollazione;
    @XmlElement(name = "SESSO", nillable = true)
    protected String sesso;
    @XmlElement(name = "TELEFONO", nillable = true)
    protected String telefono;
    @XmlElement(name = "TELEFONOCELLULARE", nillable = true)
    protected String telefonocellulare;
    @XmlElement(name = "TIPOANAGRAFE", nillable = true)
    protected String tipoanagrafe;
    @XmlElement(name = "TITOLO", nillable = true)
    protected String titolo;

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
     * Gets the value of the codcomnascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODCOMNASCITA() {
        return codcomnascita;
    }

    /**
     * Sets the value of the codcomnascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODCOMNASCITA(String value) {
        this.codcomnascita = value;
    }

    /**
     * Gets the value of the codiceanagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEANAGRAFE() {
        return codiceanagrafe;
    }

    /**
     * Sets the value of the codiceanagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEANAGRAFE(String value) {
        this.codiceanagrafe = value;
    }

    /**
     * Gets the value of the codicefiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEFISCALE() {
        return codicefiscale;
    }

    /**
     * Sets the value of the codicefiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEFISCALE(String value) {
        this.codicefiscale = value;
    }

    /**
     * Gets the value of the comuneresidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMUNERESIDENZA() {
        return comuneresidenza;
    }

    /**
     * Sets the value of the comuneresidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMUNERESIDENZA(String value) {
        this.comuneresidenza = value;
    }

    /**
     * Gets the value of the codiceIstatComNasc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIstatComNasc() {
        return codiceIstatComNasc;
    }

    /**
     * Sets the value of the codiceIstatComNasc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIstatComNasc(String value) {
        this.codiceIstatComNasc = value;
    }

    /**
     * Gets the value of the codiceIstatComRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIstatComRes() {
        return codiceIstatComRes;
    }

    /**
     * Sets the value of the codiceIstatComRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIstatComRes(String value) {
        this.codiceIstatComRes = value;
    }

    /**
     * Gets the value of the codiceStatoEsteroNasc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceStatoEsteroNasc() {
        return codiceStatoEsteroNasc;
    }

    /**
     * Sets the value of the codiceStatoEsteroNasc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceStatoEsteroNasc(String value) {
        this.codiceStatoEsteroNasc = value;
    }

    /**
     * Gets the value of the codiceStatoEsteroRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceStatoEsteroRes() {
        return codiceStatoEsteroRes;
    }

    /**
     * Sets the value of the codiceStatoEsteroRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceStatoEsteroRes(String value) {
        this.codiceStatoEsteroRes = value;
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
     * Gets the value of the datanascita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATANASCITA() {
        return datanascita;
    }

    /**
     * Sets the value of the datanascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATANASCITA(XMLGregorianCalendar value) {
        this.datanascita = value;
    }

    /**
     * Gets the value of the datanominativo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATANOMINATIVO() {
        return datanominativo;
    }

    /**
     * Sets the value of the datanominativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATANOMINATIVO(XMLGregorianCalendar value) {
        this.datanominativo = value;
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
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOME() {
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
    public void setNOME(String value) {
        this.nome = value;
    }

    /**
     * Gets the value of the nominativo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMINATIVO() {
        return nominativo;
    }

    /**
     * Sets the value of the nominativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMINATIVO(String value) {
        this.nominativo = value;
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
     * Gets the value of the pecAnagrafica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPecAnagrafica() {
        return pecAnagrafica;
    }

    /**
     * Sets the value of the pecAnagrafica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPecAnagrafica(String value) {
        this.pecAnagrafica = value;
    }

    /**
     * Gets the value of the pecProtocollazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPecProtocollazione() {
        return pecProtocollazione;
    }

    /**
     * Sets the value of the pecProtocollazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPecProtocollazione(String value) {
        this.pecProtocollazione = value;
    }

    /**
     * Gets the value of the sesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSESSO() {
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
    public void setSESSO(String value) {
        this.sesso = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONO() {
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
    public void setTELEFONO(String value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the telefonocellulare property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONOCELLULARE() {
        return telefonocellulare;
    }

    /**
     * Sets the value of the telefonocellulare property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONOCELLULARE(String value) {
        this.telefonocellulare = value;
    }

    /**
     * Gets the value of the tipoanagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOANAGRAFE() {
        return tipoanagrafe;
    }

    /**
     * Sets the value of the tipoanagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOANAGRAFE(String value) {
        this.tipoanagrafe = value;
    }

    /**
     * Gets the value of the titolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTITOLO() {
        return titolo;
    }

    /**
     * Sets the value of the titolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTITOLO(String value) {
        this.titolo = value;
    }

}
