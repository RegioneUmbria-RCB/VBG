
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Restituisce le proprietà
 * 				del Pagamento Atteso
 * 			
 * 
 * <p>Classe Java per getPagamentoAttesoWs complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getPagamentoAttesoWs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="idBoPagamentoAtteso"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="100"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="codiceServizio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codiceEnte" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="partiteDebitorieWs" type="{it/lineacomune/pagopa/be/ws/endpoint/shared}partitaDebitoriaWs" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;element name="anagCap" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="anagCfPiva"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="16"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagCivico" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="anagDenominazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="anagEmail"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;pattern value="[^@]+@[^\.]+\..+"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagIndirizzo"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;pattern value="[^@]+@[^\.]+\..+"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagLocalita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="anagNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="anagNazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="anagProvincia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="causale"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="100"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="importo"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="iuv" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="17"/&gt;
 *               &lt;maxLength value="18"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="visibileSol" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="dataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="dataScadenzaStampabile" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="dataScadenzaNote" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="annullato" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="canalePagamentoExtraPagoPa"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="dataAnnullamento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="dataIncasso" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="dataPagamento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="dataRendicontazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="datiSpecificiRiscossione"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="138"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="esitoPagamentoComunicato" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="extraPagoPa" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="incassato" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="numeroAvviso"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="18"/&gt;
 *               &lt;maxLength value="18"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="pagato" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="rendicontato" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ricevutaTelematicaWs" type="{it/lineacomune/pagopa/be/ws/endpoint/shared}ricevutaTelematicaWs" minOccurs="0"/&gt;
 *         &lt;element name="pagabile" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="codiceNonPagabile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="datiRevoca" type="{it/lineacomune/pagopa/be/ws/endpoint/shared}datiRevoca" minOccurs="0"/&gt;
 *         &lt;element name="ravvedibile" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="codiceNonRavvedibile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPagamentoAttesoWs", propOrder = {
    "id",
    "idBoPagamentoAtteso",
    "codiceServizio",
    "codiceEnte",
    "partiteDebitorieWs",
    "anagCap",
    "anagCfPiva",
    "anagCivico",
    "anagDenominazione",
    "anagEmail",
    "anagIndirizzo",
    "anagLocalita",
    "anagNaturaGiuridica",
    "anagNazione",
    "anagProvincia",
    "causale",
    "importo",
    "iuv",
    "visibileSol",
    "dataInizioValidita",
    "dataScadenza",
    "dataScadenzaStampabile",
    "dataScadenzaNote",
    "annullato",
    "canalePagamentoExtraPagoPa",
    "dataAnnullamento",
    "dataIncasso",
    "dataPagamento",
    "dataRendicontazione",
    "datiSpecificiRiscossione",
    "esitoPagamentoComunicato",
    "extraPagoPa",
    "incassato",
    "numeroAvviso",
    "pagato",
    "rendicontato",
    "ricevutaTelematicaWs",
    "pagabile",
    "codiceNonPagabile",
    "datiRevoca",
    "ravvedibile",
    "codiceNonRavvedibile"
})
public class GetPagamentoAttesoWs {

    protected long id;
    @XmlElement(required = true)
    protected String idBoPagamentoAtteso;
    @XmlElement(required = true)
    protected String codiceServizio;
    @XmlElement(required = true)
    protected String codiceEnte;
    protected List<PartitaDebitoriaWs> partiteDebitorieWs;
    @XmlElement(required = true)
    protected String anagCap;
    @XmlElement(required = true)
    protected String anagCfPiva;
    @XmlElement(required = true)
    protected String anagCivico;
    @XmlElement(required = true)
    protected String anagDenominazione;
    @XmlElement(required = true)
    protected String anagEmail;
    @XmlElement(required = true)
    protected String anagIndirizzo;
    @XmlElement(required = true)
    protected String anagLocalita;
    @XmlElement(required = true)
    protected String anagNaturaGiuridica;
    @XmlElement(required = true)
    protected String anagNazione;
    @XmlElement(required = true)
    protected String anagProvincia;
    @XmlElement(required = true)
    protected String causale;
    @XmlElement(required = true)
    protected String importo;
    protected String iuv;
    protected boolean visibileSol;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadenza;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadenzaStampabile;
    @XmlElement(required = true)
    protected String dataScadenzaNote;
    protected boolean annullato;
    @XmlElement(required = true)
    protected String canalePagamentoExtraPagoPa;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAnnullamento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataIncasso;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPagamento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRendicontazione;
    @XmlElement(required = true)
    protected String datiSpecificiRiscossione;
    protected boolean esitoPagamentoComunicato;
    protected boolean extraPagoPa;
    protected boolean incassato;
    @XmlElement(required = true)
    protected String numeroAvviso;
    protected boolean pagato;
    protected boolean rendicontato;
    protected RicevutaTelematicaWs ricevutaTelematicaWs;
    protected boolean pagabile;
    @XmlElement(required = true, nillable = true)
    protected String codiceNonPagabile;
    protected DatiRevoca datiRevoca;
    protected boolean ravvedibile;
    @XmlElement(required = true, nillable = true)
    protected String codiceNonRavvedibile;

