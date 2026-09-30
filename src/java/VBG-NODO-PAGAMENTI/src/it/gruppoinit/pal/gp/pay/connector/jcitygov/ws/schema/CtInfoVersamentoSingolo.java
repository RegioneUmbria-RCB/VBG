//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctInfoVersamentoSingolo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctInfoVersamentoSingolo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChiaveDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctChiaveDebito" minOccurs="0"/>
 *         &lt;element name="ProgressivoVersamentoSingolo" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="ImportoSingoloRichiesta" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stImporto" minOccurs="0"/>
 *         &lt;element name="ImportoSingoloPagato" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stImporto" minOccurs="0"/>
 *         &lt;element name="EsitoVersamentoSingolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TestoAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctInfoVersamentoSingolo", propOrder = {
    "chiaveDebito",
    "progressivoVersamentoSingolo",
    "importoSingoloRichiesta",
    "importoSingoloPagato",
    "esitoVersamentoSingolo",
    "testoAllegato",
    "tipoAllegato"
})
public class CtInfoVersamentoSingolo {

    @XmlElement(name = "ChiaveDebito")
    protected CtChiaveDebito chiaveDebito;
    @XmlElement(name = "ProgressivoVersamentoSingolo")
    protected BigInteger progressivoVersamentoSingolo;
    @XmlElement(name = "ImportoSingoloRichiesta")
    protected BigDecimal importoSingoloRichiesta;
    @XmlElement(name = "ImportoSingoloPagato")
    protected BigDecimal importoSingoloPagato;
    @XmlElement(name = "EsitoVersamentoSingolo", required = true)
    protected String esitoVersamentoSingolo;
    @XmlElement(name = "TestoAllegato")
    protected String testoAllegato;
    @XmlElement(name = "TipoAllegato")
    protected String tipoAllegato;

    /**
     * Recupera il valore della proprietà chiaveDebito.
     * 
     * @return
     *     possible object is
     *     {@link CtChiaveDebito }
     *     
     */
    public CtChiaveDebito getChiaveDebito() {
        return chiaveDebito;
    }

    /**
     * Imposta il valore della proprietà chiaveDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtChiaveDebito }
     *     
     */
    public void setChiaveDebito(CtChiaveDebito value) {
        this.chiaveDebito = value;
    }

    /**
     * Recupera il valore della proprietà progressivoVersamentoSingolo.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProgressivoVersamentoSingolo() {
        return progressivoVersamentoSingolo;
    }

    /**
     * Imposta il valore della proprietà progressivoVersamentoSingolo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProgressivoVersamentoSingolo(BigInteger value) {
        this.progressivoVersamentoSingolo = value;
    }

    /**
     * Recupera il valore della proprietà importoSingoloRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoSingoloRichiesta() {
        return importoSingoloRichiesta;
    }

    /**
     * Imposta il valore della proprietà importoSingoloRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoSingoloRichiesta(BigDecimal value) {
        this.importoSingoloRichiesta = value;
    }

    /**
     * Recupera il valore della proprietà importoSingoloPagato.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoSingoloPagato() {
        return importoSingoloPagato;
    }

    /**
     * Imposta il valore della proprietà importoSingoloPagato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoSingoloPagato(BigDecimal value) {
        this.importoSingoloPagato = value;
    }

    /**
     * Recupera il valore della proprietà esitoVersamentoSingolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitoVersamentoSingolo() {
        return esitoVersamentoSingolo;
    }

    /**
     * Imposta il valore della proprietà esitoVersamentoSingolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitoVersamentoSingolo(String value) {
        this.esitoVersamentoSingolo = value;
    }

    /**
     * Recupera il valore della proprietà testoAllegato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestoAllegato() {
        return testoAllegato;
    }

    /**
     * Imposta il valore della proprietà testoAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestoAllegato(String value) {
        this.testoAllegato = value;
    }

    /**
     * Recupera il valore della proprietà tipoAllegato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAllegato() {
        return tipoAllegato;
    }

    /**
     * Imposta il valore della proprietà tipoAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAllegato(String value) {
        this.tipoAllegato = value;
    }

}
