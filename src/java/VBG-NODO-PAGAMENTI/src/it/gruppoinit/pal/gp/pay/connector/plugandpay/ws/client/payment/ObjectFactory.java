
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RichiestaInviaCarrelloPosizioni_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "RichiestaInviaCarrelloPosizioni");
    private final static QName _PaymentAuthenticatedRequestBase_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "PaymentAuthenticatedRequestBase");
    private final static QName _RichiestaDownloadDatiRicevuta_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "RichiestaDownloadDatiRicevuta");
    private final static QName _ArrayOfPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "ArrayOfPosizione");
    private final static QName _Posizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Posizione");
    private final static QName _ArrayOfAccertamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "ArrayOfAccertamento");
    private final static QName _Accertamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Accertamento");
    private final static QName _Creditore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Creditore");
    private final static QName _Debitore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Debitore");
    private final static QName _Nazione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Nazione");
    private final static QName _ArrayOfParametroPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "ArrayOfParametroPosizione");
    private final static QName _ParametroPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "ParametroPosizione");
    private final static QName _RispostaInviaCarrelloPosizioni_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "RispostaInviaCarrelloPosizioni");
    private final static QName _ArrayOfEsitoInvioCarrelloPosizioni_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "ArrayOfEsitoInvioCarrelloPosizioni");
    private final static QName _EsitoInvioCarrelloPosizioni_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "EsitoInvioCarrelloPosizioni");
    private final static QName _ArrayOfErroreDiValidazionePosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "ArrayOfErroreDiValidazionePosizione");
    private final static QName _ErroreDiValidazionePosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "ErroreDiValidazionePosizione");
    private final static QName _Esito_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Esito");
    private final static QName _RispostaDownloadDatiRicevuta_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "RispostaDownloadDatiRicevuta");
    private final static QName _RichiestaDownloadDatiOriginaliRicevuta_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "RichiestaDownloadDatiOriginaliRicevuta");
    private final static QName _RispostaDownloadDatiOriginaliRicevuta_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "RispostaDownloadDatiOriginaliRicevuta");
    private final static QName _InviaCarrelloPosizioniRequest_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "request");
    private final static QName _InviaCarrelloPosizioniResponseInviaCarrelloPosizioniResult_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "InviaCarrelloPosizioniResult");
    private final static QName _DownloadDatiRicevutaResponseDownloadDatiRicevutaResult_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "DownloadDatiRicevutaResult");
    private final static QName _DownloadDatiOriginaliRicevutaResponseDownloadDatiOriginaliRicevutaResult_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "DownloadDatiOriginaliRicevutaResult");
    private final static QName _RispostaDownloadDatiOriginaliRicevutaRicevuta_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Ricevuta");
    private final static QName _ErroreDiValidazionePosizioneCodice_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Codice");
    private final static QName _ErroreDiValidazionePosizioneDescrizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Descrizione");
    private final static QName _EsitoInvioCarrelloPosizioniCodiceRiferimentoCreditore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "CodiceRiferimentoCreditore");
    private final static QName _EsitoInvioCarrelloPosizioniErroriDiValidazione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "ErroriDiValidazione");
    private final static QName _EsitoInvioCarrelloPosizioniIdentificativoPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "IdentificativoPosizione");
    private final static QName _EsitoInvioCarrelloPosizioniTipoRiferimentoCreditore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "TipoRiferimentoCreditore");
    private final static QName _RispostaInviaCarrelloPosizioniCarrelloArricchito_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "CarrelloArricchito");
    private final static QName _RispostaInviaCarrelloPosizioniEsitiInvioCarrelloPosizioni_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "EsitiInvioCarrelloPosizioni");
    private final static QName _RispostaInviaCarrelloPosizioniUrlRedirect_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "UrlRedirect");
    private final static QName _NazioneCodiceIsoNazione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "CodiceIsoNazione");
    private final static QName _NazioneNomeNazione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "NomeNazione");
    private final static QName _DebitoreCellulare_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Cellulare");
    private final static QName _DebitoreCivico_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Civico");
    private final static QName _DebitoreCodiceAvviamentoPostale_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "CodiceAvviamentoPostale");
    private final static QName _DebitoreEmail_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Email");
    private final static QName _DebitoreIndirizzo_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Indirizzo");
    private final static QName _DebitoreLocalita_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Localita");
    private final static QName _DebitoreProvincia_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Provincia");
    private final static QName _CreditoreCodiceFiscalePartitaIva_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "CodiceFiscalePartitaIva");
    private final static QName _CreditoreIBAN_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "IBAN");
    private final static QName _PosizioneAccertamenti_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "Accertamenti");
    private final static QName _PosizioneDataScadenza_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "DataScadenza");
    private final static QName _PosizioneEmailInvioRicevuta_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayPayment", "EmailInvioRicevuta");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InviaCarrelloPosizioni }
     * 
     */
    public InviaCarrelloPosizioni createInviaCarrelloPosizioni() {
        return new InviaCarrelloPosizioni();
    }

    /**
     * Create an instance of {@link RichiestaInviaCarrelloPosizioni }
     * 
     */
    public RichiestaInviaCarrelloPosizioni createRichiestaInviaCarrelloPosizioni() {
        return new RichiestaInviaCarrelloPosizioni();
    }

    /**
     * Create an instance of {@link PaymentAuthenticatedRequestBase }
     * 
     */
    public PaymentAuthenticatedRequestBase createPaymentAuthenticatedRequestBase() {
        return new PaymentAuthenticatedRequestBase();
    }

    /**
     * Create an instance of {@link RichiestaDownloadDatiRicevuta }
     * 
     */
    public RichiestaDownloadDatiRicevuta createRichiestaDownloadDatiRicevuta() {
        return new RichiestaDownloadDatiRicevuta();
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
     * Create an instance of {@link ArrayOfAccertamento }
     * 
     */
    public ArrayOfAccertamento createArrayOfAccertamento() {
        return new ArrayOfAccertamento();
    }

    /**
     * Create an instance of {@link Accertamento }
     * 
     */
    public Accertamento createAccertamento() {
        return new Accertamento();
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
     * Create an instance of {@link Nazione }
     * 
     */
    public Nazione createNazione() {
        return new Nazione();
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
     * Create an instance of {@link InviaCarrelloPosizioniResponse }
     * 
     */
    public InviaCarrelloPosizioniResponse createInviaCarrelloPosizioniResponse() {
        return new InviaCarrelloPosizioniResponse();
    }

    /**
     * Create an instance of {@link RispostaInviaCarrelloPosizioni }
     * 
     */
    public RispostaInviaCarrelloPosizioni createRispostaInviaCarrelloPosizioni() {
        return new RispostaInviaCarrelloPosizioni();
    }

    /**
     * Create an instance of {@link ArrayOfEsitoInvioCarrelloPosizioni }
     * 
     */
    public ArrayOfEsitoInvioCarrelloPosizioni createArrayOfEsitoInvioCarrelloPosizioni() {
        return new ArrayOfEsitoInvioCarrelloPosizioni();
    }

    /**
     * Create an instance of {@link EsitoInvioCarrelloPosizioni }
     * 
     */
    public EsitoInvioCarrelloPosizioni createEsitoInvioCarrelloPosizioni() {
        return new EsitoInvioCarrelloPosizioni();
    }

    /**
     * Create an instance of {@link ArrayOfErroreDiValidazionePosizione }
     * 
     */
    public ArrayOfErroreDiValidazionePosizione createArrayOfErroreDiValidazionePosizione() {
        return new ArrayOfErroreDiValidazionePosizione();
    }

    /**
     * Create an instance of {@link ErroreDiValidazionePosizione }
     * 
     */
    public ErroreDiValidazionePosizione createErroreDiValidazionePosizione() {
        return new ErroreDiValidazionePosizione();
    }

    /**
     * Create an instance of {@link DownloadDatiRicevuta }
     * 
     */
    public DownloadDatiRicevuta createDownloadDatiRicevuta() {
        return new DownloadDatiRicevuta();
    }

    /**
     * Create an instance of {@link DownloadDatiRicevutaResponse }
     * 
     */
    public DownloadDatiRicevutaResponse createDownloadDatiRicevutaResponse() {
        return new DownloadDatiRicevutaResponse();
    }

    /**
     * Create an instance of {@link RispostaDownloadDatiRicevuta }
     * 
     */
    public RispostaDownloadDatiRicevuta createRispostaDownloadDatiRicevuta() {
        return new RispostaDownloadDatiRicevuta();
    }

    /**
     * Create an instance of {@link DownloadDatiOriginaliRicevuta }
     * 
     */
    public DownloadDatiOriginaliRicevuta createDownloadDatiOriginaliRicevuta() {
        return new DownloadDatiOriginaliRicevuta();
    }

    /**
     * Create an instance of {@link RichiestaDownloadDatiOriginaliRicevuta }
     * 
     */
    public RichiestaDownloadDatiOriginaliRicevuta createRichiestaDownloadDatiOriginaliRicevuta() {
        return new RichiestaDownloadDatiOriginaliRicevuta();
    }

    /**
     * Create an instance of {@link DownloadDatiOriginaliRicevutaResponse }
     * 
     */
    public DownloadDatiOriginaliRicevutaResponse createDownloadDatiOriginaliRicevutaResponse() {
        return new DownloadDatiOriginaliRicevutaResponse();
    }

    /**
     * Create an instance of {@link RispostaDownloadDatiOriginaliRicevuta }
     * 
     */
    public RispostaDownloadDatiOriginaliRicevuta createRispostaDownloadDatiOriginaliRicevuta() {
        return new RispostaDownloadDatiOriginaliRicevuta();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaInviaCarrelloPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "RichiestaInviaCarrelloPosizioni")
    public JAXBElement<RichiestaInviaCarrelloPosizioni> createRichiestaInviaCarrelloPosizioni(RichiestaInviaCarrelloPosizioni value) {
        return new JAXBElement<RichiestaInviaCarrelloPosizioni>(_RichiestaInviaCarrelloPosizioni_QNAME, RichiestaInviaCarrelloPosizioni.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaymentAuthenticatedRequestBase }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "PaymentAuthenticatedRequestBase")
    public JAXBElement<PaymentAuthenticatedRequestBase> createPaymentAuthenticatedRequestBase(PaymentAuthenticatedRequestBase value) {
        return new JAXBElement<PaymentAuthenticatedRequestBase>(_PaymentAuthenticatedRequestBase_QNAME, PaymentAuthenticatedRequestBase.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaDownloadDatiRicevuta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "RichiestaDownloadDatiRicevuta")
    public JAXBElement<RichiestaDownloadDatiRicevuta> createRichiestaDownloadDatiRicevuta(RichiestaDownloadDatiRicevuta value) {
        return new JAXBElement<RichiestaDownloadDatiRicevuta>(_RichiestaDownloadDatiRicevuta_QNAME, RichiestaDownloadDatiRicevuta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "ArrayOfPosizione")
    public JAXBElement<ArrayOfPosizione> createArrayOfPosizione(ArrayOfPosizione value) {
        return new JAXBElement<ArrayOfPosizione>(_ArrayOfPosizione_QNAME, ArrayOfPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Posizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Posizione")
    public JAXBElement<Posizione> createPosizione(Posizione value) {
        return new JAXBElement<Posizione>(_Posizione_QNAME, Posizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAccertamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "ArrayOfAccertamento")
    public JAXBElement<ArrayOfAccertamento> createArrayOfAccertamento(ArrayOfAccertamento value) {
        return new JAXBElement<ArrayOfAccertamento>(_ArrayOfAccertamento_QNAME, ArrayOfAccertamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Accertamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Accertamento")
    public JAXBElement<Accertamento> createAccertamento(Accertamento value) {
        return new JAXBElement<Accertamento>(_Accertamento_QNAME, Accertamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Creditore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Creditore")
    public JAXBElement<Creditore> createCreditore(Creditore value) {
        return new JAXBElement<Creditore>(_Creditore_QNAME, Creditore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Debitore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Debitore")
    public JAXBElement<Debitore> createDebitore(Debitore value) {
        return new JAXBElement<Debitore>(_Debitore_QNAME, Debitore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Nazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Nazione")
    public JAXBElement<Nazione> createNazione(Nazione value) {
        return new JAXBElement<Nazione>(_Nazione_QNAME, Nazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfParametroPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "ArrayOfParametroPosizione")
    public JAXBElement<ArrayOfParametroPosizione> createArrayOfParametroPosizione(ArrayOfParametroPosizione value) {
        return new JAXBElement<ArrayOfParametroPosizione>(_ArrayOfParametroPosizione_QNAME, ArrayOfParametroPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParametroPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "ParametroPosizione")
    public JAXBElement<ParametroPosizione> createParametroPosizione(ParametroPosizione value) {
        return new JAXBElement<ParametroPosizione>(_ParametroPosizione_QNAME, ParametroPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaInviaCarrelloPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "RispostaInviaCarrelloPosizioni")
    public JAXBElement<RispostaInviaCarrelloPosizioni> createRispostaInviaCarrelloPosizioni(RispostaInviaCarrelloPosizioni value) {
        return new JAXBElement<RispostaInviaCarrelloPosizioni>(_RispostaInviaCarrelloPosizioni_QNAME, RispostaInviaCarrelloPosizioni.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEsitoInvioCarrelloPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "ArrayOfEsitoInvioCarrelloPosizioni")
    public JAXBElement<ArrayOfEsitoInvioCarrelloPosizioni> createArrayOfEsitoInvioCarrelloPosizioni(ArrayOfEsitoInvioCarrelloPosizioni value) {
        return new JAXBElement<ArrayOfEsitoInvioCarrelloPosizioni>(_ArrayOfEsitoInvioCarrelloPosizioni_QNAME, ArrayOfEsitoInvioCarrelloPosizioni.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsitoInvioCarrelloPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "EsitoInvioCarrelloPosizioni")
    public JAXBElement<EsitoInvioCarrelloPosizioni> createEsitoInvioCarrelloPosizioni(EsitoInvioCarrelloPosizioni value) {
        return new JAXBElement<EsitoInvioCarrelloPosizioni>(_EsitoInvioCarrelloPosizioni_QNAME, EsitoInvioCarrelloPosizioni.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfErroreDiValidazionePosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "ArrayOfErroreDiValidazionePosizione")
    public JAXBElement<ArrayOfErroreDiValidazionePosizione> createArrayOfErroreDiValidazionePosizione(ArrayOfErroreDiValidazionePosizione value) {
        return new JAXBElement<ArrayOfErroreDiValidazionePosizione>(_ArrayOfErroreDiValidazionePosizione_QNAME, ArrayOfErroreDiValidazionePosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErroreDiValidazionePosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "ErroreDiValidazionePosizione")
    public JAXBElement<ErroreDiValidazionePosizione> createErroreDiValidazionePosizione(ErroreDiValidazionePosizione value) {
        return new JAXBElement<ErroreDiValidazionePosizione>(_ErroreDiValidazionePosizione_QNAME, ErroreDiValidazionePosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Esito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Esito")
    public JAXBElement<Esito> createEsito(Esito value) {
        return new JAXBElement<Esito>(_Esito_QNAME, Esito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaDownloadDatiRicevuta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "RispostaDownloadDatiRicevuta")
    public JAXBElement<RispostaDownloadDatiRicevuta> createRispostaDownloadDatiRicevuta(RispostaDownloadDatiRicevuta value) {
        return new JAXBElement<RispostaDownloadDatiRicevuta>(_RispostaDownloadDatiRicevuta_QNAME, RispostaDownloadDatiRicevuta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaDownloadDatiOriginaliRicevuta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "RichiestaDownloadDatiOriginaliRicevuta")
    public JAXBElement<RichiestaDownloadDatiOriginaliRicevuta> createRichiestaDownloadDatiOriginaliRicevuta(RichiestaDownloadDatiOriginaliRicevuta value) {
        return new JAXBElement<RichiestaDownloadDatiOriginaliRicevuta>(_RichiestaDownloadDatiOriginaliRicevuta_QNAME, RichiestaDownloadDatiOriginaliRicevuta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaDownloadDatiOriginaliRicevuta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "RispostaDownloadDatiOriginaliRicevuta")
    public JAXBElement<RispostaDownloadDatiOriginaliRicevuta> createRispostaDownloadDatiOriginaliRicevuta(RispostaDownloadDatiOriginaliRicevuta value) {
        return new JAXBElement<RispostaDownloadDatiOriginaliRicevuta>(_RispostaDownloadDatiOriginaliRicevuta_QNAME, RispostaDownloadDatiOriginaliRicevuta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaInviaCarrelloPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "request", scope = InviaCarrelloPosizioni.class)
    public JAXBElement<RichiestaInviaCarrelloPosizioni> createInviaCarrelloPosizioniRequest(RichiestaInviaCarrelloPosizioni value) {
        return new JAXBElement<RichiestaInviaCarrelloPosizioni>(_InviaCarrelloPosizioniRequest_QNAME, RichiestaInviaCarrelloPosizioni.class, InviaCarrelloPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaInviaCarrelloPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "InviaCarrelloPosizioniResult", scope = InviaCarrelloPosizioniResponse.class)
    public JAXBElement<RispostaInviaCarrelloPosizioni> createInviaCarrelloPosizioniResponseInviaCarrelloPosizioniResult(RispostaInviaCarrelloPosizioni value) {
        return new JAXBElement<RispostaInviaCarrelloPosizioni>(_InviaCarrelloPosizioniResponseInviaCarrelloPosizioniResult_QNAME, RispostaInviaCarrelloPosizioni.class, InviaCarrelloPosizioniResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaDownloadDatiRicevuta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "request", scope = DownloadDatiRicevuta.class)
    public JAXBElement<RichiestaDownloadDatiRicevuta> createDownloadDatiRicevutaRequest(RichiestaDownloadDatiRicevuta value) {
        return new JAXBElement<RichiestaDownloadDatiRicevuta>(_InviaCarrelloPosizioniRequest_QNAME, RichiestaDownloadDatiRicevuta.class, DownloadDatiRicevuta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaDownloadDatiRicevuta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "DownloadDatiRicevutaResult", scope = DownloadDatiRicevutaResponse.class)
    public JAXBElement<RispostaDownloadDatiRicevuta> createDownloadDatiRicevutaResponseDownloadDatiRicevutaResult(RispostaDownloadDatiRicevuta value) {
        return new JAXBElement<RispostaDownloadDatiRicevuta>(_DownloadDatiRicevutaResponseDownloadDatiRicevutaResult_QNAME, RispostaDownloadDatiRicevuta.class, DownloadDatiRicevutaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaDownloadDatiOriginaliRicevuta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "request", scope = DownloadDatiOriginaliRicevuta.class)
    public JAXBElement<RichiestaDownloadDatiOriginaliRicevuta> createDownloadDatiOriginaliRicevutaRequest(RichiestaDownloadDatiOriginaliRicevuta value) {
        return new JAXBElement<RichiestaDownloadDatiOriginaliRicevuta>(_InviaCarrelloPosizioniRequest_QNAME, RichiestaDownloadDatiOriginaliRicevuta.class, DownloadDatiOriginaliRicevuta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaDownloadDatiOriginaliRicevuta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "DownloadDatiOriginaliRicevutaResult", scope = DownloadDatiOriginaliRicevutaResponse.class)
    public JAXBElement<RispostaDownloadDatiOriginaliRicevuta> createDownloadDatiOriginaliRicevutaResponseDownloadDatiOriginaliRicevutaResult(RispostaDownloadDatiOriginaliRicevuta value) {
        return new JAXBElement<RispostaDownloadDatiOriginaliRicevuta>(_DownloadDatiOriginaliRicevutaResponseDownloadDatiOriginaliRicevutaResult_QNAME, RispostaDownloadDatiOriginaliRicevuta.class, DownloadDatiOriginaliRicevutaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Ricevuta", scope = RispostaDownloadDatiOriginaliRicevuta.class)
    public JAXBElement<String> createRispostaDownloadDatiOriginaliRicevutaRicevuta(String value) {
        return new JAXBElement<String>(_RispostaDownloadDatiOriginaliRicevutaRicevuta_QNAME, String.class, RispostaDownloadDatiOriginaliRicevuta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Ricevuta", scope = RispostaDownloadDatiRicevuta.class)
    public JAXBElement<String> createRispostaDownloadDatiRicevutaRicevuta(String value) {
        return new JAXBElement<String>(_RispostaDownloadDatiOriginaliRicevutaRicevuta_QNAME, String.class, RispostaDownloadDatiRicevuta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Codice", scope = ErroreDiValidazionePosizione.class)
    public JAXBElement<String> createErroreDiValidazionePosizioneCodice(String value) {
        return new JAXBElement<String>(_ErroreDiValidazionePosizioneCodice_QNAME, String.class, ErroreDiValidazionePosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Descrizione", scope = ErroreDiValidazionePosizione.class)
    public JAXBElement<String> createErroreDiValidazionePosizioneDescrizione(String value) {
        return new JAXBElement<String>(_ErroreDiValidazionePosizioneDescrizione_QNAME, String.class, ErroreDiValidazionePosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "CodiceRiferimentoCreditore", scope = EsitoInvioCarrelloPosizioni.class)
    public JAXBElement<String> createEsitoInvioCarrelloPosizioniCodiceRiferimentoCreditore(String value) {
        return new JAXBElement<String>(_EsitoInvioCarrelloPosizioniCodiceRiferimentoCreditore_QNAME, String.class, EsitoInvioCarrelloPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfErroreDiValidazionePosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "ErroriDiValidazione", scope = EsitoInvioCarrelloPosizioni.class)
    public JAXBElement<ArrayOfErroreDiValidazionePosizione> createEsitoInvioCarrelloPosizioniErroriDiValidazione(ArrayOfErroreDiValidazionePosizione value) {
        return new JAXBElement<ArrayOfErroreDiValidazionePosizione>(_EsitoInvioCarrelloPosizioniErroriDiValidazione_QNAME, ArrayOfErroreDiValidazionePosizione.class, EsitoInvioCarrelloPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "IdentificativoPosizione", scope = EsitoInvioCarrelloPosizioni.class)
    public JAXBElement<String> createEsitoInvioCarrelloPosizioniIdentificativoPosizione(String value) {
        return new JAXBElement<String>(_EsitoInvioCarrelloPosizioniIdentificativoPosizione_QNAME, String.class, EsitoInvioCarrelloPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "TipoRiferimentoCreditore", scope = EsitoInvioCarrelloPosizioni.class)
    public JAXBElement<String> createEsitoInvioCarrelloPosizioniTipoRiferimentoCreditore(String value) {
        return new JAXBElement<String>(_EsitoInvioCarrelloPosizioniTipoRiferimentoCreditore_QNAME, String.class, EsitoInvioCarrelloPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "CarrelloArricchito", scope = RispostaInviaCarrelloPosizioni.class)
    public JAXBElement<ArrayOfPosizione> createRispostaInviaCarrelloPosizioniCarrelloArricchito(ArrayOfPosizione value) {
        return new JAXBElement<ArrayOfPosizione>(_RispostaInviaCarrelloPosizioniCarrelloArricchito_QNAME, ArrayOfPosizione.class, RispostaInviaCarrelloPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEsitoInvioCarrelloPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "EsitiInvioCarrelloPosizioni", scope = RispostaInviaCarrelloPosizioni.class)
    public JAXBElement<ArrayOfEsitoInvioCarrelloPosizioni> createRispostaInviaCarrelloPosizioniEsitiInvioCarrelloPosizioni(ArrayOfEsitoInvioCarrelloPosizioni value) {
        return new JAXBElement<ArrayOfEsitoInvioCarrelloPosizioni>(_RispostaInviaCarrelloPosizioniEsitiInvioCarrelloPosizioni_QNAME, ArrayOfEsitoInvioCarrelloPosizioni.class, RispostaInviaCarrelloPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "UrlRedirect", scope = RispostaInviaCarrelloPosizioni.class)
    public JAXBElement<String> createRispostaInviaCarrelloPosizioniUrlRedirect(String value) {
        return new JAXBElement<String>(_RispostaInviaCarrelloPosizioniUrlRedirect_QNAME, String.class, RispostaInviaCarrelloPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "CodiceIsoNazione", scope = Nazione.class)
    public JAXBElement<String> createNazioneCodiceIsoNazione(String value) {
        return new JAXBElement<String>(_NazioneCodiceIsoNazione_QNAME, String.class, Nazione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "NomeNazione", scope = Nazione.class)
    public JAXBElement<String> createNazioneNomeNazione(String value) {
        return new JAXBElement<String>(_NazioneNomeNazione_QNAME, String.class, Nazione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Cellulare", scope = Debitore.class)
    public JAXBElement<String> createDebitoreCellulare(String value) {
        return new JAXBElement<String>(_DebitoreCellulare_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Civico", scope = Debitore.class)
    public JAXBElement<String> createDebitoreCivico(String value) {
        return new JAXBElement<String>(_DebitoreCivico_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "CodiceAvviamentoPostale", scope = Debitore.class)
    public JAXBElement<String> createDebitoreCodiceAvviamentoPostale(String value) {
        return new JAXBElement<String>(_DebitoreCodiceAvviamentoPostale_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Email", scope = Debitore.class)
    public JAXBElement<String> createDebitoreEmail(String value) {
        return new JAXBElement<String>(_DebitoreEmail_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Indirizzo", scope = Debitore.class)
    public JAXBElement<String> createDebitoreIndirizzo(String value) {
        return new JAXBElement<String>(_DebitoreIndirizzo_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Localita", scope = Debitore.class)
    public JAXBElement<String> createDebitoreLocalita(String value) {
        return new JAXBElement<String>(_DebitoreLocalita_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Nazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Nazione", scope = Debitore.class)
    public JAXBElement<Nazione> createDebitoreNazione(Nazione value) {
        return new JAXBElement<Nazione>(_Nazione_QNAME, Nazione.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Provincia", scope = Debitore.class)
    public JAXBElement<String> createDebitoreProvincia(String value) {
        return new JAXBElement<String>(_DebitoreProvincia_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "CodiceFiscalePartitaIva", scope = Creditore.class)
    public JAXBElement<String> createCreditoreCodiceFiscalePartitaIva(String value) {
        return new JAXBElement<String>(_CreditoreCodiceFiscalePartitaIva_QNAME, String.class, Creditore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "IBAN", scope = Creditore.class)
    public JAXBElement<String> createCreditoreIBAN(String value) {
        return new JAXBElement<String>(_CreditoreIBAN_QNAME, String.class, Creditore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAccertamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "Accertamenti", scope = Posizione.class)
    public JAXBElement<ArrayOfAccertamento> createPosizioneAccertamenti(ArrayOfAccertamento value) {
        return new JAXBElement<ArrayOfAccertamento>(_PosizioneAccertamenti_QNAME, ArrayOfAccertamento.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "CodiceRiferimentoCreditore", scope = Posizione.class)
    public JAXBElement<String> createPosizioneCodiceRiferimentoCreditore(String value) {
        return new JAXBElement<String>(_EsitoInvioCarrelloPosizioniCodiceRiferimentoCreditore_QNAME, String.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "DataScadenza", scope = Posizione.class)
    public JAXBElement<XMLGregorianCalendar> createPosizioneDataScadenza(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PosizioneDataScadenza_QNAME, XMLGregorianCalendar.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "EmailInvioRicevuta", scope = Posizione.class)
    public JAXBElement<String> createPosizioneEmailInvioRicevuta(String value) {
        return new JAXBElement<String>(_PosizioneEmailInvioRicevuta_QNAME, String.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "IdentificativoPosizione", scope = Posizione.class)
    public JAXBElement<String> createPosizioneIdentificativoPosizione(String value) {
        return new JAXBElement<String>(_EsitoInvioCarrelloPosizioniIdentificativoPosizione_QNAME, String.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayPayment", name = "TipoRiferimentoCreditore", scope = Posizione.class)
    public JAXBElement<String> createPosizioneTipoRiferimentoCreditore(String value) {
        return new JAXBElement<String>(_EsitoInvioCarrelloPosizioniTipoRiferimentoCreditore_QNAME, String.class, Posizione.class, value);
    }

}
