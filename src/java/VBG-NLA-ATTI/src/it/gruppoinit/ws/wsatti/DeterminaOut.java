
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DeterminaOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeterminaOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Trattamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Trattamento_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Proponente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Proponente_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dirigente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dirigente_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DaPubblicare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumeroRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataEsecutivita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DataPubblicazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="GiorniPubblicazione" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DataFinePubblicazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeterminaOut", propOrder = {
    "trattamento",
    "trattamentoDescrizione",
    "proponente",
    "proponenteDescrizione",
    "dirigente",
    "dirigenteDescrizione",
    "daPubblicare",
    "data",
    "anno",
    "numero",
    "numeroRegistro",
    "dataEsecutivita",
    "dataPubblicazione",
    "giorniPubblicazione",
    "dataFinePubblicazione"
})
public class DeterminaOut {

    @XmlElementRef(name = "Trattamento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> trattamento;
    @XmlElementRef(name = "Trattamento_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> trattamentoDescrizione;
    @XmlElementRef(name = "Proponente", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> proponente;
    @XmlElementRef(name = "Proponente_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> proponenteDescrizione;
    @XmlElementRef(name = "Dirigente", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> dirigente;
    @XmlElementRef(name = "Dirigente_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> dirigenteDescrizione;
    @XmlElementRef(name = "DaPubblicare", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> daPubblicare;
    @XmlElement(name = "Data", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;
    @XmlElement(name = "Anno")
    protected int anno;
    @XmlElement(name = "Numero")
    protected int numero;
    @XmlElementRef(name = "NumeroRegistro", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> numeroRegistro;
    @XmlElement(name = "DataEsecutivita", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEsecutivita;
    @XmlElement(name = "DataPubblicazione", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPubblicazione;
    @XmlElement(name = "GiorniPubblicazione")
    protected int giorniPubblicazione;
    @XmlElement(name = "DataFinePubblicazione", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFinePubblicazione;

    /**
     * Gets the value of the trattamento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTrattamento() {
        return trattamento;
    }

    /**
     * Sets the value of the trattamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTrattamento(JAXBElement<String> value) {
        this.trattamento = value;
    }

    /**
     * Gets the value of the trattamentoDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTrattamentoDescrizione() {
        return trattamentoDescrizione;
    }

    /**
     * Sets the value of the trattamentoDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTrattamentoDescrizione(JAXBElement<String> value) {
        this.trattamentoDescrizione = value;
    }

    /**
     * Gets the value of the proponente property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProponente() {
        return proponente;
    }

    /**
     * Sets the value of the proponente property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProponente(JAXBElement<String> value) {
        this.proponente = value;
    }

    /**
     * Gets the value of the proponenteDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProponenteDescrizione() {
        return proponenteDescrizione;
    }

    /**
     * Sets the value of the proponenteDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProponenteDescrizione(JAXBElement<String> value) {
        this.proponenteDescrizione = value;
    }

    /**
     * Gets the value of the dirigente property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDirigente() {
        return dirigente;
    }

    /**
     * Sets the value of the dirigente property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDirigente(JAXBElement<String> value) {
        this.dirigente = value;
    }

    /**
     * Gets the value of the dirigenteDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDirigenteDescrizione() {
        return dirigenteDescrizione;
    }

    /**
     * Sets the value of the dirigenteDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDirigenteDescrizione(JAXBElement<String> value) {
        this.dirigenteDescrizione = value;
    }

    /**
     * Gets the value of the daPubblicare property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDaPubblicare() {
        return daPubblicare;
    }

    /**
     * Sets the value of the daPubblicare property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDaPubblicare(JAXBElement<String> value) {
        this.daPubblicare = value;
    }

    /**
     * Gets the value of the data property.
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
     * Sets the value of the data property.
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
     * Gets the value of the anno property.
     * 
     */
    public int getAnno() {
        return anno;
    }

    /**
     * Sets the value of the anno property.
     * 
     */
    public void setAnno(int value) {
        this.anno = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

    /**
     * Gets the value of the numeroRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * Sets the value of the numeroRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNumeroRegistro(JAXBElement<String> value) {
        this.numeroRegistro = value;
    }

    /**
     * Gets the value of the dataEsecutivita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsecutivita() {
        return dataEsecutivita;
    }

    /**
     * Sets the value of the dataEsecutivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsecutivita(XMLGregorianCalendar value) {
        this.dataEsecutivita = value;
    }

    /**
     * Gets the value of the dataPubblicazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPubblicazione() {
        return dataPubblicazione;
    }

    /**
     * Sets the value of the dataPubblicazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPubblicazione(XMLGregorianCalendar value) {
        this.dataPubblicazione = value;
    }

    /**
     * Gets the value of the giorniPubblicazione property.
     * 
     */
    public int getGiorniPubblicazione() {
        return giorniPubblicazione;
    }

    /**
     * Sets the value of the giorniPubblicazione property.
     * 
     */
    public void setGiorniPubblicazione(int value) {
        this.giorniPubblicazione = value;
    }

    /**
     * Gets the value of the dataFinePubblicazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFinePubblicazione() {
        return dataFinePubblicazione;
    }

    /**
     * Sets the value of the dataFinePubblicazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFinePubblicazione(XMLGregorianCalendar value) {
        this.dataFinePubblicazione = value;
    }

}
