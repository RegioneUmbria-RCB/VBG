package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.Allegato;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.Divisa;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.StatoPendenza;

/**
 * <p>
 * Classe Java per Pendenza.InsertReplace complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Pendenza.InsertReplace"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DescrizioneCausale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Riscossore" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Riscossore" minOccurs="0"/&gt;
 *         &lt;element name="DataCreazione" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/&gt;
 *         &lt;element name="DataEmissione" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/&gt;
 *         &lt;element name="DataPrescrizione" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/&gt;
 *         &lt;element name="AnnoRiferimento" type="{http://www.w3.org/2001/XMLSchema}gYear"/&gt;
 *         &lt;element name="DataModificaEnte" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/&gt;
 *         &lt;element name="Stato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}StatoPendenza"/&gt;
 *         &lt;element name="ImportoTotale" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Divisa" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Divisa"/&gt;
 *         &lt;element name="InfoPagamento" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}InfoPagamento.InsertReplace"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="DettaglioPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioPagamento.InsertReplace" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Allegato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Allegato" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pendenza.InsertReplace", propOrder = { "descrizioneCausale", "riscossore", "dataCreazione", "dataEmissione", "dataPrescrizione",
	"annoRiferimento", "dataModificaEnte", "stato", "importoTotale", "divisa", "infoPagamento", "allegato" })
public class PendenzaInsertReplace {

    @XmlElement(name = "DescrizioneCausale", required = true)
    protected String descrizioneCausale;
    @XmlElement(name = "Riscossore")
    protected Riscossore riscossore;
    @XmlElement(name = "DataCreazione", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCreazione;
    @XmlElement(name = "DataEmissione", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEmissione;
    @XmlElement(name = "DataPrescrizione", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPrescrizione;
    @XmlElement(name = "AnnoRiferimento", required = true)
    @XmlSchemaType(name = "gYear")
    protected XMLGregorianCalendar annoRiferimento;
    @XmlElement(name = "DataModificaEnte")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataModificaEnte;
    @XmlElement(name = "Stato", required = true)
    @XmlSchemaType(name = "string")
    protected StatoPendenza stato;
    @XmlElement(name = "ImportoTotale", required = true)
    protected BigDecimal importoTotale;
    @XmlElement(name = "Divisa", required = true)
    @XmlSchemaType(name = "string")
    protected Divisa divisa;
    @XmlElement(name = "InfoPagamento", required = true)
    protected List<PendenzaInsertReplace.InfoPagamento> infoPagamento;
    @XmlElement(name = "Allegato")
    protected Allegato allegato;

    /**
     * Recupera il valore della proprietà descrizioneCausale.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDescrizioneCausale() {

	return descrizioneCausale;
    }

    /**
     * Imposta il valore della proprietà descrizioneCausale.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDescrizioneCausale(String value) {

	this.descrizioneCausale = value;
    }

    /**
     * Recupera il valore della proprietà riscossore.
     * 
     * @return possible object is {@link Riscossore }
     * 
     */
    public Riscossore getRiscossore() {

	return riscossore;
    }

    /**
     * Imposta il valore della proprietà riscossore.
     * 
     * @param value
     *            allowed object is {@link Riscossore }
     * 
     */
    public void setRiscossore(Riscossore value) {

	this.riscossore = value;
    }

    /**
     * Recupera il valore della proprietà dataCreazione.
     * 
     * @return possible object is {@link Object }
     * 
     */
    public XMLGregorianCalendar getDataCreazione() {

	return dataCreazione;
    }

    /**
     * Imposta il valore della proprietà dataCreazione.
     * 
     * @param value
     *            allowed object is {@link Object }
     * 
     */
    public void setDataCreazione(XMLGregorianCalendar value) {

	this.dataCreazione = value;
    }

    /**
     * Recupera il valore della proprietà dataEmissione.
     * 
     * @return possible object is {@link Object }
     * 
     */
    public XMLGregorianCalendar getDataEmissione() {

	return dataEmissione;
    }

    /**
     * Imposta il valore della proprietà dataEmissione.
     * 
     * @param value
     *            allowed object is {@link Object }
     * 
     */
    public void setDataEmissione(XMLGregorianCalendar value) {

	this.dataEmissione = value;
    }

    /**
     * Recupera il valore della proprietà dataPrescrizione.
     * 
     * @return possible object is {@link Object }
     * 
     */
    public XMLGregorianCalendar getDataPrescrizione() {

	return dataPrescrizione;
    }

    /**
     * Imposta il valore della proprietà dataPrescrizione.
     * 
     * @param value
     *            allowed object is {@link Object }
     * 
     */
    public void setDataPrescrizione(XMLGregorianCalendar value) {

	this.dataPrescrizione = value;
    }

    /**
     * Recupera il valore della proprietà annoRiferimento.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getAnnoRiferimento() {

	return annoRiferimento;
    }

    /**
     * Imposta il valore della proprietà annoRiferimento.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setAnnoRiferimento(XMLGregorianCalendar value) {

	this.annoRiferimento = value;
    }

    /**
     * Recupera il valore della proprietà dataModificaEnte.
     * 
     * @return possible object is {@link Object }
     * 
     */
    public XMLGregorianCalendar getDataModificaEnte() {

	return dataModificaEnte;
    }

    /**
     * Imposta il valore della proprietà dataModificaEnte.
     * 
     * @param value
     *            allowed object is {@link Object }
     * 
     */
    public void setDataModificaEnte(XMLGregorianCalendar value) {

	this.dataModificaEnte = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return possible object is {@link StatoPendenza }
     * 
     */
    public StatoPendenza getStato() {

	return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *            allowed object is {@link StatoPendenza }
     * 
     */
    public void setStato(StatoPendenza value) {

	this.stato = value;
    }

    /**
     * Recupera il valore della proprietà importoTotale.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getImportoTotale() {

	return importoTotale;
    }

    /**
     * Imposta il valore della proprietà importoTotale.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setImportoTotale(BigDecimal value) {

	this.importoTotale = value;
    }

    /**
     * Recupera il valore della proprietà divisa.
     * 
     * @return possible object is {@link Divisa }
     * 
     */
    public Divisa getDivisa() {

	return divisa;
    }

    /**
     * Imposta il valore della proprietà divisa.
     * 
     * @param value
     *            allowed object is {@link Divisa }
     * 
     */
    public void setDivisa(Divisa value) {

	this.divisa = value;
    }

    /**
     * Gets the value of the infoPagamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the infoPagamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getInfoPagamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link PendenzaInsertReplace.InfoPagamento }
     * 
     * 
     */
    public List<PendenzaInsertReplace.InfoPagamento> getInfoPagamento() {

	if (infoPagamento == null) {
	    infoPagamento = new ArrayList<PendenzaInsertReplace.InfoPagamento>();
	}
	return this.infoPagamento;
    }

    /**
     * Recupera il valore della proprietà allegato.
     * 
     * @return possible object is {@link Allegato }
     * 
     */
    public Allegato getAllegato() {

	return allegato;
    }

    /**
     * Imposta il valore della proprietà allegato.
     * 
     * @param value
     *            allowed object is {@link Allegato }
     * 
     */
    public void setAllegato(Allegato value) {

	this.allegato = value;
    }

    /**
     * <p>
     * Classe Java per anonymous complex type.
     * 
     * <p>
     * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;extension base="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}InfoPagamento.InsertReplace"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="DettaglioPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioPagamento.InsertReplace" maxOccurs="unbounded"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "dettaglioPagamento" })
    public static class InfoPagamento extends InfoPagamentoInsertReplace {

	@XmlElement(name = "DettaglioPagamento", required = true)
	protected List<DettaglioPagamentoInsertReplace> dettaglioPagamento;

	/**
	 * Gets the value of the dettaglioPagamento property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you
	 * make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE>
	 * method for the dettaglioPagamento property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getDettaglioPagamento().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link DettaglioPagamentoInsertReplace }
	 * 
	 * 
	 */
	public List<DettaglioPagamentoInsertReplace> getDettaglioPagamento() {

	    if (dettaglioPagamento == null) {
		dettaglioPagamento = new ArrayList<DettaglioPagamentoInsertReplace>();
	    }
	    return this.dettaglioPagamento;
	}
    }
}
