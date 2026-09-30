
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per PosizioneDebitoria_Rata complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioneDebitoria_Rata"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumeroRata" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Scadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="QuintoCampo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IUV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DataSconosciuta" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioneDebitoria_Rata", propOrder = {
    "numeroRata",
    "importo",
    "scadenza",
    "quintoCampo",
    "iuv",
    "dataSconosciuta",
    "note"
})
public class PosizioneDebitoriaRata {

    @XmlElement(name = "NumeroRata")
    protected int numeroRata;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "Scadenza", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar scadenza;
    @XmlElement(name = "QuintoCampo")
    protected String quintoCampo;
    @XmlElement(name = "IUV")
    protected String iuv;
    @XmlElement(name = "DataSconosciuta")
    protected Boolean dataSconosciuta;
    @XmlElement(name = "Note")
    protected String note;

    /**
     * Recupera il valore della proprietà numeroRata.
     * 
     */
    public int getNumeroRata() {
        return numeroRata;
    }

    /**
     * Imposta il valore della proprietà numeroRata.
     * 
     */
    public void setNumeroRata(int value) {
        this.numeroRata = value;
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
     * Recupera il valore della proprietà scadenza.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getScadenza() {
        return scadenza;
    }

    /**
     * Imposta il valore della proprietà scadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setScadenza(XMLGregorianCalendar value) {
        this.scadenza = value;
    }

    /**
     * Recupera il valore della proprietà quintoCampo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuintoCampo() {
        return quintoCampo;
    }

    /**
     * Imposta il valore della proprietà quintoCampo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuintoCampo(String value) {
        this.quintoCampo = value;
    }

    /**
     * Recupera il valore della proprietà iuv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUV() {
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
    public void setIUV(String value) {
        this.iuv = value;
    }

    /**
     * Recupera il valore della proprietà dataSconosciuta.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDataSconosciuta() {
        return dataSconosciuta;
    }

    /**
     * Imposta il valore della proprietà dataSconosciuta.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDataSconosciuta(Boolean value) {
        this.dataSconosciuta = value;
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

}
