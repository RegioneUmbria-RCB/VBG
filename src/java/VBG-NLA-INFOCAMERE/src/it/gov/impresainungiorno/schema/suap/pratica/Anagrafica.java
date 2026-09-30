
package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.gov.impresainungiorno.schema.base.Stato;


/**
 * <p>Classe Java per Anagrafica complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Anagrafica"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cognome" type="{http://www.impresainungiorno.gov.it/schema/base}StringaLunga"/&gt;
 *         &lt;element name="nome" type="{http://www.impresainungiorno.gov.it/schema/base}StringaLunga"/&gt;
 *         &lt;choice&gt;
 *           &lt;sequence&gt;
 *             &lt;element name="codice-fiscale" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscalePersona"/&gt;
 *             &lt;element name="nazionalita" type="{http://www.impresainungiorno.gov.it/schema/base}Stato" minOccurs="0"/&gt;
 *           &lt;/sequence&gt;
 *           &lt;element name="senza-codice-fiscale" type="{http://www.impresainungiorno.gov.it/schema/base}Stato"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="partita-iva" type="{http://www.impresainungiorno.gov.it/schema/base}PartitaIVA" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Anagrafica", propOrder = {
    "cognome",
    "nome",
    "codiceFiscale",
    "nazionalita",
    "senzaCodiceFiscale",
    "partitaIva"
})
@XmlSeeAlso({
    AnagraficaPersona.class,
    AnagraficaRappresentante.class,
    EstremiDichiarante.class
})
public class Anagrafica {

    @XmlElement(required = true)
    protected String cognome;
    @XmlElement(required = true)
    protected String nome;
    @XmlElement(name = "codice-fiscale")
    protected String codiceFiscale;
    protected Stato nazionalita;
    @XmlElement(name = "senza-codice-fiscale")
    protected Stato senzaCodiceFiscale;
    @XmlElement(name = "partita-iva")
    protected String partitaIva;

    /**
     * Recupera il valore della proprietà cognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della proprietà cognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprietà nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietà nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietà codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietà nazionalita.
     * 
     * @return
     *     possible object is
     *     {@link Stato }
     *     
     */
    public Stato getNazionalita() {
        return nazionalita;
    }

    /**
     * Imposta il valore della proprietà nazionalita.
     * 
     * @param value
     *     allowed object is
     *     {@link Stato }
     *     
     */
    public void setNazionalita(Stato value) {
        this.nazionalita = value;
    }

    /**
     * Recupera il valore della proprietà senzaCodiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link Stato }
     *     
     */
    public Stato getSenzaCodiceFiscale() {
        return senzaCodiceFiscale;
    }

    /**
     * Imposta il valore della proprietà senzaCodiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link Stato }
     *     
     */
    public void setSenzaCodiceFiscale(Stato value) {
        this.senzaCodiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietà partitaIva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Imposta il valore della proprietà partitaIva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitaIva(String value) {
        this.partitaIva = value;
    }

}