    /**
     * Recupera il valore della proprietà id.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà idBoPagamentoAtteso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdBoPagamentoAtteso() {
        return idBoPagamentoAtteso;
    }

    /**
     * Imposta il valore della proprietà idBoPagamentoAtteso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdBoPagamentoAtteso(String value) {
        this.idBoPagamentoAtteso = value;
    }

    /**
     * Recupera il valore della proprietà codiceServizio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceServizio() {
        return codiceServizio;
    }

    /**
     * Imposta il valore della proprietà codiceServizio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceServizio(String value) {
        this.codiceServizio = value;
    }

    /**
     * Recupera il valore della proprietà codiceEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEnte() {
        return codiceEnte;
    }

    /**
     * Imposta il valore della proprietà codiceEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEnte(String value) {
        this.codiceEnte = value;
    }

    /**
     * Gets the value of the partiteDebitorieWs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partiteDebitorieWs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPartiteDebitorieWs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PartitaDebitoriaWs }
     * 
     * 
     */
    public List<PartitaDebitoriaWs> getPartiteDebitorieWs() {
        if (partiteDebitorieWs == null) {
            partiteDebitorieWs = new ArrayList<PartitaDebitoriaWs>();
        }
        return this.partiteDebitorieWs;
    }

    /**
     * Recupera il valore della proprietà anagCap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagCap() {
        return anagCap;
    }

    /**
     * Imposta il valore della proprietà anagCap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagCap(String value) {
        this.anagCap = value;
    }

    /**
     * Recupera il valore della proprietà anagCfPiva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagCfPiva() {
        return anagCfPiva;
    }

    /**
     * Imposta il valore della proprietà anagCfPiva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagCfPiva(String value) {
        this.anagCfPiva = value;
    }

    /**
     * Recupera il valore della proprietà anagCivico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagCivico() {
        return anagCivico;
    }

    /**
     * Imposta il valore della proprietà anagCivico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagCivico(String value) {
        this.anagCivico = value;
    }

    /**
     * Recupera il valore della proprietà anagDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagDenominazione() {
        return anagDenominazione;
    }

    /**
     * Imposta il valore della proprietà anagDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagDenominazione(String value) {
        this.anagDenominazione = value;
    }

    /**
     * Recupera il valore della proprietà anagEmail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagEmail() {
        return anagEmail;
    }

    /**
     * Imposta il valore della proprietà anagEmail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagEmail(String value) {
        this.anagEmail = value;
    }

    /**
     * Recupera il valore della proprietà anagIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagIndirizzo() {
        return anagIndirizzo;
    }

    /**
     * Imposta il valore della proprietà anagIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagIndirizzo(String value) {
        this.anagIndirizzo = value;
    }

    /**
     * Recupera il valore della proprietà anagLocalita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagLocalita() {
        return anagLocalita;
    }

    /**
     * Imposta il valore della proprietà anagLocalita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagLocalita(String value) {
        this.anagLocalita = value;
    }

    /**
     * Recupera il valore della proprietà anagNaturaGiuridica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagNaturaGiuridica() {
        return anagNaturaGiuridica;
    }

    /**
     * Imposta il valore della proprietà anagNaturaGiuridica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagNaturaGiuridica(String value) {
        this.anagNaturaGiuridica = value;
    }

    /**
     * Recupera il valore della proprietà anagNazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagNazione() {
        return anagNazione;
    }

    /**
     * Imposta il valore della proprietà anagNazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagNazione(String value) {
        this.anagNazione = value;
    }

    /**
     * Recupera il valore della proprietà anagProvincia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagProvincia() {
        return anagProvincia;
    }

    /**
     * Imposta il valore della proprietà anagProvincia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagProvincia(String value) {
        this.anagProvincia = value;
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
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporto(String value) {
        this.importo = value;
    }

    /**
     * Recupera il valore della proprietà iuv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIuv() {
        return iuv;
    }

    /**
     * Imposta il valore della proprietà iuv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIuv(String value) {
        this.iuv = value;
    }

    /**
     * Recupera il valore della proprietà visibileSol.
     * 
     */
    public boolean isVisibileSol() {
        return visibileSol;
    }

