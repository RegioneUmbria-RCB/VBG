//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.05.27 alle 03:53:35 PM CEST 
//


package it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per MessaggioType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="MessaggioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mittenti" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="destinatari" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="destinataricc" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="corpo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataSpedizione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataRicezione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="letto" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="dimensione" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="altriDati" type="{http://gruppoinit.it/nlapec}AltriDatiType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessaggioType", namespace = "http://gruppoinit.it/nlapec", propOrder = {
    "identificativo",
    "mittenti",
    "destinatari",
    "destinataricc",
    "oggetto",
    "corpo",
    "dataSpedizione",
    "dataRicezione",
    "letto",
    "dimensione",
    "altriDati"
})
public class MessaggioType {

    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected String identificativo;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected List<String> mittenti;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected List<String> destinatari;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected List<String> destinataricc;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected String oggetto;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected String corpo;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataSpedizione;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRicezione;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected boolean letto;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected Long dimensione;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected List<AltriDatiType> altriDati;

    /**
     * Recupera il valore della proprietà identificativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativo() {
        return identificativo;
    }

    /**
     * Imposta il valore della proprietà identificativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativo(String value) {
        this.identificativo = value;
    }

    /**
     * Gets the value of the mittenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mittenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMittenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMittenti() {
        if (mittenti == null) {
            mittenti = new ArrayList<String>();
        }
        return this.mittenti;
    }

    /**
     * Gets the value of the destinatari property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinatari property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinatari().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDestinatari() {
        if (destinatari == null) {
            destinatari = new ArrayList<String>();
        }
        return this.destinatari;
    }

    /**
     * Gets the value of the destinataricc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinataricc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinataricc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDestinataricc() {
        if (destinataricc == null) {
            destinataricc = new ArrayList<String>();
        }
        return this.destinataricc;
    }

    /**
     * Recupera il valore della proprietà oggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Imposta il valore della proprietà oggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Recupera il valore della proprietà corpo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorpo() {
        return corpo;
    }

    /**
     * Imposta il valore della proprietà corpo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorpo(String value) {
        this.corpo = value;
    }

    /**
     * Recupera il valore della proprietà dataSpedizione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataSpedizione() {
        return dataSpedizione;
    }

    /**
     * Imposta il valore della proprietà dataSpedizione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataSpedizione(XMLGregorianCalendar value) {
        this.dataSpedizione = value;
    }

    /**
     * Recupera il valore della proprietà dataRicezione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRicezione() {
        return dataRicezione;
    }

    /**
     * Imposta il valore della proprietà dataRicezione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRicezione(XMLGregorianCalendar value) {
        this.dataRicezione = value;
    }

    /**
     * Recupera il valore della proprietà letto.
     * 
     */
    public boolean isLetto() {
        return letto;
    }

    /**
     * Imposta il valore della proprietà letto.
     * 
     */
    public void setLetto(boolean value) {
        this.letto = value;
    }

    /**
     * Recupera il valore della proprietà dimensione.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDimensione() {
        return dimensione;
    }

    /**
     * Imposta il valore della proprietà dimensione.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDimensione(Long value) {
        this.dimensione = value;
    }

    /**
     * Gets the value of the altriDati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the altriDati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAltriDati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AltriDatiType }
     * 
     * 
     */
    public List<AltriDatiType> getAltriDati() {
        if (altriDati == null) {
            altriDati = new ArrayList<AltriDatiType>();
        }
        return this.altriDati;
    }

}
