
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PosizioniNoteDiCredito_Dettagli complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioniNoteDiCredito_Dettagli"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RiferimentoDettaglioPraticaEsterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RiferimentoDettaglio" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ID_VOCE_DI_COSTO" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="NomeVoceDiCosto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceFiscaleFruitore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Pagato" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioniNoteDiCredito_Dettagli", propOrder = {
    "riferimentoDettaglioPraticaEsterna",
    "riferimentoDettaglio",
    "idvocedicosto",
    "nomeVoceDiCosto",
    "codiceFiscaleFruitore",
    "importo",
    "pagato"
})
public class PosizioniNoteDiCreditoDettagli {

    @XmlElementRef(name = "RiferimentoDettaglioPraticaEsterna", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> riferimentoDettaglioPraticaEsterna;
    @XmlElement(name = "RiferimentoDettaglio")
    protected int riferimentoDettaglio;
    @XmlElementRef(name = "ID_VOCE_DI_COSTO", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> idvocedicosto;
    @XmlElement(name = "NomeVoceDiCosto")
    protected String nomeVoceDiCosto;
    @XmlElement(name = "CodiceFiscaleFruitore")
    protected String codiceFiscaleFruitore;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "Pagato")
    protected boolean pagato;

    /**
     * Recupera il valore della proprietà riferimentoDettaglioPraticaEsterna.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRiferimentoDettaglioPraticaEsterna() {
        return riferimentoDettaglioPraticaEsterna;
    }

    /**
     * Imposta il valore della proprietà riferimentoDettaglioPraticaEsterna.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRiferimentoDettaglioPraticaEsterna(JAXBElement<String> value) {
        this.riferimentoDettaglioPraticaEsterna = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoDettaglio.
     * 
     */
    public int getRiferimentoDettaglio() {
        return riferimentoDettaglio;
    }

    /**
     * Imposta il valore della proprietà riferimentoDettaglio.
     * 
     */
    public void setRiferimentoDettaglio(int value) {
        this.riferimentoDettaglio = value;
    }

    /**
     * Recupera il valore della proprietà idvocedicosto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getIDVOCEDICOSTO() {
        return idvocedicosto;
    }

    /**
     * Imposta il valore della proprietà idvocedicosto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setIDVOCEDICOSTO(JAXBElement<Integer> value) {
        this.idvocedicosto = value;
    }

    /**
     * Recupera il valore della proprietà nomeVoceDiCosto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeVoceDiCosto() {
        return nomeVoceDiCosto;
    }

    /**
     * Imposta il valore della proprietà nomeVoceDiCosto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeVoceDiCosto(String value) {
        this.nomeVoceDiCosto = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscaleFruitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscaleFruitore() {
        return codiceFiscaleFruitore;
    }

    /**
     * Imposta il valore della proprietà codiceFiscaleFruitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscaleFruitore(String value) {
        this.codiceFiscaleFruitore = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

    /**
     * Recupera il valore della proprietà pagato.
     * 
     */
    public boolean isPagato() {
        return pagato;
    }

    /**
     * Imposta il valore della proprietà pagato.
     * 
     */
    public void setPagato(boolean value) {
        this.pagato = value;
    }

}