    /**
     * Imposta il valore della proprietà visibileSol.
     * 
     */
    public void setVisibileSol(boolean value) {
        this.visibileSol = value;
    }

    /**
     * Recupera il valore della proprietà dataInizioValidita.
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
     * Imposta il valore della proprietà dataInizioValidita.
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
     * Recupera il valore della proprietà dataScadenza.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
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
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenzaStampabile.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenzaStampabile() {
        return dataScadenzaStampabile;
    }

    /**
     * Imposta il valore della proprietà dataScadenzaStampabile.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenzaStampabile(XMLGregorianCalendar value) {
        this.dataScadenzaStampabile = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenzaNote.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataScadenzaNote() {
        return dataScadenzaNote;
    }

    /**
     * Imposta il valore della proprietà dataScadenzaNote.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataScadenzaNote(String value) {
        this.dataScadenzaNote = value;
    }

    /**
     * Recupera il valore della proprietà annullato.
     * 
     */
    public boolean isAnnullato() {
        return annullato;
    }

    /**
     * Imposta il valore della proprietà annullato.
     * 
     */
    public void setAnnullato(boolean value) {
        this.annullato = value;
    }

    /**
     * Recupera il valore della proprietà canalePagamentoExtraPagoPa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanalePagamentoExtraPagoPa() {
        return canalePagamentoExtraPagoPa;
    }

    /**
     * Imposta il valore della proprietà canalePagamentoExtraPagoPa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanalePagamentoExtraPagoPa(String value) {
        this.canalePagamentoExtraPagoPa = value;
    }

    /**
     * Recupera il valore della proprietà dataAnnullamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAnnullamento() {
        return dataAnnullamento;
    }

    /**
     * Imposta il valore della proprietà dataAnnullamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAnnullamento(XMLGregorianCalendar value) {
        this.dataAnnullamento = value;
    }

    /**
     * Recupera il valore della proprietà dataIncasso.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataIncasso() {
        return dataIncasso;
    }

    /**
     * Imposta il valore della proprietà dataIncasso.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataIncasso(XMLGregorianCalendar value) {
        this.dataIncasso = value;
    }

    /**
     * Recupera il valore della proprietà dataPagamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPagamento() {
        return dataPagamento;
    }

    /**
     * Imposta il valore della proprietà dataPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPagamento(XMLGregorianCalendar value) {
        this.dataPagamento = value;
    }

    /**
     * Recupera il valore della proprietà dataRendicontazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRendicontazione() {
        return dataRendicontazione;
    }

    /**
     * Imposta il valore della proprietà dataRendicontazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRendicontazione(XMLGregorianCalendar value) {
        this.dataRendicontazione = value;
    }

    /**
     * Recupera il valore della proprietà datiSpecificiRiscossione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiSpecificiRiscossione() {
        return datiSpecificiRiscossione;
    }

    /**
     * Imposta il valore della proprietà datiSpecificiRiscossione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiSpecificiRiscossione(String value) {
        this.datiSpecificiRiscossione = value;
    }

    /**
     * Recupera il valore della proprietà esitoPagamentoComunicato.
     * 
     */
    public boolean isEsitoPagamentoComunicato() {
        return esitoPagamentoComunicato;
    }

