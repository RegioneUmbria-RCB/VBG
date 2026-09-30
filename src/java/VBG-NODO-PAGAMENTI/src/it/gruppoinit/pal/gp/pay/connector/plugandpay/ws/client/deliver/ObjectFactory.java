package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.common.Servizio;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content.
 * The Java representation of XML content can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory methods for each of these are provided in
 * this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva");
    private final static QName _DeliverAuthenticatedRequestBase_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "DeliverAuthenticatedRequestBase");
    private final static QName _RichiestaRicercaPosizionePerIdentificavo_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "RichiestaRicercaPosizionePerIdentificavo");
    private final static QName _RichiestaRicercaPagamentiGiornalieri_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "RichiestaRicercaPagamentiGiornalieri");
    private final static QName _StatoPosizioneFilter_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "StatoPosizioneFilter");
    private final static QName _RispostaRicercaPosizioniPerCodiceFiscalePartitaIva_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "RispostaRicercaPosizioniPerCodiceFiscalePartitaIva");
    private final static QName _ArrayOfPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "ArrayOfPosizione");
    private final static QName _Posizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Posizione");
    private final static QName _Creditore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Creditore");
    private final static QName _Debitore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Debitore");
    private final static QName _ArrayOfParametroPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "ArrayOfParametroPosizione");
    private final static QName _ParametroPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "ParametroPosizione");
    private final static QName _StatoPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "StatoPosizione");
    private final static QName _RispostaRicercaPosizionePerIdentificavo_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "RispostaRicercaPosizionePerIdentificavo");
    private final static QName _RispostaRicercaArricchitaPosizionePerIdentificavo_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "RispostaRicercaArricchitaPosizionePerIdentificavo");
    private final static QName _PosizioneArricchita_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "PosizioneArricchita");
    private final static QName _RispostaRicercaPagamentiGiornalieri_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "RispostaRicercaPagamentiGiornalieri");
    private final static QName _ArrayOfPagamentoGiornaliero_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "ArrayOfPagamentoGiornaliero");
    private final static QName _PagamentoGiornaliero_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "PagamentoGiornaliero");
    private final static QName _EsitoPagamento_QNAME = new QName(
	    "http://schemas.datacontract.org/2004/07/PlugAndPay.DigitBusNodoPA.Erogazione.QueryStack.Model", "EsitoPagamento");
    private final static QName _RicercaPosizioniPerCodiceFiscalePartitaIvaRequest_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "request");
    private final static QName _RicercaPosizioniPerCodiceFiscalePartitaIvaResponseRicercaPosizioniPerCodiceFiscalePartitaIvaResult_QNAME = new QName(
	    "http://e-fil.eu/PnP/PlugAndPayDeliver", "RicercaPosizioniPerCodiceFiscalePartitaIvaResult");
    private final static QName _RicercaPosizionePerIdentificavoResponseRicercaPosizionePerIdentificavoResult_QNAME = new QName(
	    "http://e-fil.eu/PnP/PlugAndPayDeliver", "RicercaPosizionePerIdentificavoResult");
    private final static QName _RicercaArricchitaPosizionePerIdentificavoResponseRicercaArricchitaPosizionePerIdentificavoResult_QNAME = new QName(
	    "http://e-fil.eu/PnP/PlugAndPayDeliver", "RicercaArricchitaPosizionePerIdentificavoResult");
    private final static QName _RicercaPagamentiGiornalieriResponseRicercaPagamentiGiornalieriResult_QNAME = new QName(
	    "http://e-fil.eu/PnP/PlugAndPayDeliver", "RicercaPagamentiGiornalieriResult");
    private final static QName _PagamentoGiornalieroCanaleDiPagamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "CanaleDiPagamento");
    private final static QName _PagamentoGiornalieroCausale_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Causale");
    private final static QName _PagamentoGiornalieroCodiceFiscalePartitaIva_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "CodiceFiscalePartitaIva");
    private final static QName _PagamentoGiornalieroCodiceRiferimentoCreditore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "CodiceRiferimentoCreditore");
    private final static QName _PagamentoGiornalieroDataDiPagamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "DataDiPagamento");
    private final static QName _PagamentoGiornalieroDataRegistrazionePagamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "DataRegistrazionePagamento");
    private final static QName _PagamentoGiornalieroDataRegolamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "DataRegolamento");
    private final static QName _PagamentoGiornalieroEsito_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Esito");
    private final static QName _PagamentoGiornalieroIdCanaleDiPagamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "IdCanaleDiPagamento");
    private final static QName _PagamentoGiornalieroIdFlussoRiversamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "IdFlussoRiversamento");
    private final static QName _PagamentoGiornalieroIdRiscossione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "IdRiscossione");
    private final static QName _PagamentoGiornalieroIdentificativoPagamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "IdentificativoPagamento");
    private final static QName _PagamentoGiornalieroModalitaDiPagamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "ModalitaDiPagamento");
    private final static QName _PagamentoGiornalieroNominativoDebitore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "NominativoDebitore");
    private final static QName _PagamentoGiornalieroOraRegistrazionePagamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "OraRegistrazionePagamento");
    private final static QName _PagamentoGiornalieroTipoRiferimentoCreditore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "TipoRiferimentoCreditore");
    private final static QName _RispostaRicercaPagamentiGiornalieriPagamenti_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Pagamenti");
    private final static QName _PosizioneArricchitaDataPagamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "DataPagamento");
    private final static QName _PosizioneArricchitaDataScadenza_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "DataScadenza");
    private final static QName _PosizioneArricchitaIdentificativoPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "IdentificativoPosizione");
    private final static QName _PosizioneArricchitaNumeroAvviso_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "NumeroAvviso");
    private final static QName _PosizioneArricchitaParametriPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "ParametriPosizione");
    private final static QName _PosizioneArricchitaServizio_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Servizio");
    private final static QName _ParametroPosizioneChiave_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Chiave");
    private final static QName _ParametroPosizioneValore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Valore");
    private final static QName _DebitoreCivico_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Civico");
    private final static QName _DebitoreCodiceAvviamentoPostale_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "CodiceAvviamentoPostale");
    private final static QName _DebitoreEmail_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Email");
    private final static QName _DebitoreIndirizzo_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Indirizzo");
    private final static QName _DebitoreLocalita_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Localita");
    private final static QName _DebitoreNominativo_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Nominativo");
    private final static QName _DebitoreProvincia_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Provincia");
    private final static QName _CreditoreCodiceEnte_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "CodiceEnte");
    private final static QName _CreditoreIBAN_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "IBAN");
    private final static QName _CreditoreIntestazione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "Intestazione");
    private final static QName _RispostaRicercaPosizioniPerCodiceFiscalePartitaIvaPosizioni_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver",
	    "Posizioni");
    private final static QName _RichiestaRicercaPagamentiGiornalieriOraFine_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "OraFine");
    private final static QName _RichiestaRicercaPagamentiGiornalieriOraInizio_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "OraInizio");
    private final static QName _PosizioneIdentificativoFlusso_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayDeliver", "IdentificativoFlusso");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver
     * 
     */
    public ObjectFactory() {

    }

    /**
     * Create an instance of {@link RicercaPosizioniPerCodiceFiscalePartitaIva }
     * 
     */
    public RicercaPosizioniPerCodiceFiscalePartitaIva createRicercaPosizioniPerCodiceFiscalePartitaIva() {

	return new RicercaPosizioniPerCodiceFiscalePartitaIva();
    }

    /**
     * Create an instance of {@link RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva }
     * 
     */
    public RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva createRichiestaRicercaPosizioniPerCodiceFiscalePartitaIva() {

	return new RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva();
    }

    /**
     * Create an instance of {@link DeliverAuthenticatedRequestBase }
     * 
     */
    public DeliverAuthenticatedRequestBase createDeliverAuthenticatedRequestBase() {

	return new DeliverAuthenticatedRequestBase();
    }

    /**
     * Create an instance of {@link RichiestaRicercaPosizionePerIdentificavo }
     * 
     */
    public RichiestaRicercaPosizionePerIdentificavo createRichiestaRicercaPosizionePerIdentificavo() {

	return new RichiestaRicercaPosizionePerIdentificavo();
    }

    /**
     * Create an instance of {@link RichiestaRicercaPagamentiGiornalieri }
     * 
     */
    public RichiestaRicercaPagamentiGiornalieri createRichiestaRicercaPagamentiGiornalieri() {

	return new RichiestaRicercaPagamentiGiornalieri();
    }

    /**
     * Create an instance of {@link RicercaPosizioniPerCodiceFiscalePartitaIvaResponse }
     * 
     */
    public RicercaPosizioniPerCodiceFiscalePartitaIvaResponse createRicercaPosizioniPerCodiceFiscalePartitaIvaResponse() {

	return new RicercaPosizioniPerCodiceFiscalePartitaIvaResponse();
    }

    /**
     * Create an instance of {@link RispostaRicercaPosizioniPerCodiceFiscalePartitaIva }
     * 
     */
    public RispostaRicercaPosizioniPerCodiceFiscalePartitaIva createRispostaRicercaPosizioniPerCodiceFiscalePartitaIva() {

	return new RispostaRicercaPosizioniPerCodiceFiscalePartitaIva();
    }

    /**
     * Create an instance of {@link ArrayOfPosizione }
     * 
     */
    public ArrayOfPosizione createArrayOfPosizione() {

	return new ArrayOfPosizione();
    }

    /**
     * Create an instance of {@link Posizione }
     * 
     */
    public Posizione createPosizione() {

	return new Posizione();
    }

    /**
     * Create an instance of {@link Creditore }
     * 
     */
    public Creditore createCreditore() {

	return new Creditore();
    }

    /**
     * Create an instance of {@link Debitore }
     * 
     */
    public Debitore createDebitore() {

	return new Debitore();
    }

    /**
     * Create an instance of {@link ArrayOfParametroPosizione }
     * 
     */
    public ArrayOfParametroPosizione createArrayOfParametroPosizione() {

	return new ArrayOfParametroPosizione();
    }

    /**
     * Create an instance of {@link ParametroPosizione }
     * 
     */
    public ParametroPosizione createParametroPosizione() {

	return new ParametroPosizione();
    }

    /**
     * Create an instance of {@link RicercaPosizionePerIdentificavo }
     * 
     */
    public RicercaPosizionePerIdentificavo createRicercaPosizionePerIdentificavo() {

	return new RicercaPosizionePerIdentificavo();
    }

    /**
     * Create an instance of {@link RicercaPosizionePerIdentificavoResponse }
     * 
     */
    public RicercaPosizionePerIdentificavoResponse createRicercaPosizionePerIdentificavoResponse() {

	return new RicercaPosizionePerIdentificavoResponse();
    }

    /**
     * Create an instance of {@link RispostaRicercaPosizionePerIdentificavo }
     * 
     */
    public RispostaRicercaPosizionePerIdentificavo createRispostaRicercaPosizionePerIdentificavo() {

	return new RispostaRicercaPosizionePerIdentificavo();
    }

    /**
     * Create an instance of {@link RicercaArricchitaPosizionePerIdentificavo }
     * 
     */
    public RicercaArricchitaPosizionePerIdentificavo createRicercaArricchitaPosizionePerIdentificavo() {

	return new RicercaArricchitaPosizionePerIdentificavo();
    }

    /**
     * Create an instance of {@link RicercaArricchitaPosizionePerIdentificavoResponse }
     * 
     */
    public RicercaArricchitaPosizionePerIdentificavoResponse createRicercaArricchitaPosizionePerIdentificavoResponse() {

	return new RicercaArricchitaPosizionePerIdentificavoResponse();
    }

    /**
     * Create an instance of {@link RispostaRicercaArricchitaPosizionePerIdentificavo }
     * 
     */
    public RispostaRicercaArricchitaPosizionePerIdentificavo createRispostaRicercaArricchitaPosizionePerIdentificavo() {

	return new RispostaRicercaArricchitaPosizionePerIdentificavo();
    }

    /**
     * Create an instance of {@link PosizioneArricchita }
     * 
     */
    public PosizioneArricchita createPosizioneArricchita() {

	return new PosizioneArricchita();
    }

    /**
     * Create an instance of {@link RicercaPagamentiGiornalieri }
     * 
     */
    public RicercaPagamentiGiornalieri createRicercaPagamentiGiornalieri() {

	return new RicercaPagamentiGiornalieri();
    }

    /**
     * Create an instance of {@link RicercaPagamentiGiornalieriResponse }
     * 
     */
    public RicercaPagamentiGiornalieriResponse createRicercaPagamentiGiornalieriResponse() {

	return new RicercaPagamentiGiornalieriResponse();
    }

    /**
     * Create an instance of {@link RispostaRicercaPagamentiGiornalieri }
     * 
     */
    public RispostaRicercaPagamentiGiornalieri createRispostaRicercaPagamentiGiornalieri() {

	return new RispostaRicercaPagamentiGiornalieri();
    }

    /**
     * Create an instance of {@link ArrayOfPagamentoGiornaliero }
     * 
     */
    public ArrayOfPagamentoGiornaliero createArrayOfPagamentoGiornaliero() {

	return new ArrayOfPagamentoGiornaliero();
    }

    /**
     * Create an instance of {@link PagamentoGiornaliero }
     * 
     */
    public PagamentoGiornaliero createPagamentoGiornaliero() {

	return new PagamentoGiornaliero();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva")
    public JAXBElement<RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva> createRichiestaRicercaPosizioniPerCodiceFiscalePartitaIva(
	    RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva value) {

	return new JAXBElement<RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva>(_RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva_QNAME,
		RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeliverAuthenticatedRequestBase }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "DeliverAuthenticatedRequestBase")
    public JAXBElement<DeliverAuthenticatedRequestBase> createDeliverAuthenticatedRequestBase(DeliverAuthenticatedRequestBase value) {

	return new JAXBElement<DeliverAuthenticatedRequestBase>(_DeliverAuthenticatedRequestBase_QNAME, DeliverAuthenticatedRequestBase.class, null,
		value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaRicercaPosizionePerIdentificavo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "RichiestaRicercaPosizionePerIdentificavo")
    public JAXBElement<RichiestaRicercaPosizionePerIdentificavo> createRichiestaRicercaPosizionePerIdentificavo(
	    RichiestaRicercaPosizionePerIdentificavo value) {

	return new JAXBElement<RichiestaRicercaPosizionePerIdentificavo>(_RichiestaRicercaPosizionePerIdentificavo_QNAME,
		RichiestaRicercaPosizionePerIdentificavo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaRicercaPagamentiGiornalieri }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "RichiestaRicercaPagamentiGiornalieri")
    public JAXBElement<RichiestaRicercaPagamentiGiornalieri> createRichiestaRicercaPagamentiGiornalieri(RichiestaRicercaPagamentiGiornalieri value) {

	return new JAXBElement<RichiestaRicercaPagamentiGiornalieri>(_RichiestaRicercaPagamentiGiornalieri_QNAME,
		RichiestaRicercaPagamentiGiornalieri.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatoPosizioneFilter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "StatoPosizioneFilter")
    public JAXBElement<StatoPosizioneFilter> createStatoPosizioneFilter(StatoPosizioneFilter value) {

	return new JAXBElement<StatoPosizioneFilter>(_StatoPosizioneFilter_QNAME, StatoPosizioneFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRicercaPosizioniPerCodiceFiscalePartitaIva
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "RispostaRicercaPosizioniPerCodiceFiscalePartitaIva")
    public JAXBElement<RispostaRicercaPosizioniPerCodiceFiscalePartitaIva> createRispostaRicercaPosizioniPerCodiceFiscalePartitaIva(
	    RispostaRicercaPosizioniPerCodiceFiscalePartitaIva value) {

	return new JAXBElement<RispostaRicercaPosizioniPerCodiceFiscalePartitaIva>(_RispostaRicercaPosizioniPerCodiceFiscalePartitaIva_QNAME,
		RispostaRicercaPosizioniPerCodiceFiscalePartitaIva.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "ArrayOfPosizione")
    public JAXBElement<ArrayOfPosizione> createArrayOfPosizione(ArrayOfPosizione value) {

	return new JAXBElement<ArrayOfPosizione>(_ArrayOfPosizione_QNAME, ArrayOfPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Posizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Posizione")
    public JAXBElement<Posizione> createPosizione(Posizione value) {

	return new JAXBElement<Posizione>(_Posizione_QNAME, Posizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Creditore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Creditore")
    public JAXBElement<Creditore> createCreditore(Creditore value) {

	return new JAXBElement<Creditore>(_Creditore_QNAME, Creditore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Debitore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Debitore")
    public JAXBElement<Debitore> createDebitore(Debitore value) {

	return new JAXBElement<Debitore>(_Debitore_QNAME, Debitore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfParametroPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "ArrayOfParametroPosizione")
    public JAXBElement<ArrayOfParametroPosizione> createArrayOfParametroPosizione(ArrayOfParametroPosizione value) {

	return new JAXBElement<ArrayOfParametroPosizione>(_ArrayOfParametroPosizione_QNAME, ArrayOfParametroPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParametroPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "ParametroPosizione")
    public JAXBElement<ParametroPosizione> createParametroPosizione(ParametroPosizione value) {

	return new JAXBElement<ParametroPosizione>(_ParametroPosizione_QNAME, ParametroPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatoPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "StatoPosizione")
    public JAXBElement<StatoPosizione> createStatoPosizione(StatoPosizione value) {

	return new JAXBElement<StatoPosizione>(_StatoPosizione_QNAME, StatoPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRicercaPosizionePerIdentificavo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "RispostaRicercaPosizionePerIdentificavo")
    public JAXBElement<RispostaRicercaPosizionePerIdentificavo> createRispostaRicercaPosizionePerIdentificavo(
	    RispostaRicercaPosizionePerIdentificavo value) {

	return new JAXBElement<RispostaRicercaPosizionePerIdentificavo>(_RispostaRicercaPosizionePerIdentificavo_QNAME,
		RispostaRicercaPosizionePerIdentificavo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRicercaArricchitaPosizionePerIdentificavo
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "RispostaRicercaArricchitaPosizionePerIdentificavo")
    public JAXBElement<RispostaRicercaArricchitaPosizionePerIdentificavo> createRispostaRicercaArricchitaPosizionePerIdentificavo(
	    RispostaRicercaArricchitaPosizionePerIdentificavo value) {

	return new JAXBElement<RispostaRicercaArricchitaPosizionePerIdentificavo>(_RispostaRicercaArricchitaPosizionePerIdentificavo_QNAME,
		RispostaRicercaArricchitaPosizionePerIdentificavo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PosizioneArricchita }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "PosizioneArricchita")
    public JAXBElement<PosizioneArricchita> createPosizioneArricchita(PosizioneArricchita value) {

	return new JAXBElement<PosizioneArricchita>(_PosizioneArricchita_QNAME, PosizioneArricchita.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRicercaPagamentiGiornalieri }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "RispostaRicercaPagamentiGiornalieri")
    public JAXBElement<RispostaRicercaPagamentiGiornalieri> createRispostaRicercaPagamentiGiornalieri(RispostaRicercaPagamentiGiornalieri value) {

	return new JAXBElement<RispostaRicercaPagamentiGiornalieri>(_RispostaRicercaPagamentiGiornalieri_QNAME,
		RispostaRicercaPagamentiGiornalieri.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPagamentoGiornaliero }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "ArrayOfPagamentoGiornaliero")
    public JAXBElement<ArrayOfPagamentoGiornaliero> createArrayOfPagamentoGiornaliero(ArrayOfPagamentoGiornaliero value) {

	return new JAXBElement<ArrayOfPagamentoGiornaliero>(_ArrayOfPagamentoGiornaliero_QNAME, ArrayOfPagamentoGiornaliero.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PagamentoGiornaliero }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "PagamentoGiornaliero")
    public JAXBElement<PagamentoGiornaliero> createPagamentoGiornaliero(PagamentoGiornaliero value) {

	return new JAXBElement<PagamentoGiornaliero>(_PagamentoGiornaliero_QNAME, PagamentoGiornaliero.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsitoPagamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.DigitBusNodoPA.Erogazione.QueryStack.Model", name = "EsitoPagamento")
    public JAXBElement<EsitoPagamento> createEsitoPagamento(EsitoPagamento value) {

	return new JAXBElement<EsitoPagamento>(_EsitoPagamento_QNAME, EsitoPagamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "request", scope = RicercaPosizioniPerCodiceFiscalePartitaIva.class)
    public JAXBElement<RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva> createRicercaPosizioniPerCodiceFiscalePartitaIvaRequest(
	    RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva value) {

	return new JAXBElement<RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva>(_RicercaPosizioniPerCodiceFiscalePartitaIvaRequest_QNAME,
		RichiestaRicercaPosizioniPerCodiceFiscalePartitaIva.class, RicercaPosizioniPerCodiceFiscalePartitaIva.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRicercaPosizioniPerCodiceFiscalePartitaIva
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "RicercaPosizioniPerCodiceFiscalePartitaIvaResult", scope = RicercaPosizioniPerCodiceFiscalePartitaIvaResponse.class)
    public JAXBElement<RispostaRicercaPosizioniPerCodiceFiscalePartitaIva> createRicercaPosizioniPerCodiceFiscalePartitaIvaResponseRicercaPosizioniPerCodiceFiscalePartitaIvaResult(
	    RispostaRicercaPosizioniPerCodiceFiscalePartitaIva value) {

	return new JAXBElement<RispostaRicercaPosizioniPerCodiceFiscalePartitaIva>(
		_RicercaPosizioniPerCodiceFiscalePartitaIvaResponseRicercaPosizioniPerCodiceFiscalePartitaIvaResult_QNAME,
		RispostaRicercaPosizioniPerCodiceFiscalePartitaIva.class, RicercaPosizioniPerCodiceFiscalePartitaIvaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaRicercaPosizionePerIdentificavo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "request", scope = RicercaPosizionePerIdentificavo.class)
    public JAXBElement<RichiestaRicercaPosizionePerIdentificavo> createRicercaPosizionePerIdentificavoRequest(
	    RichiestaRicercaPosizionePerIdentificavo value) {

	return new JAXBElement<RichiestaRicercaPosizionePerIdentificavo>(_RicercaPosizioniPerCodiceFiscalePartitaIvaRequest_QNAME,
		RichiestaRicercaPosizionePerIdentificavo.class, RicercaPosizionePerIdentificavo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRicercaPosizionePerIdentificavo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "RicercaPosizionePerIdentificavoResult", scope = RicercaPosizionePerIdentificavoResponse.class)
    public JAXBElement<RispostaRicercaPosizionePerIdentificavo> createRicercaPosizionePerIdentificavoResponseRicercaPosizionePerIdentificavoResult(
	    RispostaRicercaPosizionePerIdentificavo value) {

	return new JAXBElement<RispostaRicercaPosizionePerIdentificavo>(
		_RicercaPosizionePerIdentificavoResponseRicercaPosizionePerIdentificavoResult_QNAME, RispostaRicercaPosizionePerIdentificavo.class,
		RicercaPosizionePerIdentificavoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaRicercaPosizionePerIdentificavo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "request", scope = RicercaArricchitaPosizionePerIdentificavo.class)
    public JAXBElement<RichiestaRicercaPosizionePerIdentificavo> createRicercaArricchitaPosizionePerIdentificavoRequest(
	    RichiestaRicercaPosizionePerIdentificavo value) {

	return new JAXBElement<RichiestaRicercaPosizionePerIdentificavo>(_RicercaPosizioniPerCodiceFiscalePartitaIvaRequest_QNAME,
		RichiestaRicercaPosizionePerIdentificavo.class, RicercaArricchitaPosizionePerIdentificavo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRicercaArricchitaPosizionePerIdentificavo
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "RicercaArricchitaPosizionePerIdentificavoResult", scope = RicercaArricchitaPosizionePerIdentificavoResponse.class)
    public JAXBElement<RispostaRicercaArricchitaPosizionePerIdentificavo> createRicercaArricchitaPosizionePerIdentificavoResponseRicercaArricchitaPosizionePerIdentificavoResult(
	    RispostaRicercaArricchitaPosizionePerIdentificavo value) {

	return new JAXBElement<RispostaRicercaArricchitaPosizionePerIdentificavo>(
		_RicercaArricchitaPosizionePerIdentificavoResponseRicercaArricchitaPosizionePerIdentificavoResult_QNAME,
		RispostaRicercaArricchitaPosizionePerIdentificavo.class, RicercaArricchitaPosizionePerIdentificavoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaRicercaPagamentiGiornalieri }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "request", scope = RicercaPagamentiGiornalieri.class)
    public JAXBElement<RichiestaRicercaPagamentiGiornalieri> createRicercaPagamentiGiornalieriRequest(RichiestaRicercaPagamentiGiornalieri value) {

	return new JAXBElement<RichiestaRicercaPagamentiGiornalieri>(_RicercaPosizioniPerCodiceFiscalePartitaIvaRequest_QNAME,
		RichiestaRicercaPagamentiGiornalieri.class, RicercaPagamentiGiornalieri.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRicercaPagamentiGiornalieri }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "RicercaPagamentiGiornalieriResult", scope = RicercaPagamentiGiornalieriResponse.class)
    public JAXBElement<RispostaRicercaPagamentiGiornalieri> createRicercaPagamentiGiornalieriResponseRicercaPagamentiGiornalieriResult(
	    RispostaRicercaPagamentiGiornalieri value) {

	return new JAXBElement<RispostaRicercaPagamentiGiornalieri>(_RicercaPagamentiGiornalieriResponseRicercaPagamentiGiornalieriResult_QNAME,
		RispostaRicercaPagamentiGiornalieri.class, RicercaPagamentiGiornalieriResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "CanaleDiPagamento", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroCanaleDiPagamento(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroCanaleDiPagamento_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Causale", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroCausale(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroCausale_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "CodiceFiscalePartitaIva", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroCodiceFiscalePartitaIva(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroCodiceFiscalePartitaIva_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "CodiceRiferimentoCreditore", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroCodiceRiferimentoCreditore(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroCodiceRiferimentoCreditore_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "DataDiPagamento", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroDataDiPagamento(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroDataDiPagamento_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "DataRegistrazionePagamento", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroDataRegistrazionePagamento(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroDataRegistrazionePagamento_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "DataRegolamento", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroDataRegolamento(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroDataRegolamento_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsitoPagamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Esito", scope = PagamentoGiornaliero.class)
    public JAXBElement<EsitoPagamento> createPagamentoGiornalieroEsito(EsitoPagamento value) {

	return new JAXBElement<EsitoPagamento>(_PagamentoGiornalieroEsito_QNAME, EsitoPagamento.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "IdCanaleDiPagamento", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroIdCanaleDiPagamento(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroIdCanaleDiPagamento_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "IdFlussoRiversamento", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroIdFlussoRiversamento(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroIdFlussoRiversamento_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "IdRiscossione", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroIdRiscossione(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroIdRiscossione_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "IdentificativoPagamento", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroIdentificativoPagamento(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroIdentificativoPagamento_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "ModalitaDiPagamento", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroModalitaDiPagamento(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroModalitaDiPagamento_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "NominativoDebitore", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroNominativoDebitore(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroNominativoDebitore_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "OraRegistrazionePagamento", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroOraRegistrazionePagamento(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroOraRegistrazionePagamento_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "TipoRiferimentoCreditore", scope = PagamentoGiornaliero.class)
    public JAXBElement<String> createPagamentoGiornalieroTipoRiferimentoCreditore(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroTipoRiferimentoCreditore_QNAME, String.class, PagamentoGiornaliero.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPagamentoGiornaliero }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Pagamenti", scope = RispostaRicercaPagamentiGiornalieri.class)
    public JAXBElement<ArrayOfPagamentoGiornaliero> createRispostaRicercaPagamentiGiornalieriPagamenti(ArrayOfPagamentoGiornaliero value) {

	return new JAXBElement<ArrayOfPagamentoGiornaliero>(_RispostaRicercaPagamentiGiornalieriPagamenti_QNAME, ArrayOfPagamentoGiornaliero.class,
		RispostaRicercaPagamentiGiornalieri.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Causale", scope = PosizioneArricchita.class)
    public JAXBElement<String> createPosizioneArricchitaCausale(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroCausale_QNAME, String.class, PosizioneArricchita.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "CodiceRiferimentoCreditore", scope = PosizioneArricchita.class)
    public JAXBElement<String> createPosizioneArricchitaCodiceRiferimentoCreditore(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroCodiceRiferimentoCreditore_QNAME, String.class, PosizioneArricchita.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Creditore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Creditore", scope = PosizioneArricchita.class)
    public JAXBElement<Creditore> createPosizioneArricchitaCreditore(Creditore value) {

	return new JAXBElement<Creditore>(_Creditore_QNAME, Creditore.class, PosizioneArricchita.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "DataPagamento", scope = PosizioneArricchita.class)
    public JAXBElement<XMLGregorianCalendar> createPosizioneArricchitaDataPagamento(XMLGregorianCalendar value) {

	return new JAXBElement<XMLGregorianCalendar>(_PosizioneArricchitaDataPagamento_QNAME, XMLGregorianCalendar.class, PosizioneArricchita.class,
		value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "DataScadenza", scope = PosizioneArricchita.class)
    public JAXBElement<XMLGregorianCalendar> createPosizioneArricchitaDataScadenza(XMLGregorianCalendar value) {

	return new JAXBElement<XMLGregorianCalendar>(_PosizioneArricchitaDataScadenza_QNAME, XMLGregorianCalendar.class, PosizioneArricchita.class,
		value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Debitore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Debitore", scope = PosizioneArricchita.class)
    public JAXBElement<Debitore> createPosizioneArricchitaDebitore(Debitore value) {

	return new JAXBElement<Debitore>(_Debitore_QNAME, Debitore.class, PosizioneArricchita.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "IdentificativoPosizione", scope = PosizioneArricchita.class)
    public JAXBElement<String> createPosizioneArricchitaIdentificativoPosizione(String value) {

	return new JAXBElement<String>(_PosizioneArricchitaIdentificativoPosizione_QNAME, String.class, PosizioneArricchita.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "NumeroAvviso", scope = PosizioneArricchita.class)
    public JAXBElement<String> createPosizioneArricchitaNumeroAvviso(String value) {

	return new JAXBElement<String>(_PosizioneArricchitaNumeroAvviso_QNAME, String.class, PosizioneArricchita.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfParametroPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "ParametriPosizione", scope = PosizioneArricchita.class)
    public JAXBElement<ArrayOfParametroPosizione> createPosizioneArricchitaParametriPosizione(ArrayOfParametroPosizione value) {

	return new JAXBElement<ArrayOfParametroPosizione>(_PosizioneArricchitaParametriPosizione_QNAME, ArrayOfParametroPosizione.class,
		PosizioneArricchita.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Servizio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Servizio", scope = PosizioneArricchita.class)
    public JAXBElement<Servizio> createPosizioneArricchitaServizio(Servizio value) {

	return new JAXBElement<Servizio>(_PosizioneArricchitaServizio_QNAME, Servizio.class, PosizioneArricchita.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "TipoRiferimentoCreditore", scope = PosizioneArricchita.class)
    public JAXBElement<String> createPosizioneArricchitaTipoRiferimentoCreditore(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroTipoRiferimentoCreditore_QNAME, String.class, PosizioneArricchita.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PosizioneArricchita }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Posizione", scope = RispostaRicercaArricchitaPosizionePerIdentificavo.class)
    public JAXBElement<PosizioneArricchita> createRispostaRicercaArricchitaPosizionePerIdentificavoPosizione(PosizioneArricchita value) {

	return new JAXBElement<PosizioneArricchita>(_Posizione_QNAME, PosizioneArricchita.class,
		RispostaRicercaArricchitaPosizionePerIdentificavo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Posizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Posizione", scope = RispostaRicercaPosizionePerIdentificavo.class)
    public JAXBElement<Posizione> createRispostaRicercaPosizionePerIdentificavoPosizione(Posizione value) {

	return new JAXBElement<Posizione>(_Posizione_QNAME, Posizione.class, RispostaRicercaPosizionePerIdentificavo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Chiave", scope = ParametroPosizione.class)
    public JAXBElement<String> createParametroPosizioneChiave(String value) {

	return new JAXBElement<String>(_ParametroPosizioneChiave_QNAME, String.class, ParametroPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Valore", scope = ParametroPosizione.class)
    public JAXBElement<String> createParametroPosizioneValore(String value) {

	return new JAXBElement<String>(_ParametroPosizioneValore_QNAME, String.class, ParametroPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Civico", scope = Debitore.class)
    public JAXBElement<String> createDebitoreCivico(String value) {

	return new JAXBElement<String>(_DebitoreCivico_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "CodiceAvviamentoPostale", scope = Debitore.class)
    public JAXBElement<String> createDebitoreCodiceAvviamentoPostale(String value) {

	return new JAXBElement<String>(_DebitoreCodiceAvviamentoPostale_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "CodiceFiscalePartitaIva", scope = Debitore.class)
    public JAXBElement<String> createDebitoreCodiceFiscalePartitaIva(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroCodiceFiscalePartitaIva_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Email", scope = Debitore.class)
    public JAXBElement<String> createDebitoreEmail(String value) {

	return new JAXBElement<String>(_DebitoreEmail_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Indirizzo", scope = Debitore.class)
    public JAXBElement<String> createDebitoreIndirizzo(String value) {

	return new JAXBElement<String>(_DebitoreIndirizzo_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Localita", scope = Debitore.class)
    public JAXBElement<String> createDebitoreLocalita(String value) {

	return new JAXBElement<String>(_DebitoreLocalita_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Nominativo", scope = Debitore.class)
    public JAXBElement<String> createDebitoreNominativo(String value) {

	return new JAXBElement<String>(_DebitoreNominativo_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Provincia", scope = Debitore.class)
    public JAXBElement<String> createDebitoreProvincia(String value) {

	return new JAXBElement<String>(_DebitoreProvincia_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "CodiceEnte", scope = Creditore.class)
    public JAXBElement<String> createCreditoreCodiceEnte(String value) {

	return new JAXBElement<String>(_CreditoreCodiceEnte_QNAME, String.class, Creditore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "CodiceFiscalePartitaIva", scope = Creditore.class)
    public JAXBElement<String> createCreditoreCodiceFiscalePartitaIva(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroCodiceFiscalePartitaIva_QNAME, String.class, Creditore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "IBAN", scope = Creditore.class)
    public JAXBElement<String> createCreditoreIBAN(String value) {

	return new JAXBElement<String>(_CreditoreIBAN_QNAME, String.class, Creditore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Intestazione", scope = Creditore.class)
    public JAXBElement<String> createCreditoreIntestazione(String value) {

	return new JAXBElement<String>(_CreditoreIntestazione_QNAME, String.class, Creditore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Causale", scope = Posizione.class)
    public JAXBElement<String> createPosizioneCausale(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroCausale_QNAME, String.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "CodiceRiferimentoCreditore", scope = Posizione.class)
    public JAXBElement<String> createPosizioneCodiceRiferimentoCreditore(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroCodiceRiferimentoCreditore_QNAME, String.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Creditore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Creditore", scope = Posizione.class)
    public JAXBElement<Creditore> createPosizioneCreditore(Creditore value) {

	return new JAXBElement<Creditore>(_Creditore_QNAME, Creditore.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "DataScadenza", scope = Posizione.class)
    public JAXBElement<XMLGregorianCalendar> createPosizioneDataScadenza(XMLGregorianCalendar value) {

	return new JAXBElement<XMLGregorianCalendar>(_PosizioneArricchitaDataScadenza_QNAME, XMLGregorianCalendar.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Debitore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Debitore", scope = Posizione.class)
    public JAXBElement<Debitore> createPosizioneDebitore(Debitore value) {

	return new JAXBElement<Debitore>(_Debitore_QNAME, Debitore.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "IdentificativoPosizione", scope = Posizione.class)
    public JAXBElement<String> createPosizioneIdentificativoPosizione(String value) {

	return new JAXBElement<String>(_PosizioneArricchitaIdentificativoPosizione_QNAME, String.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "NumeroAvviso", scope = Posizione.class)
    public JAXBElement<String> createPosizioneNumeroAvviso(String value) {

	return new JAXBElement<String>(_PosizioneArricchitaNumeroAvviso_QNAME, String.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfParametroPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "ParametriPosizione", scope = Posizione.class)
    public JAXBElement<ArrayOfParametroPosizione> createPosizioneParametriPosizione(ArrayOfParametroPosizione value) {

	return new JAXBElement<ArrayOfParametroPosizione>(_PosizioneArricchitaParametriPosizione_QNAME, ArrayOfParametroPosizione.class,
		Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Servizio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Servizio", scope = Posizione.class)
    public JAXBElement<Servizio> createPosizioneServizio(Servizio value) {

	return new JAXBElement<Servizio>(_PosizioneArricchitaServizio_QNAME, Servizio.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "TipoRiferimentoCreditore", scope = Posizione.class)
    public JAXBElement<String> createPosizioneTipoRiferimentoCreditore(String value) {

	return new JAXBElement<String>(_PagamentoGiornalieroTipoRiferimentoCreditore_QNAME, String.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "Posizioni", scope = RispostaRicercaPosizioniPerCodiceFiscalePartitaIva.class)
    public JAXBElement<ArrayOfPosizione> createRispostaRicercaPosizioniPerCodiceFiscalePartitaIvaPosizioni(ArrayOfPosizione value) {

	return new JAXBElement<ArrayOfPosizione>(_RispostaRicercaPosizioniPerCodiceFiscalePartitaIvaPosizioni_QNAME, ArrayOfPosizione.class,
		RispostaRicercaPosizioniPerCodiceFiscalePartitaIva.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "OraFine", scope = RichiestaRicercaPagamentiGiornalieri.class)
    public JAXBElement<Integer> createRichiestaRicercaPagamentiGiornalieriOraFine(Integer value) {

	return new JAXBElement<Integer>(_RichiestaRicercaPagamentiGiornalieriOraFine_QNAME, Integer.class, RichiestaRicercaPagamentiGiornalieri.class,
		value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "OraInizio", scope = RichiestaRicercaPagamentiGiornalieri.class)
    public JAXBElement<Integer> createRichiestaRicercaPagamentiGiornalieriOraInizio(Integer value) {

	return new JAXBElement<Integer>(_RichiestaRicercaPagamentiGiornalieriOraInizio_QNAME, Integer.class,
		RichiestaRicercaPagamentiGiornalieri.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "IdentificativoFlusso", scope = PosizioneArricchita.class)
    public JAXBElement<String> createPosizioneArricchitaIdentificativoFlusso(String value) {

	return new JAXBElement<String>(_PosizioneIdentificativoFlusso_QNAME, String.class, PosizioneArricchita.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", name = "IdentificativoFlusso", scope = Posizione.class)
    public JAXBElement<String> createPosizioneIdentificativoFlusso(String value) {

	return new JAXBElement<String>(_PosizioneIdentificativoFlusso_QNAME, String.class, Posizione.class, value);
    }
}
