
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common.PartitaDebitoriaWs;


/**
 * Propriet� relative al
 *         Pagamento Atteso
 *       
 * 
 * <p>Classe Java per pagamentoAttesoWs complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pagamentoAttesoWs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idBoPagamentoAtteso"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="100"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="codiceServizio"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="codiceEnte"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="partiteDebitorieWs" type="{it/lineacomune/pagopa/be/ws/endpoint/shared}partitaDebitoriaWs" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="anagCap" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="5"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagCfPiva"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="16"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagCivico" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="16"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagDenominazione"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="70"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagEmail" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;pattern value="[^@]+@[^\.]+\..+"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagIndirizzo" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="70"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagLocalita" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="35"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagNaturaGiuridica"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagNazione" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="anagProvincia" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="35"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
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
 *         &lt;element name="visibileSol"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean"&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="dataInizioValidita"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime"&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="dataScadenza"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime"&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="dataScadenzaStampabile" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime"&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="dataScadenzaNote" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *               &lt;maxLength value="500"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pagamentoAttesoWs", propOrder = {
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
    "dataScadenzaNote"
})
public class PagamentoAttesoWs {

    @XmlElement(required = true)
    protected String idBoPagamentoAtteso;
    @XmlElement(required = true)
    protected String codiceServizio;
    @XmlElement(required = true)
    protected String codiceEnte;
    protected List<PartitaDebitoriaWs> partiteDebitorieWs;
    protected String anagCap;
    @XmlElement(required = true)
    protected String anagCfPiva;
    protected String anagCivico;
    @XmlElement(required = true)
    protected String anagDenominazione;
    protected String anagEmail;
    protected String anagIndirizzo;
    protected String anagLocalita;
    @XmlElement(required = true)
    protected String anagNaturaGiuridica;
    protected String anagNazione;
    protected String anagProvincia;
    @XmlElement(required = true)
    protected String causale;
    @XmlElement(required = true)
    protected String importo;
    protected String iuv;
    protected boolean visibileSol;
    @XmlElement(required = true)
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(required = true)
    protected XMLGregorianCalendar dataScadenza;
    protected XMLGregorianCalendar dataScadenzaStampabile;
    protected String dataScadenzaNote;

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

}
