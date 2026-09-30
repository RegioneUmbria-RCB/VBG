package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per PosizioneDebitoria_DettaglioBase complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioneDebitoria_DettaglioBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RiferimentoDettaglio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="ProgressivoDettaglio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="RiferimentoPraticaEsternaPrecedente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RiferimentoDettaglioPraticaEsterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ID_VOCE_DI_COSTO" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="NomeVoceDiCosto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Fruitore" type="{http://entranext.it/}Fruitore" minOccurs="0"/&gt;
 *         &lt;element name="Quantita" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Iva" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CausaleImporto" type="{http://entranext.it/}CausaliImporti"/&gt;
 *         &lt;element name="AnnoCompetenza" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="SezioniParametriSpecifici" type="{http://entranext.it/}SezioneParametriSpecifici" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="AliquotaIva" type="{http://entranext.it/}IVA"/&gt;
 *         &lt;element name="NumeroAccertamentoContabile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AnnoAccertamentoContabile" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="DescrizioneHTML" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportoPieno" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="DettaglioTributiLocali" type="{http://entranext.it/}DettaglioTributiLocaliBase" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioneDebitoria_DettaglioBase", propOrder = { "riferimentoDettaglio", "progressivoDettaglio",
	"riferimentoPraticaEsternaPrecedente", "riferimentoDettaglioPraticaEsterna", "idvocedicosto", "nomeVoceDiCosto", "fruitore", "quantita",
	"importo", "iva", "descrizione", "causaleImporto", "annoCompetenza", "sezioniParametriSpecifici", "aliquotaIva",
	"numeroAccertamentoContabile", "annoAccertamentoContabile", "descrizioneHTML", "importoPieno", "dettaglioTributiLocali" })
@XmlSeeAlso({ PosizioneDebitoriaDettaglio.class, PosizioneDebitoriaDettaglioICP.class, PosizioneDebitoriaDettaglioOSAP.class })
public abstract class PosizioneDebitoriaDettaglioBase {

