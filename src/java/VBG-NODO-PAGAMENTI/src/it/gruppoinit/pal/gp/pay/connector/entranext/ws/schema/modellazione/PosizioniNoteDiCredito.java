
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PosizioniNoteDiCredito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioniNoteDiCredito"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TipoChiaveApplicativa" type="{http://entranext.it/}TipoChiaveApplicativa"/&gt;
 *         &lt;element name="ChiaveApplicativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StornoTotale" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Dettagli" type="{http://entranext.it/}PosizioniNoteDiCredito_Dettagli" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="NomeFileAcquisito" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Documento" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioniNoteDiCredito", propOrder = {
    "tipoChiaveApplicativa",
    "chiaveApplicativa",
    "note",
    "stornoTotale",
    "dettagli",
    "nomeFileAcquisito",
    "documento",
    "numero"
})
public class PosizioniNoteDiCredito {

    @XmlElement(name = "TipoChiaveApplicativa", required = true)
    @XmlSchemaType(name = "string")
    protected TipoChiaveApplicativa tipoChiaveApplicativa;
    @XmlElement(name = "ChiaveApplicativa")
    protected String chiaveApplicativa;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "StornoTotale", required = true, type = Boolean.class, nillable = true)
    protected Boolean stornoTotale;
    @XmlElement(name = "Dettagli", nillable = true)
    protected List<PosizioniNoteDiCreditoDettagli> dettagli;
    @XmlElement(name = "NomeFileAcquisito", required = true, nillable = true)
    protected String nomeFileAcquisito;
    @XmlElement(name = "Documento", required = true, nillable = true)
    protected byte[] documento;
    @XmlElement(name = "Numero", required = true, type = Integer.class, nillable = true)
    protected Integer numero;

    /**
     * Recupera il valore della proprietà tipoChiaveApplicativa.
     * 
     * @return
     *     possible object is
     *     {@link TipoChiaveApplicativa }
     *     
     */
    public TipoChiaveApplicativa getTipoChiaveApplicativa() {
        return tipoChiaveApplicativa;
    }

    /**
     * Imposta il valore della proprietà tipoChiaveApplicativa.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoChiaveApplicativa }
     *     
     */
    public void setTipoChiaveApplicativa(TipoChiaveApplicativa value) {
        this.tipoChiaveApplicativa = value;
    }

    /**
     * Recupera il valore della proprietà chiaveApplicativa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChiaveApplicativa() {
        return chiaveApplicativa;
    }

    /**
     * Imposta il valore della proprietà chiaveApplicativa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChiaveApplicativa(String value) {
        this.chiaveApplicativa = value;
    }

    /**
     * Recupera il valore della proprietà note.
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
     * Imposta il valore della proprietà note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Recupera il valore della proprietà stornoTotale.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStornoTotale() {
        return stornoTotale;
    }

    /**
     * Imposta il valore della proprietà stornoTotale.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStornoTotale(Boolean value) {
        this.stornoTotale = value;
    }

    /**
     * Gets the value of the dettagli property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dettagli property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDettagli().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioniNoteDiCreditoDettagli }
     * 
     * 
     */
    public List<PosizioniNoteDiCreditoDettagli> getDettagli() {
        if (dettagli == null) {
            dettagli = new ArrayList<PosizioniNoteDiCreditoDettagli>();
        }
        return this.dettagli;
    }

    /**
     * Recupera il valore della proprietà nomeFileAcquisito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFileAcquisito() {
        return nomeFileAcquisito;
    }

    /**
     * Imposta il valore della proprietà nomeFileAcquisito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFileAcquisito(String value) {
        this.nomeFileAcquisito = value;
    }

    /**
     * Recupera il valore della proprietà documento.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocumento() {
        return documento;
    }

    /**
     * Imposta il valore della proprietà documento.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocumento(byte[] value) {
        this.documento = value;
    }

    /**
     * Recupera il valore della proprietà numero.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Imposta il valore della proprietà numero.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumero(Integer value) {
        this.numero = value;
    }

}
