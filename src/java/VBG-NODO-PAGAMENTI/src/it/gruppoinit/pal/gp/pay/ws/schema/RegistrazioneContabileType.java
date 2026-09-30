
package it.gruppoinit.pal.gp.pay.ws.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per RegistrazioneContabileType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RegistrazioneContabileType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="importo" type="{http://www.paevolution.com/ws/pagamenti_types/}ImportoType" minOccurs="0"/&gt;
 *         &lt;element name="anno" type="{http://www.paevolution.com/ws/pagamenti_types/}AnnoType" minOccurs="0"/&gt;
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="soggettoDebitore" type="{http://www.paevolution.com/ws/pagamenti_types/}SoggettoDebitoreType"/&gt;
 *         &lt;element name="rate" type="{http://www.paevolution.com/ws/pagamenti_types/}RateizzazioneType"/&gt; 
 *       &lt;/sequence&gt;

 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrazioneContabileType", propOrder = {
    "data",
    "descrizione",
    "importo",
    "anno",
    "note",
    "soggettoDebitore",
    "rate"
})
public class RegistrazioneContabileType {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar data;
    @XmlElement(required = true)
    protected String descrizione;
    protected BigDecimal importo;
    @XmlSchemaType(name = "integer")
    protected Integer anno;
    protected String note;
    @XmlElement(required = true)
    protected SoggettoDebitoreType soggettoDebitore;
    @XmlElement(required = true)
    protected RateizzazioneType rate;

    /**
     * Recupera il valore della proprietà data.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getData() {
        return data;
    }

    /**
     * Imposta il valore della proprietà data.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setData(XMLGregorianCalendar value) {
        this.data = value;
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
     * Recupera il valore della proprietà anno.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnno() {
        return anno;
    }

    /**
     * Imposta il valore della proprietà anno.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnno(Integer value) {
        this.anno = value;
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
     * Recupera il valore della proprietà soggettoDebitore.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoDebitoreType }
     *     
     */
    public SoggettoDebitoreType getSoggettoDebitore() {
        return soggettoDebitore;
    }

    /**
     * Imposta il valore della proprietà soggettoDebitore.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoDebitoreType }
     *     
     */
    public void setSoggettoDebitore(SoggettoDebitoreType value) {
        this.soggettoDebitore = value;
    }

    /**
     * Recupera il valore della proprietà rate.
     * 
     * @return
     *     possible object is
     *     {@link RateizzazioneType }
     *     
     */
    public RateizzazioneType getRate() {
        return rate;
    }

    /**
     * Imposta il valore della proprietà rate.
     * 
     * @param value
     *     allowed object is
     *     {@link RateizzazioneType }
     *     
     */
    public void setRate(RateizzazioneType value) {
        this.rate = value;
    }

//    /**
//     * Recupera il valore della proprietà codiceTassonomia.
//     * 
//     * @return possible object is {@link String }
//     * 
//     */
//    public String getCodiceTassonomia() {
//
//	return codiceTassonomia;
//    }
//
//    /**
//     * Imposta il valore della proprietà codiceTassonomia.
//     * 
//     * @param value
//     *            allowed object is {@link String }
//     * 
//     */
//    public void setCodiceTassonomia(String value) {
//
//	this.codiceTassonomia = value;
//    }

//    /**
//     * Recupera il valore della proprietà id.
//     * 
//     * @return
//     *     possible object is
//     *     {@link BigInteger }
//     *     
//     */
//    public BigInteger getId() {
//        return id;
//    }
//
//    /**
//     * Imposta il valore della proprietà id.
//     * 
//     * @param value
//     *     allowed object is
//     *     {@link BigInteger }
//     *     
//     */
//    public void setId(BigInteger value) {
//        this.id = value;
//    }

}
