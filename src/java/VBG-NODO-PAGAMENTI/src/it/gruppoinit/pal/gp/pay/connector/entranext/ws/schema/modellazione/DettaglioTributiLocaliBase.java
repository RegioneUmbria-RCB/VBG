
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DettaglioTributiLocaliBase complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioTributiLocaliBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceFiscaleCoobbligato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IdOperazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Revvedimento" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ImmobiliVariati" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Acconto" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Saldo" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="NumeroImmobili" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceTributo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RateazioneMeseRif" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AnnoRiferimento" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ImportoDebito" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Detrazione" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioTributiLocaliBase", propOrder = {
    "codiceFiscale",
    "codiceFiscaleCoobbligato",
    "idOperazione",
    "codiceEnte",
    "revvedimento",
    "immobiliVariati",
    "acconto",
    "saldo",
    "numeroImmobili",
    "codiceTributo",
    "rateazioneMeseRif",
    "annoRiferimento",
    "importoDebito",
    "detrazione"
})
public class DettaglioTributiLocaliBase {

    @XmlElement(name = "CodiceFiscale")
    protected String codiceFiscale;
    @XmlElement(name = "CodiceFiscaleCoobbligato")
    protected String codiceFiscaleCoobbligato;
    @XmlElement(name = "IdOperazione")
    protected String idOperazione;
    @XmlElement(name = "CodiceEnte")
    protected String codiceEnte;
    @XmlElement(name = "Revvedimento")
    protected boolean revvedimento;
    @XmlElement(name = "ImmobiliVariati")
    protected boolean immobiliVariati;
    @XmlElement(name = "Acconto")
    protected boolean acconto;
    @XmlElement(name = "Saldo")
    protected boolean saldo;
    @XmlElement(name = "NumeroImmobili")
    protected String numeroImmobili;
    @XmlElement(name = "CodiceTributo")
    protected String codiceTributo;
    @XmlElement(name = "RateazioneMeseRif")
    protected String rateazioneMeseRif;
    @XmlElement(name = "AnnoRiferimento")
    protected int annoRiferimento;
    @XmlElement(name = "ImportoDebito", required = true)
    protected BigDecimal importoDebito;
    @XmlElement(name = "Detrazione", required = true)
    protected BigDecimal detrazione;

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
     * Recupera il valore della proprietà codiceFiscaleCoobbligato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscaleCoobbligato() {
        return codiceFiscaleCoobbligato;
    }

    /**
     * Imposta il valore della proprietà codiceFiscaleCoobbligato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscaleCoobbligato(String value) {
        this.codiceFiscaleCoobbligato = value;
    }

    /**
     * Recupera il valore della proprietà idOperazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdOperazione() {
        return idOperazione;
    }

    /**
     * Imposta il valore della proprietà idOperazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdOperazione(String value) {
        this.idOperazione = value;
    }

    /**
     * Recupera il valore della proprietà codiceEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEnte() {
        return codiceEnte;
    }

    /**
     * Imposta il valore della proprietà codiceEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEnte(String value) {
        this.codiceEnte = value;
    }

    /**
     * Recupera il valore della proprietà revvedimento.
     * 
     */
    public boolean isRevvedimento() {
        return revvedimento;
    }

    /**
     * Imposta il valore della proprietà revvedimento.
     * 
     */
    public void setRevvedimento(boolean value) {
        this.revvedimento = value;
    }

    /**
     * Recupera il valore della proprietà immobiliVariati.
     * 
     */
    public boolean isImmobiliVariati() {
        return immobiliVariati;
    }

    /**
     * Imposta il valore della proprietà immobiliVariati.
     * 
     */
    public void setImmobiliVariati(boolean value) {
        this.immobiliVariati = value;
    }

    /**
     * Recupera il valore della proprietà acconto.
     * 
     */
    public boolean isAcconto() {
        return acconto;
    }

    /**
     * Imposta il valore della proprietà acconto.
     * 
     */
    public void setAcconto(boolean value) {
        this.acconto = value;
    }

    /**
     * Recupera il valore della proprietà saldo.
     * 
     */
    public boolean isSaldo() {
        return saldo;
    }

    /**
     * Imposta il valore della proprietà saldo.
     * 
     */
    public void setSaldo(boolean value) {
        this.saldo = value;
    }

    /**
     * Recupera il valore della proprietà numeroImmobili.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroImmobili() {
        return numeroImmobili;
    }

    /**
     * Imposta il valore della proprietà numeroImmobili.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroImmobili(String value) {
        this.numeroImmobili = value;
    }

    /**
     * Recupera il valore della proprietà codiceTributo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceTributo() {
        return codiceTributo;
    }

    /**
     * Imposta il valore della proprietà codiceTributo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceTributo(String value) {
        this.codiceTributo = value;
    }

    /**
     * Recupera il valore della proprietà rateazioneMeseRif.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateazioneMeseRif() {
        return rateazioneMeseRif;
    }

    /**
     * Imposta il valore della proprietà rateazioneMeseRif.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateazioneMeseRif(String value) {
        this.rateazioneMeseRif = value;
    }

    /**
     * Recupera il valore della proprietà annoRiferimento.
     * 
     */
    public int getAnnoRiferimento() {
        return annoRiferimento;
    }

    /**
     * Imposta il valore della proprietà annoRiferimento.
     * 
     */
    public void setAnnoRiferimento(int value) {
        this.annoRiferimento = value;
    }

    /**
     * Recupera il valore della proprietà importoDebito.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoDebito() {
        return importoDebito;
    }

    /**
     * Imposta il valore della proprietà importoDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoDebito(BigDecimal value) {
        this.importoDebito = value;
    }

    /**
     * Recupera il valore della proprietà detrazione.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDetrazione() {
        return detrazione;
    }

    /**
     * Imposta il valore della proprietà detrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDetrazione(BigDecimal value) {
        this.detrazione = value;
    }

}
