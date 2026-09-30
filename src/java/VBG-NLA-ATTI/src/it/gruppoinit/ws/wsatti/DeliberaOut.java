
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
 * <p>Java class for DeliberaOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeliberaOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Organo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Organo_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Trattamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Trattamento_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Proponente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Proponente_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImmediatamenteEsecutiva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ACapigruppo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="APrefetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ACoReCo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DataEsecutivita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DataPubblicazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="GiorniPubblicazione" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="GiorniEsecutivita" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DataEvidenza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DataTrascrizione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DataRipubblicazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="GiorniRipubblicazione" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IdentificativoProposta" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "DeliberaOut", propOrder = {
    "organo",
    "organoDescrizione",
    "trattamento",
    "trattamentoDescrizione",
    "proponente",
    "proponenteDescrizione",
    "immediatamenteEsecutiva",
    "aCapigruppo",
    "aPrefetto",
    "aCoReCo",
    "anno",
    "numero",
    "data",
    "dataEsecutivita",
    "dataPubblicazione",
    "giorniPubblicazione",
    "giorniEsecutivita",
    "dataEvidenza",
    "dataTrascrizione",
    "dataRipubblicazione",
    "giorniRipubblicazione",
    "identificativoProposta",
    "dataFinePubblicazione"
})
public class DeliberaOut {

    @XmlElementRef(name = "Organo", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> organo;
    @XmlElementRef(name = "Organo_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> organoDescrizione;
    @XmlElementRef(name = "Trattamento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> trattamento;
    @XmlElementRef(name = "Trattamento_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> trattamentoDescrizione;
    @XmlElementRef(name = "Proponente", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> proponente;
    @XmlElementRef(name = "Proponente_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> proponenteDescrizione;
    @XmlElementRef(name = "ImmediatamenteEsecutiva", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> immediatamenteEsecutiva;
    @XmlElement(name = "ACapigruppo")
    protected String aCapigruppo;
    @XmlElement(name = "APrefetto")
    protected String aPrefetto;
    @XmlElement(name = "ACoReCo")
    protected String aCoReCo;
    @XmlElement(name = "Anno")
    protected int anno;
    @XmlElement(name = "Numero")
    protected int numero;
    @XmlElement(name = "Data", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;
    @XmlElement(name = "DataEsecutivita", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEsecutivita;
    @XmlElement(name = "DataPubblicazione", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPubblicazione;
    @XmlElement(name = "GiorniPubblicazione")
    protected int giorniPubblicazione;
    @XmlElement(name = "GiorniEsecutivita")
    protected int giorniEsecutivita;
    @XmlElement(name = "DataEvidenza", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEvidenza;
    @XmlElement(name = "DataTrascrizione", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataTrascrizione;
    @XmlElement(name = "DataRipubblicazione", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRipubblicazione;
    @XmlElement(name = "GiorniRipubblicazione")
    protected int giorniRipubblicazione;
    @XmlElement(name = "IdentificativoProposta")
    protected int identificativoProposta;
    @XmlElement(name = "DataFinePubblicazione", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFinePubblicazione;

    /**
     * Gets the value of the organo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrgano() {
        return organo;
    }

    /**
     * Sets the value of the organo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrgano(JAXBElement<String> value) {
        this.organo = value;
    }

    /**
     * Gets the value of the organoDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrganoDescrizione() {
        return organoDescrizione;
    }

    /**
     * Sets the value of the organoDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrganoDescrizione(JAXBElement<String> value) {
        this.organoDescrizione = value;
    }

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
     * Gets the value of the immediatamenteEsecutiva property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getImmediatamenteEsecutiva() {
        return immediatamenteEsecutiva;
    }

    /**
     * Sets the value of the immediatamenteEsecutiva property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setImmediatamenteEsecutiva(JAXBElement<String> value) {
        this.immediatamenteEsecutiva = value;
    }

    /**
     * Gets the value of the aCapigruppo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACapigruppo() {
        return aCapigruppo;
    }

    /**
     * Sets the value of the aCapigruppo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACapigruppo(String value) {
        this.aCapigruppo = value;
    }

    /**
     * Gets the value of the aPrefetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPrefetto() {
        return aPrefetto;
    }

    /**
     * Sets the value of the aPrefetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPrefetto(String value) {
        this.aPrefetto = value;
    }

    /**
     * Gets the value of the aCoReCo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACoReCo() {
        return aCoReCo;
    }

    /**
     * Sets the value of the aCoReCo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACoReCo(String value) {
        this.aCoReCo = value;
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
     * Gets the value of the giorniEsecutivita property.
     * 
     */
    public int getGiorniEsecutivita() {
        return giorniEsecutivita;
    }

    /**
     * Sets the value of the giorniEsecutivita property.
     * 
     */
    public void setGiorniEsecutivita(int value) {
        this.giorniEsecutivita = value;
    }

    /**
     * Gets the value of the dataEvidenza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEvidenza() {
        return dataEvidenza;
    }

    /**
     * Sets the value of the dataEvidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEvidenza(XMLGregorianCalendar value) {
        this.dataEvidenza = value;
    }

    /**
     * Gets the value of the dataTrascrizione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataTrascrizione() {
        return dataTrascrizione;
    }

    /**
     * Sets the value of the dataTrascrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataTrascrizione(XMLGregorianCalendar value) {
        this.dataTrascrizione = value;
    }

    /**
     * Gets the value of the dataRipubblicazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRipubblicazione() {
        return dataRipubblicazione;
    }

    /**
     * Sets the value of the dataRipubblicazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRipubblicazione(XMLGregorianCalendar value) {
        this.dataRipubblicazione = value;
    }

    /**
     * Gets the value of the giorniRipubblicazione property.
     * 
     */
    public int getGiorniRipubblicazione() {
        return giorniRipubblicazione;
    }

    /**
     * Sets the value of the giorniRipubblicazione property.
     * 
     */
    public void setGiorniRipubblicazione(int value) {
        this.giorniRipubblicazione = value;
    }

    /**
     * Gets the value of the identificativoProposta property.
     * 
     */
    public int getIdentificativoProposta() {
        return identificativoProposta;
    }

    /**
     * Sets the value of the identificativoProposta property.
     * 
     */
    public void setIdentificativoProposta(int value) {
        this.identificativoProposta = value;
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
