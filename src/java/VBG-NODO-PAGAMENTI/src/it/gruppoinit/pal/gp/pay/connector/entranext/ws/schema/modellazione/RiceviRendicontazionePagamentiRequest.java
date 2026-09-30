
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per RiceviRendicontazionePagamentiRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiceviRendicontazionePagamentiRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DataContabileIniziale" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataContabileFinale" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataInserimentoIniziale" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataInserimentoFinale" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Inizio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="NumeroPosizioni" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiceviRendicontazionePagamentiRequest", propOrder = {
    "dataContabileIniziale",
    "dataContabileFinale",
    "dataInserimentoIniziale",
    "dataInserimentoFinale",
    "inizio",
    "numeroPosizioni"
})
public class RiceviRendicontazionePagamentiRequest
    extends LinkNextRequest
{

    @XmlElement(name = "DataContabileIniziale", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataContabileIniziale;
    @XmlElement(name = "DataContabileFinale", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataContabileFinale;
    @XmlElement(name = "DataInserimentoIniziale", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInserimentoIniziale;
    @XmlElement(name = "DataInserimentoFinale", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInserimentoFinale;
    @XmlElement(name = "Inizio", defaultValue = "0")
    protected Integer inizio;
    @XmlElement(name = "NumeroPosizioni", defaultValue = "1000")
    protected Integer numeroPosizioni;

    /**
     * Recupera il valore della proprietà dataContabileIniziale.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataContabileIniziale() {
        return dataContabileIniziale;
    }

    /**
     * Imposta il valore della proprietà dataContabileIniziale.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataContabileIniziale(XMLGregorianCalendar value) {
        this.dataContabileIniziale = value;
    }

    /**
     * Recupera il valore della proprietà dataContabileFinale.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataContabileFinale() {
        return dataContabileFinale;
    }

    /**
     * Imposta il valore della proprietà dataContabileFinale.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataContabileFinale(XMLGregorianCalendar value) {
        this.dataContabileFinale = value;
    }

    /**
     * Recupera il valore della proprietà dataInserimentoIniziale.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInserimentoIniziale() {
        return dataInserimentoIniziale;
    }

    /**
     * Imposta il valore della proprietà dataInserimentoIniziale.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInserimentoIniziale(XMLGregorianCalendar value) {
        this.dataInserimentoIniziale = value;
    }

    /**
     * Recupera il valore della proprietà dataInserimentoFinale.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInserimentoFinale() {
        return dataInserimentoFinale;
    }

    /**
     * Imposta il valore della proprietà dataInserimentoFinale.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInserimentoFinale(XMLGregorianCalendar value) {
        this.dataInserimentoFinale = value;
    }

    /**
     * Recupera il valore della proprietà inizio.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInizio() {
        return inizio;
    }

    /**
     * Imposta il valore della proprietà inizio.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInizio(Integer value) {
        this.inizio = value;
    }

    /**
     * Recupera il valore della proprietà numeroPosizioni.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroPosizioni() {
        return numeroPosizioni;
    }

    /**
     * Imposta il valore della proprietà numeroPosizioni.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroPosizioni(Integer value) {
        this.numeroPosizioni = value;
    }

}
