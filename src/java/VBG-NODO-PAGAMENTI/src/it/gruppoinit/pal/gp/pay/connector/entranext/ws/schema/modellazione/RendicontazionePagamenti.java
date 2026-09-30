package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Classe Java per RendicontazionePagamenti complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RendicontazionePagamenti"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID_PAGAMENTO" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Provenienza" type="{http://entranext.it/}ProvenienzePagamenti"/&gt;
 *         &lt;element name="DescrizioneProvenienza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Modalita" type="{http://entranext.it/}ModalitaPagamento"/&gt;
 *         &lt;element name="DescrizioneModalita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AnnoImposta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RiferimentoPraticaEsterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="QuintoCampo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="DataVersamento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataRiversamento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataAccredito" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataConsolidamento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataInserimento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Nominativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Cassa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TipoPagamento" type="{http://entranext.it/}TipiPagamenti"/&gt;
 *         &lt;element name="ID_FLUSSO" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Quietanza" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Annullato" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ContoCorrente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IBAN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Iuv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PresenteRT" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PagatoAFronteDi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RiferimentoDocumentoPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IdentificativoPagamentoEntraNext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NumeroProvvisorioEntrata" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="AnnoProvvisorioEntrata" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RendicontazionePagamenti", propOrder = { "idpagamento", "ente", "provenienza", "descrizioneProvenienza", "modalita",
	"descrizioneModalita", "annoImposta", "riferimentoPraticaEsterna", "quintoCampo", "importo", "dataPagamento", "dataVersamento",
	"dataRiversamento", "dataAccredito", "dataConsolidamento", "dataScadenza", "dataInserimento", "nominativo", "codiceFiscale", "cassa",
	"tipoPagamento", "idflusso", "quietanza", "annullato", "contoCorrente", "iban", "iuv", "codiceAvviso", "presenteRT", "pagatoAFronteDi",
	"riferimentoDocumentoPagamento", "identificativoPagamentoEntraNext", "numeroProvvisorioEntrata", "annoProvvisorioEntrata" })
public class RendicontazionePagamenti {