    @XmlElementRef(name = "RiferimentoDettaglio", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> riferimentoDettaglio;
    @XmlElementRef(name = "ProgressivoDettaglio", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> progressivoDettaglio;
    @XmlElement(name = "RiferimentoPraticaEsternaPrecedente", required = true, nillable = true)
    protected String riferimentoPraticaEsternaPrecedente;
    @XmlElementRef(name = "RiferimentoDettaglioPraticaEsterna", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> riferimentoDettaglioPraticaEsterna;
    @XmlElementRef(name = "ID_VOCE_DI_COSTO", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> idvocedicosto;
    @XmlElement(name = "NomeVoceDiCosto")
    protected String nomeVoceDiCosto;
    @XmlElement(name = "Fruitore")
    protected Fruitore fruitore;
    @XmlElement(name = "Quantita", required = true)
    protected BigDecimal quantita;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "Iva", required = true)
    protected BigDecimal iva;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "CausaleImporto", required = true)
    @XmlSchemaType(name = "string")
    protected CausaliImporti causaleImporto;
    @XmlElement(name = "AnnoCompetenza")
    protected int annoCompetenza;
    @XmlElement(name = "SezioniParametriSpecifici", nillable = true)
    protected List<SezioneParametriSpecifici> sezioniParametriSpecifici;
    @XmlElement(name = "AliquotaIva", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected IVA aliquotaIva;
    @XmlElement(name = "NumeroAccertamentoContabile", required = false)
    protected String numeroAccertamentoContabile;
    @XmlElement(name = "AnnoAccertamentoContabile", required = false)
    protected Integer annoAccertamentoContabile;
    @XmlElement(name = "DescrizioneHTML")
    protected String descrizioneHTML;
    @XmlElement(name = "ImportoPieno")
    protected BigDecimal importoPieno;
    @XmlElementRef(name = "DettaglioTributiLocali", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<DettaglioTributiLocaliBase> dettaglioTributiLocali;

    /**
     * Recupera il valore della proprietà riferimentoDettaglio.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     */
    public JAXBElement<Integer> getRiferimentoDettaglio() {

	return riferimentoDettaglio;
    }

    /**
     * Imposta il valore della proprietà riferimentoDettaglio.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     */
    public void setRiferimentoDettaglio(JAXBElement<Integer> value) {

	this.riferimentoDettaglio = value;
    }

    /**
     * Recupera il valore della proprietà progressivoDettaglio.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     */
    public JAXBElement<Integer> getProgressivoDettaglio() {

	return progressivoDettaglio;
    }

    /**
     * Imposta il valore della proprietà progressivoDettaglio.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     */
    public void setProgressivoDettaglio(JAXBElement<Integer> value) {

	this.progressivoDettaglio = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoPraticaEsternaPrecedente.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRiferimentoPraticaEsternaPrecedente() {

	return riferimentoPraticaEsternaPrecedente;
    }

    /**
     * Imposta il valore della proprietà riferimentoPraticaEsternaPrecedente.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRiferimentoPraticaEsternaPrecedente(String value) {

	this.riferimentoPraticaEsternaPrecedente = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoDettaglioPraticaEsterna.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getRiferimentoDettaglioPraticaEsterna() {

	return riferimentoDettaglioPraticaEsterna;
    }

    /**
     * Imposta il valore della proprietà riferimentoDettaglioPraticaEsterna.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setRiferimentoDettaglioPraticaEsterna(JAXBElement<String> value) {

	this.riferimentoDettaglioPraticaEsterna = value;
    }

    /**
     * Recupera il valore della proprietà idvocedicosto.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     */
    public JAXBElement<Integer> getIDVOCEDICOSTO() {

	return idvocedicosto;
    }

    /**
     * Imposta il valore della proprietà idvocedicosto.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     */
    public void setIDVOCEDICOSTO(JAXBElement<Integer> value) {

	this.idvocedicosto = value;
    }

    /**
     * Recupera il valore della proprietà nomeVoceDiCosto.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNomeVoceDiCosto() {

	return nomeVoceDiCosto;
    }

    /**
     * Imposta il valore della proprietà nomeVoceDiCosto.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNomeVoceDiCosto(String value) {

	this.nomeVoceDiCosto = value;
    }

    /**
     * Recupera il valore della proprietà fruitore.
     * 
     * @return possible object is {@link Fruitore }
     * 
     */
    public Fruitore getFruitore() {

	return fruitore;
    }

    /**
     * Imposta il valore della proprietà fruitore.
     * 
     * @param value
     *            allowed object is {@link Fruitore }
     * 
     */
    public void setFruitore(Fruitore value) {

	this.fruitore = value;
    }

    /**
     * Recupera il valore della proprietà quantita.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getQuantita() {

	return quantita;
    }

    /**
     * Imposta il valore della proprietà quantita.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setQuantita(BigDecimal value) {

	this.quantita = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getImporto() {

	return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setImporto(BigDecimal value) {

	this.importo = value;
    }

    /**
     * Recupera il valore della proprietà iva.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getIva() {

	return iva;
    }

    /**
     * Imposta il valore della proprietà iva.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setIva(BigDecimal value) {

	this.iva = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDescrizione() {

	return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDescrizione(String value) {

	this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà causaleImporto.
     * 
     * @return possible object is {@link CausaliImporti }
     * 
     */
    public CausaliImporti getCausaleImporto() {

	return causaleImporto;
    }

    /**
     * Imposta il valore della proprietà causaleImporto.
     * 
     * @param value
     *            allowed object is {@link CausaliImporti }
     * 
     */
    public void setCausaleImporto(CausaliImporti value) {

	this.causaleImporto = value;
    }

    /**
     * Recupera il valore della proprietà annoCompetenza.
     * 
     */
    public int getAnnoCompetenza() {

	return annoCompetenza;
    }

    /**
     * Imposta il valore della proprietà annoCompetenza.
     * 
     */
    public void setAnnoCompetenza(int value) {

	this.annoCompetenza = value;
    }

    /**
     * Gets the value of the sezioniParametriSpecifici property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the sezioniParametriSpecifici property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getSezioniParametriSpecifici().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link SezioneParametriSpecifici }
     * 
     * 
     */
    public List<SezioneParametriSpecifici> getSezioniParametriSpecifici() {

	if (sezioniParametriSpecifici == null) {
	    sezioniParametriSpecifici = new ArrayList<SezioneParametriSpecifici>();
	}
	return this.sezioniParametriSpecifici;
    }

    /**
     * Recupera il valore della proprietà aliquotaIva.
     * 
     * @return possible object is {@link IVA }
     * 
     */
    public IVA getAliquotaIva() {

	return aliquotaIva;
    }

    /**
     * Imposta il valore della proprietà aliquotaIva.
     * 
     * @param value
     *            allowed object is {@link IVA }
     * 
     */
    public void setAliquotaIva(IVA value) {

	this.aliquotaIva = value;
    }

    /**
     * Recupera il valore della proprietà numeroAccertamentoContabile.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public String getNumeroAccertamentoContabile() {

	return numeroAccertamentoContabile;
    }

    /**
     * Imposta il valore della proprietà numeroAccertamentoContabile.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setNumeroAccertamentoContabile(String value) {

	this.numeroAccertamentoContabile = value;
    }

    /**
     * Recupera il valore della proprietà annoAccertamentoContabile.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     */
    public Integer getAnnoAccertamentoContabile() {

	return annoAccertamentoContabile;
    }

    /**
     * Imposta il valore della proprietà annoAccertamentoContabile.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     */
    public void setAnnoAccertamentoContabile(Integer value) {

	this.annoAccertamentoContabile = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneHTML.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDescrizioneHTML() {

	return descrizioneHTML;
    }

    /**
     * Imposta il valore della proprietà descrizioneHTML.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDescrizioneHTML(String value) {

	this.descrizioneHTML = value;
    }

    /**
     * Recupera il valore della proprietà importoPieno.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getImportoPieno() {

	return importoPieno;
    }

    /**
     * Imposta il valore della proprietà importoPieno.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setImportoPieno(BigDecimal value) {

	this.importoPieno = value;
    }

    public JAXBElement<DettaglioTributiLocaliBase> getDettaglioTributiLocali() {

	return dettaglioTributiLocali;
    }

    /**
     * Imposta il valore della proprietà dettaglioTributiLocali.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link DettaglioTributiLocaliBase }{@code >}
     * 
     */
    public void setDettaglioTributiLocali(JAXBElement<DettaglioTributiLocaliBase> value) {

	this.dettaglioTributiLocali = value;
    }
}
