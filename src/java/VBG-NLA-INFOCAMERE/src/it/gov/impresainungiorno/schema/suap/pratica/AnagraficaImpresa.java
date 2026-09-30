
package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.gov.impresainungiorno.schema.base.CodiceREA;
import it.gov.impresainungiorno.schema.base.IndirizzoConRecapiti;
import it.gov.impresainungiorno.schema.base.Stato;


/**
 * 
 *             Dati relativi alla sede legale dell'impresa
 *         
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
 *         &lt;element name="forma-giuridica" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}FormaGiuridica"/&gt;
 *         &lt;element name="ragione-sociale" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/&gt;
 *         &lt;choice&gt;
 *           &lt;sequence&gt;
 *             &lt;element name="codice-fiscale" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscale" minOccurs="0"/&gt;
 *             &lt;element name="partita-iva" type="{http://www.impresainungiorno.gov.it/schema/base}PartitaIVA" minOccurs="0"/&gt;
 *             &lt;element name="codice-REA" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceREA" minOccurs="0"/&gt;
 *           &lt;/sequence&gt;
 *           &lt;sequence&gt;
 *             &lt;element name="stato" type="{http://www.impresainungiorno.gov.it/schema/base}Stato"/&gt;
 *             &lt;element name="identificativo-legale" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/&gt;
 *             &lt;element name="vat" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve" minOccurs="0"/&gt;
 *           &lt;/sequence&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="indirizzo" type="{http://www.impresainungiorno.gov.it/schema/base}IndirizzoConRecapiti"/&gt;
 *         &lt;element name="legale-rappresentante" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}AnagraficaRappresentante"/&gt;
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
    "partitaIva",
    "codiceREA",
    "stato",
    "identificativoLegale",
    "vat",
    "indirizzo",
    "legaleRappresentante"
})
public class AnagraficaImpresa {

    @XmlElement(name = "forma-giuridica", required = true)
    protected FormaGiuridica formaGiuridica;
    @XmlElement(name = "ragione-sociale", required = true)
    protected String ragioneSociale;
    @XmlElement(name = "codice-fiscale")
    protected String codiceFiscale;
    @XmlElement(name = "partita-iva")
    protected String partitaIva;
    @XmlElement(name = "codice-REA")
    protected CodiceREA codiceREA;
    protected Stato stato;
    @XmlElement(name = "identificativo-legale")
    protected String identificativoLegale;
    protected String vat;
    @XmlElement(required = true)
    protected IndirizzoConRecapiti indirizzo;
    @XmlElement(name = "legale-rappresentante", required = true)
    protected AnagraficaRappresentante legaleRappresentante;

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

    /**
     * Recupera il valore della proprietà codiceREA.
     * 
     * @return
     *     possible object is
     *     {@link CodiceREA }
     *     
     */
    public CodiceREA getCodiceREA() {
        return codiceREA;
    }

    /**
     * Imposta il valore della proprietà codiceREA.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceREA }
     *     
     */
    public void setCodiceREA(CodiceREA value) {
        this.codiceREA = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return
     *     possible object is
     *     {@link Stato }
     *     
     */
    public Stato getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *     allowed object is
     *     {@link Stato }
     *     
     */
    public void setStato(Stato value) {
        this.stato = value;
    }

    /**
     * Recupera il valore della proprietà identificativoLegale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoLegale() {
        return identificativoLegale;
    }

    /**
     * Imposta il valore della proprietà identificativoLegale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoLegale(String value) {
        this.identificativoLegale = value;
    }

    /**
     * Recupera il valore della proprietà vat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVat() {
        return vat;
    }

    /**
     * Imposta il valore della proprietà vat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVat(String value) {
        this.vat = value;
    }

    /**
     * Recupera il valore della proprietà indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public IndirizzoConRecapiti getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della proprietà indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public void setIndirizzo(IndirizzoConRecapiti value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della proprietà legaleRappresentante.
     * 
     * @return
     *     possible object is
     *     {@link AnagraficaRappresentante }
     *     
     */
    public AnagraficaRappresentante getLegaleRappresentante() {
        return legaleRappresentante;
    }

    /**
     * Imposta il valore della proprietà legaleRappresentante.
     * 
     * @param value
     *     allowed object is
     *     {@link AnagraficaRappresentante }
     *     
     */
    public void setLegaleRappresentante(AnagraficaRappresentante value) {
        this.legaleRappresentante = value;
    }

}
