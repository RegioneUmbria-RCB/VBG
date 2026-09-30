
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PosizioneDebitoriaInAttesa_Dettaglio complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioneDebitoriaInAttesa_Dettaglio"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NomeVoceDiCosto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Quantita" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Iva" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CausaleImporto" type="{http://entranext.it/}CausaliImporti"/&gt;
 *         &lt;element name="SezioniParametriSpecifici" type="{http://entranext.it/}SezioneParametriSpecifici" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="AliquotaIva" type="{http://entranext.it/}IVA"/&gt;
 *         &lt;element name="NumeroAccertamentoContabile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AnnoAccertamentoContabile" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="DescrizioneHTML" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioneDebitoriaInAttesa_Dettaglio", propOrder = {
    "nomeVoceDiCosto",
    "quantita",
    "importo",
    "iva",
    "descrizione",
    "causaleImporto",
    "sezioniParametriSpecifici",
    "aliquotaIva",
    "numeroAccertamentoContabile",
    "annoAccertamentoContabile",
    "descrizioneHTML"
})
public class PosizioneDebitoriaInAttesaDettaglio {

    @XmlElement(name = "NomeVoceDiCosto")
    protected String nomeVoceDiCosto;
    @XmlElement(name = "Quantita", required = true)
    protected BigDecimal quantita;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "Iva", required = true)
    protected BigDecimal iva;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "CausaleImporto", required = true)
    @XmlSchemaType(name = "string")
    protected CausaliImporti causaleImporto;
    @XmlElement(name = "SezioniParametriSpecifici", nillable = true)
    protected List<SezioneParametriSpecifici> sezioniParametriSpecifici;
    @XmlElement(name = "AliquotaIva", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected IVA aliquotaIva;
    @XmlElement(name = "NumeroAccertamentoContabile", required = true, nillable = true)
    protected String numeroAccertamentoContabile;
    @XmlElementRef(name = "AnnoAccertamentoContabile", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> annoAccertamentoContabile;
    @XmlElement(name = "DescrizioneHTML")
    protected String descrizioneHTML;

    /**
     * Recupera il valore della proprietà nomeVoceDiCosto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeVoceDiCosto() {
        return nomeVoceDiCosto;
    }

    /**
     * Imposta il valore della proprietà nomeVoceDiCosto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeVoceDiCosto(String value) {
        this.nomeVoceDiCosto = value;
    }

    /**
     * Recupera il valore della proprietà quantita.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantita() {
        return quantita;
    }

    /**
     * Imposta il valore della proprietà quantita.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantita(BigDecimal value) {
        this.quantita = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

    /**
     * Recupera il valore della proprietà iva.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIva() {
        return iva;
    }

    /**
     * Imposta il valore della proprietà iva.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIva(BigDecimal value) {
        this.iva = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà causaleImporto.
     * 
     * @return
     *     possible object is
     *     {@link CausaliImporti }
     *     
     */
    public CausaliImporti getCausaleImporto() {
        return causaleImporto;
    }

    /**
     * Imposta il valore della proprietà causaleImporto.
     * 
     * @param value
     *     allowed object is
     *     {@link CausaliImporti }
     *     
     */
    public void setCausaleImporto(CausaliImporti value) {
        this.causaleImporto = value;
    }

    /**
     * Gets the value of the sezioniParametriSpecifici property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sezioniParametriSpecifici property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSezioniParametriSpecifici().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SezioneParametriSpecifici }
     * 
     * 
     */
    public List<SezioneParametriSpecifici> getSezioniParametriSpecifici() {
        if (sezioniParametriSpecifici == null) {
            sezioniParametriSpecifici = new ArrayList<SezioneParametriSpecifici>();
        }
        return this.sezioniParametriSpecifici;
    }

    /**
     * Recupera il valore della proprietà aliquotaIva.
     * 
     * @return
     *     possible object is
     *     {@link IVA }
     *     
     */
    public IVA getAliquotaIva() {
        return aliquotaIva;
    }

    /**
     * Imposta il valore della proprietà aliquotaIva.
     * 
     * @param value
     *     allowed object is
     *     {@link IVA }
     *     
     */
    public void setAliquotaIva(IVA value) {
        this.aliquotaIva = value;
    }

    /**
     * Recupera il valore della proprietà numeroAccertamentoContabile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroAccertamentoContabile() {
        return numeroAccertamentoContabile;
    }

    /**
     * Imposta il valore della proprietà numeroAccertamentoContabile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroAccertamentoContabile(String value) {
        this.numeroAccertamentoContabile = value;
    }

    /**
     * Recupera il valore della proprietà annoAccertamentoContabile.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getAnnoAccertamentoContabile() {
        return annoAccertamentoContabile;
    }

    /**
     * Imposta il valore della proprietà annoAccertamentoContabile.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setAnnoAccertamentoContabile(JAXBElement<Integer> value) {
        this.annoAccertamentoContabile = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneHTML.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneHTML() {
        return descrizioneHTML;
    }

    /**
     * Imposta il valore della proprietà descrizioneHTML.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneHTML(String value) {
        this.descrizioneHTML = value;
    }

}
