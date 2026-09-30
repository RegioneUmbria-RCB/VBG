
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiProtocolloFascicolatoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiProtocolloFascicolatoResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Fascicolato" type="{http://it.gruppoinit/Protocollazione}EnumFascicolatoType" minOccurs="0"/>
 *         &lt;element name="NumeroFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AnnoFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NoteFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Classifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Oggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Errore" type="{http://it.gruppoinit/Protocollazione}ErroreProtocolloType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiProtocolloFascicolatoResponseType", propOrder = {
    "fascicolato",
    "numeroFascicolo",
    "dataFascicolo",
    "annoFascicolo",
    "noteFascicolo",
    "classifica",
    "oggetto",
    "errore"
})
public class DatiProtocolloFascicolatoResponseType {

    @XmlElement(name = "Fascicolato")
    protected EnumFascicolatoType fascicolato;
    @XmlElement(name = "NumeroFascicolo", nillable = true)
    protected String numeroFascicolo;
    @XmlElement(name = "DataFascicolo", nillable = true)
    protected String dataFascicolo;
    @XmlElement(name = "AnnoFascicolo", nillable = true)
    protected String annoFascicolo;
    @XmlElement(name = "NoteFascicolo", nillable = true)
    protected String noteFascicolo;
    @XmlElement(name = "Classifica", nillable = true)
    protected String classifica;
    @XmlElement(name = "Oggetto", nillable = true)
    protected String oggetto;
    @XmlElement(name = "Errore", nillable = true)
    protected ErroreProtocolloType errore;

    /**
     * Gets the value of the fascicolato property.
     * 
     * @return
     *     possible object is
     *     {@link EnumFascicolatoType }
     *     
     */
    public EnumFascicolatoType getFascicolato() {
        return fascicolato;
    }

    /**
     * Sets the value of the fascicolato property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumFascicolatoType }
     *     
     */
    public void setFascicolato(EnumFascicolatoType value) {
        this.fascicolato = value;
    }

    /**
     * Gets the value of the numeroFascicolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroFascicolo() {
        return numeroFascicolo;
    }

    /**
     * Sets the value of the numeroFascicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroFascicolo(String value) {
        this.numeroFascicolo = value;
    }

    /**
     * Gets the value of the dataFascicolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFascicolo() {
        return dataFascicolo;
    }

    /**
     * Sets the value of the dataFascicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFascicolo(String value) {
        this.dataFascicolo = value;
    }

    /**
     * Gets the value of the annoFascicolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoFascicolo() {
        return annoFascicolo;
    }

    /**
     * Sets the value of the annoFascicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoFascicolo(String value) {
        this.annoFascicolo = value;
    }

    /**
     * Gets the value of the noteFascicolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteFascicolo() {
        return noteFascicolo;
    }

    /**
     * Sets the value of the noteFascicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteFascicolo(String value) {
        this.noteFascicolo = value;
    }

    /**
     * Gets the value of the classifica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassifica() {
        return classifica;
    }

    /**
     * Sets the value of the classifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassifica(String value) {
        this.classifica = value;
    }

    /**
     * Gets the value of the oggetto property.
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
     * Sets the value of the oggetto property.
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
     * Gets the value of the errore property.
     * 
     * @return
     *     possible object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public ErroreProtocolloType getErrore() {
        return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public void setErrore(ErroreProtocolloType value) {
        this.errore = value;
    }

}
