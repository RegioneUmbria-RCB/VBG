//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.17 alle 10:12:21 AM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schemanotify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctEsitoDebitoCaricato complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctEsitoDebitoCaricato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chiaviDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/notify/1_2}ctChiaviDebito"/>
 *         &lt;element name="numeroAvviso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iuvNumeroAvviso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esitoCaricamento" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/notify/1_2}ctEsito"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctEsitoDebitoCaricato", propOrder = {
    "chiaviDebito",
    "numeroAvviso",
    "iuvNumeroAvviso",
    "esitoCaricamento"
})
public class CtEsitoDebitoCaricato {

    @XmlElement(required = true)
    protected CtChiaviDebito chiaviDebito;
    protected String numeroAvviso;
    protected String iuvNumeroAvviso;
    @XmlElement(required = true)
    protected CtEsito esitoCaricamento;

    /**
     * Recupera il valore della proprietà chiaviDebito.
     * 
     * @return
     *     possible object is
     *     {@link CtChiaviDebito }
     *     
     */
    public CtChiaviDebito getChiaviDebito() {
        return chiaviDebito;
    }

    /**
     * Imposta il valore della proprietà chiaviDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtChiaviDebito }
     *     
     */
    public void setChiaviDebito(CtChiaviDebito value) {
        this.chiaviDebito = value;
    }

    /**
     * Recupera il valore della proprietà numeroAvviso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroAvviso() {
        return numeroAvviso;
    }

    /**
     * Imposta il valore della proprietà numeroAvviso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroAvviso(String value) {
        this.numeroAvviso = value;
    }

    /**
     * Recupera il valore della proprietà iuvNumeroAvviso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIuvNumeroAvviso() {
        return iuvNumeroAvviso;
    }

    /**
     * Imposta il valore della proprietà iuvNumeroAvviso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIuvNumeroAvviso(String value) {
        this.iuvNumeroAvviso = value;
    }

    /**
     * Recupera il valore della proprietà esitoCaricamento.
     * 
     * @return
     *     possible object is
     *     {@link CtEsito }
     *     
     */
    public CtEsito getEsitoCaricamento() {
        return esitoCaricamento;
    }

    /**
     * Imposta il valore della proprietà esitoCaricamento.
     * 
     * @param value
     *     allowed object is
     *     {@link CtEsito }
     *     
     */
    public void setEsitoCaricamento(CtEsito value) {
        this.esitoCaricamento = value;
    }

}
