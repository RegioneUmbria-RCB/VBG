//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.05.27 alle 03:53:35 PM CEST 
//


package it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizioneEnte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="alias" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avviso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listaDettagli" type="{http://gruppoinit.it/nlapec}DettaglioReportType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "descrizioneEnte",
    "alias",
    "errore",
    "avviso",
    "listaDettagli"
})
@XmlRootElement(name = "ProcessaMessaggiResponse", namespace = "http://gruppoinit.it/nlapec")
public class ProcessaMessaggiResponse {

    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected String descrizioneEnte;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected String alias;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected String errore;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected String avviso;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected DettaglioReportType listaDettagli;

    /**
     * Recupera il valore della proprietà descrizioneEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneEnte() {
        return descrizioneEnte;
    }

    /**
     * Imposta il valore della proprietà descrizioneEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneEnte(String value) {
        this.descrizioneEnte = value;
    }

    /**
     * Recupera il valore della proprietà alias.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Imposta il valore della proprietà alias.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Recupera il valore della proprietà errore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrore() {
        return errore;
    }

    /**
     * Imposta il valore della proprietà errore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrore(String value) {
        this.errore = value;
    }

    /**
     * Recupera il valore della proprietà avviso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvviso() {
        return avviso;
    }

    /**
     * Imposta il valore della proprietà avviso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvviso(String value) {
        this.avviso = value;
    }

    /**
     * Recupera il valore della proprietà listaDettagli.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioReportType }
     *     
     */
    public DettaglioReportType getListaDettagli() {
        return listaDettagli;
    }

    /**
     * Imposta il valore della proprietà listaDettagli.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioReportType }
     *     
     */
    public void setListaDettagli(DettaglioReportType value) {
        this.listaDettagli = value;
    }

}
