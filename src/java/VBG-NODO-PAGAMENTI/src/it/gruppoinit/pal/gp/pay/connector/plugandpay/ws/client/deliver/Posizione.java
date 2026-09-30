
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.common.Servizio;


/**
 * <p>Classe Java per Posizione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Posizione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Causale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceRiferimentoCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Creditore" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}Creditore" minOccurs="0"/&gt;
 *         &lt;element name="DataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="Debitore" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}Debitore" minOccurs="0"/&gt;
 *	   &lt;element name="IdentificativoFlusso" minOccurs="0" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *         &lt;element name="IdentificativoPosizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportoInCentesimi" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="NumeroAvviso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ParametriPosizione" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}ArrayOfParametroPosizione" minOccurs="0"/&gt;
 *         &lt;element name="Servizio" type="{http://e-fil.eu/PnP/PlugAndPayCommon}Servizio" minOccurs="0"/&gt;
 *         &lt;element name="StatoPosizione" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}StatoPosizione" minOccurs="0"/&gt;
 *         &lt;element name="TipoRiferimentoCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Posizione", propOrder = {
    "causale",
    "codiceRiferimentoCreditore",
    "creditore",
    "dataScadenza",
    "debitore",
    "identificativoFlusso",
    "identificativoPosizione",
    "importoInCentesimi",
    "numeroAvviso",
    "parametriPosizione",
    "servizio",
    "statoPosizione",
    "tipoRiferimentoCreditore"
})
public class Posizione {

    @XmlElementRef(name = "Causale", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> causale;
    @XmlElementRef(name = "CodiceRiferimentoCreditore", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codiceRiferimentoCreditore;
    @XmlElementRef(name = "Creditore", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<Creditore> creditore;
    @XmlElementRef(name = "DataScadenza", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dataScadenza;
    @XmlElementRef(name = "Debitore", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<Debitore> debitore;
    @XmlElementRef(name = "IdentificativoFlusso", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> identificativoFlusso;
    @XmlElementRef(name = "IdentificativoPosizione", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> identificativoPosizione;
    @XmlElement(name = "ImportoInCentesimi")
    protected Long importoInCentesimi;
    @XmlElementRef(name = "NumeroAvviso", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> numeroAvviso;
    @XmlElementRef(name = "ParametriPosizione", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfParametroPosizione> parametriPosizione;
    @XmlElementRef(name = "Servizio", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<Servizio> servizio;
    @XmlElement(name = "StatoPosizione")
    @XmlSchemaType(name = "string")
    protected StatoPosizione statoPosizione;
    @XmlElementRef(name = "TipoRiferimentoCreditore", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tipoRiferimentoCreditore;

    /**
     * Recupera il valore della proprietà causale.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCausale() {
        return causale;
    }

    /**
     * Imposta il valore della proprietà causale.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCausale(JAXBElement<String> value) {
        this.causale = value;
    }

    /**
     * Recupera il valore della proprietà codiceRiferimentoCreditore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodiceRiferimentoCreditore() {
        return codiceRiferimentoCreditore;
    }

    /**
     * Imposta il valore della proprietà codiceRiferimentoCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodiceRiferimentoCreditore(JAXBElement<String> value) {
        this.codiceRiferimentoCreditore = value;
    }

    /**
     * Recupera il valore della proprietà creditore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Creditore }{@code >}
     *     
     */
    public JAXBElement<Creditore> getCreditore() {
        return creditore;
    }

    /**
     * Imposta il valore della proprietà creditore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Creditore }{@code >}
     *     
     */
    public void setCreditore(JAXBElement<Creditore> value) {
        this.creditore = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenza.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Imposta il valore della proprietà dataScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataScadenza(JAXBElement<XMLGregorianCalendar> value) {
        this.dataScadenza = value;
    }

    /**
     * Recupera il valore della proprietà debitore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Debitore }{@code >}
     *     
     */
    public JAXBElement<Debitore> getDebitore() {
        return debitore;
    }

    /**
     * Imposta il valore della proprietà debitore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Debitore }{@code >}
     *     
     */
    public void setDebitore(JAXBElement<Debitore> value) {
        this.debitore = value;
    }
    
    /**
     * Recupera il valore della proprietà identificativoFlusso.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Debitore }{@code >}
     *     
     */
    public JAXBElement<String> getIdentificativoFlusso() {
    
        return identificativoFlusso;
    }

    /**
     * Imposta il valore della proprietà identificativoFlusso.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Debitore }{@code >}
     *     
     */
    public void setIdentificativoFlusso(JAXBElement<String> identificativoFlusso) {
    
        this.identificativoFlusso = identificativoFlusso;
    }

    /**
     * Recupera il valore della proprietà identificativoPosizione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdentificativoPosizione() {
        return identificativoPosizione;
    }

    /**
     * Imposta il valore della proprietà identificativoPosizione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdentificativoPosizione(JAXBElement<String> value) {
        this.identificativoPosizione = value;
    }

    /**
     * Recupera il valore della proprietà importoInCentesimi.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getImportoInCentesimi() {
        return importoInCentesimi;
    }

    /**
     * Imposta il valore della proprietà importoInCentesimi.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setImportoInCentesimi(Long value) {
        this.importoInCentesimi = value;
    }

    /**
     * Recupera il valore della proprietà numeroAvviso.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNumeroAvviso() {
        return numeroAvviso;
    }

    /**
     * Imposta il valore della proprietà numeroAvviso.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNumeroAvviso(JAXBElement<String> value) {
        this.numeroAvviso = value;
    }

    /**
     * Recupera il valore della proprietà parametriPosizione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfParametroPosizione }{@code >}
     *     
     */
    public JAXBElement<ArrayOfParametroPosizione> getParametriPosizione() {
        return parametriPosizione;
    }

    /**
     * Imposta il valore della proprietà parametriPosizione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfParametroPosizione }{@code >}
     *     
     */
    public void setParametriPosizione(JAXBElement<ArrayOfParametroPosizione> value) {
        this.parametriPosizione = value;
    }

    /**
     * Recupera il valore della proprietà servizio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Servizio }{@code >}
     *     
     */
    public JAXBElement<Servizio> getServizio() {
        return servizio;
    }

    /**
     * Imposta il valore della proprietà servizio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Servizio }{@code >}
     *     
     */
    public void setServizio(JAXBElement<Servizio> value) {
        this.servizio = value;
    }

    /**
     * Recupera il valore della proprietà statoPosizione.
     * 
     * @return
     *     possible object is
     *     {@link StatoPosizione }
     *     
     */
    public StatoPosizione getStatoPosizione() {
        return statoPosizione;
    }

    /**
     * Imposta il valore della proprietà statoPosizione.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoPosizione }
     *     
     */
    public void setStatoPosizione(StatoPosizione value) {
        this.statoPosizione = value;
    }

    /**
     * Recupera il valore della proprietà tipoRiferimentoCreditore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoRiferimentoCreditore() {
        return tipoRiferimentoCreditore;
    }

    /**
     * Imposta il valore della proprietà tipoRiferimentoCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoRiferimentoCreditore(JAXBElement<String> value) {
        this.tipoRiferimentoCreditore = value;
    }

}