    /**
     * Imposta il valore della proprietà esitoPagamentoComunicato.
     * 
     */
    public void setEsitoPagamentoComunicato(boolean value) {
        this.esitoPagamentoComunicato = value;
    }

    /**
     * Recupera il valore della proprietà extraPagoPa.
     * 
     */
    public boolean isExtraPagoPa() {
        return extraPagoPa;
    }

    /**
     * Imposta il valore della proprietà extraPagoPa.
     * 
     */
    public void setExtraPagoPa(boolean value) {
        this.extraPagoPa = value;
    }

    /**
     * Recupera il valore della proprietà incassato.
     * 
     */
    public boolean isIncassato() {
        return incassato;
    }

    /**
     * Imposta il valore della proprietà incassato.
     * 
     */
    public void setIncassato(boolean value) {
        this.incassato = value;
    }

    /**
     * Recupera il valore della proprietà numeroAvviso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroAvviso() {
        return numeroAvviso;
    }

    /**
     * Imposta il valore della proprietà numeroAvviso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroAvviso(String value) {
        this.numeroAvviso = value;
    }

    /**
     * Recupera il valore della proprietà pagato.
     * 
     */
    public boolean isPagato() {
        return pagato;
    }

    /**
     * Imposta il valore della proprietà pagato.
     * 
     */
    public void setPagato(boolean value) {
        this.pagato = value;
    }

    /**
     * Recupera il valore della proprietà rendicontato.
     * 
     */
    public boolean isRendicontato() {
        return rendicontato;
    }

    /**
     * Imposta il valore della proprietà rendicontato.
     * 
     */
    public void setRendicontato(boolean value) {
        this.rendicontato = value;
    }

    /**
     * Recupera il valore della proprietà ricevutaTelematicaWs.
     * 
     * @return
     *     possible object is
     *     {@link RicevutaTelematicaWs }
     *     
     */
    public RicevutaTelematicaWs getRicevutaTelematicaWs() {
        return ricevutaTelematicaWs;
    }

    /**
     * Imposta il valore della proprietà ricevutaTelematicaWs.
     * 
     * @param value
     *     allowed object is
     *     {@link RicevutaTelematicaWs }
     *     
     */
    public void setRicevutaTelematicaWs(RicevutaTelematicaWs value) {
        this.ricevutaTelematicaWs = value;
    }

    /**
     * Recupera il valore della proprietà pagabile.
     * 
     */
    public boolean isPagabile() {
        return pagabile;
    }

    /**
     * Imposta il valore della proprietà pagabile.
     * 
     */
    public void setPagabile(boolean value) {
        this.pagabile = value;
    }

    /**
     * Recupera il valore della proprietà codiceNonPagabile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceNonPagabile() {
        return codiceNonPagabile;
    }

    /**
     * Imposta il valore della proprietà codiceNonPagabile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceNonPagabile(String value) {
        this.codiceNonPagabile = value;
    }

    /**
     * Recupera il valore della proprietà datiRevoca.
     * 
     * @return
     *     possible object is
     *     {@link DatiRevoca }
     *     
     */
    public DatiRevoca getDatiRevoca() {
        return datiRevoca;
    }

    /**
     * Imposta il valore della proprietà datiRevoca.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiRevoca }
     *     
     */
    public void setDatiRevoca(DatiRevoca value) {
        this.datiRevoca = value;
    }

    /**
     * Recupera il valore della proprietà ravvedibile.
     * 
     */
    public boolean isRavvedibile() {
        return ravvedibile;
    }

    /**
     * Imposta il valore della proprietà ravvedibile.
     * 
     */
    public void setRavvedibile(boolean value) {
        this.ravvedibile = value;
    }

    /**
     * Recupera il valore della proprietà codiceNonRavvedibile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceNonRavvedibile() {
        return codiceNonRavvedibile;
    }

    /**
     * Imposta il valore della proprietà codiceNonRavvedibile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceNonRavvedibile(String value) {
        this.codiceNonRavvedibile = value;
    }

}
