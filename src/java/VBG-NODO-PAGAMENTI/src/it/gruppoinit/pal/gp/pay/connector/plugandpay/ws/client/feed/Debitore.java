
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.common.TipoPagatore;


/**
 * <pClasse Java per Debitore complex type.
 * 
 * <pIl seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre
 * &lt;complexType name="Debitore"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Cellulare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Civico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceAvviamentoPostale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceFiscalePartitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Localita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Nazione" type="{http://e-fil.eu/PnP/PlugAndPayFeed}Nazione"/&gt;
 *         &lt;element name="Nominativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Provincia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TipoPagatore" type="{http://e-fil.eu/PnP/PlugAndPayCommon}TipoPagatore"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Debitore", propOrder = {
    "cellulare",
    "civico",
    "codiceAvviamentoPostale",
    "codiceFiscalePartitaIva",
    "email",
    "indirizzo",
    "localita",
    "nazione",
    "nominativo",
    "provincia",
    "tipoPagatore"
})
public class Debitore {

    @XmlElement(name = "Cellulare", required = false)
    protected String cellulare;
    @XmlElement(name = "Civico", required = false)
    protected String civico;
    @XmlElement(name = "CodiceAvviamentoPostale", required = false)
    protected String codiceAvviamentoPostale;
    @XmlElement(name = "CodiceFiscalePartitaIva", required = true, nillable = true)
    protected String codiceFiscalePartitaIva;
    @XmlElement(name = "Email", required = false)
    protected String email;
    @XmlElement(name = "Indirizzo", required = false)
    protected String indirizzo;
    @XmlElement(name = "Localita", required = false)
    protected String localita;
    @XmlElement(name = "Nazione", required = true, nillable = true)
    protected Nazione nazione;
    @XmlElement(name = "Nominativo", required = true, nillable = true)
    protected String nominativo;
    @XmlElement(name = "Provincia", required = false)
    protected String provincia;
    @XmlElement(name = "TipoPagatore", required = true)
    @XmlSchemaType(name = "string")
    protected TipoPagatore tipoPagatore;

    /**
     * Recupera il valore della proprietà cellulare.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getCellulare() {
        return cellulare;
    }

    /**
     * Imposta il valore della proprietà cellulare.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setCellulare(String value) {
        this.cellulare = value;
    }

    /**
     * Recupera il valore della proprietà civico.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getCivico() {
        return civico;
    }

    /**
     * Imposta il valore della proprietà civico.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setCivico(String value) {
        this.civico = value;
    }

    /**
     * Recupera il valore della proprietà codiceAvviamentoPostale.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getCodiceAvviamentoPostale() {
        return codiceAvviamentoPostale;
    }

    /**
     * Imposta il valore della proprietà codiceAvviamentoPostale.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setCodiceAvviamentoPostale(String value) {
        this.codiceAvviamentoPostale = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscalePartitaIva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscalePartitaIva() {
        return codiceFiscalePartitaIva;
    }

    /**
     * Imposta il valore della proprietà codiceFiscalePartitaIva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscalePartitaIva(String value) {
        this.codiceFiscalePartitaIva = value;
    }

    /**
     * Recupera il valore della proprietà email.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta il valore della proprietà email.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Recupera il valore della proprietà indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della proprietà indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della proprietà localita.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getLocalita() {
        return localita;
    }

    /**
     * Imposta il valore della proprietà localita.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setLocalita(String value) {
        this.localita = value;
    }

    /**
     * Recupera il valore della proprietà nazione.
     * 
     * @return
     *     possible object is
     *     {@link Nazione }
     *     
     */
    public Nazione getNazione() {
        return nazione;
    }

    /**
     * Imposta il valore della proprietà nazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Nazione }
     *     
     */
    public void setNazione(Nazione value) {
        this.nazione = value;
    }

    /**
     * Recupera il valore della proprietà nominativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominativo() {
        return nominativo;
    }

    /**
     * Imposta il valore della proprietà nominativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominativo(String value) {
        this.nominativo = value;
    }

    /**
     * Recupera il valore della proprietà provincia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Imposta il valore della proprietà provincia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setProvincia(String value) {
        this.provincia = value;
    }

    /**
     * Recupera il valore della proprietà tipoPagatore.
     * 
     * @return
     *     possible object is
     *     {@link TipoPagatore }
     *     
     */
    public TipoPagatore getTipoPagatore() {
        return tipoPagatore;
    }

    /**
     * Imposta il valore della proprietà tipoPagatore.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoPagatore }
     *     
     */
    public void setTipoPagatore(TipoPagatore value) {
        this.tipoPagatore = value;
    }

}
