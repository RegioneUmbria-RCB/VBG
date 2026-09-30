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
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.CIP;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.CoordinateBancarie;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.StatoPagamento;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.TipoOperazioneUpdateStatus;

/**
 * <p>
 * Classe Java per DettaglioPagamento.UpdateStatus complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioPagamento.UpdateStatus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdPagamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CIP" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}CIP" minOccurs="0"/&gt;
 *         &lt;element name="DataScadenza" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/&gt;
 *         &lt;element name="DataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/&gt;
 *         &lt;element name="DataFineValidita" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/&gt;
 *         &lt;element name="Stato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}StatoPagamento"/&gt;
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="DettaglioImporto" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioImporto" minOccurs="0"/&gt;
 *         &lt;element name="DettaglioTransazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioTransazione" minOccurs="0"/&gt;
 *         &lt;element name="Allegato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Allegato" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="CausalePagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AccreditoPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}CoordinateBancarie" minOccurs="0"/&gt;
 *         &lt;element name="TipoOperazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}TipoOperazioneUpdateStatus" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioPagamento.UpdateStatus", propOrder = { "idPagamento", "cip", "dataScadenza", "dataInizioValidita", "dataFineValidita",
	"stato", "importo", "dettaglioImporto", "dettaglioTransazione", "allegato", "causalePagamento", "accreditoPagamento", "tipoOperazione" })
public class DettaglioPagamentoUpdateStatus {

    @XmlElement(name = "IdPagamento", required = true)
    protected String idPagamento;
    @XmlElement(name = "CIP")
    protected CIP cip;
    @XmlElement(name = "DataScadenza")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataScadenza;
    @XmlElement(name = "DataInizioValidita")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(name = "DataFineValidita")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFineValidita;
    @XmlElement(name = "Stato", required = true)
    @XmlSchemaType(name = "string")
    protected StatoPagamento stato;
    @XmlElement(name = "Importo")
    protected BigDecimal importo;
    @XmlElement(name = "DettaglioImporto")
    protected DettaglioImporto dettaglioImporto;
    @XmlElement(name = "DettaglioTransazione")
    protected DettaglioTransazione dettaglioTransazione;
    @XmlElement(name = "Allegato")
    protected List<Allegato> allegato;
    @XmlElement(name = "CausalePagamento")
    protected String causalePagamento;
    @XmlElement(name = "AccreditoPagamento")
    protected CoordinateBancarie accreditoPagamento;
    @XmlElement(name = "TipoOperazione", defaultValue = "Update")
    @XmlSchemaType(name = "string")
    protected TipoOperazioneUpdateStatus tipoOperazione;

    /**
     * Recupera il valore della proprietà idPagamento.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIdPagamento() {

	return idPagamento;
    }

    /**
     * Imposta il valore della proprietà idPagamento.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIdPagamento(String value) {

	this.idPagamento = value;
    }

    /**
     * Recupera il valore della proprietà cip.
     * 
     * @return possible object is {@link CIP }
     * 
     */
    public CIP getCIP() {

	return cip;
    }

    /**
     * Imposta il valore della proprietà cip.
     * 
     * @param value
     *            allowed object is {@link CIP }
     * 
     */
    public void setCIP(CIP value) {

	this.cip = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenza.
     * 
     * @return possible object is {@link Object }
     * 
     */
    public XMLGregorianCalendar getDataScadenza() {

	return dataScadenza;
    }

    /**
     * Imposta il valore della proprietà dataScadenza.
     * 
     * @param value
     *            allowed object is {@link Object }
     * 
     */
    public void setDataScadenza(XMLGregorianCalendar value) {

	this.dataScadenza = value;
    }

    /**
     * Recupera il valore della proprietà dataInizioValidita.
     * 
     * @return possible object is {@link Object }
     * 
     */
    public XMLGregorianCalendar getDataInizioValidita() {

	return dataInizioValidita;
    }

    /**
     * Imposta il valore della proprietà dataInizioValidita.
     * 
     * @param value
     *            allowed object is {@link Object }
     * 
     */
    public void setDataInizioValidita(XMLGregorianCalendar value) {

	this.dataInizioValidita = value;
    }

    /**
     * Recupera il valore della proprietà dataFineValidita.
     * 
     * @return possible object is {@link Object }
     * 
     */
    public XMLGregorianCalendar getDataFineValidita() {

	return dataFineValidita;
    }

    /**
     * Imposta il valore della proprietà dataFineValidita.
     * 
     * @param value
     *            allowed object is {@link Object }
     * 
     */
    public void setDataFineValidita(XMLGregorianCalendar value) {

	this.dataFineValidita = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return possible object is {@link StatoPagamento }
     * 
     */
    public StatoPagamento getStato() {

	return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *            allowed object is {@link StatoPagamento }
     * 
     */
    public void setStato(StatoPagamento value) {

	this.stato = value;
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
     * Recupera il valore della proprietà dettaglioImporto.
     * 
     * @return possible object is {@link DettaglioImporto }
     * 
     */
    public DettaglioImporto getDettaglioImporto() {

	return dettaglioImporto;
    }

    /**
     * Imposta il valore della proprietà dettaglioImporto.
     * 
     * @param value
     *            allowed object is {@link DettaglioImporto }
     * 
     */
    public void setDettaglioImporto(DettaglioImporto value) {

	this.dettaglioImporto = value;
    }

    /**
     * Recupera il valore della proprietà dettaglioTransazione.
     * 
     * @return possible object is {@link DettaglioTransazione }
     * 
     */
    public DettaglioTransazione getDettaglioTransazione() {

	return dettaglioTransazione;
    }

    /**
     * Imposta il valore della proprietà dettaglioTransazione.
     * 
     * @param value
     *            allowed object is {@link DettaglioTransazione }
     * 
     */
    public void setDettaglioTransazione(DettaglioTransazione value) {

	this.dettaglioTransazione = value;
    }

    /**
     * Gets the value of the allegato property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the allegato property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAllegato().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Allegato }
     * 
     * 
     */
    public List<Allegato> getAllegato() {

	if (allegato == null) {
	    allegato = new ArrayList<Allegato>();
	}
	return this.allegato;
    }

    /**
     * Recupera il valore della proprietà causalePagamento.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCausalePagamento() {

	return causalePagamento;
    }

    /**
     * Imposta il valore della proprietà causalePagamento.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCausalePagamento(String value) {

	this.causalePagamento = value;
    }

    /**
     * Recupera il valore della proprietà accreditoPagamento.
     * 
     * @return possible object is {@link CoordinateBancarie }
     * 
     */
    public CoordinateBancarie getAccreditoPagamento() {

	return accreditoPagamento;
    }

    /**
     * Imposta il valore della proprietà accreditoPagamento.
     * 
     * @param value
     *            allowed object is {@link CoordinateBancarie }
     * 
     */
    public void setAccreditoPagamento(CoordinateBancarie value) {

	this.accreditoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà tipoOperazione.
     * 
     * @return possible object is {@link TipoOperazioneUpdateStatus }
     * 
     */
    public TipoOperazioneUpdateStatus getTipoOperazione() {

	return tipoOperazione;
    }

    /**
     * Imposta il valore della proprietà tipoOperazione.
     * 
     * @param value
     *            allowed object is {@link TipoOperazioneUpdateStatus }
     * 
     */
    public void setTipoOperazione(TipoOperazioneUpdateStatus value) {

	this.tipoOperazione = value;
    }
}
