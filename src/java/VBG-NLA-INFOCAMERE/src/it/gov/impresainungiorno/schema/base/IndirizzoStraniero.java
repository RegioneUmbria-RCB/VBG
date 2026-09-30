
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Identificazione di indirizzo straniero, per i casi in cui un indirizzo italiano non � valido 
 * 
 * <p>Classe Java per IndirizzoStraniero complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IndirizzoStraniero"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}CittaStraniera"/&gt;
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}DatiIndirizzo"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndirizzoStraniero", propOrder = {
    "cittaStraniera",
    "denominazioneStradale",
    "numeroCivico",
    "frazione"
})
public class IndirizzoStraniero {

    @XmlElement(name = "citta-straniera", required = true)
    protected String cittaStraniera;
    @XmlElement(name = "denominazione-stradale", required = true)
    protected String denominazioneStradale;
    @XmlElement(name = "numero-civico", required = true)
    protected String numeroCivico;
    protected String frazione;

    /**
     * Recupera il valore della proprietà cittaStraniera.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCittaStraniera() {
        return cittaStraniera;
    }

    /**
     * Imposta il valore della proprietà cittaStraniera.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCittaStraniera(String value) {
        this.cittaStraniera = value;
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
