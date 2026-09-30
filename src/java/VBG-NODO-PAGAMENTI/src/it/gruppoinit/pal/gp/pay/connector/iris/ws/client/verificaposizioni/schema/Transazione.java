package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.include.DettaglioCanalePagamento;

/**
 * <p>
 * Classe Java per Transazione complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Transazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CanalePagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}CanalePagamento"/&gt;
 *         &lt;element name="MezzoPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}MezzoPagamento"/&gt;
 *         &lt;element name="DettaglioCanalePagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}DettaglioCanalePagamento" minOccurs="0"/&gt;
 *         &lt;element name="IdTransazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DataOraTransazione" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/&gt;
 *         &lt;element name="CodiceAutorizzazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DataOraAutorizzazione" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/&gt;
 *         &lt;element name="TipoSicurezza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportoTransato" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="DettaglioImportoTransato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}DettaglioImportoTransato" minOccurs="0"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Transazione", propOrder = { "canalePagamento", "mezzoPagamento", "dettaglioCanalePagamento", "idTransazione", "dataOraTransazione",
	"codiceAutorizzazione", "dataOraAutorizzazione", "tipoSicurezza", "importoTransato", "dettaglioImportoTransato", "descrizione" })
public class Transazione {

    @XmlElement(name = "CanalePagamento", required = true)
    protected CanalePagamento canalePagamento;
    @XmlElement(name = "MezzoPagamento", required = true)
    protected MezzoPagamento mezzoPagamento;
    @XmlElement(name = "DettaglioCanalePagamento")
    protected DettaglioCanalePagamento dettaglioCanalePagamento;
    @XmlElement(name = "IdTransazione", required = true)
    protected String idTransazione;
    @XmlElement(name = "DataOraTransazione", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraTransazione;
    @XmlElement(name = "CodiceAutorizzazione")
    protected String codiceAutorizzazione;
    @XmlElement(name = "DataOraAutorizzazione")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraAutorizzazione;
    @XmlElement(name = "TipoSicurezza")
    protected String tipoSicurezza;
    @XmlElement(name = "ImportoTransato", required = true)
    protected BigDecimal importoTransato;
    @XmlElement(name = "DettaglioImportoTransato")
    protected DettaglioImportoTransato dettaglioImportoTransato;
    @XmlElement(name = "Descrizione")
    protected String descrizione;

    /**
     * Recupera il valore della proprietà canalePagamento.
     * 
     * @return possible object is {@link CanalePagamento }
     * 
     */
    public CanalePagamento getCanalePagamento() {

	return canalePagamento;
    }

    /**
     * Imposta il valore della proprietà canalePagamento.
     * 
     * @param value
     *            allowed object is {@link CanalePagamento }
     * 
     */
    public void setCanalePagamento(CanalePagamento value) {

	this.canalePagamento = value;
    }

    /**
     * Recupera il valore della proprietà mezzoPagamento.
     * 
     * @return possible object is {@link MezzoPagamento }
     * 
     */
    public MezzoPagamento getMezzoPagamento() {

	return mezzoPagamento;
    }

    /**
     * Imposta il valore della proprietà mezzoPagamento.
     * 
     * @param value
     *            allowed object is {@link MezzoPagamento }
     * 
     */
    public void setMezzoPagamento(MezzoPagamento value) {

	this.mezzoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà dettaglioCanalePagamento.
     * 
     * @return possible object is {@link DettaglioCanalePagamento }
     * 
     */
    public DettaglioCanalePagamento getDettaglioCanalePagamento() {

	return dettaglioCanalePagamento;
    }

    /**
     * Imposta il valore della proprietà dettaglioCanalePagamento.
     * 
     * @param value
     *            allowed object is {@link DettaglioCanalePagamento }
     * 
     */
    public void setDettaglioCanalePagamento(DettaglioCanalePagamento value) {

	this.dettaglioCanalePagamento = value;
    }

    /**
     * Recupera il valore della proprietà idTransazione.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIdTransazione() {

	return idTransazione;
    }

    /**
     * Imposta il valore della proprietà idTransazione.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIdTransazione(String value) {

	this.idTransazione = value;
    }

    /**
     * Recupera il valore della proprietà dataOraTransazione.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataOraTransazione() {

	return dataOraTransazione;
    }

    /**
     * Imposta il valore della proprietà dataOraTransazione.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataOraTransazione(XMLGregorianCalendar value) {

	this.dataOraTransazione = value;
    }

    /**
     * Recupera il valore della proprietà codiceAutorizzazione.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCodiceAutorizzazione() {

	return codiceAutorizzazione;
    }

    /**
     * Imposta il valore della proprietà codiceAutorizzazione.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCodiceAutorizzazione(String value) {

	this.codiceAutorizzazione = value;
    }

    /**
     * Recupera il valore della proprietà dataOraAutorizzazione.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataOraAutorizzazione() {

	return dataOraAutorizzazione;
    }

    /**
     * Imposta il valore della proprietà dataOraAutorizzazione.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataOraAutorizzazione(XMLGregorianCalendar value) {

	this.dataOraAutorizzazione = value;
    }

    /**
     * Recupera il valore della proprietà tipoSicurezza.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTipoSicurezza() {

	return tipoSicurezza;
    }

    /**
     * Imposta il valore della proprietà tipoSicurezza.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTipoSicurezza(String value) {

	this.tipoSicurezza = value;
    }

    /**
     * Recupera il valore della proprietà importoTransato.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getImportoTransato() {

	return importoTransato;
    }

    /**
     * Imposta il valore della proprietà importoTransato.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setImportoTransato(BigDecimal value) {

	this.importoTransato = value;
    }

    /**
     * Recupera il valore della proprietà dettaglioImportoTransato.
     * 
     * @return possible object is {@link DettaglioImportoTransato }
     * 
     */
    public DettaglioImportoTransato getDettaglioImportoTransato() {

	return dettaglioImportoTransato;
    }

    /**
     * Imposta il valore della proprietà dettaglioImportoTransato.
     * 
     * @param value
     *            allowed object is {@link DettaglioImportoTransato }
     * 
     */
    public void setDettaglioImportoTransato(DettaglioImportoTransato value) {

	this.dettaglioImportoTransato = value;
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
}
