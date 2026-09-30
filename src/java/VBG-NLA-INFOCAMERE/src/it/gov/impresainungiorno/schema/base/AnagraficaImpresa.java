
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Questo elemento contiene i dati identificativi di un'impresa. Forma-giuridica: contiene come attributo il codice della forma
 *         giuridica, come contenuto il nome esteso. 
 * 
 * <p>Classe Java per AnagraficaImpresa complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnagraficaImpresa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="forma-giuridica" type="{http://www.impresainungiorno.gov.it/schema/base}FormaGiuridica" minOccurs="0"/&gt;
 *         &lt;element name="ragione-sociale" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/&gt;
 *         &lt;element name="codice-fiscale" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscale"/&gt;
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
@XmlType(name = "AnagraficaImpresa", propOrder = {
    "formaGiuridica",
    "ragioneSociale",
    "codiceFiscale",
    "partitaIva"
})
@XmlSeeAlso({
    DettaglioImpresa.class
})
public class AnagraficaImpresa {

    @XmlElement(name = "forma-giuridica")
    protected FormaGiuridica formaGiuridica;
    @XmlElement(name = "ragione-sociale", required = true)
    protected String ragioneSociale;
    @XmlElement(name = "codice-fiscale", required = true)
    protected String codiceFiscale;
    @XmlElement(name = "partita-iva")
    protected String partitaIva;

    /**
     * Recupera il valore della proprietà formaGiuridica.
     * 
     * @return
     *     possible object is
     *     {@link FormaGiuridica }
     *     
     */
    public FormaGiuridica getFormaGiuridica() {
        return formaGiuridica;
    }

    /**
     * Imposta il valore della proprietà formaGiuridica.
     * 
     * @param value
     *     allowed object is
     *     {@link FormaGiuridica }
     *     
     */
    public void setFormaGiuridica(FormaGiuridica value) {
        this.formaGiuridica = value;
    }

    /**
     * Recupera il valore della proprietà ragioneSociale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    /**
     * Imposta il valore della proprietà ragioneSociale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagioneSociale(String value) {
        this.ragioneSociale = value;
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
