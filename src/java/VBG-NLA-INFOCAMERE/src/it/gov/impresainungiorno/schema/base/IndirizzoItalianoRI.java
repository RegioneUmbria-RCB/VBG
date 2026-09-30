
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * E' la versione con tutti i dati opzionali cosi' come possono arrivare dal R.I.
 * 
 * <p>Classe Java per IndirizzoItalianoRI complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IndirizzoItalianoRI"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}CittaItalianaRI"/&gt;
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}DatiIndirizzoItalianoRI"/&gt;
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}DatiIndirizzoRI"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndirizzoItalianoRI", propOrder = {
    "provincia",
    "comune",
    "cap",
    "toponimo",
    "denominazioneStradale",
    "numeroCivico",
    "frazione"
})
public class IndirizzoItalianoRI {

    protected ProvinciaRI provincia;
    protected ComuneRI comune;
    protected String cap;
    protected String toponimo;
    @XmlElement(name = "denominazione-stradale")
    protected String denominazioneStradale;
    @XmlElement(name = "numero-civico")
    protected String numeroCivico;
    protected String frazione;

    /**
     * Recupera il valore della proprietà provincia.
     * 
     * @return
     *     possible object is
     *     {@link ProvinciaRI }
     *     
     */
    public ProvinciaRI getProvincia() {
        return provincia;
    }

    /**
     * Imposta il valore della proprietà provincia.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvinciaRI }
     *     
     */
    public void setProvincia(ProvinciaRI value) {
        this.provincia = value;
    }

    /**
     * Recupera il valore della proprietà comune.
     * 
     * @return
     *     possible object is
     *     {@link ComuneRI }
     *     
     */
    public ComuneRI getComune() {
        return comune;
    }

    /**
     * Imposta il valore della proprietà comune.
     * 
     * @param value
     *     allowed object is
     *     {@link ComuneRI }
     *     
     */
    public void setComune(ComuneRI value) {
        this.comune = value;
    }

    /**
     * Recupera il valore della proprietà cap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCap() {
        return cap;
    }

    /**
     * Imposta il valore della proprietà cap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCap(String value) {
        this.cap = value;
    }

    /**
     * Recupera il valore della proprietà toponimo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToponimo() {
        return toponimo;
    }

    /**
     * Imposta il valore della proprietà toponimo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToponimo(String value) {
        this.toponimo = value;
    }

    /**
     * Recupera il valore della proprietà denominazioneStradale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneStradale() {
        return denominazioneStradale;
    }

    /**
     * Imposta il valore della proprietà denominazioneStradale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneStradale(String value) {
        this.denominazioneStradale = value;
    }

    /**
     * Recupera il valore della proprietà numeroCivico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCivico() {
        return numeroCivico;
    }

    /**
     * Imposta il valore della proprietà numeroCivico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCivico(String value) {
        this.numeroCivico = value;
    }

    /**
     * Recupera il valore della proprietà frazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrazione() {
        return frazione;
    }

    /**
     * Imposta il valore della proprietà frazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrazione(String value) {
        this.frazione = value;
    }

}