    @XmlElement(name = "ID_PAGAMENTO")
    protected int idpagamento;
    @XmlElement(name = "Ente")
    protected String ente;
    @XmlElement(name = "Provenienza", required = true)
    @XmlSchemaType(name = "string")
    protected ProvenienzePagamenti provenienza;
    @XmlElement(name = "DescrizioneProvenienza")
    protected String descrizioneProvenienza;
    @XmlElement(name = "Modalita", required = true)
    @XmlSchemaType(name = "string")
    protected ModalitaPagamento modalita;
    @XmlElement(name = "DescrizioneModalita")
    protected String descrizioneModalita;
    @XmlElement(name = "AnnoImposta")
    protected String annoImposta;
    @XmlElement(name = "RiferimentoPraticaEsterna")
    protected String riferimentoPraticaEsterna;
    @XmlElement(name = "QuintoCampo")
    protected String quintoCampo;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "DataPagamento", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPagamento;
    @XmlElement(name = "DataVersamento", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVersamento;
    @XmlElement(name = "DataRiversamento", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRiversamento;
    @XmlElement(name = "DataAccredito", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAccredito;
    @XmlElement(name = "DataConsolidamento", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataConsolidamento;
    @XmlElement(name = "DataScadenza", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadenza;
    @XmlElement(name = "DataInserimento", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInserimento;
    @XmlElement(name = "Nominativo")
    protected String nominativo;
    @XmlElement(name = "CodiceFiscale")
    protected String codiceFiscale;
    @XmlElement(name = "Cassa")
    protected String cassa;
    @XmlElement(name = "TipoPagamento", required = true)
    @XmlSchemaType(name = "string")
    protected TipiPagamenti tipoPagamento;
    @XmlElement(name = "ID_FLUSSO", required = true, type = Integer.class, nillable = true)
    protected Integer idflusso;
    @XmlElement(name = "Quietanza", required = true, type = Integer.class, nillable = true)
    protected Integer quietanza;
    @XmlElement(name = "Annullato")
    protected boolean annullato;
    @XmlElement(name = "ContoCorrente")
    protected String contoCorrente;
    @XmlElement(name = "IBAN")
    protected String iban;
    @XmlElement(name = "Iuv")
    protected String iuv;
    @XmlElement(name = "CodiceAvviso")
    protected String codiceAvviso;
    @XmlElement(name = "PresenteRT")
    protected boolean presenteRT;
    @XmlElement(name = "PagatoAFronteDi")
    protected String pagatoAFronteDi;
    @XmlElement(name = "RiferimentoDocumentoPagamento")
    protected String riferimentoDocumentoPagamento;
    @XmlElement(name = "IdentificativoPagamentoEntraNext")
    protected String identificativoPagamentoEntraNext;
    @XmlElementRef(name = "NumeroProvvisorioEntrata", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Long> numeroProvvisorioEntrata;
    @XmlElementRef(name = "AnnoProvvisorioEntrata", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Short> annoProvvisorioEntrata;

    /**
     * Recupera il valore della proprietà idpagamento.
     * 
     */
    public int getIDPAGAMENTO() {

	return idpagamento;
    }

    /**
     * Imposta il valore della proprietà idpagamento.
     * 
     */
    public void setIDPAGAMENTO(int value) {

	this.idpagamento = value;
    }

    /**
     * Recupera il valore della proprietà ente.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEnte() {

	return ente;
    }

    /**
     * Imposta il valore della proprietà ente.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEnte(String value) {

	this.ente = value;
    }

    /**
     * Recupera il valore della proprietà provenienza.
     * 
     * @return possible object is {@link ProvenienzePagamenti }
     * 
     */
    public ProvenienzePagamenti getProvenienza() {

	return provenienza;
    }

    /**
     * Imposta il valore della proprietà provenienza.
     * 
     * @param value
     *            allowed object is {@link ProvenienzePagamenti }
     * 
     */
    public void setProvenienza(ProvenienzePagamenti value) {

	this.provenienza = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneProvenienza.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDescrizioneProvenienza() {

	return descrizioneProvenienza;
    }

    /**
     * Imposta il valore della proprietà descrizioneProvenienza.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDescrizioneProvenienza(String value) {

	this.descrizioneProvenienza = value;
    }

    /**
     * Recupera il valore della proprietà modalita.
     * 
     * @return possible object is {@link ModalitaPagamento }
     * 
     */
    public ModalitaPagamento getModalita() {

	return modalita;
    }

    /**
     * Imposta il valore della proprietà modalita.
     * 
     * @param value
     *            allowed object is {@link ModalitaPagamento }
     * 
     */
    public void setModalita(ModalitaPagamento value) {

	this.modalita = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneModalita.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDescrizioneModalita() {

	return descrizioneModalita;
    }

    /**
     * Imposta il valore della proprietà descrizioneModalita.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDescrizioneModalita(String value) {

	this.descrizioneModalita = value;
    }

    /**
     * Recupera il valore della proprietà annoImposta.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAnnoImposta() {

	return annoImposta;
    }

    /**
     * Imposta il valore della proprietà annoImposta.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAnnoImposta(String value) {

	this.annoImposta = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRiferimentoPraticaEsterna() {

	return riferimentoPraticaEsterna;
    }

    /**
     * Imposta il valore della proprietà riferimentoPraticaEsterna.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRiferimentoPraticaEsterna(String value) {

	this.riferimentoPraticaEsterna = value;
    }

    /**
     * Recupera il valore della proprietà quintoCampo.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getQuintoCampo() {

	return quintoCampo;
    }

    /**
     * Imposta il valore della proprietà quintoCampo.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setQuintoCampo(String value) {

	this.quintoCampo = value;
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

    public XMLGregorianCalendar getDataPagamento() {

	return dataPagamento;
    }

    public void setDataPagamento(XMLGregorianCalendar dataPagamento) {

	this.dataPagamento = dataPagamento;
    }

    /**
     * Recupera il valore della proprietà dataVersamento.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataVersamento() {

	return dataVersamento;
    }

    /**
     * Imposta il valore della proprietà dataVersamento.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataVersamento(XMLGregorianCalendar value) {

	this.dataVersamento = value;
    }

    /**
     * Recupera il valore della proprietà dataRiversamento.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataRiversamento() {

	return dataRiversamento;
    }

    /**
     * Imposta il valore della proprietà dataRiversamento.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataRiversamento(XMLGregorianCalendar value) {

	this.dataRiversamento = value;
    }

    /**
     * Recupera il valore della proprietà dataAccredito.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataAccredito() {

	return dataAccredito;
    }

    /**
     * Imposta il valore della proprietà dataAccredito.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataAccredito(XMLGregorianCalendar value) {

	this.dataAccredito = value;
    }

    /**
     * Recupera il valore della proprietà dataConsolidamento.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataConsolidamento() {

	return dataConsolidamento;
    }

    /**
     * Imposta il valore della proprietà dataConsolidamento.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataConsolidamento(XMLGregorianCalendar value) {

	this.dataConsolidamento = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenza.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataScadenza() {

	return dataScadenza;
    }

    /**
     * Imposta il valore della proprietà dataScadenza.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataScadenza(XMLGregorianCalendar value) {

	this.dataScadenza = value;
    }

    /**
     * Recupera il valore della proprietà dataInserimento.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataInserimento() {

	return dataInserimento;
    }

    /**
     * Imposta il valore della proprietà dataInserimento.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataInserimento(XMLGregorianCalendar value) {

	this.dataInserimento = value;
    }

    /**
     * Recupera il valore della proprietà nominativo.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNominativo() {

	return nominativo;
    }

    /**
     * Imposta il valore della proprietà nominativo.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNominativo(String value) {

	this.nominativo = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscale.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCodiceFiscale() {

	return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietà codiceFiscale.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCodiceFiscale(String value) {

	this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietà cassa.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCassa() {

	return cassa;
    }

    /**
     * Imposta il valore della proprietà cassa.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCassa(String value) {

	this.cassa = value;
    }

    /**
     * Recupera il valore della proprietà tipoPagamento.
     * 
     * @return possible object is {@link TipiPagamenti }
     * 
     */
    public TipiPagamenti getTipoPagamento() {

	return tipoPagamento;
    }

    /**
     * Imposta il valore della proprietà tipoPagamento.
     * 
     * @param value
     *            allowed object is {@link TipiPagamenti }
     * 
     */
    public void setTipoPagamento(TipiPagamenti value) {

	this.tipoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà idflusso.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getIDFLUSSO() {

	return idflusso;
    }

    /**
     * Imposta il valore della proprietà idflusso.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setIDFLUSSO(Integer value) {

	this.idflusso = value;
    }

    /**
     * Recupera il valore della proprietà quietanza.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getQuietanza() {

	return quietanza;
    }

    /**
     * Imposta il valore della proprietà quietanza.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setQuietanza(Integer value) {

	this.quietanza = value;
    }

    /**
     * Recupera il valore della proprietà annullato.
     * 
     */
    public boolean isAnnullato() {

	return annullato;
    }

    /**
     * Imposta il valore della proprietà annullato.
     * 
     */
    public void setAnnullato(boolean value) {

	this.annullato = value;
    }

    /**
     * Recupera il valore della proprietà contoCorrente.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getContoCorrente() {

	return contoCorrente;
    }

    /**
     * Imposta il valore della proprietà contoCorrente.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setContoCorrente(String value) {

	this.contoCorrente = value;
    }

    /**
     * Recupera il valore della proprietà iban.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIBAN() {

	return iban;
    }

    /**
     * Imposta il valore della proprietà iban.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIBAN(String value) {

	this.iban = value;
    }

    /**
     * Recupera il valore della proprietà iuv.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIuv() {

	return iuv;
    }

    /**
     * Imposta il valore della proprietà iuv.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIuv(String value) {

	this.iuv = value;
    }

    
    public String getCodiceAvviso() {
    
        return codiceAvviso;
    }

    
    public void setCodiceAvviso(String codiceAvviso) {
    
        this.codiceAvviso = codiceAvviso;
    }

    /**
     * Recupera il valore della proprietà presenteRT.
     * 
     */
    public boolean isPresenteRT() {

	return presenteRT;
    }

    /**
     * Imposta il valore della proprietà presenteRT.
     * 
     */
    public void setPresenteRT(boolean value) {

	this.presenteRT = value;
    }

    /**
     * Recupera il valore della proprietà pagatoAFronteDi.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPagatoAFronteDi() {

	return pagatoAFronteDi;
    }

    /**
     * Imposta il valore della proprietà pagatoAFronteDi.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setPagatoAFronteDi(String value) {

	this.pagatoAFronteDi = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoDocumentoPagamento.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRiferimentoDocumentoPagamento() {

	return riferimentoDocumentoPagamento;
    }

    /**
     * Imposta il valore della proprietà riferimentoDocumentoPagamento.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRiferimentoDocumentoPagamento(String value) {

	this.riferimentoDocumentoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà identificativoPagamentoEntraNext.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIdentificativoPagamentoEntraNext() {

	return identificativoPagamentoEntraNext;
    }

    /**
     * Imposta il valore della proprietà identificativoPagamentoEntraNext.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIdentificativoPagamentoEntraNext(String value) {

	this.identificativoPagamentoEntraNext = value;
    }

    /**
     * Recupera il valore della proprietà numeroProvvisorioEntrata.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     */
    public JAXBElement<Long> getNumeroProvvisorioEntrata() {

	return numeroProvvisorioEntrata;
    }

    /**
     * Imposta il valore della proprietà numeroProvvisorioEntrata.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     */
    public void setNumeroProvvisorioEntrata(JAXBElement<Long> value) {

	this.numeroProvvisorioEntrata = value;
    }

    /**
     * Recupera il valore della proprietà annoProvvisorioEntrata.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     */
    public JAXBElement<Short> getAnnoProvvisorioEntrata() {

	return annoProvvisorioEntrata;
    }

    /**
     * Imposta il valore della proprietà annoProvvisorioEntrata.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     */
    public void setAnnoProvvisorioEntrata(JAXBElement<Short> value) {

	this.annoProvvisorioEntrata = value;
    }
}
