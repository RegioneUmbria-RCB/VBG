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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DettaglioReportType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioReportType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipologiaMessaggiDaProcessare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroMessaggiTotali" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numeroMessaggiConErrore" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="listaErrori" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioReportType", namespace = "http://gruppoinit.it/nlapec", propOrder = {
    "software",
    "tipologiaMessaggiDaProcessare",
    "numeroMessaggiTotali",
    "numeroMessaggiConErrore",
    "listaErrori"
})
public class DettaglioReportType {

    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected String software;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected String tipologiaMessaggiDaProcessare;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected int numeroMessaggiTotali;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected int numeroMessaggiConErrore;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected List<String> listaErrori;

    /**
     * Recupera il valore della proprietà software.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftware() {
        return software;
    }

    /**
     * Imposta il valore della proprietà software.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftware(String value) {
        this.software = value;
    }

    /**
     * Recupera il valore della proprietà tipologiaMessaggiDaProcessare.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologiaMessaggiDaProcessare() {
        return tipologiaMessaggiDaProcessare;
    }

    /**
     * Imposta il valore della proprietà tipologiaMessaggiDaProcessare.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologiaMessaggiDaProcessare(String value) {
        this.tipologiaMessaggiDaProcessare = value;
    }

    /**
     * Recupera il valore della proprietà numeroMessaggiTotali.
     * 
     */
    public int getNumeroMessaggiTotali() {
        return numeroMessaggiTotali;
    }

    /**
     * Imposta il valore della proprietà numeroMessaggiTotali.
     * 
     */
    public void setNumeroMessaggiTotali(int value) {
        this.numeroMessaggiTotali = value;
    }

    /**
     * Recupera il valore della proprietà numeroMessaggiConErrore.
     * 
     */
    public int getNumeroMessaggiConErrore() {
        return numeroMessaggiConErrore;
    }

    /**
     * Imposta il valore della proprietà numeroMessaggiConErrore.
     * 
     */
    public void setNumeroMessaggiConErrore(int value) {
        this.numeroMessaggiConErrore = value;
    }

    /**
     * Gets the value of the listaErrori property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaErrori property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaErrori().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getListaErrori() {
        if (listaErrori == null) {
            listaErrori = new ArrayList<String>();
        }
        return this.listaErrori;
    }

}
