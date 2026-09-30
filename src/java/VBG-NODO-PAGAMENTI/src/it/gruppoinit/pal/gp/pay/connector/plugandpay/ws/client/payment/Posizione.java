
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.common.Servizio;


/**
 * <pClasse Java per Posizione complex type.
 * 
 * <pIl seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre
 * &lt;complexType name="Posizione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Accertamenti" type="{http://e-fil.eu/PnP/PlugAndPayPayment}ArrayOfAccertamento" minOccurs="0"/&gt;
 *         &lt;element name="Causale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CodiceRiferimentoCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Creditore" type="{http://e-fil.eu/PnP/PlugAndPayPayment}Creditore"/&gt;
 *         &lt;element name="DataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="Debitore" type="{http://e-fil.eu/PnP/PlugAndPayPayment}Debitore"/&gt;
 *         &lt;element name="EmailInvioRicevuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IdentificativoPosizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportoInCentesimi" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ParametriPosizione" type="{http://e-fil.eu/PnP/PlugAndPayPayment}ArrayOfParametroPosizione"/&gt;
 *         &lt;element name="Servizio" type="{http://e-fil.eu/PnP/PlugAndPayCommon}Servizio"/&gt;
 *         &lt;element name="Spontaneo" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="TipoRiferimentoCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Posizione", propOrder = {
    "accertamenti",
    "causale",
    "codiceRiferimentoCreditore",
    "creditore",
    "dataScadenza",
    "debitore",
    "emailInvioRicevuta",
    "identificativoPosizione",
    "importoInCentesimi",
    "parametriPosizione",
    "servizio",
    "spontaneo",
    "tipoRiferimentoCreditore"
})
public class Posizione {

    @XmlElement(name = "Accertamenti", required = false)
    protected ArrayOfAccertamento accertamenti;
    @XmlElement(name = "Causale", required = true, nillable = true)
    protected String causale;
    @XmlElement(name = "CodiceRiferimentoCreditore", required = false)
    protected String codiceRiferimentoCreditore;
    @XmlElement(name = "Creditore", required = true, nillable = true)
    protected Creditore creditore;
    @XmlElement(name = "DataScadenza", required = false, nillable = true)
    protected XMLGregorianCalendar dataScadenza;
    @XmlElement(name = "Debitore", required = true, nillable = true)
    protected Debitore debitore;
    @XmlElement(name = "EmailInvioRicevuta", required = false)
    protected String emailInvioRicevuta;
    @XmlElement(name = "IdentificativoPosizione", required = false)
    protected String identificativoPosizione;
    @XmlElement(name = "ImportoInCentesimi")
    protected int importoInCentesimi;
    @XmlElement(name = "ParametriPosizione", required = true, nillable = true)
    protected ArrayOfParametroPosizione parametriPosizione;
    @XmlElement(name = "Servizio", required = true, nillable = true)
    protected Servizio servizio;
    @XmlElement(name = "Spontaneo")
    protected boolean spontaneo;
    @XmlElement(name = "TipoRiferimentoCreditore", required = false)
    protected String tipoRiferimentoCreditore;
    
    /**
     * Recupera il valore della proprietà accertamenti.
     * 
     * @return
     *     possible object is
     *     @link ArrayOfAccertamento
     *     
     */
    public ArrayOfAccertamento getAccertamenti() {
        return accertamenti;
    }

    /**
     * Imposta il valore della proprietà accertamenti.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAccertamento }
     *     
     */
    public void setAccertamenti(ArrayOfAccertamento value) {
        this.accertamenti = value;
    }
    
    /**
     * Recupera il valore della proprietà causale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausale() {
        return causale;
    }

    /**
     * Imposta il valore della proprietà causale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausale(String value) {
        this.causale = value;
    }

    /**
     * Recupera il valore della proprietà codiceRiferimentoCreditore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getCodiceRiferimentoCreditore() {
        return codiceRiferimentoCreditore;
    }

    /**
     * Imposta il valore della proprietà codiceRiferimentoCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setCodiceRiferimentoCreditore(String value) {
        this.codiceRiferimentoCreditore = value;
    }

    /**
     * Recupera il valore della proprietà creditore.
     * 
     * @return
     *     possible object is
     *     {@link Creditore }
     *     
     */
    public Creditore getCreditore() {
        return creditore;
    }

    /**
     * Imposta il valore della proprietà creditore.
     * 
     * @param value
     *     allowed object is
     *     {@link Creditore }
     *     
     */
    public void setCreditore(Creditore value) {
        this.creditore = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenza.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code }
     *     
     */
    public XMLGregorianCalendar getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Imposta il valore della proprietà dataScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

    /**
     * Recupera il valore della proprietà debitore.
     * 
     * @return
     *     possible object is
     *     {@link Debitore }
     *     
     */
    public Debitore getDebitore() {
        return debitore;
    }

    /**
     * Imposta il valore della proprietà debitore.
     * 
     * @param value
     *     allowed object is
     *     {@link Debitore }
     *     
     */
    public void setDebitore(Debitore value) {
        this.debitore = value;
    }

    /**
     * Recupera il valore della proprietà emailInvioRicevuta.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getEmailInvioRicevuta() {
        return emailInvioRicevuta;
    }

    /**
     * Imposta il valore della proprietà emailInvioRicevuta.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setEmailInvioRicevuta(String value) {
        this.emailInvioRicevuta = value;
    }

    /**
     * Recupera il valore della proprietà identificativoPosizione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getIdentificativoPosizione() {
        return identificativoPosizione;
    }

    /**
     * Imposta il valore della proprietà identificativoPosizione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setIdentificativoPosizione(String value) {
        this.identificativoPosizione = value;
    }

    /**
     * Recupera il valore della proprietà importoInCentesimi.
     * 
     */
    public int getImportoInCentesimi() {
        return importoInCentesimi;
    }

    /**
     * Imposta il valore della proprietà importoInCentesimi.
     * 
     */
    public void setImportoInCentesimi(int value) {
        this.importoInCentesimi = value;
    }

    /**
     * Recupera il valore della proprietà parametriPosizione.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfParametroPosizione }
     *     
     */
    public ArrayOfParametroPosizione getParametriPosizione() {
        return parametriPosizione;
    }

    /**
     * Imposta il valore della proprietà parametriPosizione.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfParametroPosizione }
     *     
     */
    public void setParametriPosizione(ArrayOfParametroPosizione value) {
        this.parametriPosizione = value;
    }

    /**
     * Recupera il valore della proprietà servizio.
     * 
     * @return
     *     possible object is
     *     {@link Servizio }
     *     
     */
    public Servizio getServizio() {
        return servizio;
    }

    /**
     * Imposta il valore della proprietà servizio.
     * 
     * @param value
     *     allowed object is
     *     {@link Servizio }
     *     
     */
    public void setServizio(Servizio value) {
        this.servizio = value;
    }

    /**
     * Recupera il valore della proprietà spontaneo.
     * 
     */
    public boolean isSpontaneo() {
        return spontaneo;
    }

    /**
     * Imposta il valore della proprietà spontaneo.
     * 
     */
    public void setSpontaneo(boolean value) {
        this.spontaneo = value;
    }

    /**
     * Recupera il valore della proprietà tipoRiferimentoCreditore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getTipoRiferimentoCreditore() {
        return tipoRiferimentoCreditore;
    }

    /**
     * Imposta il valore della proprietà tipoRiferimentoCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setTipoRiferimentoCreditore(String value) {
        this.tipoRiferimentoCreditore = value;
    }

}
